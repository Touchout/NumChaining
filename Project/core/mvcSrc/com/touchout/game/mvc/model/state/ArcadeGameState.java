package com.touchout.game.mvc.model.state;

public abstract class ArcadeGameState implements IState
{
	public enum ArcadeGameStateCode {Playing, Resulting};
	
	@Override
	public abstract IState transition(Object input);

	@Override
	public abstract Enum getStateCode();
}
