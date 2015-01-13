package boardgame.elements;

import scene.GameScene;
import alphabethame.updatehandler.UpdateHandlerList;
import coder5560.engine.Engine;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.coder5560.game.enums.Constants;
import com.coder5560.game.enums.GameEvent;

public class BoardGameScene extends GameScene {
	public Engine				_Engine;
	public GameEvent			_GameEvent;
	public UpdateHandlerList	updateHandlerList;
	public BoardView			boardView;

	public BoardGameScene(Engine engine, GameEvent gameEvent,
			UpdateHandlerList updateHandlerList) {
		this._Engine = engine;
		this._GameEvent = gameEvent;
		this.updateHandlerList = updateHandlerList;
		setSize(Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN);
		createScene();
	}

	public void createScene() {
		boardView = new BoardView(new Vector2(40 * 9, 40 * 9), 9, 9, 40);
		boardView.setPosition(getWidth() / 2, getHeight() / 2, Align.center);
		addActor(boardView);
	}
}
