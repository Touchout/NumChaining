package com.touchout.game.scene2D;

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
import com.touchout.game.event.TEventHandler;

public class NumBoardGroup extends Group
{
	Logger logger = new Logger("NumBoardGroup");
	Vector2 _position;
	final int _colCount;
	final int _rowCount;
	int _blockWidth;
	int _blockHeight;
	int _horizontalMargin = 10;
	int _VerticalMargin = 10;
	int _targetNum;
	int _correctNum;
	NumBlockActor[][] _slots;
	NumBlockActor touchedBlock;
	boolean Solved = false;
	TEventBroadcaster _eventBroadcaster;

	public NumBoardGroup(Vector2 pos, int colCount, int rowCount, int blockWidth, int blockHeight)
	{
		_position = pos;
		_colCount = colCount;
		_rowCount = rowCount;
		_blockWidth = blockWidth;
		_blockHeight = blockHeight;
		_slots = new NumBlockActor[_rowCount][_colCount];
		_targetNum = colCount * rowCount;
		_correctNum = 1;
		_eventBroadcaster = new TEventBroadcaster();
		
		addListener(inputListener);
		InitialFill();
		Shuffle();
	}
	
	public void Reinitial()
	{
		Solved = false;
		_correctNum = 1;
		for (NumBlockActor[] numBlockActors : _slots) {
			for (NumBlockActor numBlockActor : numBlockActors) {
				numBlockActor.Solved = false;
			}
		}
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
	
	public InputListener inputListener = new InputListener()
	{
		public boolean touchDown(InputEvent event, float screenX, float screenY, int pointer, int button) 
		{
			Vector2 touchPos = new Vector2();
			touchPos.set(screenX, screenY);
			localToStageCoordinates(touchPos);
			logger.debug(touchPos.x + "," + touchPos.y);
			
			if((touchedBlock = (NumBlockActor) hit(touchPos.x, touchPos.y, false)) != null)
			{
			
				if(touchedBlock.Number == _correctNum)
				{
					_correctNum++;
					touchedBlock.Solved = true;
					
					if(MathUtils.randomBoolean())
						Assets.Correct1.play();
					else
						Assets.Correct2.play();
					
					if(touchedBlock.Number == _targetNum)
					{
						Assets.Success.play();
						Solved = true;
					}
					
					broadcast(new BlockSolvedTEvent(1, this));
				}
				else
				{
					Assets.Wrong.play();
					//broadcast(new LockBoardTEvent(1, this));
					_eventBroadcaster.broadcast(new LockBoardTEvent(1, NumBoardGroup.this));
					logger.debug("None.");
				}
			}
			return true;
		};
	};
	
	private void broadcast(TEvent event) 
	{
		event.setSender(this);
		_eventBroadcaster.broadcast(event);
	}
		
	public TEventBroadcaster getTEventBroadcaster() 
	{
		return _eventBroadcaster;
	}
	
	public void lock()
	{
		setTouchable(Touchable.disabled);
		for (int row = 0; row < _rowCount; row++) 
		{
			for (int col = 0; col < _colCount; col++) 
			{
				_slots[row][col].Locked = true;
			}
		}
	}
	
	public void unlock() 
	{
		setTouchable(Touchable.enabled);
		for (int row = 0; row < _rowCount; row++) 
		{
			for (int col = 0; col < _colCount; col++) 
			{
				_slots[row][col].Locked = false;
			}
		}
	}
	
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
