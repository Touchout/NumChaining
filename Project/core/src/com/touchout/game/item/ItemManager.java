package com.touchout.game.item;

import com.badlogic.gdx.utils.Array;

public class ItemManager 
{
	private static int _itemCount;
	
	private Array<Item> _activatingItems;
	
	public static int getItemCount() {return _itemCount;}

	public static <T extends Item> T createItem(Class<T> type)
	{
		_itemCount++;
		return null;
	}
}
