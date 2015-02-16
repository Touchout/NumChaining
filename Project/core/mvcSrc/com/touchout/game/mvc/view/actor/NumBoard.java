package com.touchout.game.mvc.view.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.touchout.game.Assets;
import com.touchout.game.event.BlockSolvedTEvent;
import com.touchout.game.event.LockBoardTEvent;
import com.touchout.game.event.TEvent;
import com.touchout.game.event.TEventBroadcaster;
import com.touchout.game.event.TEventCode;
import com.touchout.game.event.ITEventHandler;
import com.touchout.game.mvc.model.NumBlockEntity;

public class NumBoard extends Group
{
	private Logger logger = new Logger("NumBoardGroup");
	private Vector2 _position;
	private final int _colCount;
	private final int _rowCount;
	private float _blockWidth;
	private float _blockHeight;
	private float _horizontalMargin = 10;
	private float _VerticalMargin = 10;
	private NumBlock[][] _blocks;
	private NumBlockEntity[][] _cells;
	
	public void bindCells(NumBlockEntity[][] cells) 
	{
		this._cells = cells;
	}

	public NumBoard(Vector2 pos, int colCount, int rowCount, float blockWidth, float blockHeight)
	{
		_position = pos;
		_colCount = colCount;
		_rowCount = rowCount;
		_blockWidth = blockWidth;
		_blockHeight = blockHeight;
		_blocks = new NumBlock[_rowCount][_colCount];
		initialFill();
	}
	
	private void onDraw()
	{
		NumBlock blockActor;
		NumBlockEntity blockEntity;
		for (int row = 0; row < _rowCount; row++) 
		{
			for (int col = 0; col < _colCount; col++) 
			{
				blockActor = _blocks[row][col]; 
				blockEntity = _cells[row][col];
				
				//Set display number
				blockActor.Number = blockEntity.Number;
				
				//Set tint color by status 
				if(blockEntity.IsLocked)
					blockActor.setColor(Color.RED);
				else if(blockEntity.IsSolved)
					blockActor.setColor(Color.GRAY);
				else
					blockActor.setColor(Color.WHITE);
			}
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) 
	{
		onDraw();
		super.draw(batch, parentAlpha);
	}
	
	private void initialFill()
	{
		int num;
		for (int row = 0; row < _rowCount; row++) 
		{
			for (int col = 0; col < _colCount; col++) 
			{
				num = row * _colCount + (col+1);
				_blocks[row][col] = new NumBlock(num, 
												row,
												col,
												_position.x + col*(_blockHeight+_horizontalMargin), 
												_position.y + row*(_blockWidth+_VerticalMargin), 
												_blockWidth, 
												_blockHeight);
				this.addActor(_blocks[row][col]);
			}
		}
	}
}
