package alphabetgame.scenes;

import scene.GameScene;
import utils.elements.Img;
import utils.factory.AudioFactory;
import utils.listener.OnClickListener;
import utils.listener.OnCompleteListener;
import alphabetgame.elements.EGoal;
import alphabetgame.elements.EText;
import alphabetgame.elements.ImgCharacter;
import alphabethame.updatehandler.UpdateHandlerList;
import coder5560.engine.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.coder5560.game.enums.Constants;
import com.coder5560.game.enums.GameEvent;
import com.coder5560.game.enums.GameEvent.Event;

public class SpellingGameScene extends GameScene {
	private Engine			_Engine;
	private Img				backGround;
	private EGoal			eSuggest;
	private EText			eGoal;
	private ImgCharacter	imgVariable;
	private ImgCharacter	imgScore;
	private String			word;
	private Array<String>	list	= new Array<String>();
	private int				pass	= 0;

	public SpellingGameScene(Engine engine, GameEvent gameEvent,
			UpdateHandlerList updateHandlerList) {
		this._Engine = engine;
		this.gameEvent = gameEvent;
		this.updateHandlerList = updateHandlerList;
		setSize(Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN);
		createScene();
		setDrawChildren(true);
	}

	void createScene() {
		{
			list.add("polite");
			list.add("selfish");
			list.add("motive");
			list.add("match");
			list.add("accountant");
			list.add("teacher");
			list.add("popular");
		}

		{
			backGround = new Img(new Texture(
					Gdx.files.internal("Img/background.png")));
			backGround.setSize(Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN);
			backGround.setTouchable(Touchable.disabled);
		}
		list.shuffle();
		word = list.get(pass);
		{
			eGoal = new EText(word);
			eGoal.setDimesion(new Vector2(60, 60));
			eGoal.setAlignText(Align.center);
			eGoal.setPosition(Constants.WIDTH_SCREEN / 2, 400, Align.center);
			eGoal.appearText();
			eGoal.setBlock(0);
		}

		{
			imgVariable = new ImgCharacter(eGoal.getStringAtBlock());
			imgVariable.setSize(100, 100);
			imgVariable.setPosition(Constants.WIDTH_SCREEN / 2,
					Constants.HEIGHT_SCREEN / 2, Align.center);
			imgVariable.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(float x, float y) {
				}
			});
			imgScore = new ImgCharacter("" + pass);
			imgScore.setSize(40, 40);
			imgScore.setPosition(40, Constants.HEIGHT_SCREEN - 40, Align.center);
			imgScore.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(float x, float y) {
				}
			});
		}

		{
			eSuggest = new EGoal("acdb", clickEvent);
			eSuggest.setDimesion(new Vector2(100, 100));
			eSuggest.setAlignText(Align.center);
			eSuggest.setPosition(Constants.WIDTH_SCREEN / 2, 100, Align.center);
			eSuggest.appearText();
			eSuggest.generateTarget(String.valueOf(word.charAt(0)), word);
		}

		{
			addActor(backGround);
			addActor(imgVariable);
			addActor(imgScore);
			addActor(eGoal);
			addActor(eSuggest);
		}
		resetWord();
	}

	public void updateWordCompleted(float delta,
			final OnCompleteListener onComplete) {
		AudioFactory.playSound(word, false, onComplete);
	}

	public void resetWord() {
		if (pass < list.size - 1)
			pass++;
		else {
			pass = 0;
			list.shuffle();
		}
		imgScore.setText("" + pass);
		imgScore.scaleOut(0.5f, null);
		word = list.get(pass);
		eGoal.buildText(word);
		eGoal.scaleOut(null);
		eGoal.setBlock(0);
		imgVariable.setText(eGoal.getStringAtBlock());
		imgVariable.scaleOut(0.5f, null);
		eSuggest.generateTarget(String.valueOf(word.charAt(0)), word);
		eSuggest.scaleOut(null);
	}

	void destroyScene() {
		_Engine.clear();
	}

	GameEvent			gameEvent;
	UpdateHandlerList	updateHandlerList;
	ClickEvent			clickEvent	= new ClickEvent() {

										@Override
										public void broadcastEvent(String c) {
											if (eGoal.getIndexBlock() >= SpellingGameScene.this.word
													.length() - 1) {
												if (c.equalsIgnoreCase(eGoal
														.getStringAtBlock())) {
													eGoal.setBlock(SpellingGameScene.this.word
															.length());
													eGoal.scaleOut(null);
													gameEvent
															.broadcastEvent(
																	Event.GAME_COMPLETE,
																	null);
												} else {
													gameEvent.broadcastEvent(
															Event.GAME_OVER,
															null);
												}
											} else {
												if (c.equalsIgnoreCase(eGoal
														.getStringAtBlock())) {
													eGoal.setBlock(eGoal
															.getIndexBlock() + 1);
													eSuggest.generateTarget(
															eGoal.getStringAtBlock(),
															SpellingGameScene.this.word);
													imgVariable
															.setText(eGoal
																	.getStringAtBlock());
													imgVariable.scaleOut(0.2f,
															null);
												} else {
													gameEvent.broadcastEvent(
															Event.GAME_OVER,
															null);
												}
											}
										}
									};

	public interface ClickEvent {
		public void broadcastEvent(String word);
	}

}
