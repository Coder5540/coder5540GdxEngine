package boardgame.elements;

import utils.listener.OnCompleteListener;
import alphabethame.updatehandler.UpdateHandlerList;
import coder5560.engine.Engine;

import com.coder5560.game.enums.GameEvent;

public class BoardGameSceneHandler {
	private Engine			_Engine;
	private BoardGameScene	_SpellingGameScene;

	public BoardGameSceneHandler(Engine engine, GameEvent gameEvent,
			UpdateHandlerList updateHandlerList) {
		super();
		this._Engine = engine;
		_SpellingGameScene = new BoardGameScene(_Engine, gameEvent,
				updateHandlerList);
		engine.addActor(_SpellingGameScene);
	}

	public void appear() {

	}

	public void updateRunning(float delta) {
		isWordComplete = false;
	}

	boolean	isWordComplete	= false;

	public void updateWordCompleted(float delta,
			final OnCompleteListener onComplete) {

	}

}
