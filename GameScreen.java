package io.github.MeteorDash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen{
	final MeteorDash game;
	
	AssetManager assMan;
	Texture backgroundTexture;
	MainPlayer player1;
	Texture meteorTexture;
	Sound dropSound;
	Music music;
	//Sprite bucketSprite;
	Vector2 touchPos;
	Array<Meteor> meteors = new Array<>();
	float dropTimer;
	//Rectangle bucketRectangle;
	Rectangle dropRectangle;
	int hitsTaken;
	float totalTime;
	int points;
	AudioManager audioMan;
	
	public GameScreen(final MeteorDash game, AudioManager audioMan) {
		this.game = game;
		this.audioMan = audioMan;
		
		//load images
		backgroundTexture = new Texture("spacebg.png");
		Texture player1Texture = new Texture("ship.png");
		meteorTexture = new Texture("meteor.png");
		player1 = new MainPlayer(player1Texture);
		points = 0;
		
		//load sounds
		game.bgm.stop();
		game.bgm.dispose();
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("CanVas - Cole Slawter.mp3"));
        music.setLooping(true);
        music.setVolume(0.2f);
        
        touchPos = new Vector2();
        
        dropRectangle = new Rectangle();
	}
	
	@Override
	public void show() {
		if (!music.isPlaying()) {
	        music.setLooping(true);
	        music.setVolume(0.3f);
	        music.play();
	    }
	}
	
	@Override
	public void render(float delta) {
		input();
		logic();
		draw();
	}
	
	private void input() {
		float delta = Gdx.graphics.getDeltaTime();
		boolean moveLeft = Gdx.input.isKeyPressed(Input.Keys.LEFT);
		boolean moveRight = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
		boolean moveUp = Gdx.input.isKeyPressed(Input.Keys.UP);
		boolean moveDown = Gdx.input.isKeyPressed(Input.Keys.DOWN);
    	player1.move(moveLeft, moveRight, moveUp, moveDown, delta, game.viewport);
    	
    	if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY()); // Get where the touch happened on screen
            game.viewport.unproject(touchPos); // Convert the units to the world units of the viewport
            player1.setPosition(touchPos, game.viewport); // Change the horizontally centered position of the plane
        }
    	
    	if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			audioMan.playSound("menuSelect");
			game.setScreen(new MainMenuScreen(game, audioMan));
			dispose();
		}
	}
	
	private void logic() {
		float delta = Gdx.graphics.getDeltaTime();
		player1.clampPosition(game.viewport);
		for (int i = meteors.size - 1; i >=0; i--) {
			Meteor meteor = meteors.get(i);
			meteor.update(delta);
			
			if ((meteor.getSprite().getY() < -meteor.getSprite().getHeight())) {
				meteors.removeIndex(i);
				points += 100;
			}
			else if((meteor.getSprite().getX() > game.viewport.getWorldWidth()) || (meteor.getSprite().getX() + meteor.getSprite().getWidth() 
					< game.viewport.getWorldWidth() - game.viewport.getWorldWidth())) {
				meteors.removeIndex(i);
				points += 100;
			}
			else if (player1.getHitbox().overlaps(meteor.getHitbox())) {
				hitsTaken++;
				meteors.removeIndex(i);
				dropSound.play();
			}
		}
		
		dropTimer += delta;
		if (dropTimer > 0.7f) {
			dropTimer = 0;
			createMeteor();
		}
	}
	
	private void draw() {
		float delta = Gdx.graphics.getDeltaTime();
		totalTime += delta;
		ScreenUtils.clear(Color.BLACK);
		game.viewport.apply();
		game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
		
		game.batch.begin();
		
		float worldWidth = game.viewport.getWorldWidth();
		float worldHeight = game.viewport.getWorldHeight();
		
		game.batch.draw(backgroundTexture,  0,  0,  worldWidth,  worldHeight);
		player1.draw(game.batch);
		
		game.font.draw(game.batch, "Hits Taken: " + hitsTaken, 0, worldHeight);
		game.font.draw(game.batch, "Time Elapsed: " + String.format("%.0f", totalTime), 0, worldHeight - 0.2f);
		game.font.draw(game.batch, "Points: " + points, 0, worldHeight - 0.4f);
		
		for (Meteor meteor  : meteors) {
			meteor.getSprite().draw(game.batch);
		}
		
		game.batch.end();
		
	}
	
	private void createMeteor() {
		float worldWidth = game.viewport.getWorldWidth();
		float worldHeight = game.viewport.getWorldHeight();
		
		Meteor meteor = new Meteor(meteorTexture, worldWidth, worldHeight);
		meteor.setSpeedMod(totalTime * 0.03f + 1);
		meteors.add(meteor);
	}
	
	@Override
	public void resize(int width,int height) {
		game.viewport.update(width,  height, true);
	}
	
	@Override
	public void hide() {
		System.out.println("Hide() activated");
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	@Override
	public void dispose() {
		System.out.println("Disposing GameScreen resources...");
	    backgroundTexture.dispose();
	    for (Meteor meteor : meteors) {
	        meteor.dispose();  // Call dispose on each Meteor
	    }
	    dropSound.dispose();
	    music.stop();
	    music.dispose();
	    if (!(music.isPlaying())) {
	    	System.out.println("Music disposed of properly");
	    }
	    meteorTexture.dispose();
	    player1.dispose();
	    System.out.println("All GameScreen resources disposed.");
	}
}
