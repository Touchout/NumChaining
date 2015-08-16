package com.touchout.game.mvc.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
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
import com.touchout.game.mvc.view.actor.TextureRegionActor;

public class MainMenuView 
{
	NumChaining _game;
	MainMenuModel _model;
	MainMenuController _controller;
	
	Logger logger = new Logger("MainMenuView");
	//OrthographicCamera _camera;

	//Stages
	Stage _mainStage;
	Stage _bgStage;
	
	//Actors
	//TextActor _title;
	//TextButton _startButton;
	
	TextureRegionActor _background;
	TextureRegionActor _startButton;
	TextureRegionActor _highScoreButton;
	TextActor _subTitle;
	
	public MainMenuView(MainMenuModel model, MainMenuController controller)
	{
		_model = model;
		_controller = controller;
		_game = controller.getGame();
		
		initializeActors();
		
		//Set Stage
		_bgStage = new Stage(new StretchViewport(GlobalConfig.FRUSTUM_WIDTH, GlobalConfig.FRUSTUM_HEIGHT), _game.batch);
		_bgStage.addActor(_background);
		
		_mainStage = new Stage(new FitViewport(GlobalConfig.FRUSTUM_WIDTH, GlobalConfig.FRUSTUM_HEIGHT), _game.batch);
		//_mainStage.addActor(_title);
		//_mainStage.addActor(_background);
		_mainStage.addActor(_startButton);
		_mainStage.addActor(_highScoreButton);
		_mainStage.addActor(_subTitle);
		
		Gdx.input.setInputProcessor(_mainStage);
			
		//Set logger
		logger.setLevel(Logger.DEBUG);
	}
	
	public void reset() {
		Gdx.input.setInputProcessor(_mainStage);
	}
	
	private void initializeActors() 
	{
//		Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
//		float titleWidth, titleX, titleY; 
//		float startButtonWidth, startButtonHeight, startButtonX, startButtonY;
//		
//		//*** Compute centralize position ***//
//		//Title
//		titleWidth = Assets.MainTitleFont.getBounds(GlobalConfig.GAME_TITLE).width;
//		titleX = (GlobalConfig.FRUSTUM_WIDTH - titleWidth) / 2;
//		titleY = 800;
//		
//		
//		//StartButton	
//		startButtonWidth = titleWidth;
//		startButtonHeight = 100;
//		startButtonX = (GlobalConfig.FRUSTUM_WIDTH - startButtonWidth) / 2;
//		startButtonY = 600;
//		
//		//*** Initialization ***//
//		_title = new TextActor(Assets.MainTitleFont, GlobalConfig.GAME_TITLE, titleX, titleY);
//		_startButton = new TextButton("Start", skin);
//		_startButton.setSize(startButtonWidth, startButtonHeight);
//		_startButton.setPosition(startButtonX, startButtonY);
//		_startButton.getLabel().setFontScale(2);
//		_startButton.addListener(new InputListener(){
//			@Override
//			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//				return true;
//			}
//			
//			@Override
//			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//				_controller.startArcadeGame();
//			}
//		});
		
		_background = new TextureRegionActor(Assets.mainBackgroundTexture);
		_background.setBounds(0, 0, GlobalConfig.FRUSTUM_WIDTH, GlobalConfig.FRUSTUM_HEIGHT);
		
		float titleCoordX, titleCoordY;
		String subtitle = "tap button with right sequence";
		titleCoordX = (GlobalConfig.FRUSTUM_WIDTH - Assets.SubTitleFont.getBounds(subtitle).width)/2;
		titleCoordY = 680;
		_subTitle = new TextActor(Assets.SubTitleFont, subtitle, titleCoordX, titleCoordY);
		
		_startButton = new TextureRegionActor(Assets.DarkButtonTexture);
		float buttonPadding = (GlobalConfig.FRUSTUM_WIDTH - _startButton.getWidth())/2;
		_startButton.setPosition(buttonPadding , 400);
		_startButton.setScale(1.8f);
		_startButton.Font = Assets.MainMenuButtonFont;
		_startButton.Text = "START TO PLAY";
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
		
		_highScoreButton= new TextureRegionActor(Assets.LightButtonTexture);
		buttonPadding = (GlobalConfig.FRUSTUM_WIDTH - _highScoreButton.getWidth())/2;
		_highScoreButton.setPosition(buttonPadding, 270);
		_highScoreButton.setScale(1.8f);
		_highScoreButton.Font = Assets.MainMenuButtonFontDark;
		_highScoreButton.Text = "YOUR SCORE";
	}	
	
	public void resize(int width, int height) 
	{
		_mainStage.getViewport().update(width, height);
		_bgStage.getViewport().update(width, height);
	}
	
	public void update()
	{
	}
	
	public void render()
	{
		//Clear
		Gdx.gl.glClearColor(0xF9, 0xF9, 0xF9, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
				
		//Draw gaming component (board...etc)
		//_camera.update();
//		_game.batch.begin();
//		_game.batch.draw(Assets.mainBackgroundTexture,0,0);
//		_game.batch.end();
		
		_bgStage.draw();
		_mainStage.draw();
		
	}
}
