package io.github.MeteorDash;

public class GameSettings {
	private static GameSettings instance;
	private String difficulty;
	
	public GameSettings() {
		difficulty = "easy";
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
	
	public String getDifficulty() {
		return difficulty;
	}
}
