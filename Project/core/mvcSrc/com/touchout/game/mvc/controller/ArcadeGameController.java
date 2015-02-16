package com.touchout.game.mvc.controller;

import com.touchout.game.NumChaining;
import com.touchout.game.mvc.model.ArcadeGameModel;
import com.touchout.game.mvc.model.ArcadeGameModel.GameState;
import com.touchout.game.mvc.view.ArcadeGameView;

public class ArcadeGameController 
{
	NumChaining _game;
	ArcadeGameModel _model;
	ArcadeGameView _view;

	public NumChaining getGame() { return _game; }
	
	public ArcadeGameController(ArcadeGameModel model, NumChaining game)
	{
		_model = model;
		_game = game;
		
		//Initial View
		_view = new ArcadeGameView(_model, this);
	}
	
	public void resize(int width, int height) 
	{
		_view.resize(width, height);
	}
	
	public void update(float delta)
	{
		_model.update(delta);
	}
	
	public void renderView()
	{
		_view.update();
		_view.render();
	}
	
	public void touchBlock(int row, int col) 
	{
		if(_model.getState() == GameState.Playing)
			_model.touchBlock(row, col);
	}
	
	public void restartGame() 
	{
		_model.reset();
	}
}
