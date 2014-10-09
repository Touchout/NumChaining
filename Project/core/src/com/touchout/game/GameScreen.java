package com.touchout.game;

import java.util.Dictionary;
import java.util.HashMap;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table.Debug;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.touchout.game.component.NumBoardGroup;
import com.touchout.game.component.ResultingUI;
import com.touchout.game.event.BlockSolvedTEvent;
import com.touchout.game.event.LockBoardTEvent;
import com.touchout.game.event.TEvent;
import com.touchout.game.event.TEventBroadcaster;
import com.touchout.game.event.TEventCode;
import com.touchout.game.event.TEventHandler;
import com.touchout.game.states.GameStateCode;
import com.touchout.game.states.GameStateMachine;
import com.touchout.game.states.GameStater;
import com.touchout.game.states.PlayingStater;
import com.touchout.game.states.ResultingStater;

public class GameScreen extends ScreenAdapter
{
	Logger logger = new Logger("game");
	final NumChaining GAME;
	OrthographicCamera _camera;
	TEventBroadcaster _braodcaster;
	
	//Game State
	GameStateMachine _stateMachine;
	
	//Game metadata
	GameMetadata _metadata;
	
	//Stages
	Stage _gameStage;
	Stage _hudStage;
	Stage _uiStage;

	//Actors, Groups
	ResultingUI _resultingUI;
	NumBoardGroup _board;
	
	public NumBoardGroup getBoard() {
		return _board;
	}

	public GameMetadata getMetadata() {
		return _metadata;
	}

	public ResultingUI getResultingUI() {
		return _resultingUI;
	}
	
	public Stage getUiStage() {
		return _uiStage;
	}

	public GameScreen(NumChaining game)
	{
		GAME = game;
		
		//Set Game States
		_stateMachine = new GameStateMachine(this, GameStateCode.Intro);
		_braodcaster = new TEventBroadcaster();
		_braodcaster.addTEventHandler(_stateMachine);
		
		//Set Game Board
		//Position computing for centralize game board (block assumed to be square)
		//calculate length of (block+margin)
		int blockMarginSet = (Config.BOARD_UPPER_BOUND - Config.BLOCK_MARGIN) / Config.ROW_COUNT;
		//calculate block length
		int blockSize = blockMarginSet - Config.BLOCK_MARGIN;
		//calculate board width
		int boardWidth = blockMarginSet*Config.ROW_COUNT - Config.BLOCK_MARGIN;
		//calculate board horizontal padding
		int horizontalPadding = (Config.FRUSTUM_WIDTH - boardWidth) / 2;
		
		_board = new NumBoardGroup(new Vector2(horizontalPadding, Config.BLOCK_MARGIN), Config.COLUMN_COUNT, Config.ROW_COUNT, blockSize, blockSize);
		_board.getTEventBroadcaster().addTEventHandler(_stateMachine);
		
		//Set Game Metadata
		_metadata = new GameMetadata();
		
		//Set Game Stage
		_gameStage = new Stage(new FitViewport(Config.FRUSTUM_WIDTH, Config.FRUSTUM_HEIGHT), GAME.batch);
		_gameStage.addActor(_board);
		_gameStage.addActor(_metadata);
		_gameStage.addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) 
			{
				TEvent startPlayingEvent = new TEvent(this);
				startPlayingEvent.Tag = TEventCode.StartPlaying;
				_braodcaster.broadcast(startPlayingEvent);
				return false;
			}
		});
		Gdx.input.setInputProcessor(_gameStage);
		
		//Set Resulting UI
		_resultingUI = new ResultingUI();
		_resultingUI.getBroadcaster().addTEventHandler(_stateMachine);

		//Set UI Stage
		_uiStage = new Stage(new FitViewport(Config.FRUSTUM_WIDTH, Config.FRUSTUM_HEIGHT), GAME.batch);
		_uiStage.addActor(_resultingUI);
		
		//Set Camera
		_camera = new OrthographicCamera();
		_camera.setToOrtho(false, Config.FRUSTUM_WIDTH, Config.FRUSTUM_HEIGHT);
		
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		logger.setLevel(Logger.DEBUG);
		
		Assets.bgMusic.play();
	}
	
	public void restart() 
	{
		_metadata.initialize();
		_board.reinitial();
		_board.shuffle();
		_resultingUI.initialize();
		Gdx.input.setInputProcessor(_gameStage);
		Assets.bgMusic.play();
	}
	
	@Override
	public void resize(int width, int height) 
	{
		_gameStage.getViewport().update(width, height);
	}
	
	@Override
	public void render(float delta) 
	{
		//Perform game logical process
		_stateMachine.update(delta);
		
		//Clear
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
		
		//Draw gaming component (board...etc)
		_camera.update();
		_gameStage.draw();
		_gameStage.act();
		
		//Draw UI
		if(_stateMachine.getCurrentStateCode() == GameStateCode.Resulting)
		{
			_uiStage.draw();
			_uiStage.act();
		}
		
		//Draw HUDs (time, score..etc)
		GAME.batch.setProjectionMatrix(_camera.combined);		
		GAME.batch.begin();
		
		//draw time
		float posX = (Config.FRUSTUM_WIDTH - Assets.TimeFont.getMultiLineBounds(_metadata.getGameTimeString()).width) / 2;
		float posY = (Config.FRUSTUM_HEIGHT + Assets.TimeFont.getMultiLineBounds(_metadata.getGameTimeString()).height 
						+ Config.BOARD_UPPER_BOUND) /2;
		Assets.TimeFont.draw(GAME.batch, _metadata.getGameTimeString(), posX, posY);

		//draw score
		Assets.ScoreFont.draw(GAME.batch, String.format("%010d", _metadata.getScore()), 20, 1260);
		
		GAME.batch.end();
	}	 
}