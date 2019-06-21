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
		Optional<User> users = userRepository.findById(1);
		assertNotNull(users);
		System.out.println("user by id ==="+users);
		assertTrue(!users.isPresent());
	}

	@Test
	public void CreateOrder() {
		Order order=new Order();
		order.setCustomerEmail("akm");
		order.setCustomerName("arun km ");
		Order neworders = orderRepository.save(order);
		//assertNotNull(orders);
		System.out.println("neworders = "+neworders);
		//assertTrue(!orders.isEmpty());
	}
	
	@Test
	public void CreateUser() {
		User user=new User();
		user.setName("ak");
		user.setEmail("arun k");
		User newUser = userRepository.save(user);
		//assertNotNull(orders);
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