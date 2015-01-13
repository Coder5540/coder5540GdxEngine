package musicplayer.screen;
import screens.AbstractGameScreen;
import screens.GameCore;
import utils.elements.LaserBound;
import utils.factory.Log;
import utils.listener.OnComplete;
import alphabethame.updatehandler.IUpdateHandler;
import alphabethame.updatehandler.MemoryManager;
import alphabethame.updatehandler.UpdateHandlerList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.coder5560.game.assets.AssetFactory;
import com.coder5560.game.assets.AssetsAlphabet;
import com.coder5560.game.enums.Constants;
import com.coder5560.game.enums.GameEvent;


public class MusicPlayerScreen extends AbstractGameScreen{
	private AssetManager				assetManager;
	public UpdateHandlerList	updateHandlerList;
	MusicPlayerSceneHandler				_MusicPlayerSceneHandler;
	LaserBound					laserBound;

	public MusicPlayerScreen(GameCore game) {
		super(game);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void show() {
		super.show();
		updateHandlerList = new UpdateHandlerList();
		loadResource();
		Array<Vector2> vertices = new Array<Vector2>();
		vertices.add(new Vector2(0, 0));
		vertices.add(new Vector2(0, Constants.HEIGHT_SCREEN));
		vertices.add(new Vector2(Constants.WIDTH_SCREEN,
				Constants.HEIGHT_SCREEN));
		vertices.add(new Vector2(Constants.WIDTH_SCREEN, 0));
		vertices.add(new Vector2(0, 0));
		laserBound = new LaserBound(vertices, Color.BLUE);
	}

	void loadResource() {
		assetManager = AssetFactory.getInstance().getManager();
		assetManager.clear();
		assetManager.load(Constants.PACK_ALPHABET, TextureAtlas.class);
		assetManager.finishLoading();

		IUpdateHandler iUpdateHandler = new IUpdateHandler() {

			@Override
			public void reset() {

			}

			@Override
			public void onUpdate(float delta) {
				if (assetManager.update()) {
					finishLoading();
					updateHandlerList.removeValue(this, false);
				}
			}
		};

		MemoryManager memoryManager = new MemoryManager();
		updateHandlerList = new UpdateHandlerList(10);
		updateHandlerList.add(memoryManager);
		updateHandlerList.add(iUpdateHandler);
	}

	void finishLoading() {
		TextureAtlas textureAtlas = assetManager.get(Constants.PACK_ALPHABET);
		for (Texture t : textureAtlas.getTextures()) {
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		AssetsAlphabet.getInstance().loadResource(textureAtlas);
		createGameObject();
	}

	void createGameObject() {
		getEngine().clear();
		_MusicPlayerSceneHandler = new MusicPlayerSceneHandler(getEngine(), gameEvent,
				updateHandlerList);
		getEngine().setGameCore(parent);
		setGameState(GameState.RUNING);
	}

	@Override
	public void update(float delta) {
		updateHandlerList.onUpdate(delta);
		switch (getGameState()) {
			case RUNING:
				if (_MusicPlayerSceneHandler != null)
					_MusicPlayerSceneHandler.updateRunning(delta);
				break;

			case WORD_COMPLETE:
				if (_MusicPlayerSceneHandler != null)
					_MusicPlayerSceneHandler.updateWordCompleted(delta, new OnComplete() {
						@Override
						public void onComplete(Object data) {
							setGameState(GameState.RUNING);
						}
						@Override
						public void onError(Object data) {
							setGameState(GameState.RUNING);
						}
					});
				break;

			default:
				break;
		}
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		getEngine().getBatch().begin();
		laserBound.render((SpriteBatch) getEngine().getBatch(), delta);
		getEngine().getBatch().end();

	}

	public GameEvent	gameEvent	= new GameEvent() {

										@Override
										public void broadcastEvent(Event event,
												Object message) {

											if (event == Event.GAME_COMPLETE) {
												Log.d("Game Complete");
												setGameState(GameState.WORD_COMPLETE);
											}
											if (event == Event.GAME_OVER) {
												Log.d("Game Over");
											}

										}
									};
}
