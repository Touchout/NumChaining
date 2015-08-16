package com.touchout.game.mvc.view.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.touchout.game.mvc.core.Assets;

public class ProgressBar extends Actor 
{
	//private ParticleEffect _indicator; 
	private ShapeRenderer _shapeRenderer;
	public float _max, _min;
	private float _scaleFactor;
	private float _current;
	private boolean _showUpperBound = false;
	private boolean _alwaysShow = false;
	
	public ProgressBar(float x, float y, float width, float height)
	{
		this.setBounds(x, y, width, height);
		_max = 100;
		_current = 100;
		_min = 0;
		_scaleFactor = 1;
		
//		_indicator = new ParticleEffect();
//		_indicator.load(Gdx.files.internal("data/ComboIndicator.p"), Gdx.files.internal("data"));
//		_indicator.setPosition(this.getX() + this.getWidth() * (_current-Min)/(Max-Min), this.getY());
//		_indicator.findEmitter("blue").setContinuous(true);
//		_indicator.findEmitter("green").setContinuous(true);
//		_indicator.start();
		
		_shapeRenderer = new ShapeRenderer();
	}
	
	public void reset()
	{
//		_indicator.setPosition(this.getRight(), this.getY());
//		_indicator.reset();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) 
	{
		if(_alwaysShow || _current > _min)
		{
			float currentX = this.getX() + this.getWidth() * (_current-_min)/(_max-_min) * _scaleFactor;
			
			//batch.setProjectionMatrix(projection);
			
			//_indicator.setPosition(currentX, this.getY());
			//_indicator.draw(batch, Gdx.graphics.getDeltaTime());
			
			batch.end();
			_shapeRenderer.setProjectionMatrix(this.getStage().getCamera().combined);
			_shapeRenderer.begin(ShapeType.Filled);			
			//draw ball
			//_shapeRenderer.circle(currentX + 10, this.getY(), 10);
			
			if(this.isShowUpperBound())
			{
				_shapeRenderer.setColor(Assets.COLOR_GRAY);
				_shapeRenderer.rectLine(this.getX(), this.getY(), this.getRight(), this.getY(), 5);
			}
			
			//draw line
			//_shapeRenderer.line(this.getX(), this.getY(), currentX, this.getY());
			_shapeRenderer.setColor(Assets.COLOR_DARK);
			_shapeRenderer.rectLine(this.getX(), this.getY(), currentX, this.getY(), 5);
			
			_shapeRenderer.end();
			batch.begin();
			
			//_shapeRenderer.rect(x, y, width, height);
			//_shapeRenderer.circle(x, y, radius);
		}
	}

	
	//******************** Getters & Setters  ********************//
	
	public float getMax() {
		return _max;
	}

	public void setMax(float max) {
		_max = max;
	}

	public float getMin() {
		return _min;
	}

	public void setMin(float min) {
		_min = min;
	}
	
	public void setScaleFactor(float value) {
		_scaleFactor = value;
	}
	public void setCurrent(float value)
	{
		_current = MathUtils.clamp(value, _min, _max);
	}
	
	public boolean isAlwaysShow() {
		return _alwaysShow;
	}

	public void setAlwaysShow(boolean alwaysShow) {
		this._alwaysShow = alwaysShow;
	}

	public boolean isShowUpperBound() {
		return _showUpperBound;
	}

	public void setShowUpperBound(boolean showUpperBound) {
		this._showUpperBound = showUpperBound;
	}

}
