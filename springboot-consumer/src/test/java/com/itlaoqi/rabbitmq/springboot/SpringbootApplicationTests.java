package com.itlaoqi.rabbitmq.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 在学习过程中遇到任何问题可以加我的QQ群722570599(老齐的IT指导群)
 在这里老齐将给你直接提供帮助与解答，只为你可以学的更轻松。
 */
@RunWith(SpringRunner.class)
@SpringBootTest
/*@@请加Q群：369531466,与几百名工程师共同学习,遇到难题可随时@老齐,多一点真诚，少一点套路@@*/
public class SpringbootApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void aaa(){
		int a = 73;
		int b = 8;
		BigDecimal df1=new BigDecimal(a);
		BigDecimal df2=new BigDecimal(b);
		System.out.println("AAA" + df1.divide(df2,2,BigDecimal.ROUND_HALF_UP));
		TestA t = new TestA();
		t.setAge(df1.divide(df2,BigDecimal.ROUND_HALF_UP));
		System.out.println("BBB" + t.getAge());
	//	System.out.println("BBB:" + BigDecimal.valueOf(a/3));
	}

}
