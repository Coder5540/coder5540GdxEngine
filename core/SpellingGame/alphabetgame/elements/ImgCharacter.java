package alphabetgame.elements;

import keyboard.VirtualKeyboard.OnDoneListener;
import utils.elements.Img;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.coder5560.game.assets.AssetsAlphabet;

public class ImgCharacter extends Img {
	public ImgCharacter(String character) {
		super(AssetsAlphabet.getInstance().assetWord.getChar(character
				.equalsIgnoreCase("?") ? "Question" : character));
		setName(character);
	}

	public void setText(String text) {
		setDrawable(new TextureRegionDrawable(
				AssetsAlphabet.getInstance().assetWord.getChar(text
						.equalsIgnoreCase("?") ? "Question" : text)));
		setName(text);
	}


	public void scaleOut(float duration,OnDoneListener onDoneListener) {
		setScale(0);
		setColor(getColor().r, getColor().g, getColor().b, 0);
		addAction(Actions.delay(
				0,
				Actions.parallel(Actions.alpha(1, duration),
						Actions.scaleTo(1, 1, duration, Interpolation.swingOut))));
	}

	public void scaleIn(float duration,OnDoneListener onDoneListener) {
		addAction(Actions.delay(
				0,
				Actions.parallel(Actions.alpha(0, duration),
						Actions.scaleTo(0, 0, duration, Interpolation.swingIn))));
	}

}
