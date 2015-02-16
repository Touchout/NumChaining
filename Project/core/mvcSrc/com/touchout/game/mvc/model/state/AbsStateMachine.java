package com.touchout.game.mvc.model.state;


public abstract class AbsStateMachine 
{	
	private IState _currentState;
	
	public IState getCurrentState() {
		return _currentState;
	}

	public void setCurrentState(IState _currentState) {
		this._currentState = _currentState;
	}
	
	protected abstract IState _update();

	public void Update()
	{
		_currentState = _update();
	}
}
