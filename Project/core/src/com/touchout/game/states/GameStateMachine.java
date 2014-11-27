package com.touchout.game.states;

import java.util.HashMap;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.touchout.game.ArcadeMetadata;
import com.touchout.game.ArcadeModeComponentPackage;
import com.touchout.game.IGameComponentPackage;
import com.touchout.game.event.StateChangeTEvent;
import com.touchout.game.event.TEvent;
import com.touchout.game.event.TEventBroadcaster;
import com.touchout.game.event.ITEventHandler;
import com.touchout.game.states.arcade.IntroStater;
import com.touchout.game.states.arcade.PlayingStater;
import com.touchout.game.states.arcade.ResultingStater;

public class GameStateMachine implements ITEventHandler 
{
	private List<GameStater> _staters;
	private HashMap<GameStateCode, GameStater> _stateDictionary;
	private GameStater _currentStater;
	private TEventBroadcaster _broadcaster;
	
	public TEventBroadcaster getBroadcaster() {
		return _broadcaster;
	}

	public GameStateMachine(ArcadeModeComponentPackage pack, ArcadeMetadata metadata, GameStateCode initialStateCode)
	{
		_broadcaster = new TEventBroadcaster();
		
		_stateDictionary = new HashMap<GameStateCode, GameStater>();
		_stateDictionary.put(GameStateCode.Intro, new IntroStater(pack,metadata));
		_stateDictionary.put(GameStateCode.Playing, new PlayingStater(pack,metadata));
		_stateDictionary.put(GameStateCode.Resulting, new ResultingStater(pack,metadata));
		
		_currentStater = _stateDictionary.get(initialStateCode);
	}
	
	public void update(float delta)
	{
		GameStater nextState = _stateDictionary.get(_currentStater.update(delta));
		
		if(nextState.getStateCode() != _currentStater.getStateCode())
		{
			_currentStater = nextState;
			_broadcaster.broadcast(new StateChangeTEvent(this, _currentStater.getStateCode()));
		}
	}
	
	public GameStateCode getCurrentStateCode()
	{
		return _currentStater.getStateCode();
	}
	
	@Override
	public void handle(TEvent event) 
	{
//		for (GameStater stater : _staters.getItems()) {
//			stater.handle(event);
//		}
		_currentStater.handle(event);
	}
	
}
