package com.pawan.order.dao;

import java.util.List;

import com.pawan.order.model.Order;

public interface OrderDAO {
	
	Order getById(Object id) throws DataAccessException;
	   
    List<Order> getAll() throws DataAccessException;
    
    Order add(Order object) throws DataAccessException;
	

}
