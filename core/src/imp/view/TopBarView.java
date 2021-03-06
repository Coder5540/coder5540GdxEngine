package imp.view;

import utils.elements.Img;
import utils.factory.FontFactory.fontType;
import utils.factory.Log;
import utils.factory.StringSystem;
import utils.factory.Style;
import utils.listener.OnClickListener;
import utils.listener.OnComplete;
import utils.listener.OnCompleteListener;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.coder5560.game.assets.Assets;
import com.coder5560.game.enums.ViewState;
import com.coder5560.game.views.View;

public class TopBarView extends View {
	Img		transparent;
	Img		iconMenu;
	Label	lbTitle;

	public TopBarView buildComponent() {
		setBackground(new NinePatchDrawable(new NinePatch(Style.ins.np4,
				new Color(0, 191 / 255f, 1, 1))));
		transparent = new Img(Assets.instance.ui.reg_ninepatch);
		transparent.setColor(0 / 255f, 0 / 255f, 0 / 255f, 0.4f);
		iconMenu = new Img(Assets.instance.ui.reg_submenu);
		iconMenu.setSize(60, 60);
		left();
		add(iconMenu).padLeft(4).left();
		lbTitle = new Label("IEnglish", Style.ins.getLabelStyle(25,
				fontType.Bold));
		add(lbTitle).padLeft(10).left();
		buildListener();
		return this;
	}

	public void setTopName(String name) {
		if (lbTitle != null)
			lbTitle.setText(name);
	}

	@Override
	public void show(OnCompleteListener listener) {
		super.show(listener);
	}

	@Override
	public void hide(OnCompleteListener listener) {
		super.hide(listener);
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void destroyComponent() {
	}

	@Override
	public void back() {
	}

	void buildListener() {
		
		
		
		
		
		
		
		iconMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(float x, float y) {
				Log.d("Click Menu Button");
				if (getViewController().getView(StringSystem.VIEW_MAIN_MENU)
						.getViewState() == ViewState.HIDE) {
					iconMenu.setTouchable(Touchable.disabled);
					getViewController().getView(StringSystem.VIEW_MAIN_MENU)
							.show(new OnComplete() {
								public void onComplete(Object object) {
									iconMenu.setTouchable(Touchable.enabled);
								}

							});
					return;
				}
				if (getViewController().getView(StringSystem.VIEW_MAIN_MENU)
						.getViewState() == ViewState.SHOW) {
					iconMenu.setTouchable(Touchable.disabled);
					getViewController().getView(StringSystem.VIEW_MAIN_MENU)
							.hide(new OnComplete() {
								@Override
								public void onComplete(Object object) {
									iconMenu.setTouchable(Touchable.enabled);
								}
							});
				}
			}
		});
	}
}
