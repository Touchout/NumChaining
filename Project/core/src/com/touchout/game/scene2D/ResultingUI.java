package com.touchout.game.scene2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.touchout.game.Config;

public class ResultingUI extends Group implements Disposable
{
	Skin skin;
	Table scoreBoard;
	Label score;
	TextButton restartButton;
	
	public ResultingUI()
	{
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		//stage = new Stage(new FitViewport(Config.FRUSTUM_WIDTH, Config.FRUSTUM_HEIGHT));
		//Gdx.input.setInputProcessor(stage);
		
		scoreBoard = new Table(skin);
		restartButton = new TextButton("Restart", skin);
		restartButton.getLabel().setFontScale(2);

		//restartButton.setWidth(100);
		score = new Label("Score: 75", skin);
		score.setFontScale(5);
		
		scoreBoard.add(score).expand();
		scoreBoard.row().pad(20, 0, 20, 0);
		scoreBoard.add(restartButton).prefWidth(200).prefHeight(100);

		initialize();
		
		//scoreBoard.debug();
		this.addActor(scoreBoard);
	}
	
	public void initialize() 
	{
		//SetAction
		scoreBoard.getActions().clear();
		scoreBoard.addAction(Actions.scaleTo(1, 1, 1f, Interpolation.elasticOut));
		
		scoreBoard.setBackground("default-round-large");
		scoreBoard.setWidth(600);
		scoreBoard.setHeight(350);
		scoreBoard.setPosition(60, 400);
		scoreBoard.setOrigin(scoreBoard.getWidth()/2, scoreBoard.getHeight()/2);
		scoreBoard.setScale(0.3f);
		scoreBoard.setTransform(true);
	}
	
	public void setRestartListener(InputListener listener) 
	{
		restartButton.addListener(listener);
	}
	
	public void setScore(String scoreStr) 
	{
		score.setText("Score: " + scoreStr);
	}

	@Override
	public void dispose() 
	{
		skin.dispose();
	}
	
	
}
