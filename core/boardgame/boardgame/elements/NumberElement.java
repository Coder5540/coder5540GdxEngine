package boardgame.elements;

import coder5560.engine.actor.Text;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.coder5560.game.assets.Assets;

class NumberElement extends coder5560.engine.actor.GroupElement implements Poolable{

	int number;
	
	Text number_text;
	
	String content;
	
	Color base_color = new Color(), 
	number_color = new Color();
	
	float padding = 0;
	
	boolean immutable = false;
	
	public NumberElement setColor(Color base_color, Color number_color) {
		
		this.base_color.set(base_color);
		setColor(base_color);
		this.number_color.set(number_color);
		return this;
	}
	
	public NumberElement setImmutable(boolean immutable) {
		this.immutable = immutable;
		return this;
	}
	
	public NumberElement setContent(String content) {
		this.content = content;
		return this;
	}
	
	public NumberElement setPadding(float padding) {
		this.padding = padding;
		return this;
	}
	
	public NumberElement setNumber(int number) {
		
		this.number = number;
		
		return this;
	}

	public NumberElement buildText() {
		
		if(number_text == null) {
			number_text = new Text(0, 0,Assets.instance.fontFactory.getFont(20), "a");
			number_text.setAlign(Text.CENTER);
			addActor(number_text);
		}
		return this;
	}
	
	@Override
	protected void beginDrawActor(Batch batch) {
		setColor(base_color);
		number_text.setColor(number_color);
	}

	@Override
	public void onDraw(Batch batch) {
	}

	@Override
	protected void beginDrawChildren(Batch batch) {
		number_text.setText(content).setWrapWidth(getWidth()).moveTo(0, getHeight()/2 - number_text.getBounds().height/2);
	}

	@Override
	public void reset() {
		remove();
		immutable = false;
	}
	
}
