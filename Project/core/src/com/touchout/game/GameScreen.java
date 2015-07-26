package com.touchout.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.touchout.game.event.StateChangeTEvent;
import com.touchout.game.event.TEvent;
import com.touchout.game.event.TEventBroadcaster;
import com.touchout.game.event.TEventCode;
import com.touchout.game.event.ITEventHandler;
import com.touchout.game.mvc.Assets;
import com.touchout.game.mvc.Config;
import com.touchout.game.mvc.NumChaining;
import com.touchout.game.states.GameStateCode;
import com.touchout.game.states.GameStateMachine;

public class GameScreen extends ScreenAdapter implements ITEventHandler
{
	private Logger _logger = new Logger("game");
	private final NumChaining _game;
	private OrthographicCamera _camera;
	private TEventBroadcaster _braodcaster;
	//Game State
	private GameStateMachine _stateMachine;
	//Game metadata
	private ArcadeMetadata _metadata;
	//ComponentPackage
	private ArcadeModeComponentPackage _componentPackage;
	//Stages
	private Stage _gameStage;
	private Stage _uiStage;

	public GameScreen(NumChaining game)
	{
		_game = game;
		_game.batch.enableBlending();
		
		//Instantiation
		_metadata = new ArcadeMetadata();
		_componentPackage = new ArcadeModeComponentPackage();
		_stateMachine = new GameStateMachine(_componentPackage, _metadata, GameStateCode.Intro);
		
		//Set Game Component Package
		_componentPackage.initialize();
		_componentPackage.getBoard().getTEventBroadcaster().addTEventHandler(_stateMachine);
		_componentPackage.getResultingUI().getBroadcaster().addTEventHandler(_stateMachine);
		_componentPackage.getResultingUI().getBroadcaster().addTEventHandler(this);
		
		//Set GameStateMachine listen screen events
		_braodcaster = new TEventBroadcaster();
		_braodcaster.addTEventHandler(_stateMachine);
	
		//Set Screen listen GameStateMachine events
		_stateMachine.getBroadcaster().addTEventHandler(this);
		
		//Set Game Stage
		_gameStage = new Stage(new FitViewport(Config.FRUSTUM_WIDTH, Config.FRUSTUM_HEIGHT), _game.batch);
		_gameStage.addActor(_metadata);
		_gameStage.addActor(_componentPackage.getBoard());
		_gameStage.addActor(_componentPackage.getTimeBonusHint());
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

		//Set UI Stage
		_uiStage = new Stage(new FitViewport(Config.FRUSTUM_WIDTH, Config.FRUSTUM_HEIGHT), _game.batch);
		_uiStage.addActor(_componentPackage.getResultingUI());
		
		//Set Camera
		_camera = new OrthographicCamera();
		_camera.setToOrtho(false, Config.FRUSTUM_WIDTH, Config.FRUSTUM_HEIGHT);
		
		//Set Logger
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		_logger.setLevel(Logger.DEBUG);
		
		//Start Game Play
		this.restart();
	}
	
	public void restart() 
	{
		_metadata.initialize();
		_componentPackage.restart();
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
		_gameStage.act(delta);
		
		//Draw UI
		if(_stateMachine.getCurrentStateCode() == GameStateCode.Resulting)
		{
			_uiStage.draw();
			_uiStage.act();
		}
		
		//Draw HUDs (time, score..etc)
		_game.batch.setProjectionMatrix(_camera.combined);		
		_game.batch.begin();
		
		//draw time
		float posX = (Config.FRUSTUM_WIDTH - Assets.TimeFont.getMultiLineBounds(_metadata.getGameTimeString()).width) / 2;
		float posY = (Config.FRUSTUM_HEIGHT + Assets.TimeFont.getMultiLineBounds(_metadata.getGameTimeString()).height 
						+ Config.BOARD_UPPER_BOUND) /2;
		Assets.TimeFont.draw(_game.batch, _metadata.getGameTimeString(), posX, posY);

		//draw score
		Assets.ScoreFont.draw(_game.batch, String.format("%010d", _metadata.getScore()), 20, 1260);
		Assets.LevelFont.draw(_game.batch, String.format("LEVEL %2d", _metadata.getLevel()), 500, 1260);
		
		_game.batch.end();
	}

	@Override
	public void handle(TEvent event) 
	{
		if(event instanceof StateChangeTEvent)
		{
			if(((StateChangeTEvent) event).getTargetStateCode() == GameStateCode.Resulting)
				Gdx.input.setInputProcessor(_uiStage);
		}
		
		if(event.Tag == TEventCode.RestartGame)
		{
			this.restart();
		}
	}	 
}
