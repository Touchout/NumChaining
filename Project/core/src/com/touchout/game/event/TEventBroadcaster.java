package com.touchout.game.event;

import com.badlogic.gdx.utils.Array;

public class TEventBroadcaster 
{
	Array<TEventHandler> _tEventHandlers;
	
	public TEventBroadcaster()
	{
		_tEventHandlers = new Array<TEventHandler>();
	}
	
	public void addTEventHandler(TEventHandler handler) 
	{
		this._tEventHandlers.add(handler);
	}
	
	public void clearEventHandler() 
	{
		this._tEventHandlers.clear();
	}
	
	public void broadcast(TEvent event) 
	{
		for (TEventHandler tEventHandler : _tEventHandlers) 
		{
			tEventHandler.handle(event);
		}
	}
}
