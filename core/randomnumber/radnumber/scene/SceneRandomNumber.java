package radnumber.scene;

import scene.GameScene;
import utils.elements.Img;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.coder5560.game.enums.Constants;

public class SceneRandomNumber extends GameScene {
	Img		bg;
	Label	label;
	int		index	= 10;

	public SceneRandomNumber() {
		super();
		{
			LabelStyle style = new LabelStyle();
			style.font = new BitmapFont(
					Gdx.files.internal("font/testwhite.fnt"));
			label = new Label("" + index, style);
			label.setPosition(Constants.WIDTH_SCREEN / 2,
					Constants.HEIGHT_SCREEN / 2, Align.center);
		}
		addActor(label);
		setDrawChildren(true);
	}

	@Override
	public void act(float deltatime) {
		super.act(deltatime);
		update(deltatime);
	}

	boolean	cantouch	= false;
	private int	countTime	= 0, maxTime = 40;

	public void update(float deltaTime) {
		if (Gdx.input.justTouched() && cantouch) {
			cantouch = false;
		}
		if (!cantouch) {
			countTime++;
			if (countTime < maxTime) {
				if (countTime % 2 == 0)
					index = MathUtils.random(0, 100);
			} else {
				cantouch = true;
				countTime = 0;
			}
		}
		label.setText("" + index);
	}
}
