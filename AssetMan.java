package io.github.MeteorDash;

import com.badlogic.gdx.assets.AssetManager;
//import com.badlogic.gdx.assets.loaders.ParticleEffectLoader.ParticleEffectParameter;
//import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetMan {
	public final AssetManager manager = new AssetManager();
	
	//Textures
	public final String mainPack = "Pack/MeteorDashMainPack.atlas";
	public final String healthPack = "Pack/HealthPack.atlas";
	public final String arrowsPack = "Pack/ArrowsPack.atlas";
	
	//Sounds
	public final String bang = "Sounds/bang.mp3";
	public final String explode = "Sounds/explode.mp3";
	public final String glass = "Sounds/glass.mp3";
	public final String menuSelect = "Sounds/menuSelect.mp3";
	public final String menuTick = "Sounds/menuTick.mp3";
	public final String startGame = "Sounds/startGame.mp3";
	
	//Music
	public final String coleSlawter = "Music/CanVas - Cole Slawter.mp3";
	public final String deadPplFaces = "Music/CanVas - Dead People Faces.mp3";
	
	public void loadImages() {
		manager.load(mainPack, TextureAtlas.class);
		manager.load(healthPack, TextureAtlas.class);
		manager.load(arrowsPack, TextureAtlas.class);
	}
	
	public void loadSounds() {
		manager.load(bang, Sound.class);
		manager.load(explode, Sound.class);
		manager.load(glass, Sound.class);
		manager.load(menuSelect, Sound.class);
		manager.load(menuTick, Sound.class);
		manager.load(startGame, Sound.class);
	}
	
	public void loadMusic() {
		manager.load(coleSlawter, Music.class);
		manager.load(deadPplFaces, Music.class);
	}
	
	public void loadFonts() {
		//TODO: Figure out fonts
	}
	
	public void loadParticleEffects() {
		//TODO: Figure out particle effects
	}
}
