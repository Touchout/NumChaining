package com.touchout.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.touchout.game.component.ComboCounter;
import com.touchout.game.event.BlockSolvedTEvent;
import com.touchout.game.event.TEvent;
import com.touchout.game.event.TEventCode;
import com.touchout.game.event.TEventHandler;

public class GameMetadata extends Group implements TEventHandler
{
	String _txtTime;
	float _gameTime;
	int _score;
	float _remainPenaltyTime;
	float _remainComboTime;
	
	ComboCounter comboCounter;
	
	public int getComboCount() {
		return comboCounter.ComboCount;
	}

	public void increaseComboCount() 
	{
		comboCounter.ComboCount++;
	}
	
	public void clearComboCount() {
		comboCounter.ComboCount = 0;
	}

	public float getRemainComboTime() {
		return _remainComboTime;
	}

	public void setRemainComboTime(float _remainComboTime) {
		this._remainComboTime = _remainComboTime;
	}

	public GameMetadata()
	{
		comboCounter = new ComboCounter(450, Config.BOARD_UPPER_BOUND + 50);
		addActor(comboCounter);
		setTouchable(Touchable.disabled);
		initialize();
	}
	
	public void initialize() 
	{
		_score = 0;
		_gameTime = Config.DEFUALT_GAME_TIME;
		_txtTime = "";
		_remainPenaltyTime = 0;
		_remainComboTime = 0;
		comboCounter.ComboCount = 0;
	}

	public String getGameTimeString() 
	{
		return _txtTime;
	}

	public int getGameTime() {
		return (int)_gameTime;
	}

	public void elapseGameTime(float delta) 
	{
		this._gameTime -= delta;
		int minutes = (int)(_gameTime / 60.0);
		int seconds = (int)(_gameTime - minutes * 60);
		_txtTime = "" + minutes;
		if (seconds < 10) {
			_txtTime += ":0" + seconds;
		}
		else {
			_txtTime += ":" + seconds;
		}
		
		//update combo time
		_remainComboTime -= delta;
		_remainComboTime = _remainComboTime < 0 ? 0 : _remainComboTime;
		if(_remainComboTime <= 0) clearComboCount();
	}
	
	public boolean checkPenaltyOver(float delta) 
	{
		if(_remainPenaltyTime > 0)
		{
			_remainPenaltyTime -= delta;
			if(_remainPenaltyTime <= 0)
				return true;
		}
		
		return false;
	}
	
	public void setPenalty(float value) 
	{
		_remainPenaltyTime = value;
	}

	public int getScore() 
	{
		return _score;
	}
	
	public String getScoreString() 
	{
		return String.valueOf(_score);
	}

	public void increaseScore(int reward) 
	{
		this._score += reward;		
	}

	@Override
	public void handle(TEvent event) 
	{
		if(event instanceof BlockSolvedTEvent)
			_score += ((BlockSolvedTEvent)event).Reward;
	}
}
