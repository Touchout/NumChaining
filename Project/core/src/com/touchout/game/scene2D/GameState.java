package com.touchout.game.scene2D;

import com.touchout.game.event.BlockSolvedTEvent;
import com.touchout.game.event.TEvent;
import com.touchout.game.event.TEventHandler;

public class GameState implements TEventHandler
{
	String _txtTime;
	float _gameTime;
	int _score;
	boolean _gameOver;
	boolean _playing, _finished;
	float _remainPenaltyTime;
	
	public GameState()
	{
		initialize();
	}
	
	public void initialize() 
	{
		_score = 0;
		_gameTime = 30;
		_gameOver = false;
		_txtTime = "";
		_playing = false;
		_finished = false;
		_remainPenaltyTime = 0;
	}
	
	public boolean IsPaying() {
		return _playing;
	}
	
	public void setPlaying(boolean value) {
		_playing = value;
	}

	public boolean IsFinished() {
		return _finished;
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

	public boolean isGameOver() {
		return _gameOver;
	}

	public void setGameOver(boolean _gameOver) {
		this._gameOver = _gameOver;
	}

	@Override
	public void handle(TEvent event) 
	{
		if(event instanceof BlockSolvedTEvent)
			_score += ((BlockSolvedTEvent)event).Reward;
	}
}
