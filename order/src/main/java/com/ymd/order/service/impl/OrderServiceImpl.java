package com.ymd.order.service.impl;

import com.ymd.order.dataobject.OrderDetail;
import com.ymd.order.dataobject.OrderMaster;
import com.ymd.order.dto.OrderDTO;
import com.ymd.order.enums.OrderstatusEnum;
import com.ymd.order.enums.PayStatusEnum;
import com.ymd.order.repository.OrderDetailRepository;
import com.ymd.order.repository.OrderMasterRepository;
import com.ymd.order.service.OrderService;
import com.ymd.order.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMasterRepository orderMasterRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
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
