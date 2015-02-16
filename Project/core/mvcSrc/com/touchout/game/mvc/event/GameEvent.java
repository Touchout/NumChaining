package com.touchout.game.mvc.event;

import com.badlogic.gdx.utils.Array;

public class GameEvent 
{
	Array<IGameEventHandler> _handlers;
	
	public GameEvent()
	{
		_handlers = new Array<IGameEventHandler>();
	}
	
	public void addTEventHandler(IGameEventHandler handler) 
	{
		this._handlers.add(handler);
	}
	
	public void clearEventHandler() 
	{
		this._handlers.clear();
	}
	
	public void fire(GameEventArg event) 
	{
		for (IGameEventHandler handler : _handlers) 
		{
			handler.handle(event);
		}
	}
}
