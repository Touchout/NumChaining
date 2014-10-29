package com.touchout.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Table.Debug;
import com.badlogic.gdx.utils.Logger;
import com.touchout.game.Assets;
import com.touchout.game.Config;
import com.touchout.game.GameMetadata;
import com.touchout.game.GameScreen;
import com.touchout.game.component.NumBoard;
import com.touchout.game.event.BlockSolvedTEvent;
import com.touchout.game.event.LockBoardTEvent;
import com.touchout.game.event.TEvent;
import com.touchout.game.event.TEventCode;

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
		GameMetadata metadata = _gameScreen.getMetadata();
		
		//time's up
		if(metadata.getGameTime() <= 0)
		{
			Assets.bgMusic.stop();
			Assets.endSound.play();
			
			_gameScreen.getResultingUI().setScore(metadata.getScoreString());
			int highScore = Assets.preferences.getInteger("highScore");
			if(metadata.getScore() > highScore)
			{
				Assets.preferences.putInteger("highScore", metadata.getScore());
				highScore = metadata.getScore();
				Assets.preferences.flush();
			}
			_gameScreen.getResultingUI().setHighScore(highScore);
			
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
		metadata.update(delta);
		
		if(_gameScreen.getBoard().isLocked())
			if(metadata.isPenaltyOver())
			{
				Assets.bgMusic.play();
				_gameScreen.getBoard().unlock();
			}
		
		return GameStateCode.Playing;
	}

	@Override
	public void handle(TEvent event) 
	{
		GameMetadata metadata = _gameScreen.getMetadata();
		
		if(event instanceof LockBoardTEvent)
		{			
			LockBoardTEvent realEvent = (LockBoardTEvent) event;
			if(realEvent.Time >= 0)
				metadata.setPenaltyTime(realEvent.Time);
			else {
				metadata.resetPenaltyTime();
			}
			((NumBoard)event.getSender()).lock();
			Assets.bgMusic.pause();
		}
		
		if(event instanceof BlockSolvedTEvent)
		{
			if(metadata.getRemainComboTime() == 0)
			{
				metadata.clearComboCount();
				metadata.resetLevel();
			}
			
			metadata.increaseComboCount();
			if(metadata.increaseComboBonusCount() == metadata.getComboBonusTarget())
			{
				Assets.Correct3.play();
				metadata.increasGameTime(2);
				_gameScreen.displayTimeBonusHint();
				metadata.clearComboBonusCount();
				metadata.nextLevel();
			}
			
			int reward = ((BlockSolvedTEvent) event).Reward * metadata.getComboCount();
			metadata.increaseScore(reward);
			
			metadata.resetRemainComboTime();
		}
		
		if(event.Tag == TEventCode.BoardSolved)
		{
			Assets.globalLogger.debug("BoardsCleared: " + metadata.getBoardsClearCount());
			Assets.globalLogger.debug("Level: " + metadata.getLevel());
//			if(metadata.increaseBoardsClearCount() % 3 == 0)
//			{
//				metadata.nextLevel();
//				Assets.globalLogger.debug("Level: " + metadata.getLevel());
//			}
		}
	}

	@Override
	protected GameStateCode setStateCode() 
	{
		return GameStateCode.Playing;
	}
}
