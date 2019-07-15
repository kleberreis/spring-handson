package br.com.eng.handson2.service;

import static org.mockito.BDDMockito.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.eng.handson2.exception.OrderNotFoundException;
import br.com.eng.handson2.model.Order;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OrderControllerTest {

	@MockBean
	private OrderService service;
	
    @Autowired
    private MockMvc mockMvc;
	
	@Test
	public void findByIdWhenOrderNotFound() throws Exception {		
		Order order = new Order();
		order.setId(300);
		String orderNotFound = "Order not found";
		
		given(this.service.getOne(order.getId())).willThrow(new OrderNotFoundException(orderNotFound));
		
        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/order/%d", order.getId())))
        	.andExpect(MockMvcResultMatchers.status().isNotFound())
        	.andExpect(MockMvcResultMatchers.jsonPath("errorMessage").value(orderNotFound))
        	.andExpect(MockMvcResultMatchers.jsonPath("errorCode").value(HttpStatus.NOT_FOUND.value()));
	}
	
	@Test
	public void findById() throws Exception {		
		Order order = new Order();
		order.setId(300);
		order.setPayload("<payload></payload>");
		order.setTid("123");
		
		given(this.service.getOne(order.getId())).willReturn(order);
		
        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/order/%d", order.getId())))
        	.andExpect(MockMvcResultMatchers.status().isOk())
        	.andExpect(MockMvcResultMatchers.jsonPath("tid").value("123"))
        	.andExpect(MockMvcResultMatchers.jsonPath("payload").value("<payload></payload>"));
	}
}
