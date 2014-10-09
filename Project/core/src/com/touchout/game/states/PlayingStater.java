package com.touchout.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Table.Debug;
import com.badlogic.gdx.utils.Logger;
import com.touchout.game.Assets;
import com.touchout.game.GameScreen;
import com.touchout.game.component.NumBoardGroup;
import com.touchout.game.event.BlockSolvedTEvent;
import com.touchout.game.event.LockBoardTEvent;
import com.touchout.game.event.TEvent;

public class PlayingStater extends GameStater 
{
	GameScreen _gameScreen;
	public PlayingStater(Screen screen) {
		super(screen);
		_gameScreen = (GameScreen) _screen;
	}
	
	@Override
	public GameStateCode update(float delta) 
	{
		//time's up
		if(_gameScreen.getMetadata().getGameTime() <= 0)
		{
			Assets.bgMusic.stop();
			Assets.endSound.play();
			
			_gameScreen.getResultingUI().setScore(_gameScreen.getMetadata().getScoreString());
			
			//Set input processor to UI stage, prevent touch to game board
			Gdx.input.setInputProcessor(_gameScreen.getUiStage());
			return GameStateCode.Resulting;
		}
		
		//If current board is solved, renew one to keep playing
		if(_gameScreen.getBoard().Solved)
		{
			_gameScreen.getBoard().reinitial();
			_gameScreen.getBoard().shuffle();
		}
		
		//Update Game Time
		_gameScreen.getMetadata().elapseGameTime(delta);
		
		if(_gameScreen.getMetadata().checkPenaltyOver(delta))
			_gameScreen.getBoard().unlock();
		
		return GameStateCode.Playing;
	}

	@Override
	public void handle(TEvent event) 
	{
		if(event instanceof LockBoardTEvent)
		{			
			_gameScreen.getMetadata().setPenalty(((LockBoardTEvent) event).Time);
			((NumBoardGroup)event.getSender()).lock();
		}
		
		if(event instanceof BlockSolvedTEvent)
		{
			if(_gameScreen.getMetadata().getRemainComboTime() == 0)
			{
				_gameScreen.getMetadata().clearComboCount();
			}
			
			_gameScreen.getMetadata().increaseComboCount();
			
			int reward = ((BlockSolvedTEvent) event).Reward * _gameScreen.getMetadata().getComboCount();
			_gameScreen.getMetadata().increaseScore(reward);
			
			_gameScreen.getMetadata().setRemainComboTime(1);
			
			Assets.globalLogger.debug("Combo: " + _gameScreen.getMetadata().getComboCount());
		}
	}

	@Override
	protected GameStateCode setStateCode() 
	{
		return GameStateCode.Playing;
	}
}
