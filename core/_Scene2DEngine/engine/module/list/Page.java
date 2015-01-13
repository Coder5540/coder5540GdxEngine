package engine.module.list;

import coder5560.engine.actor.TableElement;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class Page extends TableElement {
	private int	id;

	public Page(int id, float width, float height) {
		super();
		this.id = id;
		this.setSize(width, height);
		setTransform(true);
		setup();
	}

	public Page() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setup() {
		Label lb = new Label("View " + id, new LabelStyle(new BitmapFont(),
				Color.BLUE));
		add(lb);
	}
}
