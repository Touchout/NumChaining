package com.touchout.game.states;

import com.badlogic.gdx.Screen;
import com.touchout.game.event.TEvent;
import com.touchout.game.event.TEventHandler;

public abstract class GameStater implements TEventHandler 
{
	protected Screen _screen;
	protected GameStateCode _stateCode;
	
	public GameStateCode getStateCode() {
		return _stateCode;
	}
	public GameStater(Screen screen)
	{
		_screen = screen;
		_stateCode = setStateCode(); 
	}
	
	protected abstract GameStateCode setStateCode();
	
	public abstract GameStateCode update(float delta);

	@Override
	public abstract void handle(TEvent event);
	
}
