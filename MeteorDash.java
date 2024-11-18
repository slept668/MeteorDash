package io.github.MeteorDash;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MeteorDash extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public FitViewport viewport;
	AudioManager audioMan;
	public Music bgm;
	
	public void create() {
		audioMan = new AudioManager();
		bgm = Gdx.audio.newMusic(Gdx.files.internal("CanVas - Dead People Faces.mp3"));
		audioMan.addSound("menuTick", Gdx.audio.newSound(Gdx.files.internal("Sounds/menuTick.mp3")));
		audioMan.addSound("menuSelect", Gdx.audio.newSound(Gdx.files.internal("Sounds/menuSelect.mp3")));
		audioMan.addSound("startGame", Gdx.audio.newSound(Gdx.files.internal("Sounds/startGame.mp3")));
		bgm.setLooping(true);
		bgm.setVolume(0.2f);
		bgm.play();
		batch = new SpriteBatch();
		//libGDX default font by calling function without argument
		font = new BitmapFont();
		viewport = new FitViewport(8, 5);
		
		//Scale font to viewport by ratio of viewport height to screen height
		font.setUseIntegerPositions(false);
		font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());
		
		this.setScreen(new MainMenuScreen(this, audioMan));
	}
	
	public void render() {
		super.render();
	}
	
	public void dispose() {
		audioMan.dispose();
		bgm.dispose();
		batch.dispose();
		font.dispose();
		screen.dispose();
	}
}
