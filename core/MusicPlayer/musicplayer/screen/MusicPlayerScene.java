package musicplayer.screen;

import ienglish.screens.DictionaryScreen;
import scene.GameScene;
import screens.ScreenTransitionFade;
import utils.elements.Button;
import utils.listener.OnComplete;
import alphabethame.updatehandler.UpdateHandlerList;
import coder5560.engine.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.coder5560.game.enums.Constants;
import com.coder5560.game.enums.GameEvent;

public class MusicPlayerScene extends GameScene {
	private Engine				_Engine;
	private GameEvent			gameEvent;
	private UpdateHandlerList	updateHandlerList;

	Button						btnPlay, btnPause;
	Button						btnStart, btnEnd;
	Button						btnLoop;
	Button						btnVocabularyScreen;
	Music						music;

	public MusicPlayerScene(Engine engine, GameEvent gameEvent,
			UpdateHandlerList updateHandlerList) {
		this._Engine = engine;
		this.gameEvent = gameEvent;
		this.updateHandlerList = updateHandlerList;
		this.setSize(Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN);
		setDrawChildren(true);
		setVisible(true);
		createScene();
		music = Gdx.audio.newMusic(Gdx.files.internal("data/Unit6.mp3"));
	}

	int		time		= 0;
	float	start, end;
	boolean	startLoop	= false;

	void createScene() {
		btnPause = new Button(100, 60);
		btnPlay = new Button(100, 60);
		btnStart = new Button(100, 60);
		btnEnd = new Button(100, 60);
		btnLoop = new Button(100, 60);
		btnVocabularyScreen = new Button(100, 60);
		btnPause.buildBackground(
				new TextureRegion(new Texture(Gdx.files.internal("Img/1.png"))))
				.buildText("Pause").buildOnClick(true, new OnComplete() {
					@Override
					public void onComplete(Object data) {
						music.pause();
					}
				}).buidPosition().buidToContainer();

		btnPlay.buildBackground(
				new TextureRegion(new Texture(Gdx.files.internal("Img/1.png"))))
				.buildText("Play").buildOnClick(true, new OnComplete() {
					@Override
					public void onComplete(Object data) {
						time += 2;
						music.setPosition(time);
						music.play();
					}
				}).buidPosition().buidToContainer();
		btnStart.buildBackground(
				new TextureRegion(new Texture(Gdx.files.internal("Img/1.png"))))
				.buildText("Start").buildOnClick(true, new OnComplete() {
					@Override
					public void onComplete(Object data) {
						start = music.getPosition();
					}
				}).buidPosition().buidToContainer();
		btnEnd.buildBackground(
				new TextureRegion(new Texture(Gdx.files.internal("Img/1.png"))))
				.buildText("End").buildOnClick(true, new OnComplete() {
					@Override
					public void onComplete(Object data) {
						if (startLoop)
							end = 10000;
						else
							end = music.getPosition();
						music.stop();
					}
				}).buidPosition().buidToContainer();
		btnLoop.buildBackground(
				new TextureRegion(new Texture(Gdx.files.internal("Img/1.png"))))
				.buildText("Loop").buildOnClick(true, new OnComplete() {
					@Override
					public void onComplete(Object data) {
						music.setPosition(start);
						startLoop = true;

					}
				}).buidPosition().buidToContainer();

		btnVocabularyScreen
				.buildBackground(
						new TextureRegion(new Texture(Gdx.files
								.internal("Img/1.png"))))
				.buildText("Vocabulary").buildOnClick(true, new OnComplete() {
					@Override
					public void onComplete(Object data) {
						_Engine.getGameCore().setScreen(
								new DictionaryScreen(_Engine.getGameCore()),
								ScreenTransitionFade.init(1f));
					}
				}).buidPosition().buidToContainer();

		btnPause.setPosition(100, 40);
		btnPlay.setPosition(220, 40);
		btnStart.setPosition(320, 40);
		btnEnd.setPosition(420, 40);
		btnLoop.setPosition(520, 40);
		btnVocabularyScreen.setPosition(620, 40);

		addActor(btnPlay);
		addActor(btnPause);
		addActor(btnStart);
		addActor(btnEnd);
		addActor(btnLoop);
		addActor(btnVocabularyScreen);
	}

	void destroyScene() {
		_Engine.clear();
	}

	@Override
	public void act(float deltatime) {
		update(deltatime);
		super.act(deltatime);
	}

	public void update(float deltaTime) {
		if (startLoop) {
			if (music.getPosition() >= end) {
				music.setPosition(start);
			}
		}
	}
}