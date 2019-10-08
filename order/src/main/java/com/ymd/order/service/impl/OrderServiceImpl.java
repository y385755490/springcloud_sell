package com.ymd.order.service.impl;

import com.ymd.client.ProductClient;
import com.ymd.common.DecreaseStockInput;
import com.ymd.common.ProductInfoOutput;
import com.ymd.order.dataobject.OrderDetail;
import com.ymd.order.dataobject.OrderMaster;
import com.ymd.order.dto.CartDTO;
import com.ymd.order.dto.OrderDTO;
import com.ymd.order.enums.OrderstatusEnum;
import com.ymd.order.enums.PayStatusEnum;
import com.ymd.order.enums.ResultEnum;
import com.ymd.order.exception.OrderException;
import com.ymd.order.repository.OrderDetailRepository;
import com.ymd.order.repository.OrderMasterRepository;
import com.ymd.order.service.OrderService;
import com.ymd.order.utils.KeyUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMasterRepository orderMasterRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductClient productClient;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.uuid();
        //查询商品信息（调用商品服务）
        List<String> productIdlist = orderDTO.getOrderDetailList().stream().map(OrderDetail::getProductId).
                collect(Collectors.toList());
        List<ProductInfoOutput> productInfoOutputs = productClient.listForOrder(productIdlist);

        //计算总价
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            for (ProductInfoOutput productInfo : productInfoOutputs) {
                if (StringUtils.equals(productInfo.getProductId(), orderDetail.getProductId())) {
                    //单价*数量
                    orderAmount = productInfo.getProductPrice().multiply(orderDetail.getProductQuantity()).
                            add(orderAmount);
                    BeanUtils.copyProperties(productInfo, orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.uuid());
                    //订单详情入库
                    orderDetailRepository.save(orderDetail);
                }
            }
        }

        //扣库存（调用商品服务）
        List<DecreaseStockInput> cartDTOS = new ArrayList<>();
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            DecreaseStockInput cartDTO = new DecreaseStockInput();
            cartDTO.setProductId(orderDetail.getProductId());
            cartDTO.setProducQuantity(orderDetail.getProductQuantity().intValue());
            cartDTOS.add(cartDTO);
        }
        productClient.decreaseStock(cartDTOS);

        //订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(KeyUtil.uuid());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(new BigDecimal(5));
        orderMaster.setOrderStatus(OrderstatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(String orderId) {
        //1.先查询订单
        Optional<OrderMaster> orderMasterOptional = orderMasterRepository.findById(orderId);
        if (!orderMasterOptional.isPresent()){
            throw new OrderException(ResultEnum.ORDER_NOT_EXIST);
        }
        //2.判断订单状态
        OrderMaster orderMaster = orderMasterOptional.get();
        if (OrderstatusEnum.NEW.getCode() != orderMaster.getOrderStatus()){
            throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //3.修改订单状态为完结
        orderMaster.setOrderStatus(OrderstatusEnum.FINISHED.getCode());
        orderMasterRepository.save(orderMaster);
        //查询订单详情
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)){
            throw new OrderException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
