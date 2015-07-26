package com.touchout.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.touchout.game.mvc.Config;

public class MenuScreen extends ScreenAdapter 
{
	Skin skin;
	Stage stage;
	Table scoreBoard;
	
	public MenuScreen()
	{
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		stage = new Stage(new FitViewport(Config.FRUSTUM_WIDTH, Config.FRUSTUM_HEIGHT));
		Gdx.input.setInputProcessor(stage);
		
		scoreBoard = new Table(skin);
		TextButton restartButton = new TextButton("Restart", skin);
//		restartButton.addListener(new InputListener(){
//			@Override
//			public boolean touchDown(InputEvent event, float x, float y,
//					int pointer, int button) {
//				scoreBoard.setWidth(200);
//				scoreBoard.setHeight(125);
//				scoreBoard.addAction(Actions.sizeTo(400, 250, 1f, Interpolation.elasticOut));
//				return super.touchDown(event, x, y, pointer, button);
//			}
//		});
//		
		//restartButton.setWidth(100);
		Label score = new Label("Score: 75", skin);
		score.setFontScale(5);
		
		scoreBoard.setBackground("default-round-large");
		scoreBoard.setWidth(400);
		scoreBoard.setHeight(250);
		scoreBoard.setPosition(200, 400);
		scoreBoard.setOrigin(scoreBoard.getWidth()/2, scoreBoard.getHeight()/2);
		scoreBoard.setScale(0.3f);
		//scoreBoard.scaleBy(-0.5f);
		scoreBoard.setTransform(true);
		//scoreBoard.setCenterPosition(300, 300);
		
		scoreBoard.add(score).expand().minSize(0);
		scoreBoard.row().pad(20, 0, 20, 0);
		scoreBoard.add(restartButton).minSize(0).prefWidth(100).prefHeight(50);
		
		//SetAction
		//scoreBoard.addAction(Actions.moveTo(300, 600, 1, Interpolation.exp10In));
		//scoreBoard.addAction(Actions.sizeTo(400, 250, 1f, Interpolation.elasticOut));
		scoreBoard.addAction(Actions.scaleTo(1, 1, 1f, Interpolation.elasticOut));
		
		//scoreBoard.debug();
		stage.addActor(scoreBoard);
	}
	
	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
		stage.act();
		//Table.drawDebug(stage);
		
		super.render(delta);
	}
	
	@Override
	public void dispose() 
	{
		super.dispose();
		skin.dispose();
		stage.dispose();
	}
}
