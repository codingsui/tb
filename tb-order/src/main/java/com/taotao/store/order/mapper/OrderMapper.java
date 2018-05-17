package com.taotao.store.order.mapper;

import java.util.Date;
import java.util.List;


import com.taotao.store.order.pojo.OrderItem;
import com.taotao.store.order.pojo.OrderShipping;
import org.apache.ibatis.annotations.Param;

import com.taotao.store.order.pojo.Order;

public interface OrderMapper extends IMapper<Order>{
	
	public void paymentOrderScan(@Param("date") Date date);


	OrderShipping queryOrderShippingByOrderId(String orderId);



	OrderItem queryOrderItemByOrderId(String orderId);
}
