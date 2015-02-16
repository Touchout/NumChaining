package com.touchout.game.component;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TextActor extends Actor 
{
	BitmapFont _font;
	String _text;
	
	public BitmapFont getFont() { return _font;}

	public void setFont(BitmapFont _font) {	this._font = _font;	}
	
	public String getText() { return _text;}

	public void setText(String _text) {	this._text = _text;	}

	public TextActor(BitmapFont font, String text, float x, float y)
	{
		super();
		_font = font;
		_text = text;
		setX(x);
		setY(y);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) 
	{
		_font.draw(batch, _text, getX(), getY());
	}
}
