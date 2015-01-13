package boardgame.elements;

import utils.elements.Img;
import utils.listener.OnClickListener;
import coder5560.engine.actor.GroupElement;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.coder5560.game.assets.Assets;

public class NodeWord extends GroupElement {
	private Label	lbText;
	private Img		bg;

	public NodeWord(int col, int row) {
	}

	public NodeWord buildText(String text) {
		if (lbText == null) {
			LabelStyle style = new LabelStyle();
			style.font = Assets.instance.fontFactory.fontWhite;
			lbText = new Label(text, style);
			lbText.setOrigin(Align.center);
			lbText.setAlignment(Align.center);
		} else {
			lbText.setText(text);
		}
		if (lbText.hasParent())
			lbText.remove();
		addActor(lbText);
		lbText.debug();
		return this;
	}

	public NodeWord buildBackgroud(TextureRegion region) {
		if (bg == null)
			this.bg = new Img(region);
			this.bg.setDrawable(new TextureRegionDrawable(region));
		if (bg.hasParent())
			bg.remove();
		addActor(bg);
		if (lbText != null)
			lbText.toFront();
		return this;
	}

	public NodeWord buidSize(float width, float height) {
		this.setSize(width, height);
		return this;
	}

	public NodeWord buildOnClickListener(OnClickListener onclicked,
			boolean removeListener) {
		if (removeListener) {
			bg.clearListeners();
		}
		bg.addListener(new ClickListener() {

		});
		return this;
	}

	public NodeWord validateElements() {
		bg.setSize(getWidth(), getHeight());
		lbText.setSize((float).75*getWidth(), (float).75*getHeight());
		lbText.setBounds(0, 0, getWidth(), getHeight());
		lbText.layout();
		
		bg.setOrigin(Align.center);
		bg.setPosition(getWidth() / 2, getHeight() / 2, Align.center);
		lbText.setPosition(getWidth() / 2, getHeight() / 2, Align.center);
		return this;
	}

	@Override
	public void onDraw(Batch batch) {
	}

	public Label getLbText() {
		return lbText;
	}

	public void setLbText(Label lbText) {
		this.lbText = lbText;
	}

	public Img getBg() {
		return bg;
	}

	public void setBg(Img bg) {
		this.bg = bg;
	}

}
