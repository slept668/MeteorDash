package io.github.MeteorDash;

public class GameSettings {
	private static GameSettings instance;
	private String difficulty;
	private int diffNum;
	
	public GameSettings() {
		difficulty = "easy";
		diffNum = 1;
	}
	
	public static GameSettings getInstance() {
		if (instance == null) {
			instance = new GameSettings();
		}
		return instance;
	}
	
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	
	public void diffRight() {
		diffNum++;
		if (diffNum > 3) {
			diffNum = 1;
			setDifficulty("easy");
		}
		else if (diffNum == 2) {
			setDifficulty("medium");
		}
		else if (diffNum == 3) {
			setDifficulty("hard");
		}
	}
	
	public void diffLeft() {
		diffNum--;
		if (diffNum < 1) {
			diffNum = 3;
			setDifficulty("hard");
		}
		else if (diffNum == 2) {
			setDifficulty("medium");
		}
		else if (diffNum == 1) {
			setDifficulty("easy");
		}
	}
	
	public String getDifficulty() {
		return difficulty;
	}
}
