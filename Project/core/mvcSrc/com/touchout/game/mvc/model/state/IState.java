package com.touchout.game.mvc.model.state;

public interface IState 
{	
	public IState transition(Object input);
	
	public Enum getStateCode();
}
