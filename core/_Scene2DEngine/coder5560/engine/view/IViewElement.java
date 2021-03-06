package coder5560.engine.view;

import utils.listener.OnCompleteListener;
import alphabethame.updatehandler.IUpdateHandler;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.coder5560.game.enums.GameEvent;
import com.coder5560.game.enums.ViewState;

public interface IViewElement {

	public IViewElement show(Stage stage, float duration,
			final OnCompleteListener listener);

	public IViewElement hide(float duration, final OnCompleteListener listener);

	public void act(float deltatime);

	public void onBeginDraw(Batch batch);

	public void onEndDraw(Batch batch);

	public IViewElement registerUpdateHandler(IUpdateHandler handler);

	public boolean unregisterUpdateHandler(IUpdateHandler handler);

	public boolean clearUpdatehandler(IUpdateHandler handler);

	public IViewElement clearUpdateHandlers();

	public IViewElement setViewState(ViewState state);

	public ViewState getViewState();

	public boolean isIgnoreUpdate();

	public IViewElement setIgnoreUpdate(boolean pIgnoreUpdate);

	public IViewElement setViewName(ViewName viewName);

	public ViewName getParentViewName();

	public ViewName getViewName();

	public IController getController();

	public void setPosition(float x, float y);

	public Rectangle getBound();

	public IViewElement onGameEvent(GameEvent gameEvent);

	public IViewElement destroyElement();

	public void back();
}
