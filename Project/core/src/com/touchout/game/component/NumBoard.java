package com.touchout.game.component;

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
import com.touchout.game.event.BlockSolvedTEvent;
import com.touchout.game.event.LockBoardTEvent;
import com.touchout.game.event.TEvent;
import com.touchout.game.event.TEventBroadcaster;
import com.touchout.game.event.TEventCode;
import com.touchout.game.event.ITEventHandler;
import com.touchout.game.mvc.Assets;

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
	private int _targetNum;
	private int _correctNum;
	private NumBlock[][] _slots;
	private NumBlock touchedBlock;
	private TEventBroadcaster _eventBroadcaster;
	private boolean _locked = false;
	
	public boolean isLocked() {
		return _locked;
	}

	public boolean Solved = false;

	public NumBoard(Vector2 pos, int colCount, int rowCount, float blockSize, float blockSize2)
	{
		_position = pos;
		_colCount = colCount;
		_rowCount = rowCount;
		_blockWidth = blockSize;
		_blockHeight = blockSize2;
		_slots = new NumBlock[_rowCount][_colCount];
		_targetNum = colCount * rowCount;
		_correctNum = 1;
		_eventBroadcaster = new TEventBroadcaster();
		
		addListener(inputListener);
		InitialFill();
		shuffle();
	}
	
	public void reinitial()
	{
		setTouchable(Touchable.enabled);
		Solved = false;
		_correctNum = 1;
		for (NumBlock[] numBlockActors : _slots) {
			for (NumBlock numBlockActor : numBlockActors) {
				numBlockActor.reinitial();
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
				_slots[row][col] = new NumBlock(num,_position.x + col*(_blockHeight+_horizontalMargin)
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
			
			if((touchedBlock = (NumBlock) hit(touchPos.x, touchPos.y, false)) != null)
			{
				if(touchedBlock.Number == _correctNum)
				{
					//按到正確block
					_correctNum++;
					touchedBlock.Solved = true;
					
					//播放音效
					int idx = MathUtils.random(0, 1); 
					if( idx == 0)
						Assets.Get.play();
					else if(idx == 1)
						Assets.Get2.play();
					//else if(idx == 2)
						//Assets.Get3.play();
					
					//Assets.Click.play();
//					if(MathUtils.randomBoolean())
//						Assets.Correct1.play();
//					else
//						Assets.Correct2.play();
					
					//確認是否已是最後一個block
					if(touchedBlock.Number == _targetNum)
					{
						Assets.Success.play();
						Solved = true;
						broadcast(new TEvent(this, TEventCode.BoardSolved));
					}
					
					//廣播正確按到block事件
					broadcast(new BlockSolvedTEvent(1, this));
				}
				else
				{
					//按到錯誤block
					Assets.Wrong.play();
					//broadcast(new LockBoardTEvent(1, this));
					_eventBroadcaster.broadcast(new LockBoardTEvent(NumBoard.this));
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
		_locked = true;
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
		_locked = false;
	}
	
	public void shuffle() 
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
