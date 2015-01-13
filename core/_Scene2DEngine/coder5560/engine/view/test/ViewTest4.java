package coder5560.engine.view.test;

import utils.elements.Button;
import utils.factory.Log;
import utils.listener.OnComplete;
import utils.listener.OnCompleteListener;
import coder5560.engine.view.IController;
import coder5560.engine.view.IViewElement;
import coder5560.engine.view.ViewElement;
import coder5560.engine.view.ViewName;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.coder5560.game.assets.Assets;
import com.coder5560.game.enums.GameEvent;

public class ViewTest4 extends ViewElement {
	Button	btn;

	public ViewTest4(ViewName viewParentName, IController controller,
			ViewName viewName, Rectangle bound) {
		super(viewParentName, controller, viewName, bound);
	}

	@Override
	public IViewElement buildComponent() {
		{
			btn = new Button(100, 60);
			btn.buildBackground(Assets.instance.ui.reg_ninepatch)
					.buildText("Next").buildOnClick(new OnComplete() {

						@Override
						public void onComplete(Object data) {
							Log.d("On Clicked");
						}
					}).buidPosition(300, 50, Align.center).buidToContainer()
					.buildColor(Color.RED);
			addActor(btn);
			setBackground(new NinePatchDrawable(new NinePatch(
					Assets.instance.ui.reg_ninepatch, Color.GRAY)));
		}
		return this;
	}

	@Override
	public IViewElement show(Stage stage, float duration,
			OnCompleteListener listener) {
		super.show(stage, duration, listener);
		setTouchable(Touchable.enabled);
		{
			// do some thing here
		}
		return this;
	}

	@Override
	public IViewElement hide(float duration, OnCompleteListener listener) {
		super.hide(duration, listener);
		{
			// do some thing here
		}
		return this;
	}

	@Override
	public void back() {
		super.back();
	}

	@Override
	public IViewElement onGameEvent(GameEvent gameEvent) {
		{
			// do some thing here
		}
		return this;
	}

}
