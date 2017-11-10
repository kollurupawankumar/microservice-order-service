package com.pawan.order.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import com.pawan.order.model.Order;


@Repository
public class OrderDAOImpl implements OrderDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderDAOImpl.class);

	@Autowired
	protected MongoOperations mongoOperations;

	@Override
	public Order getById(Object id) throws DataAccessException {
		if (logger.isDebugEnabled())
			logger.debug("type {} getById", id);
		try {
			return mongoOperations.findById(id, Order.class);
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public List<Order> getAll() throws DataAccessException {
		if (logger.isDebugEnabled())
			logger.debug("Product getAll function is getting called");
		try {
			System.out.println("Pawan :"+mongoOperations.findAll(Order.class).size());
			return mongoOperations.findAll(Order.class);
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public Order add(Order object) throws DataAccessException {
		if (logger.isDebugEnabled())
			logger.debug("Add function for the object " + object.toString());
		try {
			mongoOperations.insert(object);
			return object;
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}

}
