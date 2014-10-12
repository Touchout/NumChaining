package com.touchout.game;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.touchout.game.component.ComboBar;
import com.touchout.game.component.ComboCounter;
import com.touchout.game.event.BlockSolvedTEvent;
import com.touchout.game.event.TEvent;
import com.touchout.game.event.TEventHandler;

public class GameMetadata extends Group implements TEventHandler
{
	private String _txtTime;
	private float _gameTime;
	private int _score;
	private float _remainPenaltyTime;
	private float _remainComboTime;
	
	private ComboCounter _comboCounter;
	private ComboBar _comboBar;
	
	public int getComboCount() {
		return _comboCounter.ComboCount;
	}

	public void increaseComboCount() 
	{
		_comboCounter.ComboCount++;
	}
	
	public void clearComboCount() 
	{
		_comboCounter.ComboCount = 0;
	}

	public float getRemainComboTime() {
		return _remainComboTime;
	}

	public void setRemainComboTime(float remainComboTime) 
	{
		this._remainComboTime = remainComboTime;
		this._comboBar.Current = remainComboTime;
	}

	public GameMetadata()
	{
		_comboCounter = new ComboCounter(450, Config.BOARD_UPPER_BOUND + 50);
		_comboBar = new ComboBar(10, Config.BOARD_UPPER_BOUND + 100, Config.FRUSTUM_WIDTH - 30, 20);
		_comboBar.Max = 1;
		_comboBar.Current = 0;
		addActor(_comboCounter);
		addActor(_comboBar);
		setTouchable(Touchable.disabled);
		initialize();
	}
	
	public void initialize() 
	{
		_score = 0;
		_gameTime = Config.DEFUALT_GAME_TIME;
		_txtTime = "";
		this.setPenaltyTime(0);
		this.setRemainComboTime(1);
		_comboCounter.ComboCount = 0;
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
		float remainComboTime = this.getRemainComboTime() - delta;
		this.setRemainComboTime(remainComboTime < 0 ? 0 : remainComboTime);
		if(this.getRemainComboTime() <= 0) clearComboCount();
	}
	
	public boolean isPenaltyOver(float delta) 
	{
		if(_remainPenaltyTime > 0)
		{
			_remainPenaltyTime -= delta;
			if(_remainPenaltyTime <= 0)
				return true;
		}
		
		return false;
	}
	
	public void setPenaltyTime(float value) 
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
		{
			_score += ((BlockSolvedTEvent)event).Reward;
			//_comboBar.reset();
		}
	}
}
