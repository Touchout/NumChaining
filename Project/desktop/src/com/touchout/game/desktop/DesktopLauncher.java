package com.touchout.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.touchout.game.NumChaining;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 504;
		config.height = 896;
		//config.width = 720 /2;
		//config.height = 1280/2;
		new LwjglApplication(new NumChaining(), config);
	}
}
