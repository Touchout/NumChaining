package com.touchout.game.event;

public class TEvent 
{
	private Object Sender;
	
	public TEvent(Object sender)
	{
		this.Sender = sender;
	}
	
	public Object getSender() {
		return Sender;		
	}
	
	public void setSender(Object value) 
	{
		this.Sender = value;
	}
}