package io.github.slept668GameTest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
	final Drop game;
	
	public MainMenuScreen(final Drop game) {
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		ScreenUtils.clear(Color.BLACK);
		
		game.viewport.apply();
		game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
		
		game.batch.begin();
		//draw text, x and y are meters
		game.font.draw(game.batch,  "Holy shit, it's working!",  1,  1.5f);
		game.font.draw(game.batch,  "Touch/Click to start",  1,  1);
		game.batch.end();
		
		if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
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
