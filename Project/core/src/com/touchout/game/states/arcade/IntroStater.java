package com.touchout.game.states.arcade;

import com.touchout.game.ArcadeMetadata;
import com.touchout.game.ArcadeModeComponentPackage;
import com.touchout.game.event.BlockSolvedTEvent;
import com.touchout.game.event.TEvent;
import com.touchout.game.event.TEventCode;
import com.touchout.game.states.GameStateCode;

public class IntroStater extends ArcadeGameStater 
{
	public IntroStater(ArcadeModeComponentPackage pack, ArcadeMetadata metadata) 
	{
		super(pack, metadata);
	}

	private boolean _start = false;
	
	
	@Override
	public GameStateCode update(float delta) 
	{
		GameStateCode result = _start ? GameStateCode.Playing : GameStateCode.Intro;
		_start = false;
		return result;
	}

	@Override
	public void handle(TEvent event) 
	{
		if(event.Tag.equals(TEventCode.StartPlaying))
			_start = true;
		
		if(event instanceof BlockSolvedTEvent)
		{
			_arcadeMetadata.increaseScore(((BlockSolvedTEvent) event).Reward);
		}
	}
	
	@Override
	protected GameStateCode defineStateCode() {
		return GameStateCode.Intro;
	}

}
