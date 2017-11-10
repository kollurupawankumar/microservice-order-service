package com.pawan.order.service;

import java.util.List;

import com.pawan.order.model.Order;

public interface OrderService {
	
	Order getById(String id);

	Order add(Order obj);

	List<Order> getAll();

}
