package com.touchout.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Logger;

public class GameLevel extends InputAdapter
{
	Logger logger = new Logger("level");
	NumBlock touchedBlock;
	
	GameScreen _screen;
	NumBoard _board;
	boolean _playing, _finished;
	int _targetNum;
	
	public GameLevel(GameScreen screen, int rowCount, int colCount)
	{
		_screen = screen;
		int blockMarginSet = (Config.BOARD_UPPER_BOUND - Config.BLOCK_MARGIN) / rowCount;
		int blockSize = blockMarginSet - Config.BLOCK_MARGIN;
		int boardWidth = blockMarginSet*rowCount - Config.BLOCK_MARGIN;
		int horizontalPadding = (Config.FRUSTUM_WIDTH - boardWidth) / 2;
		
		_board = new NumBoard(new Vector2(horizontalPadding, Config.BLOCK_MARGIN), colCount, rowCount, blockSize, blockSize);
		//_board._blockWidth = _board._blockHeight = blockSize;
		
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
		if(!_playing)
			_playing = true;
		
		Vector3 touchPos = new Vector3();
		touchPos.set(screenX, screenY, 0);
		_screen._camera.unproject(touchPos);
		
		if((touchedBlock = _board.GetTouchedBlock(new Vector2(touchPos.x,touchPos.y))) != null)
		{
			logger.debug(String.valueOf(touchedBlock.Number));
			
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
		}
		else
		{
			Assets.Wrong.play();
			logger.debug("None.");
		}
		return true;
	}
	
	public boolean Update(OrthographicCamera camera)
	{
		if(Gdx.input.justTouched() && !_finished)
		{
			if(!_playing)
				_playing = true;
			
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			
			if((touchedBlock = _board.GetTouchedBlock(new Vector2(touchPos.x,touchPos.y))) != null)
			{
				logger.debug(String.valueOf(touchedBlock.Number));
				
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
				
				return true;
			}
			else
			{
				Assets.Wrong.play();
				logger.debug("None.");
			}
			
		}
		return false;
	}
	
	public void Draw(SpriteBatch batch)
	{
		_board.draw(batch);
	}
}
