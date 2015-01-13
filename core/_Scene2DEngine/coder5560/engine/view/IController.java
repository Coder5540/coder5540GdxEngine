package coder5560.engine.view;

import screens.AbstractGameScreen;
import screens.GameCore;
import coder5560.engine.Engine;

public interface IController {

	public void update(float delta);

	public GameCore getGameCore();

	public Engine getEngine();

	public AbstractGameScreen getScreen();

	public boolean isContainView(ViewName name);

	public IViewElement getView(ViewName name);

	public IController removeView(ViewName name);

	public IController addView(IViewElement view);

	public IViewElement getCurrentView();

	public IController setCurrentView(IViewElement view);

	public IController onBack();

}
