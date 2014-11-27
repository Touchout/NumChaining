package com.touchout.game.item;

import com.touchout.game.IGameComponentPackage;

public abstract class TimeLastingItem extends Item 
{
	private float _activateTime;
	
	public TimeLastingItem(ItemManager manager) {super(manager);}
	
	@Override
	public void fire(IGameComponentPackage pack, Object metadata) 
	{
		super.fire(pack, metadata);
		
		
	}
}
