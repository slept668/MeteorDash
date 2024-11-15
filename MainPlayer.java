package io.github.slept668GameTest;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainPlayer {
	private Texture texture;
	private Sprite sprite;
	private Rectangle hitbox;
	
	public MainPlayer(Texture texture) {
		this.texture = texture;
		this.sprite = new Sprite(texture);
		this.sprite.setSize(1,  1);
		this.hitbox = new Rectangle();
		updateHitbox();
	}
	
	public void move(float dx, float dy, Viewport viewport) {
		sprite.translate(dx, dy);
		clampPosition(viewport);
		updateHitbox();
	}
	
	public void setPosition(Vector2 position, Viewport viewport) {
		sprite.setCenter(position.x, position.y);
		clampPosition(viewport);
		updateHitbox();
	}
	
	private void updateHitbox() {
		float hbWidth = sprite.getWidth() * 0.5f;
		float hbHeight = sprite.getHeight() * 0.5f;
		float hbX = sprite.getX() + (sprite.getWidth() - hbWidth) / 2;
		float hbY = sprite.getY() + (sprite.getHeight() - hbHeight) / 2;
		hitbox.set(hbX, hbY, hbWidth, hbHeight);
	}
	
	public void clampPosition(Viewport viewport) {
		float worldWidth = viewport.getWorldWidth();
		float worldHeight = viewport.getWorldHeight();
		sprite.setX(MathUtils.clamp(sprite.getX(), 0, worldWidth - sprite.getWidth()));
		sprite.setY(MathUtils.clamp(sprite.getY(), 0,   worldHeight - sprite.getHeight()));
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}
	
	public void dispose() {
		texture.dispose();
	}
}
