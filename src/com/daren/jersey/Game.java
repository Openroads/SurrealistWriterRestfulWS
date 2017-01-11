package com.daren.jersey;


public class Game {
	private int gameID;
	private String lastTwoWords;
	private int roundAmount;
	private int currentRound;
	private int max_word;
	private int status;
	
	public int getGameID() {
		return gameID;
	}
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
	public String getLastTwoWords() {
		return lastTwoWords;
	}
	public void setLastTwoWords(String lastTwoWords) {
		this.lastTwoWords = lastTwoWords;
	}
	public int getRoundAmount() {
		return roundAmount;
	}
	public void setRoundAmount(int roundAmount) {
		this.roundAmount = roundAmount;
	}
	public int getCurrentRound() {
		return currentRound;
	}
	public void setCurrentRound(int currentRound) {
		this.currentRound = currentRound;
	}
	public int getMax_word() {
		return max_word;
	}
	public void setMax_word(int max_word) {
		this.max_word = max_word;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}