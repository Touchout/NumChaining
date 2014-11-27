package com.touchout.game.states.arcade;

import com.touchout.game.ArcadeMetadata;
import com.touchout.game.ArcadeModeComponentPackage;
import com.touchout.game.event.TEvent;
import com.touchout.game.event.TEventCode;
import com.touchout.game.states.GameStateCode;

public class ResultingStater extends ArcadeGameStater 
{
	private boolean _restart = false;
	
	public ResultingStater(ArcadeModeComponentPackage pack, ArcadeMetadata metadata) 
	{
		super(pack, metadata);
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
		if(event.Tag == TEventCode.RestartGame)
		{
			_restart = true;
		}
	}

	@Override
	protected GameStateCode defineStateCode() 
	{
		return GameStateCode.Resulting;
	}

}
