package com.touchout.game.scene2D;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.touchout.game.Assets;
import com.touchout.game.Config;
import com.touchout.game.NumBlock;
import com.touchout.game.NumBoard;

public class GameLevelExtStage extends Stage
{
	Logger logger = new Logger("level");
	NumBlockActor touchedBlock;

	NumBoardGroup _board;
	boolean _playing, _finished;
	int _targetNum;
	int _correctNum;
	int Score;
	
	public GameLevelExtStage(SpriteBatch batch, int rowCount, int colCount)
	{
		super(new FitViewport(Config.FRUSTUM_WIDTH, Config.FRUSTUM_HEIGHT), batch);
		

		int blockMarginSet = (Config.BOARD_UPPER_BOUND - Config.BLOCK_MARGIN) / rowCount;
		int blockSize = blockMarginSet - Config.BLOCK_MARGIN;
		int boardWidth = blockMarginSet*rowCount - Config.BLOCK_MARGIN;
		int horizontalPadding = (Config.FRUSTUM_WIDTH - boardWidth) / 2;
		
		_board = new NumBoardGroup(new Vector2(horizontalPadding, Config.BLOCK_MARGIN), colCount, rowCount, blockSize, blockSize);
		this.addActor(_board);
		logger.setLevel(Application.LOG_DEBUG);
		//_board._blockWidth = _board._blockHeight = blockSize;
		
		Score = 0;
		_correctNum = 1;
		_targetNum = rowCount * colCount;
		_playing = false;
		_finished = false;
	}
	
	public boolean IsPaying() {
		return _playing;
	}

	public boolean IsFinished() {
		return _finished;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) 
	{		
		if(_finished)
			return true;
		
		if(!_playing)
			_playing = true;
		
		Vector2 touchPos = new Vector2();
		touchPos.set(screenX, screenY);
		screenToStageCoordinates(touchPos);
		logger.debug(touchPos.x + "," + touchPos.y);
		
		if((touchedBlock = (NumBlockActor) _board.hit(touchPos.x, touchPos.y, false)) != null)
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
					_playing = false;
					_finished = true;
					Assets.Success.play();
				}
				
				Score++;
			}
			else
			{
				Assets.Wrong.play();
				logger.debug("None.");
			}
		}
		return true;
	}
	
	
//	public void Draw(SpriteBatch batch)
//	{
//		//_board.draw(batch);
//		_board.draw(batch, 1);
//	}
}
