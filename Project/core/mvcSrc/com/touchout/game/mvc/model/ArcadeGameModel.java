package com.touchout.game.mvc.model;

import com.touchout.game.Config;
import com.touchout.game.mvc.ArcadeMetadata;

public class ArcadeGameModel 
{
	NumBlockEntity[][] _boardEntity;
	ArcadeMetadata _metadata;
	
	public ArcadeMetadata getMetadata() {	return _metadata;}

	public NumBlockEntity[][] getBboardEntity() { return _boardEntity; }

	public ArcadeGameModel()
	{
		initialize();
	}
	
	public void update(float delta)
	{
		_metadata.update(delta);
	}
	
	private void initialize()
	{
		//init board
		int num;
		_boardEntity = new NumBlockEntity[Config.ROW_COUNT][Config.COLUMN_COUNT];
		for (int row = 0; row < Config.ROW_COUNT; row++) 
			for (int col = 0; col < Config.COLUMN_COUNT; col++) 
			{
				num = row * Config.COLUMN_COUNT + (col+1);
				_boardEntity[row][col] = new NumBlockEntity();
				_boardEntity[row][col].Number = num;
			}
		
		//init metadata
		_metadata = new ArcadeMetadata();
		_metadata.initialize();
	}
	
	public void touchBlock(int row, int col) 
	{
		_boardEntity[row][col].IsSolved = true;
	}
}
