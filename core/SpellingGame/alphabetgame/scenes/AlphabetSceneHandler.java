package alphabetgame.scenes;

import utils.listener.OnComplete;
import utils.listener.OnCompleteListener;
import alphabethame.updatehandler.UpdateHandlerList;
import coder5560.engine.Engine;

import com.coder5560.game.enums.GameEvent;

public class AlphabetSceneHandler {
	private Engine				_Engine;
	private SpellingGameScene	_SpellingGameScene;

	public AlphabetSceneHandler(Engine engine, GameEvent gameEvent,
			UpdateHandlerList updateHandlerList) {
		super();
		this._Engine = engine;
		_SpellingGameScene = new SpellingGameScene(_Engine, gameEvent,
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
		if (!isWordComplete) {
			_SpellingGameScene.updateWordCompleted(delta, new OnComplete() {
				@Override
				public void onComplete(Object data) {
					_SpellingGameScene.resetWord();
					if (onComplete != null)
						onComplete.onComplete("");
				}
			});
			isWordComplete = true;
		}

	}

}
