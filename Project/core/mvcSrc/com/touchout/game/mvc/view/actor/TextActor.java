package com.touchout.game.mvc.view.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TextActor extends Actor 
{
	BitmapFont _font;
	String _text;
	
	public BitmapFont getFont() { return _font;}

	public void setFont(BitmapFont _font) {	this._font = _font;	}
	
	public String getText() { return _text;}

	public void setText(String _text) {	this._text = _text;	}

	public TextBounds getSize() {
		return _font.getBounds(_text);
	}
	
	public TextBounds getSize(String str) {
		return _font.getBounds(str);
	}
	
	public void recalculate()
	{
		setBounds(this.getX(), this.getY(),  _font.getBounds(_text).width,  _font.getBounds(_text).height);
	}
	
	public TextActor(BitmapFont font, String text, float x, float y)
	{
		super();
		_font = font;
		_text = text;
		setX(x);
		setY(y);
		recalculate();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) 
	{
		_font.draw(batch, _text, getX(), getY());
	}
}
