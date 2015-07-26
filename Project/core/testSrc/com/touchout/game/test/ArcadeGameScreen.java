package com.touchout.game.test;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.Logger;
import com.touchout.game.mvc.controller.ArcadeGameController;
import com.touchout.game.mvc.core.NumChaining;
import com.touchout.game.mvc.model.ArcadeGameModel;

public class ArcadeGameScreen extends ScreenAdapter
{
	Logger logger = new Logger("MvcTestScreen");

	//Model
	ArcadeGameModel _model;
	
	//controller
	ArcadeGameController _controller;
	
	public ArcadeGameScreen(NumChaining game)
	{
		//Initial model
		_model = new ArcadeGameModel();
		
		//Initial controller
		_controller = new ArcadeGameController(_model, game);

		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		logger.setLevel(Logger.DEBUG);
	}
	
	public void reset() {
		_controller.restartGame();
	}
	
	@Override
	public void resize(int width, int height) 
	{
		_controller.resize(width, height);
	}
	
	@Override
	public void render(float delta) 
	{
		_controller.update(delta);
		_controller.renderView();
	}	 
}
