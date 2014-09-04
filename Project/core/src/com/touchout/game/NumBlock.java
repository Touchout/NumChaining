package com.touchout.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class NumBlock 
{
	public int Number;
	public boolean Solved = false;
	Rectangle BoundingBox;
	//Texture Image;
	
	public NumBlock(Integer number, Rectangle rec)
	{
		Number = number;
		BoundingBox = rec;
	}
	
	public boolean isTouched(Vector2 point) 
	{
		return BoundingBox.contains(point);
	}
	
	public boolean isTouchedInOrder(Vector2 point,int correctNum) 
	{
		boolean hitted = BoundingBox.contains(point);
		if(hitted && this.Number == correctNum)
		{
			Solved = true;
			return true;
		}
		else {
			return false;
		}
	}
	
	public void draw(SpriteBatch batch)
	{
		if(this.Solved)
			batch.setColor(Color.DARK_GRAY);
		
		batch.draw(Assets.NumBlockTextures[this.Number]
				,this.BoundingBox.x
				,this.BoundingBox.y
				,this.BoundingBox.width
				,this.BoundingBox.height);
		
		batch.setColor(Color.WHITE);
	}
}
