package com.touchout.game.event;

public class TEvent 
{
	private Object Sender;
	public Object Tag;
	
	public TEvent(Object sender)
	{
		this.Sender = sender;
		this.Tag = null;
	}
	
	public TEvent(Object sender, Object tag)
	{
		this.Sender = sender;
		this.Tag = tag;
	}
	
	public Object getSender() {
		return Sender;		
	}
	
	public void setSender(Object value) 
	{
		this.Sender = value;
	}
}
