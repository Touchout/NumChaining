package deprecated;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.touchout.game.Config;
import com.touchout.game.component.NumBoardGroup;

public class GameStage extends Stage
{
	Logger logger = new Logger("level");

	NumBoardGroup _board;
	
	
	public GameStage(int rowCount, int colCount ,SpriteBatch batch)
	{
		super(new FitViewport(Config.FRUSTUM_WIDTH, Config.FRUSTUM_HEIGHT), batch);
		
		int blockMarginSet = (Config.BOARD_UPPER_BOUND - Config.BLOCK_MARGIN) / rowCount;
		int blockSize = blockMarginSet - Config.BLOCK_MARGIN;
		int boardWidth = blockMarginSet*rowCount - Config.BLOCK_MARGIN;
		int horizontalPadding = (Config.FRUSTUM_WIDTH - boardWidth) / 2;
		
		_board = new NumBoardGroup(new Vector2(horizontalPadding, Config.BLOCK_MARGIN), colCount, rowCount, blockSize, blockSize);
		this.addActor(_board);
		logger.setLevel(Application.LOG_DEBUG);
		//_board._blockWidth = _board._blockHeight = blockSize;
	}
	
	public NumBoardGroup getBoard()
	{
		return _board;
	}
	
	public void renewBoard() 
	{
		_board.reinitial();
		_board.shuffle();
	}
	
	public boolean getBoardSolved() 
	{
		return _board.Solved;
	}
	
	
}
