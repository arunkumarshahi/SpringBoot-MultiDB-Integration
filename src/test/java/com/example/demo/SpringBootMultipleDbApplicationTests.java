package com.example.demo;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.orders.entities.Order;
import com.example.demo.orders.repositories.OrderRepository;
import com.example.demo.security.entities.User;
import com.example.demo.security.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMultipleDbApplicationTests {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderRepository orderRepository;

	@Test
	public void findAllUsers() {
		List<User> users = (List<User>) userRepository.findAll();
		assertNotNull(users);
		for(User u:users) {
		System.out.println("user by id ==="+u);
		}
		//assertTrue(!users.isPresent());
	}

	@Test
	public void CreateOrder() {
		Order order=new Order();
		order.setCustomerEmail("@vodafine");
		order.setCustomerName("arun vf	 ");
		Order neworders = orderRepository.save(order);
		assertNotNull(neworders);
		System.out.println("neworders = "+neworders);
		
	}
	
	@Test
	public void CreateUser() {
		User user=new User();
		user.setName("ak");
		user.setEmail("arunkr@gmail.com");
		User newUser = userRepository.save(user);
		assertNotNull(newUser);
		System.out.println("newUser.... = "+newUser);
		//assertTrue(!orders.isEmpty());
	}
	
	@Test
	public void findAllOrders() {
		List<Order> orders = orderRepository.findAll();
		assertNotNull(orders);
		System.out.println("orders = "+orders);
		assertTrue(!orders.isEmpty());
	}
}