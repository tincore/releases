package com.tincore.gsp.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { GspResourceApplication.class, TestConfiguration.class })
@ActiveProfiles("test")
public class GspResourceApplicationTests {

	@Test
	public void contextLoads() {
	}

}
