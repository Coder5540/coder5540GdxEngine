package musicplayer.screen;


import utils.listener.OnCompleteListener;
import alphabethame.updatehandler.UpdateHandlerList;
import coder5560.engine.Engine;

import com.coder5560.game.enums.GameEvent;

public class MusicPlayerSceneHandler {
	private Engine			_Engine;
	private MusicPlayerScene	_DictionaryScene;

	public MusicPlayerSceneHandler(Engine engine, GameEvent gameEvent,
			UpdateHandlerList updateHandlerList) {
		super();
		this._Engine = engine;
		_DictionaryScene = new MusicPlayerScene(_Engine, gameEvent,
				updateHandlerList);
		engine.addActor(_DictionaryScene);
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
