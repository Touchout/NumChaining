package com.touchout.game.states;

import com.badlogic.gdx.Screen;
import com.touchout.game.IGameComponentPackage;
import com.touchout.game.event.TEvent;
import com.touchout.game.event.ITEventHandler;

public abstract class GameStater implements ITEventHandler 
{
	protected IGameComponentPackage _componentPackage;
	protected Object _metadata;
	protected GameStateCode _stateCode;
	
	public GameStateCode getStateCode() {
		return _stateCode;
	}
	public GameStater(IGameComponentPackage pack, Object metadata)
	{
		_componentPackage = pack;
		_metadata = metadata;
		_stateCode = defineStateCode(); 
	}
	
	protected abstract GameStateCode defineStateCode();
	
	public abstract GameStateCode update(float delta);
	
	@Override
	public abstract void handle(TEvent event);
	
}
