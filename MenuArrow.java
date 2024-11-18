package io.github.MeteorDash;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.util.List;

public class MenuArrow {
	private Texture texture;
	public Sprite sprite;
	private float menuNum;
	private List<Vector2> menuLocations;
	
	public MenuArrow(Texture texture) {
		this.texture = texture;
		this.sprite = new Sprite(texture);
		this.sprite.setSize(0.25f,  0.25f);
		this.menuNum = 1;
		
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setRotation(90f);
		sprite.setX(-1);
		sprite.setY(-1);
	}
	
	public void setMenuLocations(List<Vector2> locations) {
		this.menuLocations = locations;
	}
	
	public void menuMove() {
		if (menuLocations != null && !menuLocations.isEmpty()) {
			int index = (int) (menuNum - 1);
			Vector2 location = menuLocations.get(index);
			
			sprite.setX(location.x - sprite.getWidth());
			sprite.setY(location.y - sprite.getHeight() / 1.35f);
		}
	}
	
	public void menuUp() {
		menuNum++;
		if (menuNum > menuLocations.size()) {
			menuNum = 1;
		}
		menuMove();
	}
	
	public void menuDown() {
		menuNum--;
		if (menuNum < 1) {
			menuNum = menuLocations.size();
		}
		menuMove();
	}
	
	public float getMenuStatus() {
		return menuNum;
	}
	
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}
	
	public void dispose() {
		texture.dispose();
	}
}
