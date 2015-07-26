package com.touchout.game.mvc.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.touchout.game.mvc.controller.ArcadeGameController;
import com.touchout.game.mvc.controller.MainMenuController;
import com.touchout.game.mvc.core.Assets;
import com.touchout.game.mvc.core.GlobalConfig;
import com.touchout.game.mvc.core.NumChaining;
import com.touchout.game.mvc.event.GameEventArg;
import com.touchout.game.mvc.event.IGameEventHandler;
import com.touchout.game.mvc.model.ArcadeGameModel;
import com.touchout.game.mvc.model.MainMenuModel;
import com.touchout.game.mvc.model.ArcadeGameModel.GameState;
import com.touchout.game.mvc.view.actor.ProgressBar;
import com.touchout.game.mvc.view.actor.NumBlock;
import com.touchout.game.mvc.view.actor.NumBoard;
import com.touchout.game.mvc.view.actor.ResultPanel;
import com.touchout.game.mvc.view.actor.TextActor;

public class MainMenuView 
{
	NumChaining _game;
	MainMenuModel _model;
	MainMenuController _controller;
	
	Logger logger = new Logger("MainMenuView");
	//OrthographicCamera _camera;

	//Stages
	Stage _mainStage;	
	
	//Actors
	TextActor _title;
	TextButton _startButton;
	
	public MainMenuView(MainMenuModel model, MainMenuController controller)
	{
		_model = model;
		_controller = controller;
		_game = controller.getGame();
		
		initializeActors();
		
		//Set Stage
		_mainStage = new Stage(new FitViewport(GlobalConfig.FRUSTUM_WIDTH, GlobalConfig.FRUSTUM_HEIGHT), _game.batch);
		_mainStage.addActor(_title);
		_mainStage.addActor(_startButton);
		Gdx.input.setInputProcessor(_mainStage);
			
		//Set logger
		logger.setLevel(Logger.DEBUG);
	}
	
	public void reset() {
		Gdx.input.setInputProcessor(_mainStage);
	}
	
	private void initializeActors() 
	{
		Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		float titleWidth, titleX, titleY; 
		float startButtonWidth, startButtonHeight, startButtonX, startButtonY;
		
		//*** Compute centralize position ***//
		//Title
		titleWidth = Assets.MainTitleFont.getBounds("NumChain").width;
		titleX = (GlobalConfig.FRUSTUM_WIDTH - titleWidth) / 2;
		titleY = 800;
		
		
		//StartButton	
		startButtonWidth = titleWidth;
		startButtonHeight = 100;
		startButtonX = (GlobalConfig.FRUSTUM_WIDTH - startButtonWidth) / 2;
		startButtonY = 600;
		
		//*** Initialization ***//
		_title = new TextActor(Assets.MainTitleFont, "NumChain", titleX, titleY);
		_startButton = new TextButton("Start", skin);
		_startButton.setSize(startButtonWidth, startButtonHeight);
		_startButton.setPosition(startButtonX, startButtonY);
		_startButton.getLabel().setFontScale(2);
		_startButton.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				_controller.startArcadeGame();
			}
		});
	}	
	
	public void resize(int width, int height) 
	{
		_mainStage.getViewport().update(width, height);
	}
	
	public void update()
	{
	}
	
	public void render()
	{
		//Clear
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
				
		//Draw gaming component (board...etc)
		//_camera.update();
		_mainStage.draw();
	}
}
