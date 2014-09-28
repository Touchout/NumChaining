package com.touchout.game.event;

public class BlockSolvedTEvent extends TEvent
{
	public int Reward;
	
	public BlockSolvedTEvent(int reward, Object sender)
	{
		super(sender);
		Reward = reward;
	}
}
