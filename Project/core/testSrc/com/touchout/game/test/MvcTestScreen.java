package com.touchout.game.test;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.touchout.game.Config;
import com.touchout.game.NumChaining;
import com.touchout.game.component.ComboBar;
import com.touchout.game.mvc.actor.NumBlock;
import com.touchout.game.mvc.actor.NumBoard;
import com.touchout.game.mvc.controller.ArcadeGameController;
import com.touchout.game.mvc.model.ArcadeGameModel;
import com.touchout.game.mvc.model.NumBlockEntity;
import com.touchout.game.mvc.view.ArcadeGameView;

public class MvcTestScreen extends ScreenAdapter
{
	Logger logger = new Logger("MvcTestScreen");

	//Model
	ArcadeGameModel _model;
	
	//controller
	ArcadeGameController _controller;
	
	public MvcTestScreen(NumChaining game)
	{
		//Initial model
		_model = new ArcadeGameModel();
		
		//Initial controller
		_controller = new ArcadeGameController(_model, game);

		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		logger.setLevel(Logger.DEBUG);
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
