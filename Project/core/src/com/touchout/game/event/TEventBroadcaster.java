package com.touchout.game.event;

import com.badlogic.gdx.utils.Array;

public class TEventBroadcaster 
{
	Array<ITEventHandler> _tEventHandlers;
	
	public TEventBroadcaster()
	{
		_tEventHandlers = new Array<ITEventHandler>();
	}
	
	public void addTEventHandler(ITEventHandler handler) 
	{
		this._tEventHandlers.add(handler);
	}
	
	public void clearEventHandler() 
	{
		this._tEventHandlers.clear();
	}
	
	public void broadcast(TEvent event) 
	{
		for (ITEventHandler tEventHandler : _tEventHandlers) 
		{
			tEventHandler.handle(event);
		}
	}
}
