package com.touchout.game.scene2D;

import javafx.geometry.Pos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.sun.javafx.scene.paint.GradientUtils.Point;
import com.touchout.game.NumBlock;

public class NumBoardGroup extends Group
{
	Vector2 _position;
	final int _colCount;
	final int _rowCount;
	int _blockWidth;
	int _blockHeight;
	int _horizontalMargin = 10;
	int _VerticalMargin = 10;
	NumBlockActor[][] _slots;
	
	public NumBoardGroup(Vector2 pos, int colCount, int rowCount, int blockWidth, int blockHeight)
	{
		_position = pos;
		_colCount = colCount;
		_rowCount = rowCount;
		_blockWidth = blockWidth;
		_blockHeight = blockHeight;
		_slots = new NumBlockActor[_rowCount][_colCount];
		
		InitialFill();
		Shuffle();
	}
	
	private void InitialFill()
	{
		int num;
		Rectangle rec;
		for (int row = 0; row < _rowCount; row++) 
		{
			for (int col = 0; col < _colCount; col++) 
			{
				num = row * _colCount + (col+1);
				_slots[row][col] = new NumBlockActor(num,_position.x + col*(_blockHeight+_horizontalMargin)
						, _position.y + row*(_blockWidth+_VerticalMargin), _blockWidth, _blockHeight);
				this.addActor(_slots[row][col]);
			}
		}
	}
	
//	public void draw(SpriteBatch batch)
//	{
//		for (NumBlockActor[] numBlocks : _slots) 
//		{
//			for (NumBlockActor numBlock : numBlocks) 
//			{
//				numBlock.draw(batch);
//			}
//		}
//	}
	
//	public NumBlockActor GetTouchedBlock(Vector2 point)
//	{
//		for (int row = 0; row < _rowCount; row++) 
//		{
//			for (int col = 0; col < _colCount; col++) 
//			{
//				if(_slots[row][col].isTouchedInOrder(point,_correctNum))
//				{
//					_correctNum++;
//					return _slots[row][col];
//				}
//			}
//		}
//		
//		return null;
//	}
	
	public void Shuffle() 
	{
		int rowToSwap, colToSwap;
		int temp;
		
		for (int row = 0; row < _rowCount; row++) 
		{
			for (int col = 0; col < _colCount; col++) 
			{
				rowToSwap = MathUtils.random(_rowCount-1);
				colToSwap = MathUtils.random(_colCount-1);
				
				temp = _slots[rowToSwap][colToSwap].Number;
				_slots[rowToSwap][colToSwap].Number = _slots[row][col].Number;
				_slots[row][col].Number = temp;
			}
		}
	}
}
