package com.touchout.game.mvc.controller;

import com.touchout.game.mvc.core.NumChaining;
import com.touchout.game.mvc.model.ArcadeGameModel;
import com.touchout.game.mvc.model.ArcadeGameModel.GameState;
import com.touchout.game.mvc.model.MainMenuModel;
import com.touchout.game.mvc.view.ArcadeGameView;
import com.touchout.game.mvc.view.MainMenuView;

public class MainMenuController 
{
	NumChaining _game;
	MainMenuModel _model;
	MainMenuView _view;

	public NumChaining getGame() { return _game; }
	
	public MainMenuController(MainMenuModel model, NumChaining game)
	{
		_model = model;
		_game = game;
		
		//Initial View
		_view = new MainMenuView(_model, this);
	}
	
	public void resize(int width, int height) 
	{
		_view.resize(width, height);
	}
	
	public void startArcadeGame() 
	{
		_game.startArcadeGame();		
	}
	
	public void update(float delta)
	{		
	}
	
	public void reset() {
		_view.reset();		
	}
	
	public void renderView()
	{
		_view.update();
		_view.render();
	}		
}
