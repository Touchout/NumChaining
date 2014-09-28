package com.touchout.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.touchout.game.scene2D.GameScreen;
import com.touchout.game.scene2D.MenuScreen;

public class NumChaining extends Game 
{
	public SpriteBatch batch;
	
	@Override
	public void create () 
	{
		Assets.Load();
		batch = new SpriteBatch();
		//setScreen(new GameScreen(this));
		setScreen(new GameScreen(this));
		//setScreen(new MenuScreen());
	}

	@Override
	public void render () {
		super.render();
	}
	
}
