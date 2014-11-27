package com.touchout.game.mvc;

import com.badlogic.gdx.math.MathUtils;
import com.touchout.game.Config;
import com.touchout.game.event.BlockSolvedTEvent;
import com.touchout.game.event.TEvent;
import com.touchout.game.event.ITEventHandler;

public class ArcadeMetadata implements ITEventHandler
{
	private String _txtTime;
	private float _gameTime;
	private int _score;
	private float _specPenaltyTime;
	private float _remainPenaltyTime;
	private float _specComboTime;
	private float _remainComboTime;
	private int _comboCount;
	private int _comboBonusCount;
	private int _level;
	private int _boardsClearCount;
	private int _comboBonusTarget;
	
	public int getCcomboBonusCount() {
		return _comboBonusCount;
	}

	public void setComboBonusCount(int _comboBonusCount) {
		this._comboBonusCount = _comboBonusCount;
	}
	
	public int increaseComboBonusCount() {
		this._comboBonusCount++;
		return _comboBonusCount;
	}
	
	public void clearComboBonusCount() {
		this._comboBonusCount = 0;
	}
	
	public int getComboBonusTarget() {
		return _comboBonusTarget;
	}

	public void setComboBonusTarget(int _comboBonusTarget) {
		this._comboBonusTarget = _comboBonusTarget;
	}

	
	public int increaseBoardsClearCount()
	{
		_boardsClearCount++;
		return _boardsClearCount;
	}
	
	public int getBoardsClearCount()
	{
		return _boardsClearCount;
	}
	
	public void resetLevel() 
	{
		_level = 1;
		_specComboTime = 2.0f;
	}
	
	public int getLevel() {
		return _level;
	}
	
	public void nextLevel()
	{
		_level++;
		_specComboTime = MathUtils.clamp(_specComboTime - 0.2f, 0.2f, Float.MAX_VALUE);
	}
	
	public int getComboCount() {
		return _comboCount;
	}

	public int increaseComboCount() 
	{
		_comboCount++;
		return _comboCount;
	}
	
	public void clearComboCount() 
	{
		_comboCount = 0;
	}

	public float getRemainComboTime() {
		return _remainComboTime;
	}

	public void resetRemainComboTime() 
	{
		_remainComboTime = _specComboTime;
	}
	
	public void setRemainComboTime(float remainComboTime) 
	{
		_remainComboTime = remainComboTime;
	}
	
	public ArcadeMetadata()
	{
		initialize();
	}
	
	public void initialize() 
	{
		_score = 0;
		_gameTime = Config.DEFUALT_GAME_TIME;
		_txtTime = "";
		_specPenaltyTime = 0.5f;
		_boardsClearCount = 0;
		_comboBonusTarget = 10;
		setPenaltyTime(0);
		resetRemainComboTime();
		clearComboCount();
		clearComboBonusCount();
		resetLevel();
	}

	public String getGameTimeString() 
	{
		return _txtTime;
	}

	public void increasGameTime(int value)
	{
		_gameTime += value;				
	}
	
	public int getGameTime() {
		return (int)_gameTime;
	}

	public void update(float delta) 
	{
		
		this._gameTime = MathUtils.clamp(_gameTime - delta, 0, Float.MAX_VALUE);
		int minutes = (int)(_gameTime / 60.0);
		int seconds = (int)(_gameTime - minutes * 60);
		_txtTime = "" + minutes;
		if (seconds < 10) {
			_txtTime += ":0" + seconds;
		}
		else {
			_txtTime += ":" + seconds;
		}
		
		//update penalty time
		setPenaltyTime(MathUtils.clamp(_remainPenaltyTime - delta, 0, Float.MAX_VALUE));
		
		//update combo time, combo count
		setRemainComboTime(MathUtils.clamp(this.getRemainComboTime() - delta, 0, Float.MAX_VALUE));
		if(this.getRemainComboTime() <= 0) clearComboCount();
	}
	
	public boolean isPenaltyOver() 
	{		
		return _remainPenaltyTime <= 0;
	}
	
	public void resetPenaltyTime() 
	{
		_remainPenaltyTime = _specPenaltyTime;
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
