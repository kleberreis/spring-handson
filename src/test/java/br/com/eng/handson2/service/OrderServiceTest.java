package br.com.eng.handson2.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.eng.handson2.exception.OrderNotFoundException;
import br.com.eng.handson2.model.Order;
import br.com.eng.handson2.repository.OrderRepository;

@RunWith(SpringRunner.class)
public class OrderServiceTest {

	@MockBean
	private OrderRepository repository;
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void orderNotFound() throws Exception {
		OrderService service = new OrderService(repository);
		
		Order order = new Order();
		order.setId(300);
		
		given(this.repository.findById(order.getId())).willReturn(Optional.empty());
		
		expectedEx.expect(OrderNotFoundException.class);
	    expectedEx.expectMessage("Order not found");
	    
		service.getOne(order.getId());
	}
	
	@Test(expected=OrderNotFoundException.class)
	public void orderNotFound2() throws Exception {
		OrderService service = new OrderService(repository);
		
		Order order = new Order();
		order.setId(300);
		
		given(this.repository.findById(order.getId())).willReturn(Optional.empty());
	    
		service.getOne(order.getId());
	}
	
	@Test
	public void orderFound() throws Exception {
		OrderService service = new OrderService(repository);
		
		Order order = new Order();
		order.setId(300);
		order.setPayload("{\"payload\":\"\"}");
		order.setTid("1234");
		
		given(this.repository.findById(order.getId())).willReturn(Optional.of(order));
			    
		Order returnedOrder = service.getOne(order.getId());
		
		assertEquals(order.getId(), returnedOrder.getId());
		assertEquals(order.getPayload(), returnedOrder.getPayload());
		assertEquals(order.getTid(), returnedOrder.getTid());
	}
}
