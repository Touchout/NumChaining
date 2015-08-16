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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
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
import com.touchout.game.mvc.view.actor.ScoreBanner;
import com.touchout.game.mvc.view.actor.TextActor;
import com.touchout.game.mvc.view.actor.TextureRegionActor;
import com.touchout.game.mvc.view.actor.TimeBannerActor;

public class ArcadeGameView implements IView
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
	//TextActor _gameTimeCounter;
	TextActor _comboCounter;
	TextActor _score;
	TextActor _plus;
	ProgressBar _comboMeter; // ,_boostMeter;
	//ResultPanel _resultPanel;
	TimeBannerActor _timeBanner;
	ScoreBanner _scoreBanner;
	TextureRegionActor _restartBtn;
	
	//time after gameover
	float _gameOverWaitTime = 2f;
	
	public ArcadeGameView(ArcadeGameModel model, ArcadeGameController controller)
	{
		_model = model;
		_controller = controller;
		_game = controller.getGame();
		
		initializeActors();
		
		//Set Game Stage
		_gameStage = new Stage(new FitViewport(GlobalConfig.FRUSTUM_WIDTH, GlobalConfig.FRUSTUM_HEIGHT), _game.batch);
		_gameStage.addActor(_board);
		//_gameStage.addActor(_gameTimeCounter);
		_gameStage.addActor(_timeBanner);
		_gameStage.addActor(_comboCounter);
		_gameStage.addActor(_comboMeter);
		//_gameStage.addActor(_boostMeter);
		_gameStage.addActor(_scoreBanner);
		_gameStage.addActor(_plus);
		_gameStage.addActor(_restartBtn);
		Gdx.input.setInputProcessor(_gameStage);
		
		//Set UI Stage
//		_uiStage = new Stage(new FitViewport(GlobalConfig.FRUSTUM_WIDTH, GlobalConfig.FRUSTUM_HEIGHT), _game.batch);
//		_uiStage.addActor(_resultPanel);
		
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
		float boardPosX, timerPosX, cCounterPosX, comboBarPosX, boostMeterPosX, scoreX;
		float boardPosY, timerPosY, cCounterPosY, comboBarPosY, boostMeterPosY, scoreY;
		float boardWidth,boardHeight, timerWidth, cCounterWidth, comboBarWidth, comboBarHeight, boostMeterWidth, boostMeterHeight;
		
		//*** Compute centralize position ***//
		//Board
		boardWidth = GlobalConfig.BLOCK_WIDTH * GlobalConfig.COLUMN_COUNT + (GlobalConfig.COLUMN_COUNT-1) * GlobalConfig.BLOCK_MARGIN;
		boardHeight = GlobalConfig.BLOCK_HEIGHT * GlobalConfig.ROW_COUNT + (GlobalConfig.ROW_COUNT-1) * GlobalConfig.BLOCK_MARGIN;
		boardPosX= (GlobalConfig.FRUSTUM_WIDTH - boardWidth) / 2;
		boardPosY = GlobalConfig.BOARD_UPPER_BOUND - boardHeight;
		
		//Timer
//		timerWidth = Assets.TimeFont.getBounds("00:00").width;
//		timerPosX = (GlobalConfig.FRUSTUM_WIDTH - timerWidth) / 2;
//		timerPosY = GlobalConfig.TIMER_VPOSITION;
		
		//TimerNew
		_timeBanner = new TimeBannerActor();
		_timeBanner.setPosition((GlobalConfig.FRUSTUM_WIDTH - _timeBanner.getWidth())/2, 
				GlobalConfig.FRUSTUM_HEIGHT - _timeBanner.getHeight());
		_timeBanner.setScale(2);
		_timeBanner.relayout();
		
		_plus = new TextActor(Assets.TimePlusFont, "+2", _timeBanner.getRight() + 80, _timeBanner.getCenterY()-30);
		_plus.setVisible(false);
		
		//ScoreBanner
		_scoreBanner = new ScoreBanner();
		_scoreBanner.setPosition((GlobalConfig.FRUSTUM_WIDTH - _scoreBanner.getWidth())/2, 0);
		_scoreBanner.setScale(2.5f);
		_scoreBanner.relayout();
		
		//ComboCounter
		cCounterWidth = Assets.ComboFont.getBounds("00 COMBOS").width;
		cCounterPosX = (GlobalConfig.FRUSTUM_WIDTH - cCounterWidth)/2;
		cCounterPosY = GlobalConfig.BOARD_UPPER_BOUND + 110;
		
		//ComboBar
		comboBarWidth = boardWidth;
		comboBarHeight = 15;
		comboBarPosX = boardPosX;
		comboBarPosY = GlobalConfig.BOARD_UPPER_BOUND +70;
		
		//BoostMeter
//		boostMeterWidth = cCounterPosX - boardPosX - 50;
//		boostMeterHeight = 15;
//		boostMeterPosX = boardPosX;
//		boostMeterPosY = GlobalConfig.BOARD_UPPER_BOUND + 50;
		
		//*** Initialization ***//
		//_gameTimeCounter = new TextActor(Assets.TimeFont, "", timerPosX, timerPosY);
		_timeBanner.Text = "";
		
		_comboCounter = new TextActor(Assets.ComboFont, "", cCounterPosX, cCounterPosY);	
		
		_comboMeter = new ProgressBar(comboBarPosX, comboBarPosY, comboBarWidth, comboBarHeight);
		_comboMeter.setMax(_model.getMetadata().getRemianComboTimeSpec());
		_comboMeter.setMin(0);
		_comboMeter.setCurrent(_model.getMetadata().getRemainComboTime());
		_comboMeter.setShowUpperBound(true);
		_comboMeter.setAlwaysShow(true);
		
//		_boostMeter = new ProgressBar(boostMeterPosX, boostMeterPosY, boostMeterWidth, boostMeterHeight);
//		_boostMeter.setMax(_model.getMetadata().getComboBonusTarget());
//		_boostMeter.setMin(0);
//		_boostMeter.setCurrent(_model.getMetadata().getCcomboBonusCount());
//		_boostMeter.setAlwaysShow(true);
//		_boostMeter.setShowUpperBound(true);
		
//		_resultPanel = new ResultPanel();
//		_resultPanel.getRestartButtonPressedEvent().addTEventHandler(new IGameEventHandler() {
//			@Override
//			public void handle(GameEventArg event) 
//			{ 
//				_controller.restartGame();
//			}
//		});		
//		_resultPanel.getMainMenuButtonPressedEvent().addTEventHandler(new IGameEventHandler() {
//			@Override
//			public void handle(GameEventArg event) 
//			{ 
//				_controller.backToMainMenu();
//			}
//		});
		
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
		
		_restartBtn = new TextureRegionActor(Assets.restartTexture);
		_restartBtn.setPosition(0, -50);
		_restartBtn.setScale(0.5f);
		_restartBtn.addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float screenX, float screenY,int pointer, int button) 
			{
				_controller.backToMainMenu();
				return false;
			}
		});
		
		_model.getBlockSolvedEvent().addTEventHandler(new IGameEventHandler() {
			@Override
			public void handle(GameEventArg event) {
				Vector2 coord = (Vector2)event.Tag;
				_board.getBlock((int)coord.x, (int)coord.y).bounce();
			}
		});
		
		_model.getBoardUnlockEvent().addTEventHandler(new IGameEventHandler() {
			@Override
			public void handle(GameEventArg event) {
				_board.getBlock((Integer)(event.Tag)).bounce();
			}});
		
		_model.getMetadata().timePlusEvent.addTEventHandler(new IGameEventHandler() {			
			@Override
			public void handle(GameEventArg event) {
				_plus.addAction(Actions.sequence(Actions.visible(true), Actions.delay(0.5f),Actions.visible(false)));
			}
		});
	}
	
	public void reset()
	{
		//_resultPanel.initialize();
		Gdx.input.setInputProcessor(_gameStage);
	}
	
	private void switchToResulting() 
	{
		//_resultPanel.setScore(_model.getMetadata().getScoreString());
		//_resultPanel.setHighScore(0);
		//_controller.goToStatistics();
		//Gdx.input.setInputProcessor(_uiStage);
		Gdx.input.setInputProcessor(null);
		
	}
	
	public void resize(int width, int height) 
	{
		_gameStage.getViewport().update(width, height);
		//_uiStage.getViewport().update(width, height);
	}
	
	public void update()
	{
		//_gameTimeCounter.setText(_model.getMetadata().getGameTimeString());
		_timeBanner.Text = _model.getMetadata().getGameTimeString();
		_scoreBanner.Text = _model.getMetadata().getScoreString();
		_comboCounter.setText(String.format("%3d COMBOS",_model.getMetadata().getComboCount()));
		_comboMeter.setCurrent(_model.getMetadata().getRemainComboTime());
		_comboMeter.setScaleFactor((_model.getMetadata().getCcomboBonusCount()+1)/10f);
		//_boostMeter.setCurrent(_model.getMetadata().getCcomboBonusCount() % _model.getMetadata().getComboBonusTarget());
	}
	
	public void render()
	{
		//Clear
		Gdx.gl.glClearColor(0xF9, 0xF9, 0xF9, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
				
		//Draw gaming component (board...etc)
		//_camera.update();
		_gameStage.draw();
		_gameStage.act();
		
		if(_model.getState() == GameState.Resulting)
		{
			//_uiStage.draw();
			//_uiStage.act();
			
			_gameOverWaitTime -= Gdx.app.getGraphics().getDeltaTime();
			if(_gameOverWaitTime <= 0)
				_controller.goToStatistics();
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
