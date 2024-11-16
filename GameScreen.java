package io.github.slept668GameTest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen{
	final Drop game;
	
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
	
	public GameScreen(final Drop game) {
		this.game = game;
		
		//load images
		backgroundTexture = new Texture("spacebg.png");
		Texture player1Texture = new Texture("ship.png");
		meteorTexture = new Texture("meteor.png");
		player1 = new MainPlayer(player1Texture);
		points = 0;
		
		//load sounds
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("CanVas - Cole Slawter.mp3"));
        music.setLooping(true);
        music.setVolume(0.3f);
        
        //bucketSprite = new Sprite(bucketTexture);
        //bucketSprite.setSize(1,  1);
        
        touchPos = new Vector2();
         
        //bucketRectangle = new Rectangle();
        dropRectangle = new Rectangle();
	}
	
	@Override
	public void show() {
		System.out.println("GameScreen.show()");
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
		float speed = 4f;
		float delta = Gdx.graphics.getDeltaTime();
		float moveX = 0;
		float moveY = 0;
		
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveX = speed * delta;
        }
    	else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
    		moveX = -speed * delta;
        }
    	if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
    		moveY = speed * delta;
        }
    	else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
    		moveY = -speed * delta;
        }
    	// Normalize diagonal movement to avoid faster diagonal speeds
    	if (moveX != 0 && moveY != 0) {
    	    float length = (float) Math.sqrt(moveX * moveX + moveY * moveY);
    	    moveX /= length; // Normalize the X component
    	    moveY /= length; // Normalize the Y component
    	    moveX *= speed * delta; // Scale back to desired speed
    	    moveY *= speed * delta; // Scale back to desired speed
    	}
    	player1.move(moveX, moveY,game.viewport);
    	
    	if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY()); // Get where the touch happened on screen
            game.viewport.unproject(touchPos); // Convert the units to the world units of the viewport
            player1.setPosition(touchPos, game.viewport); // Change the horizontally centered position of the bucket
            //bucketSprite.setCenterY(touchPos.y); // Change the horizontally centered position of the bucket //Y axis touch movement
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
			else if (player1.getHitbox().overlaps(meteor.getHitbox())) {
				hitsTaken++;
				meteors.removeIndex(i);
				dropSound.play();
			}
		}
		
		dropTimer += delta;
		if (dropTimer > 1f) {
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
		meteors.add(meteor);
	}
	
	@Override
	public void resize(int width,int height) {
		game.viewport.update(width,  height, true);
	}
	
	@Override
	public void hide() {
		System.out.println("Hide() activated");
		dispose();
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
