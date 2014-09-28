package com.touchout.game.scene2D;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table.Debug;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.touchout.game.Assets;
import com.touchout.game.Config;
import com.touchout.game.NumChaining;
import com.touchout.game.event.BlockSolvedTEvent;
import com.touchout.game.event.LockBoardTEvent;
import com.touchout.game.event.TEvent;
import com.touchout.game.event.TEventHandler;

public class GameScreen extends ScreenAdapter
{
	Logger logger = new Logger("game");
	final NumChaining GAME;
	OrthographicCamera _camera;
	
	//Game State
	GameState _gameState;
	
	//Stages
	GameStage _gameStage;
	Stage _uiStage;
	
	//Actors, Groups
	ResultingUI _resultingUI;
	
	public GameScreen(NumChaining game)
	{
		GAME = game;
		
		//Set Game State
		_gameState = new GameState();
		
		//Set Game Stage
		_gameStage = new GameStage(Config.ROW_COUNT, Config.COLUMN_COUNT, GAME.batch);
		_gameStage.getBoard().getTEventBroadcaster().addTEventHandler(_gameState);
		_gameStage.getBoard().getTEventBroadcaster().addTEventHandler(new TEventHandler() {			
			@Override
			public void handle(TEvent event) {
				if(event instanceof LockBoardTEvent)
				{
					_gameState.setPenalty(((LockBoardTEvent) event).Time);
					((NumBoardGroup)event.getSender()).lock();
				}
			}
		});
		_gameStage.addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) 
			{
				if(!_gameState.IsPaying())
					_gameState.setPlaying(true);
				return false;
			}
		});
		Gdx.input.setInputProcessor(_gameStage);
		
		//Set UI Stage
		_resultingUI = new ResultingUI();
		_resultingUI.setRestartListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) 
			{
			
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) 
			{
				restart();
			}
		});
		_uiStage = new Stage(new FitViewport(Config.FRUSTUM_WIDTH, Config.FRUSTUM_HEIGHT), GAME.batch);
		_uiStage.addActor(_resultingUI);
		
		//Set Camera
		_camera = new OrthographicCamera();
		_camera.setToOrtho(false, Config.FRUSTUM_WIDTH, Config.FRUSTUM_HEIGHT);
		
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		logger.setLevel(Logger.DEBUG);
		
		Assets.bgMusic.play();
	}
	
	private void restart() 
	{
		_gameState.initialize();
		_gameStage.renewBoard();
		_resultingUI.initialize();
		Gdx.input.setInputProcessor(_gameStage);
		Assets.bgMusic.play();
	}
	
	@Override
	public void resize(int width, int height) 
	{
		_gameStage.getViewport().update(width, height);
	}
	
	private void logicalProcess(float delta) 
	{
		//If game time's up, set game over 
		if(_gameState.getGameTime() <= 0)
		{
			if(!_gameState.isGameOver())
			{
				Assets.bgMusic.stop();
				Assets.endSound.play();
				
				_gameState.setGameOver(true);
				_resultingUI.setScore(_gameState.getScoreString());
				
				//Set input processor to UI stage, prevent touch to game board
				Gdx.input.setInputProcessor(_uiStage);
			}			
		}
		else
		{
			//If current board is solved, renew one to keep playing
			if(_gameStage.getBoardSolved())
			{
				_gameStage.renewBoard();
			}
			
			//Update Game Time
			if(_gameState.IsPaying())
			{
				_gameState.elapseGameTime(delta);
				
				if(_gameState.checkPenaltyOver(delta))
					_gameStage.getBoard().unlock();
			}
		}
	}
	
	@Override
	public void render(float delta) 
	{
		//Perform game logical process
		logicalProcess(delta);
		
		//Clear
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
		
		//Draw gaming component (board...etc)
		_camera.update();
		_gameStage.draw();
		_gameStage.act();
		
		//Draw UI
		if(_gameState.isGameOver())
		{
			_uiStage.draw();
			_uiStage.act();
		}
		
		//Draw HUDs (time, score..etc)
		GAME.batch.setProjectionMatrix(_camera.combined);		
		GAME.batch.begin();
		
		//draw time
		float posX = (Config.FRUSTUM_WIDTH - Assets.TimeFont.getMultiLineBounds(_gameState.getGameTimeString()).width) / 2;
		float posY = (Config.FRUSTUM_HEIGHT + Assets.TimeFont.getMultiLineBounds(_gameState.getGameTimeString()).height 
						+ Config.BOARD_UPPER_BOUND) /2;
		Assets.TimeFont.draw(GAME.batch, _gameState.getGameTimeString(), posX, posY);

		//draw score
		Assets.ScoreFont.draw(GAME.batch, String.format("%010d", _gameState.getScore()), 20, 1260);
		
		GAME.batch.end();
	}	 
}
