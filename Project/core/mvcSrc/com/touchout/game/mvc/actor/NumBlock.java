package com.touchout.game.mvc.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.touchout.game.Assets;

public class NumBlock extends Actor
{
	public int Number;
	public int Row;
	public int Col;

	public NumBlock(Integer number, int row, int col, float x, float y, float width, float height)
	{
		Number = number;
		Row = row;
		Col = col;
		setBounds(x, y, width, height);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) 
	{
		if(this.getColor() != Color.WHITE)
			batch.setColor(this.getColor());
		
		batch.draw(Assets.NumBlockTextures[this.Number]
				,this.getX()
				,this.getY()
				,this.getWidth()
				,this.getHeight());
		
		batch.setColor(Color.WHITE);
	}
}
