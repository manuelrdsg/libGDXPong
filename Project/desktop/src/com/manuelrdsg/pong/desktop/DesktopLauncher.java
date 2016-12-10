package com.manuelrdsg.pong.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.manuelrdsg.pong.Pong_Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Pong";
		config.width = 1280;
		config.height = 720;


		new LwjglApplication(new Pong_Game(), config);

	}
}
