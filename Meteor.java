package io.github.MeteorDash;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;

public class Meteor {
	private Sprite sprite;
	private Rectangle hitbox;
	private float speed; //fall speed
	private float randomDirection = 0;
	private float speedMod = 1;
	
	public Meteor(Texture texture, float worldWidth, float worldHeight) {
		//create sprite and set size
		sprite = new Sprite(texture);
		sprite.setSize(1, 1);
		
		//initial position
		sprite.setX(MathUtils.random(0f, worldWidth - sprite.getWidth()));
		sprite.setY(worldHeight); //right above the top of the screen
		
		hitbox = new Rectangle();
		updateHitbox();
		
		speed = MathUtils.random(20f, 27f) / 10;
	}
	
	public Meteor(Sprite sprite,float worldWidth, float worldHeight) {
		this.sprite = sprite;
		sprite.setSize(1, 1);
		 
		//initial position
		sprite.setX(MathUtils.random(0f, worldWidth - sprite.getWidth()));
		sprite.setY(worldHeight); //right above the top of the screen
		
		hitbox = new Rectangle();
		updateHitbox();
		
		speed = MathUtils.random(20f, 27f) / 10;
	}
	
	public void update(float delta) {
		//set rotation point or shit goes wild
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		//fall based on speed
		sprite.translateY(-speed * delta * speedMod);
		//rotation
		float rotateSpeed = 90f;
		sprite.rotate(rotateSpeed * delta);
		if (sprite.getRotation() >= 360) {
			sprite.setRotation(sprite.getRotation() - 360);
		}
		//random fall vectors
		if (randomDirection == 0) {
			randomDirection = MathUtils.random(-10, 10f) / 10;
		}
		
		sprite.translateX(randomDirection * delta);
		
		updateHitbox();
	}
	
	public void updateHitbox() {
		float hbWidth = sprite.getWidth() * 0.6f;
		float hbHeight = sprite.getHeight() * 0.6f;
		float hbX = sprite.getX() + (sprite.getWidth() - hbWidth) / 2;
		float hbY = sprite.getY() + (sprite.getHeight() - hbHeight) / 2;
		hitbox.set(hbX, hbY, hbWidth, hbHeight);
	}
	
	public void setSpeedMod(float mod) {
		this.speedMod = mod;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	
	public void dispose() {
	}
}
