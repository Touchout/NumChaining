package com.touchout.game.mvc.view;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sun.prism.GraphicsPipeline.ShaderType;
import com.touchout.game.mvc.controller.ArcadeGameController;
import com.touchout.game.mvc.core.Assets;
import com.touchout.game.mvc.core.GlobalConfig;
import com.touchout.game.mvc.core.NumChaining;
import com.touchout.game.mvc.event.GameEventArg;
import com.touchout.game.mvc.event.IGameEventHandler;
import com.touchout.game.mvc.model.ArcadeGameModel;
import com.touchout.game.mvc.model.ArcadeGameModel.GameState;
import com.touchout.game.mvc.view.actor.ProgressBar;
import com.touchout.game.mvc.view.actor.NumBlock;
import com.touchout.game.mvc.view.actor.NumBoard;
import com.touchout.game.mvc.view.actor.ResultPanel;
import com.touchout.game.mvc.view.actor.TextActor;

public class ArcadeGameView 
{
	NumChaining _game;
	ArcadeGameModel _model;
	ArcadeGameController _controller;
	
	Logger logger = new Logger("ArcadeGameView");
	//OrthographicCamera _camera;

	//Stages
	Stage _gameStage;
	Stage _uiStage;
	
	//Actors
	NumBoard _board;
	TextActor _gameTimeCounter;
	TextActor _comboCounter;
	ProgressBar _comboMeter, _boostMeter;
	ResultPanel _resultPanel;
	
	public ArcadeGameView(ArcadeGameModel model, ArcadeGameController controller)
	{
		_model = model;
		_controller = controller;
		_game = controller.getGame();
		
		initializeActors();
		
		//Set Game Stage
		_gameStage = new Stage(new FitViewport(GlobalConfig.FRUSTUM_WIDTH, GlobalConfig.FRUSTUM_HEIGHT), _game.batch);
		_gameStage.addActor(_board);
		_gameStage.addActor(_gameTimeCounter);
		_gameStage.addActor(_comboCounter);
		_gameStage.addActor(_comboMeter);
		_gameStage.addActor(_boostMeter);
		Gdx.input.setInputProcessor(_gameStage);
		
		//Set UI Stage
		_uiStage = new Stage(new FitViewport(GlobalConfig.FRUSTUM_WIDTH, GlobalConfig.FRUSTUM_HEIGHT), _game.batch);
		_uiStage.addActor(_resultPanel);
		
		//Register Events
		_model.getGameOverEvent().addTEventHandler(new IGameEventHandler() {			
			@Override
			public void handle(GameEventArg event) { switchToResulting(); }
		});
		
		//Set Camera
		//_camera = new OrthographicCamera();
		//_camera.setToOrtho(false, Config.FRUSTUM_WIDTH, Config.FRUSTUM_HEIGHT);
		
		//Set logger
		logger.setLevel(Logger.DEBUG);
	}
	
