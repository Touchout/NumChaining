package com.touchout.game.states.arcade;

import com.touchout.game.ArcadeMetadata;
import com.touchout.game.ArcadeModeComponentPackage;
import com.touchout.game.component.NumBoard;
import com.touchout.game.event.BlockSolvedTEvent;
import com.touchout.game.event.LockBoardTEvent;
import com.touchout.game.event.TEvent;
import com.touchout.game.event.TEventCode;
import com.touchout.game.item.Item;
import com.touchout.game.item.ItemManager;
import com.touchout.game.mvc.Assets;
import com.touchout.game.states.GameStateCode;
import com.touchout.game.states.GameStater;

public class PlayingStater extends ArcadeGameStater 
{	
	public PlayingStater(ArcadeModeComponentPackage pack, ArcadeMetadata metadata) 
	{
		super(pack, metadata);
	}

	@Override
	public GameStateCode update(float delta) 
	{				
		//time's up
		if(_arcadeMetadata.getGameTime() <= 0)
		{
			Assets.bgMusic.stop();
			Assets.endSound.play();
			
			_arcadePackage.getResultingUI().setScore(_arcadeMetadata.getScoreString());
			int highScore = Assets.preferences.getInteger("highScore");
			if(_arcadeMetadata.getScore() > highScore)
			{
				Assets.preferences.putInteger("highScore", _arcadeMetadata.getScore());
				highScore = _arcadeMetadata.getScore();
				Assets.preferences.flush();
			}
			_arcadePackage.getResultingUI().setHighScore(highScore);
			
			//Set input processor to UI stage, prevent touch to game board
			return GameStateCode.Resulting;
		}
		
		//If current board is solved, renew one to keep playing
		if(_arcadePackage.getBoard().Solved)
		{
			_arcadePackage.getBoard().reinitial();
			_arcadePackage.getBoard().shuffle();
		}
		
		//Update Game Time
		_arcadeMetadata.update(delta);
		
		if(_arcadePackage.getBoard().isLocked())
			if(_arcadeMetadata.isPenaltyOver())
			{
				Assets.bgMusic.play();
				_arcadePackage.getBoard().unlock();
			}
		
		return GameStateCode.Playing;
	}

	@Override
	public void handle(TEvent event) 
	{		
		if(event instanceof LockBoardTEvent)
		{			
			LockBoardTEvent realEvent = (LockBoardTEvent) event;
			if(realEvent.Time >= 0)
				_arcadeMetadata.setPenaltyTime(realEvent.Time);
			else {
				_arcadeMetadata.resetPenaltyTime();
			}
			((NumBoard)event.getSender()).lock();
			Assets.bgMusic.pause();
		}
		
		if(event instanceof BlockSolvedTEvent)
		{
			if(_arcadeMetadata.getRemainComboTime() == 0)
			{
				_arcadeMetadata.clearComboCount();
				_arcadeMetadata.resetLevel();
			}
			
			_arcadeMetadata.increaseComboCount();
			if(_arcadeMetadata.increaseComboBonusCount() == _arcadeMetadata.getComboBonusTarget())
			{
				Assets.Correct3.play();
				_arcadeMetadata.increasGameTime(2);
				_arcadePackage.displayTimeBonusHint();
				_arcadeMetadata.clearComboBonusCount();
				_arcadeMetadata.nextLevel();
			}
			
			int reward = ((BlockSolvedTEvent) event).Reward * _arcadeMetadata.getComboCount();
			_arcadeMetadata.increaseScore(reward);
			
			_arcadeMetadata.resetRemainComboTime();
		}
		
		if(event.Tag == TEventCode.BoardSolved)
		{
			Assets.globalLogger.debug("BoardsCleared: " + _arcadeMetadata.getBoardsClearCount());
			Assets.globalLogger.debug("Level: " + _arcadeMetadata.getLevel());
//			if(metadata.increaseBoardsClearCount() % 3 == 0)
//			{
//				metadata.nextLevel();
//				Assets.globalLogger.debug("Level: " + metadata.getLevel());
//			}
		}
	}

	@Override
	protected GameStateCode defineStateCode() 
	{
		return GameStateCode.Playing;
	}
}
