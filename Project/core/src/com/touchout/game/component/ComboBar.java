package com.touchout.game.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sun.org.glassfish.external.statistics.annotations.Reset;

public class ComboBar extends Actor 
{
	private ParticleEffect _indicator; 
	private ShapeRenderer _shapeRenderer;
	public float Max, Min;
	private float _current;
	private boolean _showUpperBound = false;
	private boolean _alwaysShow = false;
	
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

	public void setCurrent(float value)
	{
		_current = MathUtils.clamp(value, Min, Max);
	}
	
	public ComboBar(float x, float y, float width, float height)
	{
		this.setBounds(x, y, width, height);
		Max = 100;
		_current = 100;
		Min = 0;
		
		_indicator = new ParticleEffect();
		_indicator.load(Gdx.files.internal("data/ComboIndicator.p"), Gdx.files.internal("data"));
		_indicator.setPosition(this.getX() + this.getWidth() * (_current-Min)/(Max-Min), this.getY());
		_indicator.findEmitter("blue").setContinuous(true);
		_indicator.findEmitter("green").setContinuous(true);
		_indicator.start();
		
		_shapeRenderer = new ShapeRenderer();
	}
	
	public void reset()
	{
		_indicator.setPosition(this.getRight(), this.getY());
		_indicator.reset();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) 
	{
		if(_alwaysShow || _current > Min)
		{
			float currentX = this.getX() + this.getWidth() * (_current-Min)/(Max-Min);
			
			//batch.setProjectionMatrix(projection);
			
			//_indicator.setPosition(currentX, this.getY());
			//_indicator.draw(batch, Gdx.graphics.getDeltaTime());
			
			batch.end();
			_shapeRenderer.setProjectionMatrix(this.getStage().getCamera().combined);
			_shapeRenderer.begin(ShapeType.Line);
			_shapeRenderer.setColor(1, 1, 1, 1);
			//draw ball
			_shapeRenderer.circle(currentX, this.getY(), 10);
			//draw line
			_shapeRenderer.line(this.getX(), this.getY(), currentX, this.getY());
			
			if(this.isShowUpperBound())
			{
				_shapeRenderer.line(this.getRight(), this.getTop(), this.getRight(), this.getY());
			}
			
			_shapeRenderer.end();
			batch.begin();
			
			//_shapeRenderer.rect(x, y, width, height);
			//_shapeRenderer.circle(x, y, radius);
		}
	}
}
