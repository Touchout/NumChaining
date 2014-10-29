package com.touchout.game.component;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TextActor extends Actor 
{
	BitmapFont _font;
	String _text;
	
	public TextActor(BitmapFont font, String text, int x, int y)
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
