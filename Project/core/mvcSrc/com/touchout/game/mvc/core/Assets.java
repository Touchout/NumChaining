package com.touchout.game.mvc.core;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Logger;

public class Assets 
{
	public static Preferences preferences = Gdx.app.getPreferences("NumChaining");
	public static Logger globalLogger;
	public static TextureRegion[] NumBlockTextures;
	public static TextureRegion[] NumBlockBackgroundTextures;
	public static TextureRegion mainBackgroundTexture;
	public static TextureRegion DarkButtonTexture;
	public static TextureRegion LightButtonTexture;
	public static TextureRegion timeBannerTexture;
	public static TextureRegion statisticBackgroundTexture;
	public static TextureRegion scoreBackgroundTexture;
	public static TextureRegion logoTexture;
	public static TextureRegion restartTexture;
	//public static TextureRegion NumBlockTextureReverse;
	public static Sound Correct1, Correct2, Correct3, Click, Success, Wrong, endSound;
	public static Sound Get,Get2,Get3;
	public static Music bgMusic;
	public static BitmapFont TimeFont;
	public static BitmapFont TimeTitleFont;
	public static BitmapFont TimeBonusFont;
	public static BitmapFont ScoreFont;
	public static BitmapFont ComboFont;
	public static BitmapFont LevelFont;
	public static BitmapFont MainTitleFont;
	public static BitmapFont SubTitleFont;
	public static BitmapFont BlockFont;
	public static BitmapFont MainMenuButtonFont;
	public static BitmapFont MainMenuButtonFontDark;
	public static BitmapFont FinalScoreFont;
	public static BitmapFont TimePlusFont;
	
	public static Color COLOR_DARK = new Color(0x0A132AFF);
	public static Color COLOR_GRAY = new Color(0xCCCCCCFF);
	public static Color COLOR_ORAGE = new Color(0xF59E02FF);
	
	public static Sound PressSound = Gdx.audio.newSound(Gdx.files.internal("sounds/water_drop_next.mp3"));
	public static Sound AlarmSound = Gdx.audio.newSound(Gdx.files.internal("sounds/wrong_alarm_next.mp3"));
	public static Sound PlusSound = Gdx.audio.newSound(Gdx.files.internal("sounds/magic_sound_next.mp3"));
	
	public static TextureRegion getBlockBackgroundTexture() {
		return NumBlockBackgroundTextures[MathUtils.random(3)];
	}
	
	public static void Load() 
	{
		//Load Textures
		mainBackgroundTexture = LoadTexture("graphics/mainbg.png");
		DarkButtonTexture = LoadTexture("graphics/btn_primary.png");;
		LightButtonTexture = LoadTexture("graphics/btn_secondary.png"); 
		timeBannerTexture = LoadTexture("graphics/bg_small_dark.png");		
		statisticBackgroundTexture = LoadTexture("graphics/statisticbg.png");
		scoreBackgroundTexture = LoadTexture("graphics/scorebg.png");
		restartTexture  = LoadTexture("graphics/home.png");
		logoTexture = LoadTexture("graphics/logo.png");
		
		NumBlockTextures = new TextureRegion[17];
		NumBlockBackgroundTextures = new TextureRegion[5];
		
		for (int i = 1; i <= 16; i++) 
		{
			NumBlockTextures[i] = LoadTexture(String.format("data/num_block_%02d.png", i));
		}
		
		for (int i = 0; i < 5; i++) 
		{
			NumBlockBackgroundTextures[i] = LoadTexture(String.format("blocks/blockBg_%d.png", i));
		}
		
		//Load Audio
		Correct1 = Gdx.audio.newSound(Gdx.files.internal("correct.mp3"));
		//Correct1 = Gdx.audio.newSound(Gdx.files.internal("mouse_click.mp3"));
		Correct2 = Gdx.audio.newSound(Gdx.files.internal("correct2.mp3"));
		Correct3 = Gdx.audio.newSound(Gdx.files.internal("correct3.mp3"));
		Get = Gdx.audio.newSound(Gdx.files.internal("get.mp3"));
		Get2 = Gdx.audio.newSound(Gdx.files.internal("get2.mp3"));
		Get3 = Gdx.audio.newSound(Gdx.files.internal("get3.mp3"));
		Click = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));
		Success = Gdx.audio.newSound(Gdx.files.internal("correct_long.mp3"));
		Wrong = Gdx.audio.newSound(Gdx.files.internal("radio_noise01.mp3"));
		endSound = Gdx.audio.newSound(Gdx.files.internal("endup.mp3"));
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("hidari_sinri.mp3"));
		bgMusic.setVolume(0.9f);
		bgMusic.setLooping(true);
		
		//Load Font		
		FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Lato-Regular.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();					
		parameter.characters = "0123456789LEV";
		LevelFont = fontGenerator.generateFont(parameter);
		LevelFont.setColor(COLOR_DARK);
		parameter.size = 100;
		parameter.characters = "TIME+";
		TimeBonusFont = fontGenerator.generateFont(parameter);
		TimeBonusFont.setColor(COLOR_DARK);
		parameter.size = 100;
		parameter.characters = GlobalConfig.GAME_TITLE;
		MainTitleFont = fontGenerator.generateFont(parameter);
		MainTitleFont.setColor(COLOR_DARK);
		parameter.size = 80;
		parameter.characters = "1234567890";
		BlockFont = fontGenerator.generateFont(parameter);
		parameter.size = 40;
		parameter.characters = " STARPLYUCOEHGIN";
		MainMenuButtonFont = fontGenerator.generateFont(parameter);		
		MainMenuButtonFontDark = fontGenerator.generateFont(parameter);
		MainMenuButtonFontDark.setColor(COLOR_DARK);
		
		fontGenerator.dispose();
		
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Lato-Black.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = 40;
		parameter.characters = " STARPLYUCOEHGIN";
		MainMenuButtonFont = fontGenerator.generateFont(parameter);		
		parameter.size = 25;
		parameter.characters = " TIMERANSCOYU";
		TimeTitleFont = fontGenerator.generateFont(parameter);
		parameter.size = 80;
		parameter.characters = "0123456789:";
		TimeTitleFont.setColor(new Color(0xCCCCCCFF));
		TimeFont = fontGenerator.generateFont(parameter);
		parameter.size = 50;
		parameter.characters = "0123456789";
		ScoreFont = fontGenerator.generateFont(parameter);
		ScoreFont.setColor(COLOR_DARK);
		parameter.size = 25;
		parameter.characters = "0123456789COMBS";
		ComboFont = fontGenerator.generateFont(parameter);
		ComboFont.setColor(COLOR_DARK);
		parameter.size = 100;
		parameter.characters = "0123456789";
		FinalScoreFont = fontGenerator.generateFont(parameter);
		FinalScoreFont.setColor(COLOR_ORAGE);
		parameter.size = 60;
		parameter.characters = "+2";
		TimePlusFont = fontGenerator.generateFont(parameter);
		TimePlusFont.setColor(COLOR_ORAGE);
		
		fontGenerator.dispose();
		
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Lato-Italic.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = 35;
		parameter.characters = "abcdefghijklmnopqrstuvwxyz ";
		SubTitleFont = fontGenerator.generateFont(parameter);		
		SubTitleFont.setColor(COLOR_DARK);
		
		fontGenerator.dispose();
	}
	
	private static TextureRegion LoadTexture(String path) 
	{
		return new TextureRegion(new Texture(Gdx.files.internal(path)));
	}
}
