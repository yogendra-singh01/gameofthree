package com.game.gameofthree.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.AbstractSubscribableChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.game.gameofthree.config.WebSocketConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebSocketConfig.class})
public class AppTest {
	
	@Autowired 
	private AbstractSubscribableChannel clientInboundChannel;

	@Autowired 
	private AbstractSubscribableChannel clientOutboundChannel;

	
	
	@Before
	public void setUp() throws Exception {

		
	}

}
