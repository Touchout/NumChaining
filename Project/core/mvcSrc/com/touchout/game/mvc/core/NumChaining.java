package com.touchout.game.mvc.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.touchout.game.test.ArcadeGameScreen;
import com.touchout.game.test.MainMenuScreen;

public class NumChaining extends Game 
{
	public SpriteBatch batch;
	MainMenuScreen _mainMenuScreen;
	ArcadeGameScreen _arcadeGameScreen;
	
	@Override
	public void create () 
	{
		Assets.globalLogger = new Logger("Global");
		Assets.globalLogger.setLevel(Logger.DEBUG);
		Assets.Load();
		batch = new SpriteBatch();
		
		_arcadeGameScreen = new ArcadeGameScreen(this);
		_mainMenuScreen = new MainMenuScreen(this);
				
		//setScreen(new GameScreen(this));
		//setScreen(new GameScreen(this));
		//setScreen(new ArcadeGameScreen(this));
		setScreen(_mainMenuScreen);
		//setScreen(new PlaygroundScreen(this));
	}
	
	public void startArcadeGame() 
	{
		setScreen(_arcadeGameScreen);
		_arcadeGameScreen.reset();
	}
	
	public void backToMainMenu() 
	{
		setScreen(_mainMenuScreen);
		_mainMenuScreen.reset();
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override 
	public void resume () {
		Assets.Load();
		super.resume();
	}
}