	private void initializeActors() 
	{
		float boardPosX, timerPosX, cCounterPosX, comboBarPosX, boostMeterPosX;
		float boardPosY, timerPosY, cCounterPosY, comboBarPosY, boostMeterPosY;
		float boardWidth,boardHeight, timerWidth, cCounterWidth, comboBarWidth, comboBarHeight, boostMeterWidth, boostMeterHeight;
		
		//*** Compute centralize position ***//
		//Board
		boardWidth = GlobalConfig.BLOCK_WIDTH * GlobalConfig.COLUMN_COUNT + (GlobalConfig.COLUMN_COUNT-1) * GlobalConfig.BLOCK_MARGIN;
		boardHeight = GlobalConfig.BLOCK_HEIGHT * GlobalConfig.ROW_COUNT + (GlobalConfig.ROW_COUNT-1) * GlobalConfig.BLOCK_MARGIN;
		boardPosX= (GlobalConfig.FRUSTUM_WIDTH - boardWidth) / 2;
		boardPosY = (GlobalConfig.BOARD_UPPER_BOUND - boardHeight) / 2;
		
		//Timer
		timerWidth = Assets.TimeFont.getBounds("00:00").width;
		timerPosX = (GlobalConfig.FRUSTUM_WIDTH - timerWidth) / 2;
		timerPosY = GlobalConfig.TIMER_VPOSITION;
		
		//ComboCounter
		cCounterWidth = Assets.ComboFont.getBounds("   ").width;
		cCounterPosX = boardPosX + boardWidth - cCounterWidth;
		cCounterPosY = GlobalConfig.BOARD_UPPER_BOUND + 50;
		
		//ComboBar
		comboBarWidth = boardWidth;
		comboBarHeight = 15;
		comboBarPosX = boardPosX;
		comboBarPosY = GlobalConfig.BOARD_UPPER_BOUND +100;
		
		//BoostMeter
		boostMeterWidth = cCounterPosX - boardPosX - 50;
		boostMeterHeight = 15;
		boostMeterPosX = boardPosX;
		boostMeterPosY = GlobalConfig.BOARD_UPPER_BOUND + 50;
		
		//*** Initialization ***//
		_gameTimeCounter = new TextActor(Assets.TimeFont, "", timerPosX, timerPosY);
		
		_comboCounter = new TextActor(Assets.ComboFont, "", cCounterPosX, cCounterPosY);
		
		_comboMeter = new ProgressBar(comboBarPosX, comboBarPosY, comboBarWidth, comboBarHeight);
		_comboMeter.setMax(_model.getMetadata().getRemianComboTimeSpec());
		_comboMeter.setMin(0);
		_comboMeter.setCurrent(_model.getMetadata().getRemainComboTime());
		
		_boostMeter = new ProgressBar(boostMeterPosX, boostMeterPosY, boostMeterWidth, boostMeterHeight);
		_boostMeter.setMax(_model.getMetadata().getComboBonusTarget());
		_boostMeter.setMin(0);
		_boostMeter.setCurrent(_model.getMetadata().getCcomboBonusCount());
		_boostMeter.setAlwaysShow(true);
		_boostMeter.setShowUpperBound(true);
		
		_resultPanel = new ResultPanel();
		_resultPanel.getRestartButtonPressedEvent().addTEventHandler(new IGameEventHandler() {
			@Override
			public void handle(GameEventArg event) 
			{ 
				_controller.restartGame();
			}
		});		
		_resultPanel.getMainMenuButtonPressedEvent().addTEventHandler(new IGameEventHandler() {
			@Override
			public void handle(GameEventArg event) 
			{ 
				_controller.backToMainMenu();
			}
		});
		
		_board = new NumBoard(new Vector2(boardPosX, boardPosY), 
				GlobalConfig.ROW_COUNT, 
				GlobalConfig.COLUMN_COUNT, 
				GlobalConfig.BLOCK_WIDTH, 
				GlobalConfig.BLOCK_HEIGHT);
		_board.addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float screenX, float screenY,int pointer, int button) 
			{
				Vector2 touchPos = new Vector2();
				touchPos.set(screenX, screenY);
				_board.localToStageCoordinates(touchPos);
				logger.debug(touchPos.x + "," + touchPos.y);
				
				NumBlock touchedBlock;
				if((touchedBlock = (NumBlock) _board.hit(touchPos.x, touchPos.y, false)) != null)
				{
					_controller.touchBlock(touchedBlock.Row, touchedBlock.Col);
				}
				
				return true;
			}
		});
		
		_board.bindCells(_model.getBboardEntity().getCells());
	}
	
	public void reset()
	{
		_resultPanel.initialize();
		Gdx.input.setInputProcessor(_gameStage);
	}
	
	private void switchToResulting() 
	{
		_resultPanel.setScore(_model.getMetadata().getScoreString());
		_resultPanel.setHighScore(0);
		Gdx.input.setInputProcessor(_uiStage);
	}
	
	public void resize(int width, int height) 
	{
		_gameStage.getViewport().update(width, height);
		_uiStage.getViewport().update(width, height);
	}
	
	public void update()
	{
		_gameTimeCounter.setText(_model.getMetadata().getGameTimeString());
		_comboCounter.setText(String.format("%3d",_model.getMetadata().getComboCount()));
		_comboMeter.setCurrent(_model.getMetadata().getRemainComboTime());
		_boostMeter.setCurrent(_model.getMetadata().getCcomboBonusCount() % _model.getMetadata().getComboBonusTarget());
	}
	
	public void render()
	{
		//Clear
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
				
		//Draw gaming component (board...etc)
		//_camera.update();
		_gameStage.draw();
		_gameStage.act();
		
		if(_model.getState() == GameState.Resulting)
		{
			_uiStage.draw();
			_uiStage.act();
		}
		
//		ShapeRenderer renderer = new ShapeRenderer();
//		renderer.setProjectionMatrix(_stage.getCamera().combined);
//		renderer.setColor(Color.WHITE);
//		renderer.begin(ShapeType.Line);
//		renderer.line(0, Config.BOARD_UPPER_BOUND, Config.FRUSTUM_WIDTH, Config.BOARD_UPPER_BOUND);
//		renderer.end();
//		renderer.dispose();
	}
}
