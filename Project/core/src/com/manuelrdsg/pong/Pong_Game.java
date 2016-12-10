package com.manuelrdsg.pong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class Pong_Game extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private TextureAtlas atlas;
	private Sprite background;
	private Sprite player1;
	private Sprite player2;
	private Sprite ball;
	private Vector2 direction = new Vector2(200f, 30f);
	private int scoreP1;
	private String scoreBoardP1;
	private BitmapFont scoreFontP1;
	private int scoreP2;
	private String scoreBoardP2;
	private BitmapFont scoreFontP2;
	private Sound soundWall;
	private Sound soundPaddle;
	private Sound soundScore;


	@Override
	public void create () {

		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);

		soundWall = Gdx.audio.newSound(Gdx.files.internal("sonido/plop.wav"));
		soundPaddle = Gdx.audio.newSound(Gdx.files.internal("sonido/beep.wav"));
		soundScore = Gdx.audio.newSound(Gdx.files.internal("sonido/peeeeep.wav"));

		scoreP1 = 0;
		scoreBoardP1 = "Player1: 0";
		scoreFontP1 = new BitmapFont();
		scoreFontP1.setColor(1,1,1,1);

		scoreP2 = 0;
		scoreBoardP2 = "Player 2: 0";
		scoreFontP2 = new BitmapFont();
		scoreFontP2.setColor(1,1,1,1);

		atlas = new TextureAtlas(Gdx.files.internal("textures_pong.txt"));
		background = new Sprite(atlas.findRegion("Background_PONG"));
		ball = new Sprite(atlas.findRegion("Ball"));
		ball.setPosition(200, 320);
		player1 = new Sprite(atlas.findRegion("player_bar"));
		player1.setPosition(20, 320);
		player2 = new Sprite(atlas.findRegion("player_bar"));
		player2.setPosition(1260, 320);


	}


	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float Speed = 200.0f;

		if(Gdx.input.isKeyPressed(Input.Keys.W)) player1.setPosition(20, player1.getY() + Gdx.graphics.getDeltaTime() * Speed);
		if(Gdx.input.isKeyPressed(Input.Keys.S)) player1.setPosition(20, player1.getY() - Gdx.graphics.getDeltaTime() * Speed);
		if(Gdx.input.isKeyPressed(Input.Keys.DPAD_UP))  player2.setPosition(1260, player2.getY() + Gdx.graphics.getDeltaTime() * Speed);
		if(Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN))  player2.setPosition(1260, player2.getY() - Gdx.graphics.getDeltaTime() * Speed);

		ball.translate(direction.x*Gdx.graphics.getDeltaTime(), direction.y*Gdx.graphics.getDeltaTime());

		if(ball.getBoundingRectangle().overlaps(player1.getBoundingRectangle()) || ball.getBoundingRectangle().overlaps(player2.getBoundingRectangle())) {
			direction.x = -(direction.x + 100);
			direction.y = (direction.y + 75);
			soundPaddle.play();
		}

		//------------------ Colision Ball-Screeen --------- //

		if(ball.getY() <= 0) {
			ball.setY(0);
			direction.y = - (direction.y + 50);
			direction.x = direction.x + 50;
			soundWall.play();
		}else if(ball.getY() >= Gdx.graphics.getHeight() - ball.getHeight()) {
			ball.setY(Gdx.graphics.getHeight() - ball.getHeight());
			direction.y = - (direction.y + 50);
			direction.x = direction.x + 20;
			soundWall.play();
		}

		//------------------ Colision Ball-Screeen (Score) --------- //

		if(ball.getX() <= 0) {
			scoreP2++;
			scoreBoardP2 = "player1: " + scoreP2;
			ball.setPosition(200, 320);
			direction.x = -direction.x;
			soundScore.play();
		}else if(ball.getX() >= Gdx.graphics.getWidth() - ball.getWidth()) {
			scoreP1++;
			scoreBoardP1 = "player1: " + scoreP1;
			ball.setPosition(1080, 320);
			direction.x = -direction.x;
			soundScore.play();
		}

		//------------------ Colision Player-Screeen --------- //

		if(player1.getY() >= Gdx.graphics.getHeight() - player1.getHeight())
			player1.setY(Gdx.graphics.getHeight() - player1.getHeight());


		if(player1.getY() <= 0)
			player1.setY(0);

		if(player2.getY() >= Gdx.graphics.getHeight() - player2.getHeight())
			player2.setY(Gdx.graphics.getHeight() - player2.getHeight());

		if(player2.getY() <= 0)
			player2.setY(0);

		batch.begin();
		background.draw(batch);
		scoreFontP1.draw(batch, scoreBoardP1, 270,700);
		scoreFontP2.draw(batch, scoreBoardP2, 1010,700);
		ball.draw(batch);
		player1.draw(batch);
		player2.draw(batch);

		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		soundWall.dispose();
		soundPaddle.dispose();
		soundScore.dispose();
		atlas.dispose();
	}
}
