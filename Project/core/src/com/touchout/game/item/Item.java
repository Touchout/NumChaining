package com.touchout.game.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.touchout.game.IGameComponentPackage;

public abstract class Item 
{	
	final TextureRegion _icon;
	final ItemManager _manager;
	boolean _expired;
	
	//========Getters/Setters========//
	public boolean isExpired() {return _expired;}
	public TextureRegion getIcon() {return _icon;}
	
	public Item(ItemManager manager)
	{
		_icon = this.defineIcon();
		_expired = false;
		_manager = manager;
	}
	
	protected abstract TextureRegion defineIcon();
	protected abstract void _fire(IGameComponentPackage pack, Object metadata);
	
	public void fire(IGameComponentPackage pack, Object metadata)
	{
		if(!this._expired)
			_fire(pack, metadata);
	}
}
