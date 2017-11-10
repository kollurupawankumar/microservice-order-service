package com.pawan.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pawan.order.dao.DataAccessException;
import com.pawan.order.dao.OrderDAO;
import com.pawan.order.model.Order;

@Service
public class OrderServiceImpl implements OrderService {
	

	@Autowired
	private OrderDAO orderDao;
	
	@Override
	public Order getById(String id) {
		try {
			return orderDao.getById(id);
		} catch (DataAccessException de) {
			return null;
		}
	}

	@Override
	public Order add(Order obj) {
		try {
			return orderDao.add(obj);
		} catch (DataAccessException de) {
			return null;
		}
	}
	
	@Override
	public List<Order> getAll() {
		try {
			return orderDao.getAll();
		} catch (DataAccessException de) {
			return null;
		}
	}

}
