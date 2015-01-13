package scene;


public abstract class GameSceneBackground implements IGameSceneBackground {	
	
	protected float posX, posY, width, height;
	
	public GameSceneBackground() {
	}

	public void reset(float posX, float posY, float width, float height) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
	}
}
