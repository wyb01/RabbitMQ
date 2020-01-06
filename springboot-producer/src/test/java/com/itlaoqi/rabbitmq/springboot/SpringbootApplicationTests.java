package com.itlaoqi.rabbitmq.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {

	@Resource
	MessageProducer messageProducer;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testSendMsg(){
		messageProducer.sendMsg(new Employee("3306" , "老齐" , 18));
	}
}
