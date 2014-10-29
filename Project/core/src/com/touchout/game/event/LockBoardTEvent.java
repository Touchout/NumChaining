package com.touchout.game.event;

public class LockBoardTEvent extends TEvent 
{
	public int Time = -1;
	
	public LockBoardTEvent(int time, Object sender)
	{
		super(sender);
		Time = time;
	}
	
	public LockBoardTEvent(Object sender)
	{
		super(sender);
	}
}
