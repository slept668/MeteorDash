package io.github.MeteorDash;

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
import java.util.ArrayList;
import java.util.List;

public class MainMenuScreen implements Screen {
	final MeteorDash game;
	public AssetMan assetMan;
	public TextureAtlas mainPack;
	public TextureAtlas arrowsPack;
	public Sprite bg;
	public Sprite arrowSprite;
	public Sound menuTick;
	public Sound menuSelect;
	public Sound startGame;
	public MenuArrow selecArrow;
	private boolean keyUpPressed = false;  // Add flags for UP and DOWN keys
	private boolean keyDownPressed = false;
	
	public MainMenuScreen(final MeteorDash game) {
		this.game = game;
		this.assetMan = game.getAssetMan();
		
		mainPack = assetMan.manager.get("Pack/MeteorDashMainPack.atlas");
		arrowsPack = assetMan.manager.get("Pack/ArrowsPack.atlas");
		bg = mainPack.createSprite("pixelart_starfield");
		arrowSprite = arrowsPack.createSprite("File");
		selecArrow = new MenuArrow(arrowSprite);
		
		menuTick = assetMan.manager.get("Sounds/menuTick.mp3");
		menuSelect = assetMan.manager.get("Sounds/menuSelect.mp3");
		startGame = assetMan.manager.get("Sounds/startGame.mp3");
		
	}
	
	@Override
	public void render(float delta) {
		input();
		logic();
		draw();
	}
	
	public void input() {
		if (Gdx.input.isKeyPressed(Input.Keys.UP) && !keyUpPressed) {
	        selecArrow.menuUp();
	        menuTick.play();
	        //audioMan.playSound("menuTick");
	        keyUpPressed = true;  // Mark the UP key as pressed
	    } else if (!Gdx.input.isKeyPressed(Input.Keys.UP)) {
	        keyUpPressed = false;  // Reset the flag when the key is released
	    }
		
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && !keyDownPressed) {
	        selecArrow.menuDown();
	        menuTick.play();
	        //audioMan.playSound("menuTick");
	        keyDownPressed = true;  // Mark the DOWN key as pressed
	    } else if (!Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
	        keyDownPressed = false;  // Reset the flag when the key is released
	    }
		
		if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
			if (selecArrow.getMenuStatus() == 1) {
				startGame.play();
				//audioMan.playSound("startGame");
				game.setScreen(new GameScreen(game));
				dispose();
			}
			else if (selecArrow.getMenuStatus() == 2) {
				menuSelect.play();
				//audioMan.playSound("menuSelect");
				game.setScreen(new Settings(game));
				dispose();
			}
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
		String startGame = "Start Game";
		String settings = "Settings";
		
		layout.setText(game.font, startGame);
		float startGameX = ((game.viewport.getWorldWidth() - layout.width) / 2f);
		float startGameY = (game.viewport.getWorldHeight() / 10) * 6f;
		
		
		layout.setText(game.font, settings);
		float settingsX = ((game.viewport.getWorldWidth() - layout.width)) / 2f;
		float settingsY = (game.viewport.getWorldHeight() / 10) * 5.5f;
		
		List<Vector2> menuLocations = new ArrayList<>();
		menuLocations.add(new Vector2(startGameX, startGameY));
        menuLocations.add(new Vector2(settingsX, settingsY));
        selecArrow.setMenuLocations(menuLocations);
        selecArrow.menuMove();
		
		game.batch.begin();
		game.batch.draw(bg,  0,  0,  worldWidth,  worldHeight);
		game.font.draw(game.batch,  startGame, startGameX, startGameY);
		game.font.draw(game.batch,  settings,  settingsX, settingsY);
		selecArrow.draw(game.batch);
		game.batch.end();
		
		
	}
	
	@Override
	public void resize(int width, int height) {
		game.viewport.update(width, height, true);
	}
	
	

	@Override
	public void show() {
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
