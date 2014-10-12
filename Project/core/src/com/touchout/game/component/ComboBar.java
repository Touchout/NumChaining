package com.touchout.game.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sun.org.glassfish.external.statistics.annotations.Reset;

public class ComboBar extends Actor 
{
	private ParticleEffect _indicator; 
	private ShapeRenderer _shapeRenderer;
	public float Max, Min, Current;
	
	public ComboBar(int x, int y, int width, int height)
	{
		this.setBounds(x, y, width, height);
		Max = 100;
		Current = 100;
		Min = 0;
		
		_indicator = new ParticleEffect();
		_indicator.load(Gdx.files.internal("data/ComboIndicator.p"), Gdx.files.internal("data"));
		_indicator.setPosition(this.getX() + this.getWidth() * (Current-Min)/(Max-Min), this.getY());
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
		if(Current > Min)
		{
			float currentX = this.getX() + this.getWidth() * (Current-Min)/(Max-Min);
			
			 _indicator.setPosition(currentX, this.getY());
			 _indicator.draw(batch, Gdx.graphics.getDeltaTime());
			 
			 
			 _shapeRenderer.setProjectionMatrix(this.getStage().getCamera().combined);
			 _shapeRenderer.begin(ShapeType.Line);
			 _shapeRenderer.setColor(1, 1, 1, 1);		
			 _shapeRenderer.line(this.getX(), this.getY(), currentX, this.getY());
			 //_shapeRenderer.rect(x, y, width, height);
			 //_shapeRenderer.circle(x, y, radius);
			 _shapeRenderer.end();
		}
	}
}
