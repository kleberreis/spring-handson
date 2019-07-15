package br.com.eng.handson2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eng.handson2.exception.OrderAlreadyExistsException;
import br.com.eng.handson2.exception.OrderNotFoundException;
import br.com.eng.handson2.model.Order;
import br.com.eng.handson2.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	private OrderService service;
	
	@Autowired
	public OrderController(OrderService service) {
		super();
		this.service = service;
	}

	@GetMapping("/teste")
	public List<Order> teste() {
		Order order = new Order();
		order.setId(1);
		order.setPayload("teste");
		
		return java.util.Arrays.asList(order);
	}
	
	@RequestMapping("/teste2")
	public List<Order> teste2() {
		return service.findAll();
	}
	
	@GetMapping
	public List<Order> getAll() {
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public Order getOne(@PathVariable("id") Integer id) throws Exception {
		return service.getOne(id);
	}
	
	@PostMapping
	public void createOrder(@RequestBody Order order) throws OrderAlreadyExistsException {
		service.save(order);
	}
	
	
    @ExceptionHandler({OrderAlreadyExistsException.class})
    ResponseEntity handleInternalServerError(OrderAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler({OrderNotFoundException.class})
    ResponseEntity handleInternalServerError(OrderNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler({Exception.class})
    ResponseEntity handleInternalServerError(Exception e) {
        return new ResponseEntity<>("Error Interno", HttpStatus.BAD_REQUEST);
    }
}
