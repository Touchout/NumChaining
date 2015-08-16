package com.touchout.game.mvc.model;

import com.badlogic.gdx.math.MathUtils;

public class NumBoardEntity 
{
	NumBlockEntity[][] _cells;
	int _rowCount, _colCount;
	boolean _locked = false;
	
	public NumBlockEntity[][] getCells() { return _cells; }
	
	public boolean isLocked() { return _locked; }
	
	public NumBoardEntity(int rowCount, int ColCount)
	{
		_locked = false;
		_rowCount = rowCount;
		_colCount = ColCount;
		_cells = new NumBlockEntity[_rowCount][_colCount];
		
		handleEachCell(new CellHandler() {
			@Override
			public void handle(int row, int col) {
				int num;
				num = row * _colCount + (col+1);
				_cells[row][col] = new NumBlockEntity();
				_cells[row][col].Number = num;
			}
		});
	}
	
	public void lock() 
	{
		handleEachCell(new CellHandler() {
			@Override
			public void handle(int row, int col) {
				_cells[row][col].IsLocked = true;
			}
		});
		_locked = true;
	}
	
	public void unlock() 
	{
		handleEachCell(new CellHandler() {
			@Override
			public void handle(int row, int col) {
				_cells[row][col].IsLocked = false;
			}
		});
		_locked = false;
	}
	
	public void solveBlock(int row, int col) 
	{
		_cells[row][col].IsSolved = true;
		_cells[row][col].BgNumber = 4;		
	}
	
	public void renew()
	{
		handleEachCell(new CellHandler() {
			@Override
			public void handle(int row, int col) 
			{
				_cells[row][col].IsSolved = false;
			}
		});
	}
	
	public void shuffle()
	{
		handleEachCell(new CellHandler() {
			@Override
			public void handle(int row, int col) 
			{
				int rowToSwap, colToSwap;
				int temp;
				
				rowToSwap = MathUtils.random(_rowCount-1);
				colToSwap = MathUtils.random(_colCount-1);
				
				temp = _cells[rowToSwap][colToSwap].Number;
				_cells[rowToSwap][colToSwap].Number = _cells[row][col].Number;
				_cells[row][col].Number = temp;
				_cells[row][col].BgNumber = MathUtils.random(3);
			}
		});
	}
	
	private void handleEachCell(CellHandler handler)
	{
		for (int row = 0; row < _rowCount; row++) {
			for (int col = 0; col < _colCount; col++) {
				handler.handle(row, col);
			}
		}
	}
	
	interface CellHandler{
		void handle(int row, int col);
	}
}
