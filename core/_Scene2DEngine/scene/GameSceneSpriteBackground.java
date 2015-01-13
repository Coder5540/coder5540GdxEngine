package scene;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameSceneSpriteBackground extends GameSceneBackground{

	TextureRegion region;
	
	public GameSceneSpriteBackground(TextureRegion region) {
		this.region = region;
	}

	@Override
	public void draw(Batch batch) {		
		batch.draw(region, posX, posY, width, height);
	}

}
