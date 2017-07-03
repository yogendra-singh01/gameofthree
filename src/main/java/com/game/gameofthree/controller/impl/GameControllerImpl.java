package com.game.gameofthree.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.game.gameofthree.businesshelper.BusinessHelper;
import com.game.gameofthree.controller.GameController;
import com.game.gameofthree.entity.Player;

@Controller
public class GameControllerImpl implements GameController{

	@Autowired
	public BusinessHelper businessHelper;

	@MessageMapping("/status")
	@SendTo("/game/result")
	public String status(Player player){
		return businessHelper.getStatus(player);
	}

	@MessageMapping("/start")
	@SendTo("/game/result")
	public String start(String playerName) {
		return businessHelper.start(playerName);
	}
	
	@MessageMapping("/stop")
	@SendTo("/game/result")
	public String stop(String playerName){
		return businessHelper.stop(playerName);
	}
}
