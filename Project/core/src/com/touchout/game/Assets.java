package com.touchout.game;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Logger;

public class Assets 
{
	public static Logger globalLogger;
	public static TextureRegion[] NumBlockTextures;
	//public static TextureRegion NumBlockTextureReverse;
	public static Sound Correct1, Correct2, Success, Wrong, endSound;
	public static Music bgMusic;
	public static BitmapFont TimeFont;
	public static BitmapFont ScoreFont;
	public static BitmapFont ComboFont;
	
	public static void Load() 
	{
		//Load Textures
		NumBlockTextures = new TextureRegion[17];
		
		for (int i = 1; i <= 16; i++) 
		{
			NumBlockTextures[i] = LoadTexture(String.format("data/num_block_%02d.png", i));
		}
		
		
		//Load Audio
		Correct1 = Gdx.audio.newSound(Gdx.files.internal("correct.mp3"));
		Correct2 = Gdx.audio.newSound(Gdx.files.internal("correct2.mp3"));
		Success = Gdx.audio.newSound(Gdx.files.internal("correct_long.mp3"));
		Wrong = Gdx.audio.newSound(Gdx.files.internal("wrong.mp3"));
		endSound = Gdx.audio.newSound(Gdx.files.internal("endup.mp3"));
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("relax_summer.wav"));
		bgMusic.setVolume(0.4f);
		bgMusic.setLooping(true);
		
		//Load Font
		FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("lcd.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 200;
		parameter.characters = "0123456789:";
		TimeFont = fontGenerator.generateFont(parameter);
		parameter.size = 50;
		ScoreFont = fontGenerator.generateFont(parameter);
		parameter.characters = "0123456789Combs";
		ComboFont = fontGenerator.generateFont(parameter);
		fontGenerator.dispose();
	}
	
	private static TextureRegion LoadTexture(String path) 
	{
		return new TextureRegion(new Texture(Gdx.files.internal(path)));
	}
}
