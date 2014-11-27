package com.touchout.game.item;

import com.touchout.game.IGameComponentPackage;

public abstract class OneShotItem extends Item 
{
	public OneShotItem(ItemManager manager) { super(manager);}
	
	@Override
	public void fire(IGameComponentPackage pack, Object metadata) 
	{
		super.fire(pack, metadata);
		this._expired = true;
	}
}
