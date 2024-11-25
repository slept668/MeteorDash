package io.github.MeteorDash;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class Settings implements Screen {
	final MeteorDash game;
	public GameSettings gameSettings;
	public TextureAtlas mainPack;
	public TextureAtlas arrowsPack;
	public AssetMan assetMan;
	public Sprite bg;
	public Sprite arrowSprite;
	public MenuArrow selecArrow;
	public Sound menuTick;
	public Sound menuSelect;
	private boolean keyUpPressed = false;  // Add flags for UP and DOWN keys
	private boolean keyDownPressed = false;
	private boolean keyRightPressed = false;
	private boolean keyLeftPressed = false;
	// Add timers to prevent continuous key fire
    private float keyRepeatTimer = 0f;
    private float keyRepeatDelay = 0.3f;  // 300ms delay between key presses
	
	public Settings(final MeteorDash game) {
		this.game = game;
		this.gameSettings = game.gameSettings;
		this.assetMan = game.getAssetMan();
		mainPack = assetMan.manager.get("Pack/MeteorDashMainPack.atlas");
		arrowsPack = assetMan.manager.get("Pack/ArrowsPack.atlas");
		bg = mainPack.createSprite("pixelart_starfield");
		arrowSprite = arrowsPack.createSprite("File");
		selecArrow = new MenuArrow(arrowSprite);
		
		menuTick = assetMan.manager.get("Sounds/menuTick.mp3");
		menuSelect = assetMan.manager.get("Sounds/menuSelect.mp3");
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		input(delta);
		logic();
		draw();
	}
	
	public void input(float delta) {
		keyRepeatTimer -= delta;  // Decrease the timer by the time passed since the last frame
		
		if (Gdx.input.isKeyPressed(Input.Keys.UP) && !keyUpPressed) {
			menuTick.play();
	        selecArrow.menuUp();
	        keyUpPressed = true;  // Mark the UP key as pressed
	    } else if (!Gdx.input.isKeyPressed(Input.Keys.UP)) {
	        keyUpPressed = false;  // Reset the flag when the key is released
	    }
		
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && !keyDownPressed) {
			menuTick.play();
	        selecArrow.menuDown();
	        keyDownPressed = true;  // Mark the DOWN key as pressed
	    } else if (!Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
	        keyDownPressed = false;  // Reset the flag when the key is released
	    }
		
		if (selecArrow.getMenuStatus() == 1 && Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !keyUpPressed
				 && keyRepeatTimer <= 0) {
			menuTick.play();
	        gameSettings.diffRight();
	        keyRightPressed = true;  // Mark the UP key as pressed
	        keyRepeatTimer = keyRepeatDelay;
	    } else if (!Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
	        keyRightPressed = false;  // Reset the flag when the key is released
	    }
		
		if (selecArrow.getMenuStatus() == 1 && Gdx.input.isKeyPressed(Input.Keys.LEFT) && !keyUpPressed
				 && keyRepeatTimer <= 0) {
			menuTick.play();
	        gameSettings.diffLeft();
	        keyLeftPressed = true;  // Mark the UP key as pressed
	        keyRepeatTimer = keyRepeatDelay;
	    } else if (!Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
	        keyLeftPressed = false;  // Reset the flag when the key is released
	    }
		
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			menuSelect.play();
			game.setScreen(new MainMenuScreen(game));
			dispose();
		}
	}
	
	public void logic() {
		
	}
	
	public void draw() {
ScreenUtils.clear(Color.BLACK);
		
		game.viewport.apply();
		game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
		float worldWidth = game.viewport.getWorldWidth();
		float worldHeight = game.viewport.getWorldHeight();
		
		GlyphLayout layout = new GlyphLayout();
		String difficulty = "Difficulty: ";
		String volume = "Volume: ";
		String diffSetting = gameSettings.getDifficulty();
		
		layout.setText(game.font, difficulty);
		float difficultyX = ((game.viewport.getWorldWidth() - layout.width) / 2f);
		float difficultyY = (game.viewport.getWorldHeight() / 10) * 6f;
		
		
		layout.setText(game.font, volume);
		float volumeX = ((game.viewport.getWorldWidth() - layout.width)) / 2f;
		float volumeY = (game.viewport.getWorldHeight() / 10) * 5.5f;
		
		List<Vector2> menuLocations = new ArrayList<>();
		menuLocations.add(new Vector2(difficultyX, difficultyY));
        menuLocations.add(new Vector2(volumeX, volumeY));
        selecArrow.setMenuLocations(menuLocations);
        selecArrow.menuMove();
		
		game.batch.begin();
		game.batch.draw(bg,  0,  0,  worldWidth,  worldHeight);
		game.font.draw(game.batch,  difficulty, difficultyX, difficultyY);
		game.font.draw(game.batch,  volume,  volumeX, volumeY);
		game.font.draw(game.batch, diffSetting, difficultyX + 1,  difficultyY);
		selecArrow.draw(game.batch);
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
	}
	
}
