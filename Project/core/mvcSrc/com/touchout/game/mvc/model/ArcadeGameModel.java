package com.touchout.game.mvc.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.touchout.game.mvc.core.ArcadeMetadata;
import com.touchout.game.mvc.core.Assets;
import com.touchout.game.mvc.core.GlobalConfig;
import com.touchout.game.mvc.core.NumChaining;
import com.touchout.game.mvc.event.GameEvent;
import com.touchout.game.mvc.event.GameEventArg;
import com.touchout.game.mvc.view.actor.NumBlock;

public class ArcadeGameModel 
{	
	public enum GameState {Playing, Resulting};
	
	NumBoardEntity _boardEntity;
	ArcadeMetadata _metadata;
	private int _targetNumber, _goalNumber; 
	private GameState _state;
	private GameEvent _gameOverEvent = new GameEvent();
	private GameEvent _blockSolvedEvent = new GameEvent();
	private GameEvent _boardUnlockEvent = new GameEvent();

	public ArcadeMetadata getMetadata() {	return _metadata;}

	public NumBoardEntity getBboardEntity() { return _boardEntity; }

	public ArcadeGameModel()	
	{
		initialize();
	}
	
	public void update(float delta)
	{
		_metadata.update(delta);
		if(_boardEntity.isLocked())
		{
			if(_metadata.isPenaltyOver())
			{
				_boardEntity.unlock();
				_boardUnlockEvent.fire(new GameEventArg(null, _targetNumber));
			}
		}
		
		if(_metadata.getGameTime() <= 0 && _state == GameState.Playing)
		{
			_state = GameState.Resulting;
			_gameOverEvent.fire(new GameEventArg(null));
		}
	}
	
	private void initialize()
	{
		//initial target number
		_targetNumber = 1;
		_goalNumber = GlobalConfig.ROW_COUNT * GlobalConfig.COLUMN_COUNT;
		
		//init board
		_boardEntity = new NumBoardEntity(GlobalConfig.ROW_COUNT, GlobalConfig.COLUMN_COUNT);
		_boardEntity.shuffle();
		
		//init metadata
		_metadata = new ArcadeMetadata();
		_metadata.initialize();
		
		//init state
		_state = GameState.Playing;
	}
	
	public void reset() 
	{
		_metadata.initialize();
		_boardEntity.renew();
		_boardEntity.shuffle();
		_targetNumber = 1;
		_goalNumber = GlobalConfig.ROW_COUNT * GlobalConfig.COLUMN_COUNT;		
		_state = GameState.Playing;
	}
	
	public void touchBlock(int row, int col) 
	{
		//Press on correct block
		if(_boardEntity.getCells()[row][col].Number == _targetNumber)
		{
			Assets.PressSound.play();
			
			_metadata.increaseScore(1);
			_metadata.resetRemainComboTime();
			_metadata.increaseComboCount();
			_metadata.increaseComboBonusCount();
			_boardEntity.solveBlock(row, col);
			_targetNumber++;
		
//			if(_metadata.getComboCount() % 2 == 0 && _metadata.getComboCount() > 0)
//			{
//				_metadata.nextLevel();
//			}
			
			if(_boardEntity.getCells()[row][col].Number == _goalNumber)
			{
				_boardEntity.renew();
				_boardEntity.shuffle();
				_targetNumber = 1;
			}
						
			_blockSolvedEvent.fire(new GameEventArg(null, new Vector2(row, col)));
		}
		else //Press on incorrect block
		{
			Assets.AlarmSound.play();
			
			_metadata.clearComboCount();
			_metadata.clearComboBonusCount();
			_metadata.resetPenaltyTime();
			_metadata.resetLevel();
			_boardEntity.lock();
		}
	}
	
	//******************** Getters & Setters  ********************//
	
	public GameState getState() {
		return _state;
	}
	
	public GameEvent getGameOverEvent() {
		return _gameOverEvent;
	}
	
	public GameEvent getBlockSolvedEvent() {
		return _blockSolvedEvent;
	}
	
	public GameEvent getBoardUnlockEvent() {
		return _boardUnlockEvent;
	}
}
