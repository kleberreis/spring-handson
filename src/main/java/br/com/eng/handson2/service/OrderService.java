package br.com.eng.handson2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eng.handson2.exception.OrderAlreadyExistsException;
import br.com.eng.handson2.exception.OrderNotFoundException;
import br.com.eng.handson2.model.Order;
import br.com.eng.handson2.repository.OrderRepository;

@Service
public class OrderService {

	private OrderRepository repository;

	@Autowired
	public OrderService(OrderRepository repository) {
		super();
		this.repository = repository;
	}

	public List<Order> findAll() {
		return repository.findAll();
	}

	public void save(Order order) throws OrderAlreadyExistsException {
		
		Order orderRetrievy = repository.minhaBuscaPorTid(order.getTid());

		if(orderRetrievy != null)
			throw new OrderAlreadyExistsException("Já existe uma order para essa TID!");
		
		repository.save(order);
	}

	public Order getOne(Integer id) throws Exception {
		Optional<Order> order = repository.findById(id);
		
		if(!order.isPresent())
			throw new OrderNotFoundException("Order not found");
			
		return order.get();
	}
	
	
	
}
