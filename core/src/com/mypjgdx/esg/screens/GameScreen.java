package com.mypjgdx.esg.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mypjgdx.esg.game.Level;
import com.mypjgdx.esg.game.Map;
import com.mypjgdx.esg.game.WorldController;
import com.mypjgdx.esg.game.WorldRenderer;

public class GameScreen extends AbstractGameScreen {

    private WorldController worldController;
    private WorldRenderer worldRenderer;

    SpriteBatch batch;
    public Texture bg;

    private Stage stage; //
    private Stage max;
    private Skin skin; //

    public static final int SCENE_WIDTH = 1280; //เซตค่าความกว้างของจอ
    public static final int SCENE_HEIGHT = 720; //เซตค่าความสูงของจอ

    private Label text_bulletMax;
    private Label text_beamMax;
    private Label text_trapMax;
    private Label text_bullet;
    private Label text_beam;
    private Label text_trap;

    public GameScreen(Game game) {
        super(game);

        stage = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        max = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT));
        Gdx.input.setInputProcessor(max);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        bg = new Texture("bg.png");

        text_bulletMax = new Label("Bullet Max : " ,skin);
        text_bulletMax.setColor(0, 1, 1, 1);
        text_bulletMax.setFontScale(1.2f,1.2f);
        text_bulletMax.setPosition(100, 650);

        text_beamMax = new Label("Beam Max : " ,skin);
        text_beamMax.setColor(0, 1, 1, 1);
        text_beamMax.setFontScale(1.1f,1.1f);
        text_beamMax.setPosition(300, 650);

        text_trapMax = new Label("Trap Max : " ,skin);
        text_trapMax.setColor(0, 1, 1, 1);
        text_trapMax.setFontScale(1.1f,1.1f);
        text_trapMax.setPosition(500, 650);

        stage.addActor(text_bulletMax);
        stage.addActor(text_beamMax);
        stage.addActor(text_trapMax);

        batch = new SpriteBatch();

    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        max.clear();

        if(worldController.level.player.bulletMax==0){
	        text_bullet = new Label("0" ,skin);
        }else if(worldController.level.player.bulletMax==1){
        	text_bullet = new Label("1" ,skin);
        }else if(worldController.level.player.bulletMax==2){
        	text_bullet = new Label("2" ,skin);
        }else if(worldController.level.player.bulletMax==3){
        	text_bullet = new Label("3" ,skin);
        }else if(worldController.level.player.bulletMax==4){
        	text_bullet = new Label("4" ,skin);
        }else if(worldController.level.player.bulletMax==5){
        	text_bullet = new Label("5" ,skin);
        }else if(worldController.level.player.bulletMax==6){
        	text_bullet = new Label("6" ,skin);
        }else if(worldController.level.player.bulletMax==7){
        	text_bullet = new Label("7" ,skin);
        }else if(worldController.level.player.bulletMax==8){
        	text_bullet = new Label("8" ,skin);
        }else if(worldController.level.player.bulletMax==9){
        	text_bullet = new Label("9" ,skin);
        }else if(worldController.level.player.bulletMax==10){
        	text_bullet = new Label("10" ,skin);
        }else if(worldController.level.player.bulletMax==11){
        	text_bullet = new Label("11" ,skin);
        }else if(worldController.level.player.bulletMax==12){
        	text_bullet = new Label("12" ,skin);
        }else if(worldController.level.player.bulletMax==13){
        	text_bullet = new Label("13" ,skin);
        }else if(worldController.level.player.bulletMax==14){
        	text_bullet = new Label("14" ,skin);
        }else if(worldController.level.player.bulletMax==15){
        	text_bullet = new Label("15" ,skin);
        }else if(worldController.level.player.bulletMax==16){
        	text_bullet = new Label("16" ,skin);
        }else if(worldController.level.player.bulletMax==17){
        	text_bullet = new Label("17" ,skin);
        }else if(worldController.level.player.bulletMax==18){
        	text_bullet = new Label("18" ,skin);
        }else if(worldController.level.player.bulletMax==19){
        	text_bullet = new Label("19" ,skin);
        }else if(worldController.level.player.bulletMax==20){
        	text_bullet = new Label("20" ,skin);
        }else if(worldController.level.player.bulletMax==21){
        	text_bullet = new Label("21" ,skin);
        }else if(worldController.level.player.bulletMax==22){
        	text_bullet = new Label("22" ,skin);
        }else if(worldController.level.player.bulletMax==23){
        	text_bullet = new Label("23" ,skin);
        }else if(worldController.level.player.bulletMax==24){
        	text_bullet = new Label("24" ,skin);
        }else{
        	text_bullet = new Label("25" ,skin);
        }

	    text_bullet.setColor(0, 1, 1, 1);
	    text_bullet.setFontScale(1.2f,1.2f);
	    text_bullet.setPosition(203, 650);

	    if(worldController.level.player.beamMax!=1){
	        text_beam = new Label("0" ,skin);
	    }else{
	        text_beam = new Label("1" ,skin);
	    }

        text_beam.setColor(0, 1, 1, 1);
        text_beam.setFontScale(1.1f,1.1f);
        text_beam.setPosition(401, 650);

	    if(worldController.level.player.trapMax==0){
	    	text_trap = new Label("0" ,skin);
	    }else if(worldController.level.player.trapMax==1){
	    	text_trap = new Label("1" ,skin);
	    }else if(worldController.level.player.trapMax==2){
	        text_trap = new Label("2" ,skin);
	    }else{
	        text_trap = new Label("3" ,skin);
	    }

        text_trap.setColor(0, 1, 1, 1);
        text_trap.setFontScale(1.1f,1.1f);
        text_trap.setPosition(595, 650);

        max.addActor(text_bullet);
        max.addActor(text_beam);
        max.addActor(text_trap);

        if (Gdx.input.isKeyJustPressed(Keys.M)) {
            game.setScreen(new MenuScreen(game));
            return;
        }

        if (!worldController.level.player.isAlive()) {
            game.setScreen(new GameOverScreen(game));
            return;
        }

        if ((worldController.level.isFinished())){
            worldController.init(new Level(worldController.level.map.next()));
            return;
        }

        worldController.update(Gdx.graphics.getDeltaTime()); //อัพเดท Game World
        worldRenderer.render();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        max.act(Gdx.graphics.getDeltaTime());
        max.draw();
    }

    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
        stage.getViewport().update(width, height);
        max.getViewport().update(width, height);
    }

    @Override
    public void show() {

        worldController = new WorldController(new Level(Map.MAP_01));
        worldRenderer = new WorldRenderer(worldController);
    }

    @Override
    public void hide() {
        worldRenderer.dispose();
    }

    @Override
    public void pause() {}

}
