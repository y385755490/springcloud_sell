package com.ymd.order.service.impl;

import com.ymd.order.client.ProductClient;
import com.ymd.order.dataobject.OrderDetail;
import com.ymd.order.dataobject.OrderMaster;
import com.ymd.order.dataobject.ProductInfo;
import com.ymd.order.dto.CartDTO;
import com.ymd.order.dto.OrderDTO;
import com.ymd.order.enums.OrderstatusEnum;
import com.ymd.order.enums.PayStatusEnum;
import com.ymd.order.repository.OrderDetailRepository;
import com.ymd.order.repository.OrderMasterRepository;
import com.ymd.order.service.OrderService;
import com.ymd.order.utils.KeyUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.uuid();
        //查询商品信息（调用商品服务）
        List<String> productIdlist = orderDTO.getOrderDetailList().stream().map(OrderDetail::getProductId).
                collect(Collectors.toList());
        List<ProductInfo> productInfos = productClient.listForOrder(productIdlist);

        //计算总价
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            for (ProductInfo productInfo : productInfos){
                if (StringUtils.equals(productInfo.getProductId(),orderDetail.getProductId())){
                    //单价*数量
                    orderAmount = productInfo.getProductPrice().multiply(orderDetail.getProductQuantity()).
                            add(orderAmount);
                    BeanUtils.copyProperties(productInfo,orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.uuid());
                    //订单详情入库
                    orderDetailRepository.save(orderDetail);
                }
            }
        }

        //扣库存（调用商品服务）
        List<CartDTO> cartDTOS = new ArrayList<>();
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            CartDTO cartDTO = new CartDTO();
            cartDTO.setProductId(orderDetail.getProductId());
            cartDTO.setProducQuantity(orderDetail.getProductQuantity().intValue());
            cartDTOS.add(cartDTO);
        }
        productClient.decreaseStock(cartDTOS);

        //订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(KeyUtil.uuid());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(new BigDecimal(5));
        orderMaster.setOrderStatus(OrderstatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }
}
