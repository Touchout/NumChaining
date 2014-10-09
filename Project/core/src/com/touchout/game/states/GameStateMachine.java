package com.touchout.game.states;

import java.util.HashMap;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.touchout.game.event.TEvent;
import com.touchout.game.event.TEventHandler;

public class GameStateMachine implements TEventHandler 
{
	List<GameStater> _staters;
	HashMap<GameStateCode, GameStater> _stateDictionary;
	GameStater _currentStater;
	
	public GameStateMachine(Screen screen, GameStateCode initialStateCode)
	{
		_stateDictionary = new HashMap<GameStateCode, GameStater>();
		_stateDictionary.put(GameStateCode.Intro, new IntroStater(screen));
		_stateDictionary.put(GameStateCode.Playing, new PlayingStater(screen));
		_stateDictionary.put(GameStateCode.Resulting, new ResultingStater(screen));
		
		_currentStater = _stateDictionary.get(initialStateCode);
	}
	
	public void update(float delta)
	{
		_currentStater = _stateDictionary.get(_currentStater.update(delta));
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
