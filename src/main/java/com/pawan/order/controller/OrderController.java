package com.pawan.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.pawan.order.model.Order;
import com.pawan.order.model.Product;
import com.pawan.order.service.OrderService;


@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping
    public List<Order> getAll() {
		return orderService.getAll();
    }
	
	
	@RequestMapping("/{id}")
    public Order getById(@PathVariable("id") String id) {
		return orderService.getById(id);
    }
	
	@RequestMapping(path="/{date}/{pid}",  method = RequestMethod.GET)
    public Order add(@PathVariable("date") String date, @PathVariable("pid") String pid) {
		Order order = new Order();
		//call the product service by id and store in the order object
		Product product = restTemplate.getForObject("http://pawan-product-service/products/{id}", Product.class, pid);
		order.setProduct(product);
		order.setDateOfOrder(date);
    	return orderService.add(order);
    }
    
  
    
}
