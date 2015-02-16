package com.touchout.game.mvc.event;

public class GameEventArg 
{
	private Object Sender;
	public Object Tag;
	
	public GameEventArg(Object sender)
	{
		this.Sender = sender;
		this.Tag = null;
	}
	
	public GameEventArg(Object sender, Object tag)
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
