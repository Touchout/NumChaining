package com.touchout.game.mvc.view.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.touchout.game.mvc.event.GameEvent;
import com.touchout.game.mvc.event.GameEventArg;

public class ResultPanel extends Group implements Disposable
{
	Skin skin;
	Table scoreBoard;
	Label score,highScore;
	TextButton restartButton;
	GameEvent _restartButtonPressedEvent = new GameEvent();
	TextButton mainMenuButton;
	GameEvent _mainMenuButtonPressedEvent = new GameEvent();

	public ResultPanel()
	{
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		//stage = new Stage(new FitViewport(Config.FRUSTUM_WIDTH, Config.FRUSTUM_HEIGHT));
		//Gdx.input.setInputProcessor(stage);
		
		scoreBoard = new Table(skin);
		restartButton = new TextButton("Restart", skin);
		restartButton.getLabel().setFontScale(2);
		restartButton.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				_restartButtonPressedEvent.fire(new GameEventArg(null));
			}
		});
		
		mainMenuButton = new TextButton("MainMenu", skin);
		mainMenuButton.getLabel().setFontScale(2);
		mainMenuButton.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				_mainMenuButtonPressedEvent.fire(new GameEventArg(null));
			}
		});

		//restartButton.setWidth(100);
		score = new Label("", skin);
		score.setFontScale(5);
		highScore = new Label("", skin);
		highScore.setFontScale(2);
		
		scoreBoard.add(score).expand().colspan(2);
		scoreBoard.row();
		scoreBoard.add(highScore).colspan(2);
		scoreBoard.row().pad(20, 0, 20, 0);
		scoreBoard.add(restartButton).prefWidth(180).prefHeight(100);
		scoreBoard.add(mainMenuButton).prefWidth(180).prefHeight(100);

		initialize();
		
		//scoreBoard.debug();
		this.addActor(scoreBoard);
	}
	
	public void initialize() 
	{
		//SetAction
		scoreBoard.setTouchable(Touchable.disabled);
		scoreBoard.getActions().clear();
		scoreBoard.addAction(Actions.sequence(
				Actions.scaleTo(1, 1, 1f, Interpolation.elasticOut),
				Actions.touchable(Touchable.enabled)));
		
		scoreBoard.setBackground("default-round-large");
		scoreBoard.setWidth(600);
		scoreBoard.setHeight(350);
		scoreBoard.setPosition(60, 400);
		scoreBoard.setOrigin(scoreBoard.getWidth()/2, scoreBoard.getHeight()/2);
		scoreBoard.setScale(0.3f);
		scoreBoard.setTransform(true);
	}
	
	public GameEvent getRestartButtonPressedEvent() {
		return _restartButtonPressedEvent;
	}
	
	public GameEvent getMainMenuButtonPressedEvent() {
		return _mainMenuButtonPressedEvent;
	}
	
	public void setScore(String scoreStr) 
	{
		score.setText("Score: " + scoreStr);
	}
	
	public void setHighScore(int value) 
	{
		highScore.setText("HighScore: " + String.valueOf(value));
	}

	@Override
	public void dispose() 
	{
		skin.dispose();
	}
	
	
}
