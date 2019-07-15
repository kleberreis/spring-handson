package br.com.eng.handson2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import br.com.eng.handson2.model.OrderError;
import br.com.eng.handson2.service.OrderService;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

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
	public ResponseEntity<Object> createOrder(@RequestBody Order order) throws OrderAlreadyExistsException {
		Order orderSaved = service.save(order);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(linkTo(getClass()).slash(orderSaved.getId()).toUri());
		
		return new ResponseEntity<Object>(null, headers, HttpStatus.CREATED);
	}
	
	
    @ExceptionHandler({OrderAlreadyExistsException.class})
    ResponseEntity<OrderError> handleInternalServerError(OrderAlreadyExistsException e) {
    	OrderError error = new OrderError(HttpStatus.CONFLICT.value(), e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler({OrderNotFoundException.class})
    ResponseEntity<OrderError> handleInternalServerError(OrderNotFoundException e) {
    	OrderError error = new OrderError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<OrderError>(error, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler({Exception.class})
    ResponseEntity<OrderError> handleInternalServerError(Exception e) {
    	OrderError error = new OrderError(HttpStatus.BAD_REQUEST.value(), "Error Interno");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
