package alphabetgame.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.FloatArray;

public class TextEffect {

	private TextButton[]	listchar;
	private FloatArray		post, advances;
	private String			text;
	private Stage			stage;

	private static final float	time	= 0.5f, delay = 0.2f;
	private static final float	x		= 50f, y = 200f;
	BitmapFont					newFont;

	public TextEffect(Stage stage, String text) {
		this.stage = stage;
		this.text = text;
		buildComponent();
		appearText();
	}

	public void buildComponent() {
		newFont = new BitmapFont(Gdx.files.internal("font/testwhite.fnt"));
		listchar = new TextButton[text.length()];
		advances = new FloatArray();
		post = new FloatArray();

		newFont.computeGlyphAdvancesAndPositions(text, advances, post);

		final TextButtonStyle style = new TextButtonStyle();
		style.font = newFont;

		/*-------- List Text --------*/
		for (int i = 0; i < text.length(); i++) {
			listchar[i] = new TextButton(String.valueOf(text.charAt(i)), style);
			listchar[i].setTransform(true);
			listchar[i].setPosition(x + post.get(i), y);
			listchar[i].setOrigin(advances.get(i) / 2,
					listchar[i].getHeight() / 4);
			stage.addActor(listchar[i]);
		}
		resetText();
	}

	private void resetText() {
		for (int i = 0; i < text.length(); i++) {
			listchar[i].setPosition(x + post.get(i), y);
			listchar[i].setOrigin(advances.get(i) / 2,
					listchar[i].getHeight() / 4);
			listchar[i].setColor(0, 0, 0, 1);
			listchar[i].setScale(1f);
		}
	}

	private void appearText() {
		resetText();
		for (int i = 0; i < text.length(); i++) {
			listchar[i].setScale(0f);
			listchar[i].setColor(0, 0, 0, 0);
			listchar[i].addAction(Actions.delay(delay * i, Actions.parallel(
					Actions.alpha(1, time),
					Actions.scaleTo(1, 1, time, Interpolation.swingOut))));
		}
	}

	public void runningText() {
		resetText();
		for (int i = 0; i < text.length(); i++) {
			listchar[i].setVisible(false);
			Action action = Actions.sequence(Actions.moveBy(800, 0),
					Actions.visible(true),
					Actions.moveBy(-400, 0, .5f, Interpolation.linear),
					Actions.moveBy(-50, 0, 1f, Interpolation.linear),
					Actions.moveBy(-350, 0, .5f, Interpolation.exp5Out));

			listchar[i].addAction(Actions.delay(delay * i, action));
		}

	}

	public void wavingText() {
		resetText();
		for (int i = 0; i < text.length(); i++) {
			Action action = Actions.sequence(
					Actions.moveBy(0, 60, 1f, Interpolation.swingOut),
					Actions.moveBy(0, -120, 1.5f, Interpolation.swingIn),
					Actions.moveBy(0, 60, 1f, Interpolation.swingOut));
			listchar[i].addAction(Actions.delay(delay * i, action));
		}
	}

	public void dropText() {
		resetText();
		for (int i = 0; i < text.length(); i++) {
			listchar[i].setVisible(false);
			Action action = Actions.sequence(Actions.moveBy(0, 500),
					Actions.visible(true),
					Actions.moveBy(0, -500, 1f, Interpolation.swingOut));

			listchar[i].addAction(Actions.delay(delay * i, action));
		}
	}

	public void scaleText() {
		resetText();
		for (int i = 0; i < text.length(); i++) {

			Vector2 position = new Vector2();
			listchar[i].setOrigin(listchar[i].getWidth() / 2,
					listchar[i].getHeight() / 2);
			position.set(listchar[i].getX(), listchar[i].getY());
			listchar[i].setVisible(false);
			Action action = Actions.sequence(Actions.moveTo(360, 200), Actions
					.scaleTo(4f, 4f), Actions.alpha(0f), Actions.visible(true),
					Actions.parallel(
							Actions.scaleTo(1, 1, .5f, Interpolation.swingIn),
							Actions.alpha(1f, .5f, Interpolation.linear)),
					Actions.moveTo(position.x, position.y, .3f,
							Interpolation.swingOut));

			listchar[i].addAction(Actions.delay(2 * delay * i, action));
		}
	}

	public void combineAction() {
		Action action = Actions.sequence(Actions.run(new Runnable() {

			@Override
			public void run() {
				appearText();
			}
		}), Actions.delay(2.5f), Actions.run(new Runnable() {

			@Override
			public void run() {
				runningText();
			}
		}), Actions.delay(5f), Actions.run(new Runnable() {

			@Override
			public void run() {
				wavingText();
			}
		}), Actions.delay(5f), Actions.run(new Runnable() {

			@Override
			public void run() {
				scaleText();
			}
		}), Actions.delay(5f), Actions.run(new Runnable() {

			@Override
			public void run() {
				dropText();
			}
		}));
		stage.addAction(action);
	}
}
