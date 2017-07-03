package com.game.gameofthree.businesshelper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.game.gameofthree.entity.Player;

@Component
public class BusinessHelper {

	private static Map<String, Integer> playerMap = new HashMap<String, Integer>();

	/**
	 * Method to perform the business logic for the numbers entered
	 * @param player
	 * @return
	 */
	public String getStatus(Player player) {
		String playerName = player.getName();
		//Check the player has just started the game and not yet provided the number
		if(playerMap.get(playerName) == null){
			playerMap.put(player.getName(), player.getValue());
			return player.getName() + ": Entered value " + player.getValue();
		}
		else{
			int previousValue = playerMap.get(playerName);
			int newValue = player.getValue();
			int difference = previousValue - newValue;
			//Check if the difference between the previous number and the new number is either -1 or 0 or 1
			if (difference <-1 || difference > 1) {
				return player.getName() + ": The difference between number should be from -1,0,1 from previous number "+previousValue;
			}
			//Check if the number is divisible by 3
			else if (newValue % 3 != 0) {
				return player.getName() + ": The number should be divisible by 3";
			} else {
				//If the quotient is 1 , then the player is winner
				if (newValue / 3 == 1) {
					return player.getName() + " is winner !";
				} else {
					//Update the old value with the new value in Map
					playerMap.put(player.getName(), player.getValue());
					if(playerMap.size() >1){
						return player.getName() + ": Entered value " + newValue + ", Player 2 your turn!";
					}
					else{
						return player.getName() + ": Entered value " + newValue;
					}
					
				}
			}	
		}
		
	}
	
	/**
	 * Method to restrict player numbers to two and to store playernames in memory
	 * @param playerName
	 * @return
	 */
	public String start(String playerName) {
		String msg = "";
		if (playerMap.size() < 2) {
			if (playerMap.size() == 0) {
				playerMap.put(playerName, null);
				msg = "Welcome " + playerName + ". Please provide a number !";
			} else if (!playerMap.containsKey(playerName)) {
				playerMap.put(playerName, null);
				msg = "Welcome " + playerName + " Game is on !";
			}
		} else {
			msg = "Only two players can play at a time";
		}
		return msg;
	}

	/**
	 * 
	 * @param playerName
	 * @return
	 */
	public String stop(String playerName) {
		String msg = "";
		if (playerMap.size() > 0 && playerMap.containsKey(playerName)) {
			playerMap.remove(playerName);
			msg = playerName + " left";
		}
		return msg;
	}
}
