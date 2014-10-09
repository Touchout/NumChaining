package com.touchout.game.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.touchout.game.Assets;

public class NumBlockActor extends Actor
{
	public int Number;
	public boolean Solved = false;
	public boolean Locked = false;
	
	public NumBlockActor(Integer number, float x, float y, float width, float height)
	{
		Number = number;
		setBounds(x, y, width, height);
	}
	
	public void reinitial() 
	{
		Solved = false;
		Locked = false;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) 
	{
		if(this.Solved)
			batch.setColor(Color.DARK_GRAY);
	
		if(this.Locked)
			batch.setColor(Color.RED);
		
		batch.draw(Assets.NumBlockTextures[this.Number]
				,this.getX()
				,this.getY()
				,this.getWidth()
				,this.getHeight());
		
		batch.setColor(Color.WHITE);
	}
}
