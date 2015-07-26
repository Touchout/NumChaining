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
import com.touchout.game.component.ComboBar;
import com.touchout.game.component.NumBoard;
import com.touchout.game.component.ResultingUI;
import com.touchout.game.event.BlockSolvedTEvent;
import com.touchout.game.event.LockBoardTEvent;
import com.touchout.game.event.TEvent;
import com.touchout.game.event.TEventBroadcaster;
import com.touchout.game.event.TEventCode;
import com.touchout.game.event.ITEventHandler;
import com.touchout.game.mvc.Config;
import com.touchout.game.mvc.NumChaining;
import com.touchout.game.states.GameStateCode;
import com.touchout.game.states.GameStateMachine;
import com.touchout.game.states.GameStater;
import com.touchout.game.states.arcade.PlayingStater;
import com.touchout.game.states.arcade.ResultingStater;

public class PlaygroundScreen extends ScreenAdapter
{
	Logger logger = new Logger("Playground");
	OrthographicCamera _camera;

	//Stages
	Stage _gameStage;

	//Actors, Groups
	ComboBar comboBar;
	
	//time
	float time = 10;
	
	public PlaygroundScreen(NumChaining game)
	{
		comboBar = new ComboBar(100, 500, 400, 100);
		comboBar.Max = 10;
		
		//Set Game Stage
		_gameStage = new Stage(new FitViewport(Config.FRUSTUM_WIDTH, Config.FRUSTUM_HEIGHT), game.batch);
		_gameStage.addActor(comboBar);
		Gdx.input.setInputProcessor(_gameStage);
		
		//Set Camera
		_camera = new OrthographicCamera();
		_camera.setToOrtho(false, Config.FRUSTUM_WIDTH, Config.FRUSTUM_HEIGHT);
		
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		logger.setLevel(Logger.DEBUG);
	}
	
	@Override
	public void resize(int width, int height) 
	{
		_gameStage.getViewport().update(width, height);
	}
	
	@Override
	public void render(float delta) 
	{
		//Clear
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
		
		time -= delta;
		time = time > 0 ? time : 0;
		comboBar.setCurrent(time);
		
		//Draw gaming component (board...etc)
		_camera.update();
		_gameStage.draw();
		_gameStage.act();
	}	 
}
