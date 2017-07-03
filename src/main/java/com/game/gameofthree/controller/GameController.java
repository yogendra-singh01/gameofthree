package com.game.gameofthree.controller;

import com.game.gameofthree.entity.Player;

public interface GameController {

	/**
	 * 
	 * @param player
	 * @return
	 */
	String status(Player player);
	
	/**
	 * 
	 * @param playerName
	 * @return
	 */
	String start(String playerName);
	
	/**
	 * 
	 * @param playerName
	 * @return
	 */
	String stop(String playerName);
}
