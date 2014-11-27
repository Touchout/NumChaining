package com.touchout.game.event;

import com.touchout.game.states.GameStateCode;

public class StateChangeTEvent extends TEvent 
{
	private GameStateCode _targetStateCode;
	
	public GameStateCode getTargetStateCode() 
	{
		return _targetStateCode;
	}

	public StateChangeTEvent(Object sender, GameStateCode targetStateCode) 
	{
		super(sender);
		_targetStateCode = targetStateCode; 
	}
}
