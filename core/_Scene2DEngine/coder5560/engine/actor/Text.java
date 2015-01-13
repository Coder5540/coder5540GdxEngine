package coder5560.engine.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;

public class Text extends Element {		
	
	public static final int LEFT = 0,
							CENTER = 1,
							RIGHT = 2;
	
	protected int align = CENTER;
	
	protected BitmapFont font;
	
	protected String content;	
	
	private Color strokeColor;
	
	protected float wrapWidth;
	
	public Text(float posX, float posY, BitmapFont font, String content) {
		setPosition(posX, posY);
		this.content = content;
		this.font = font;
	}
	
	public Text moveTo(float x, float y) {
		setPosition(x, y);
		return this;
	}
	
	public Text setWrapWidth(float wrapWidth) {
		this.wrapWidth = wrapWidth;
		return this;
	}
	
	public Text setAlign(int align) {
		this.align = align;
		return this;
	}
	
	public Text setFont(BitmapFont font) {
		this.font = font;
		return this;
	}
	
	public Text setText(String content) {
		this.content = content;
		return this;
	}
	
	@Override
	public void onDraw (Batch batch) {		
		if(getScaleX() > 0 && getScaleY() > 0) {
			if(strokeColor != null) {
				font.setColor(strokeColor);
			   font.setScale(getScaleX()*1.2f, getScaleY()*1.2f);
			   realdraw(batch);
		   }
		   font.setColor(batch.getColor());
		   font.setScale(getScaleX(), getScaleY());
		   realdraw(batch);
		}
	}
	
	private void realdraw(Batch batch) {
		if(wrapWidth == 0) {
			TextBounds b = font.getBounds(content);		
			if(align == CENTER) {
			       font.draw(batch, 
			    		   content,     		   
			    		   getX()-b.width/2, 
			    		   getY()-b.height/2);
		  	} else if (align == LEFT) {
		  		font.draw(batch, 
		  				content,     		   
		  				getX(), 
		  				getY()-b.height/2);
		  	} else {
		  		font.draw(batch, 
		  				content,     		   
		  				getX()-b.width, 
		  				getY()-b.height/2);
		  	}
		} else {
			font.drawWrapped(batch, content, getX(), getY(), wrapWidth,
					align == CENTER ? HAlignment.CENTER : (align == LEFT ? HAlignment.LEFT : HAlignment.RIGHT));
		}
	}
	
	public TextBounds getBounds() {
		font.setScale(getScaleX(), getScaleY());
		if(wrapWidth > 0) {
			return font.getWrappedBounds(content, wrapWidth);
		}
		return font.getBounds(content);
	}
	
	public void setStroke(float r, float g, float b, float a) {
		strokeColor = new Color(r, g, b, a);
	}
}
