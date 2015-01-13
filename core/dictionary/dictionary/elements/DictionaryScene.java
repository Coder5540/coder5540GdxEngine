package dictionary.elements;

import musicplayer.screen.MusicPlayerScreen;
import scene.GameScene;
import screens.ScreenTransitionFade;
import utils.elements.Button;
import utils.listener.OnComplete;
import alphabethame.updatehandler.UpdateHandlerList;
import coder5560.engine.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.coder5560.game.enums.Constants;
import com.coder5560.game.enums.GameEvent;

public class DictionaryScene extends GameScene {
	private Engine				_Engine;
	private GameEvent			gameEvent;
	private UpdateHandlerList	updateHandlerList;
	private WordMean			wordMean;
	private Button				btnAudio;

	public DictionaryScene(Engine engine, GameEvent gameEvent,
			UpdateHandlerList updateHandlerList) {
		this._Engine = engine;
		this.gameEvent = gameEvent;
		this.updateHandlerList = updateHandlerList;
		setSize(Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN);
		createScene();
		setDrawChildren(true);
	}

	void createScene() {
		wordMean = new WordMean();
		wordMean.setSize(Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN);
		wordMean.load();
		addActor(wordMean);

		btnAudio = new Button(100, 60);
		btnAudio.buildBackground(
				new TextureRegion(new Texture(Gdx.files.internal("Img/1.png"))))
				.buildText("Listenning").buildOnClick(true, new OnComplete() {
					@Override
					public void onComplete(Object data) {
						_Engine.getGameCore().setScreen(new MusicPlayerScreen(_Engine.getGameCore()), ScreenTransitionFade.init(1f));
					}
				}).buidPosition().buidToContainer();
		btnAudio.setPosition(Constants.WIDTH_SCREEN - btnAudio.getWidth() - 10,
				Constants.HEIGHT_SCREEN - btnAudio.getHeight() - 10);
		addActor(btnAudio);
	}

	void destroyScene() {
		_Engine.clear();
	}

}