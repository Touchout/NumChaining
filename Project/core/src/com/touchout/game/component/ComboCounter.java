package com.touchout.game.component;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.touchout.game.mvc.Assets;

public class ComboCounter extends Actor 
{
	public int ComboCount = 0;
	
	public ComboCounter(float x, float y)
	{
		setPosition(x, y);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) 
	{
		if(ComboCount > 1)
			Assets.ComboFont.draw(batch, String.format("% 2d Combos", ComboCount) , getX(), getY());
	}
}
