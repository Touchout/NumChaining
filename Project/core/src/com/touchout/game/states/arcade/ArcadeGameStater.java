package com.touchout.game.states.arcade;

import com.touchout.game.ArcadeMetadata;
import com.touchout.game.ArcadeModeComponentPackage;
import com.touchout.game.component.NumBoard;
import com.touchout.game.event.BlockSolvedTEvent;
import com.touchout.game.event.LockBoardTEvent;
import com.touchout.game.event.TEvent;
import com.touchout.game.event.TEventCode;
import com.touchout.game.mvc.Assets;
import com.touchout.game.states.GameStateCode;
import com.touchout.game.states.GameStater;

public abstract class ArcadeGameStater extends GameStater 
{
	protected ArcadeModeComponentPackage _arcadePackage;
	protected ArcadeMetadata _arcadeMetadata;
	
	public ArcadeGameStater(ArcadeModeComponentPackage pack, ArcadeMetadata metadata) 
	{
		super(pack, metadata);
		_arcadePackage = (ArcadeModeComponentPackage) _componentPackage;
		_arcadeMetadata = (ArcadeMetadata) _metadata;
	}
}
