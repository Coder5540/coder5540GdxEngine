package alphabetgame.elements;

import keyboard.VirtualKeyboard.OnDoneListener;
import utils.listener.OnClickListener;
import alphabetgame.scenes.SpellingGameScene.ClickEvent;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.coder5560.game.enums.Constants;

public class EText extends Group {
	String						text;
	Array<ImgCharacter>			listCharacter;
	private static final float	time		= 0.5f, delay = 0.2f;
	private ClickEvent			clickEvent;
	private boolean				wrap		= false;
	private int					alignText	= Align.center;
	private Vector2				dimesion	= new Vector2();

	private int					indexBlock	= 0;

	public EText(String text) {
		super();
		buildText(text);
	}

	public EText(String text, ClickEvent clickEvent) {
		super();
		buildText(text);
		this.clickEvent = clickEvent;

	}

	public void buildText(String text) {
		clear();
		this.text = text;
		listCharacter = new Array<ImgCharacter>();
		for (int i = 0; i < text.length(); i++) {
			final String c = String.valueOf(text.charAt(i));
			if (!c.equalsIgnoreCase("") && c != null) {
				ImgCharacter imgCharacter = new ImgCharacter(c);
				imgCharacter.setPosition(Constants.WIDTH_SCREEN / 2,
						Constants.HEIGHT_SCREEN / 2, Align.center);
				imgCharacter.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(float x, float y) {
						if (clickEvent != null) {
							clickEvent.broadcastEvent(c);
						}
					}
				});
				imgCharacter.setSize(100, 100);
				listCharacter.add(imgCharacter);
				imgCharacter.setColor(Color.GRAY);
				addActor(imgCharacter);
			}
		}
	}

	public void validateElements() {
		float offsetX = 0;
		float offsetY = 0;

		if (alignText == Align.center) {
			offsetX = -listCharacter.size * dimesion.x / 2;
			offsetY = -dimesion.y / 2;
		}

		for (int i = 0; i < listCharacter.size; i++) {
			ImgCharacter imgCharacter = listCharacter.get(i);
			imgCharacter.setSize(dimesion.x, dimesion.y);
			imgCharacter.setPosition(dimesion.x * i + offsetX, 0 + offsetY);
		}
	}

	public void resetText() {
		validateElements();
		for (int i = 0; i < listCharacter.size; i++) {
			ImgCharacter imgCharacter = listCharacter.get(i);
			imgCharacter.setColor(1, 1, 1, 1);
			imgCharacter.setScale(1f);
		}
	}

	public void appearText() {
		resetText();
		for (int i = 0; i < text.length(); i++) {
			listCharacter.get(i).setScale(0f);
			listCharacter.get(i).setColor(1, 1, 1, 0);
			listCharacter.get(i).addAction(
					Actions.delay(delay * i,
							Actions.parallel(Actions.alpha(1, time),
									Actions.scaleTo(1, 1, time,
											Interpolation.swingOut))));
		}
	}

	public void runningText() {
		resetText();
		for (int i = 0; i < text.length(); i++) {
			listCharacter.get(i).setVisible(false);
			Action action = Actions.sequence(Actions.moveBy(800, 0),
					Actions.visible(true),
					Actions.moveBy(-400, 0, .5f, Interpolation.linear),
					Actions.moveBy(-50, 0, 1f, Interpolation.linear),
					Actions.moveBy(-350, 0, .5f, Interpolation.exp5Out));

			listCharacter.get(i).addAction(Actions.delay(delay * i, action));
		}

	}

	public void wavingText() {
		resetText();
		for (int i = 0; i < text.length(); i++) {
			Action action = Actions.sequence(
					Actions.moveBy(0, 60, 1f, Interpolation.swingOut),
					Actions.moveBy(0, -120, 1.5f, Interpolation.swingIn),
					Actions.moveBy(0, 60, 1f, Interpolation.swingOut));
			listCharacter.get(i).addAction(Actions.delay(delay * i, action));
		}
	}

	public void dropText() {
		resetText();
		for (int i = 0; i < text.length(); i++) {
			listCharacter.get(i).setVisible(false);
			Action action = Actions.sequence(Actions.moveBy(0, 500),
					Actions.visible(true),
					Actions.moveBy(0, -500, 1f, Interpolation.swingOut));

			listCharacter.get(i).addAction(Actions.delay(delay * i, action));
		}
	}

	public void scaleText() {
		resetText();
		for (int i = 0; i < text.length(); i++) {

			Vector2 position = new Vector2();
			listCharacter.get(i).setOrigin(listCharacter.get(i).getWidth() / 2,
					listCharacter.get(i).getHeight() / 2);
			position.set(listCharacter.get(i).getX(), listCharacter.get(i)
					.getY());
			listCharacter.get(i).setVisible(false);
			Action action = Actions.sequence(Actions.moveTo(getWidth() / 2,
					getHeight() / 2), Actions.scaleTo(4f, 4f), Actions
					.alpha(0f), Actions.visible(true), Actions.parallel(
					Actions.scaleTo(1, 1, .5f, Interpolation.swingIn),
					Actions.alpha(1f, .5f, Interpolation.linear)),
					Actions.moveTo(position.x, position.y, .3f,
							Interpolation.swingOut));

			listCharacter.get(i)
					.addAction(Actions.delay(2 * delay * i, action));
		}
	}

	public void scaleOut(OnDoneListener onDoneListener) {
		resetText();
		for (int i = 0; i < text.length(); i++) {
			listCharacter.get(i).setScale(0f);
			listCharacter.get(i).setColor(1, 1, 1, 0);
			listCharacter.get(i).addAction(
					Actions.delay(0,
							Actions.parallel(Actions.alpha(1, time),
									Actions.scaleTo(1, 1, time,
											Interpolation.swingOut))));
		}
	}

	public void scaleIn(OnDoneListener onDoneListener) {
		for (int i = 0; i < text.length(); i++) {
			listCharacter.get(i)
					.addAction(
							Actions.delay(0, Actions.parallel(Actions.alpha(0,
									time), Actions.scaleTo(0, 0, time,
									Interpolation.swingIn))));
		}
	}

	public boolean isWrap() {
		return wrap;
	}

	public void setWrap(boolean wrap) {
		this.wrap = wrap;
	}

	public int getAlignText() {
		return alignText;
	}

	public void setAlignText(int alignText) {
		this.alignText = alignText;
	}

	public Vector2 getDimesion() {
		return dimesion;
	}

	public void setDimesion(Vector2 dimesion) {
		this.dimesion = dimesion;
		for (int i = 0; i < text.length(); i++) {
			listCharacter.get(i).setSize(dimesion.x, dimesion.y);
		}
	}

	public void setBlock(int indexBlock) {
		this.indexBlock = indexBlock;
		for (int i = 0; i < text.length(); i++) {
			ImgCharacter charracter = listCharacter.get(i);
			if (i == indexBlock) {
				charracter.setText("?");
				charracter.setColor(1, 1, 1, 1);
			} else if (i > indexBlock) {
				charracter.setText("?");
				charracter.setColor(Color.GRAY);
			} else {
				charracter.setText(String.valueOf(text.charAt(i)));
				charracter.setColor(1, 1, 1, 1);
			}
		}
	}

	public String getStringAtBlock() {
		return String.valueOf(text.charAt(indexBlock));
	}

	public int getIndexBlock() {
		return indexBlock;
	}
}
