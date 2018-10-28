package com.imooc.sell;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SellApplicationTests {


	@Test
	public void contextLoads() {
		String username = "cocoslee";
		log.debug("11111111");
		log.info("22222222");
		log.warn("333333333");
		log.error("44444444{}",username);
	}





}
