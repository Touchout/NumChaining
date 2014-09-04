package com.touchout.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Logger;

public class GameScreen extends ScreenAdapter 
{
	Logger logger = new Logger("game");
	final NumChaining GAME;
	//final NumBoard board;
	//final NumBoard board2;
	OrthographicCamera _camera;
	
	String _txtTime;
	float _gameTime;
	int _score;
	
	GameLevel _currentLevel;
	boolean _gameOver = false;
	
	
	
	public GameScreen(NumChaining game)
	{
		GAME = game;
		//board = new NumBoard(new Vector2(50,50), Config.COLUMN_COUNT, Config.ROW_COUNT);
		//board2 = new NumBoard(new Vector2(85,600), Config.COLUMN_COUNT, Config.ROW_COUNT);
		_currentLevel = new GameLevel(this, Config.ROW_COUNT, Config.COLUMN_COUNT);
		Gdx.input.setInputProcessor(_currentLevel);
		
		_camera = new OrthographicCamera();
		_camera.setToOrtho(false, Config.FRUSTUM_WIDTH, Config.FRUSTUM_HEIGHT);
		
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		logger.setLevel(Logger.DEBUG);
		
		_gameTime = 30;
		_score = 0;
		_txtTime = "";
		
		Assets.bgMusic.play();
	}
	
	@Override
	public void render(float delta) 
	{
		if(_gameTime <= 0)
		{
			if(_gameOver == false)
			{
				Assets.bgMusic.stop();
				Assets.endSound.play();
			}
			_gameOver = true;
			_currentLevel._finished = true;
		}
		else
		{
			if(_currentLevel._finished)
			{
				_currentLevel = new GameLevel(this, Config.ROW_COUNT, Config.COLUMN_COUNT);
				Gdx.input.setInputProcessor(_currentLevel);
				_currentLevel._playing = true;
			}
			
			//Update Game Time
			if(_currentLevel._playing)
			{
				_gameTime -= delta;
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
			
			//UpdateGame
			//if(_currentLevel.Update(_camera))
				//_score ++;
			
			//Clear
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			//draw board
			_camera.update();
			GAME.batch.setProjectionMatrix(_camera.combined);
			GAME.batch.begin();
			_currentLevel.Draw(GAME.batch);
			//draw time
			float posX = (Config.FRUSTUM_WIDTH - Assets.TimeFont.getMultiLineBounds(_txtTime).width) / 2;
			float posY = (Config.FRUSTUM_HEIGHT + Assets.TimeFont.getMultiLineBounds(_txtTime).height 
							+ Config.BOARD_UPPER_BOUND) /2;
			Assets.TimeFont.draw(GAME.batch, _txtTime, posX, posY);
			Assets.ScoreFont.draw(GAME.batch, String.format("%010d",_score), 20, 1260);
			
			GAME.batch.end();
			//board2.render(GAME.batch,true);
		}
	}
	
	 
}
