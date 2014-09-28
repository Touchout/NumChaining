package com.touchout.game.event;

public class LockBoardTEvent extends TEvent 
{
	public int Time;
	
	public LockBoardTEvent(int time, Object sender)
	{
		super(sender);
		Time = time;
	}
}
