package com.touchout.game;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.touchout.game.component.NumBoard;
import com.touchout.game.component.ResultingUI;
import com.touchout.game.mvc.Assets;
import com.touchout.game.mvc.Config;
import com.touchout.game.mvc.view.actor.TextActor;

public class ArcadeModeComponentPackage implements IGameComponentPackage 
{	
	//Actors, Groups
	private ResultingUI _resultingUI;
	private NumBoard _board;
	TextActor _timeBonusHint;
	
	public TextActor getTimeBonusHint() {
		return _timeBonusHint;
	}

	public NumBoard getBoard() {
		return _board;
	}
	
	public ResultingUI getResultingUI() {
		return _resultingUI;
	}
	
	public ArcadeModeComponentPackage()
	{
		super();
		initialize();
	}
	
	@Override
	public void initialize() 
	{
		//Set Game Board
		//Position computing for centralize game board (block assumed to be square)
		//calculate length of (block+margin)
		float blockMarginSet = (Config.BOARD_UPPER_BOUND - Config.BLOCK_MARGIN) / Config.ROW_COUNT;
		//calculate block length
		float blockSize = blockMarginSet - Config.BLOCK_MARGIN;
		//calculate board width
		float boardWidth = blockMarginSet*Config.ROW_COUNT - Config.BLOCK_MARGIN;
		//calculate board horizontal padding
		float horizontalPadding = (Config.FRUSTUM_WIDTH - boardWidth) / 2;
		
		_board = new NumBoard(new Vector2(horizontalPadding, Config.BLOCK_MARGIN), Config.COLUMN_COUNT, Config.ROW_COUNT, blockSize, blockSize);

		//Set Time Bonus Hint
		_timeBonusHint = new TextActor(Assets.TimeBonusFont, "TIME++", 100, 700);
		_timeBonusHint.setVisible(false);
		
		//Set Resulting UI
		_resultingUI = new ResultingUI();
	}

	@Override
	public void restart() 
	{
		_board.reinitial();
		_board.shuffle();
		_resultingUI.initialize();
	}

	@Override
	public void updateComponent(Object metaData) {
		// TODO Auto-generated method stub

	}
	
	public void displayTimeBonusHint()
	{
		//_timeBonusHint.setColor(1, 1, 1, 1);
		_timeBonusHint.setVisible(true);
		_timeBonusHint.setPosition(100, 700);
		_timeBonusHint.addAction(Actions.sequence(
						//Actions.alpha(0, 1f, Interpolation.),
						Actions.moveBy(0, 50, 0.5f,Interpolation.exp5),
						//Actions.fadeOut(1f),
						Actions.hide()));
	}

}
