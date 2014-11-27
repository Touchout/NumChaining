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
import com.touchout.game.Assets;
import com.touchout.game.Config;
import com.touchout.game.NumChaining;
import com.touchout.game.component.TextActor;
import com.touchout.game.mvc.actor.NumBlock;
import com.touchout.game.mvc.actor.NumBoard;
import com.touchout.game.mvc.controller.ArcadeGameController;
import com.touchout.game.mvc.model.ArcadeGameModel;

public class ArcadeGameView 
{
	NumChaining _game;
	ArcadeGameModel _model;
	ArcadeGameController _controller;
	
	Logger logger = new Logger("ArcadeGameView");
	//OrthographicCamera _camera;

	//Stages
	Stage _stage;
	
	//Actors
	NumBoard _board;
	TextActor _gameTimeCounter;
	
	public ArcadeGameView(ArcadeGameModel model, ArcadeGameController controller)
	{
		_model = model;
		_controller = controller;
		_game = controller.getGame();
		
		initializeActors();
		
		//Set Game Stage
		_stage = new Stage(new FitViewport(Config.FRUSTUM_WIDTH, Config.FRUSTUM_HEIGHT), _game.batch);
		_stage.addActor(_board);
		_stage.addActor(_gameTimeCounter);
		Gdx.input.setInputProcessor(_stage);
		
		//Set Camera
		//_camera = new OrthographicCamera();
		//_camera.setToOrtho(false, Config.FRUSTUM_WIDTH, Config.FRUSTUM_HEIGHT);
		
		//Set logger
		logger.setLevel(Logger.DEBUG);
	}
	
	private void initializeActors() 
	{
		_gameTimeCounter = new TextActor(Assets.TimeFont, "", 250, 700);
		_board = new NumBoard(new Vector2(10,10), 
				Config.ROW_COUNT, 
				Config.COLUMN_COUNT, 
				Config.BLOCK_WIDTH, 
				Config.BLOCK_HEIGHT);
		
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
		
		_board.bindCells(_model.getBboardEntity());
	}
	
	public void resize(int width, int height) 
	{
		_stage.getViewport().update(width, height);
	}
	
	public void update()
	{
		_gameTimeCounter.setText(_model.getMetadata().getGameTimeString());
	}
	
	public void render()
	{
		//Clear
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
				
		//Draw gaming component (board...etc)
		//_camera.update();
		_stage.draw();
		_stage.act();
		
		ShapeRenderer renderer = new ShapeRenderer();
		renderer.setProjectionMatrix(_stage.getCamera().combined);
		renderer.setColor(Color.WHITE);
		renderer.begin(ShapeType.Line);
		renderer.line(0, 700, 720, 700);
		renderer.end();
		renderer.dispose();
	}
}
