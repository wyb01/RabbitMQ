package com.itlaoqi.rabbitmq.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
/**
 在学习过程中遇到任何问题可以加我的QQ群722570599(老齐的IT指导群)
 在这里老齐将给你直接提供帮助与解答，只为你可以学的更轻松。
 */
@RunWith(SpringRunner.class)
@SpringBootTest
/*@@请加Q群：369531466,与几百名工程师共同学习,遇到难题可随时@老齐,多一点真诚，少一点套路@@*/public class SpringbootApplicationTests {
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
