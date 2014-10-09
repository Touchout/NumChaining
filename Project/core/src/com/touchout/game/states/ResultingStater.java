package com.touchout.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.touchout.game.Assets;
import com.touchout.game.GameScreen;
import com.touchout.game.event.TEvent;
import com.touchout.game.event.TEventCode;

public class ResultingStater extends GameStater 
{
	private boolean _restart = false;
	public ResultingStater(Screen screen) {
		super(screen);
	}

	@Override
	public GameStateCode update(float delta) 
	{
		//GameScreen screen = (GameScreen) _screen;
		GameStateCode nextStateCode = _restart ? GameStateCode.Intro : GameStateCode.Resulting;
		_restart = false;
		return nextStateCode;
	}

	@Override
	public void handle(TEvent event) 
	{
		if(event.Tag != null && event.Tag == TEventCode.RestartGame)
		{
			GameScreen screen = (GameScreen) _screen;
			screen.restart();
			_restart = true;
		}
	}

	@Override
	protected GameStateCode setStateCode() 
	{
		return GameStateCode.Resulting;
	}

}
