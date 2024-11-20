package io.github.MeteorDash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreen implements Screen {
	final MeteorDash game;
	AudioManager audioMan;
	Texture bg;
	Music music;
	
	public GameOverScreen(final MeteorDash game, AudioManager audioMan) {
		this.game = game;
		this.audioMan = audioMan;
		music = Gdx.audio.newMusic(Gdx.files.internal("sounds/gameOverBGM.mp3"));
        music.setLooping(true);
		
		bg = new Texture("pixelart_starfield.png");
	}

	@Override
	public void show() {
		if (!music.isPlaying()) {
	        music.setLooping(true);
	        music.setVolume(0.2f);
	        music.play();
	    }
		
	}

	@Override
	public void render(float delta) {
		input();
		logic();
		draw();
		
	}
	
	public void input(){
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
		String gameOver = "Game Over";
		layout.setText(game.font, gameOver);
		float gameOverX = ((game.viewport.getWorldWidth() - layout.width) / 2f);
		float gameOverY = (game.viewport.getWorldHeight() / 10) * 6f;
		
		game.batch.begin();
		game.batch.draw(bg,  0,  0,  worldWidth,  worldHeight);
		game.font.draw(game.batch,  gameOver, gameOverX, gameOverY);
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
		music.stop();
		music.dispose();
	}

}
