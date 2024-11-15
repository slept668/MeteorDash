package io.github.slept668GameTest;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;

public class Meteor {
	private Sprite sprite;
	private Rectangle hitbox;
	private float speed; //fall speed
	
	public Meteor(Texture texture, float worldWidth, float worldHeight) {
		//create sprite and set size
		sprite = new Sprite(texture);
		sprite.setSize(1, 1);
		
		//initial position
		sprite.setX(MathUtils.random(0f, worldWidth - sprite.getWidth()));
		sprite.setY(worldHeight); //right above the top of the screen
		
		hitbox = new Rectangle();
		updateHitbox();
		
		speed = MathUtils.random(1f, 3f);
	}
	
	public void update(float delta) {
		//fall based on speed
		sprite.translateY(-speed *delta);
		updateHitbox();
	}
	
	public void updateHitbox() {
		hitbox.set(sprite.getX(), sprite.getY(),sprite.getWidth(),sprite.getHeight());
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	
	public void dispose() {
		sprite.getTexture().dispose();
	}
}
