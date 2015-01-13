package scene;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface IGameSceneBackground{

	public void draw(Batch batch);
	public void reset(float posX, float posY, float width, float height);
}
