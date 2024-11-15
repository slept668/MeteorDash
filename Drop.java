package io.github.slept668GameTest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Drop extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public FitViewport viewport;
	
	public void create() {
		batch = new SpriteBatch();
		//libGDX default font by calling function without argument
		font = new BitmapFont();
		viewport = new FitViewport(8, 5);
		
		//Scale font to viewport by ratio of viewport height to screen height
		font.setUseIntegerPositions(false);
		font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());
		
		this.setScreen(new MainMenuScreen(this));
	}
	
	public void render() {
		super.render();
	}
	
	public void dispose() {
		batch.dispose();
		font.dispose();
		screen.dispose();
	}
}
