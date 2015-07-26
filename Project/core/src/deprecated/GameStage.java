package deprecated;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.touchout.game.component.NumBoard;
import com.touchout.game.mvc.Config;

public class GameStage extends Stage
{
	Logger logger = new Logger("level");

	NumBoard _board;
	
	
	public GameStage(int rowCount, int colCount ,SpriteBatch batch)
	{
		super(new FitViewport(Config.FRUSTUM_WIDTH, Config.FRUSTUM_HEIGHT), batch);
		
		float blockMarginSet = (Config.BOARD_UPPER_BOUND - Config.BLOCK_MARGIN) / rowCount;
		float blockSize = blockMarginSet - Config.BLOCK_MARGIN;
		float boardWidth = blockMarginSet*rowCount - Config.BLOCK_MARGIN;
		float horizontalPadding = (Config.FRUSTUM_WIDTH - boardWidth) / 2;
		
		_board = new NumBoard(new Vector2(horizontalPadding, Config.BLOCK_MARGIN), colCount, rowCount, blockSize, blockSize);
		this.addActor(_board);
		logger.setLevel(Application.LOG_DEBUG);
		//_board._blockWidth = _board._blockHeight = blockSize;
	}
	
	public NumBoard getBoard()
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
