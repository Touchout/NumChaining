package com.touchout.game.mvc.model.state;

import com.touchout.game.mvc.core.ArcadeMetadata;
import com.touchout.game.mvc.model.ArcadeGameModel.GameState;

public class ArcadeStateMachine extends AbsStateMachine 
{
	ArcadeMetadata _metadata;
	public ArcadeStateMachine(ArcadeMetadata metadata) 
	{
		_metadata = metadata;
	}

	@Override
	protected IState _update() 
	{
		return getCurrentState().transition(_metadata);
	}
	
	public static abstract class ArcadeGameState implements IState
	{
		public enum ArcadeGameStateCode {Playing, Resulting};
		
		@Override
		public abstract IState transition(Object input);

		@Override
		public abstract Enum getStateCode();
	}
}
