package com.touchout.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.touchout.game.test.MvcTestScreen;

public class NumChaining extends Game 
{
	public SpriteBatch batch;
	
	@Override
	public void create () 
	{
		Assets.globalLogger = new Logger("Global");
		Assets.globalLogger.setLevel(Logger.DEBUG);
		Assets.Load();
		batch = new SpriteBatch();
		//setScreen(new GameScreen(this));
		//setScreen(new GameScreen(this));
		setScreen(new MvcTestScreen(this));
		//setScreen(new MenuScreen());
		//setScreen(new PlaygroundScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
}
