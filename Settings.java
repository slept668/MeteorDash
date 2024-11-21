package io.github.MeteorDash;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class Settings implements Screen {
	final MeteorDash game;
	public Texture bg;
	AudioManager audioMan;
	MenuArrow selecArrow;
	private boolean keyUpPressed = false;  // Add flags for UP and DOWN keys
	private boolean keyDownPressed = false;
	
	public Settings(final MeteorDash game, AudioManager audioMan) {
		this.game = game;
		this.audioMan = audioMan;
		bg = new Texture("BG/pixelart_starfield.png");
		Texture arrowTexture = new Texture("Arrows/Red Arrow 3/File1.png");
		selecArrow = new MenuArrow(arrowTexture);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		input();
		logic();
		draw();
	}
	
	public void input() {
		if (Gdx.input.isKeyPressed(Input.Keys.UP) && !keyUpPressed) {
			audioMan.playSound("menuTick");
	        selecArrow.menuUp();
	        keyUpPressed = true;  // Mark the UP key as pressed
	    } else if (!Gdx.input.isKeyPressed(Input.Keys.UP)) {
	        keyUpPressed = false;  // Reset the flag when the key is released
	    }
		
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && !keyDownPressed) {
			audioMan.playSound("menuTick");
	        selecArrow.menuDown();
	        keyDownPressed = true;  // Mark the DOWN key as pressed
	    } else if (!Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
	        keyDownPressed = false;  // Reset the flag when the key is released
	    }
		
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			audioMan.playSound("menuSelect");
			game.setScreen(new MainMenuScreen(game, audioMan));
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
		
		layout.setText(game.font, difficulty);
		float startGameX = ((game.viewport.getWorldWidth() - layout.width) / 2f);
		float startGameY = (game.viewport.getWorldHeight() / 10) * 6f;
		
		
		layout.setText(game.font, volume);
		float settingsX = ((game.viewport.getWorldWidth() - layout.width)) / 2f;
		float settingsY = (game.viewport.getWorldHeight() / 10) * 5.5f;
		
		List<Vector2> menuLocations = new ArrayList<>();
		menuLocations.add(new Vector2(startGameX, startGameY));
        menuLocations.add(new Vector2(settingsX, settingsY));
        selecArrow.setMenuLocations(menuLocations);
        selecArrow.menuMove();
		
		game.batch.begin();
		game.batch.draw(bg,  0,  0,  worldWidth,  worldHeight);
		game.font.draw(game.batch,  difficulty, startGameX, startGameY);
		game.font.draw(game.batch,  volume,  settingsX, settingsY);
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
		bg.dispose();
		selecArrow.dispose();
	}
	
}
