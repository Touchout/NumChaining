package com.touchout.game.states;

import com.badlogic.gdx.Screen;
import com.touchout.game.GameScreen;
import com.touchout.game.event.BlockSolvedTEvent;
import com.touchout.game.event.TEvent;
import com.touchout.game.event.TEventCode;

public class IntroStater extends GameStater 
{
	private boolean _start = false;
	GameScreen _gameScreen;
	
	public IntroStater(Screen screen) {
		super(screen);
		_gameScreen = (GameScreen) _screen;
	}
	
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
		if(event.Tag != null && event.Tag.equals(TEventCode.StartPlaying))
			_start = true;
		
		if(event instanceof BlockSolvedTEvent)
		{
			_gameScreen.getMetadata().increaseScore(((BlockSolvedTEvent) event).Reward);
		}
	}
	
	@Override
	protected GameStateCode setStateCode() {
		return GameStateCode.Intro;
	}

}
