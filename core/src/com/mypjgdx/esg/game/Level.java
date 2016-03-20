package com.mypjgdx.esg.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mypjgdx.esg.collision.CollisionCheck;
import com.mypjgdx.esg.collision.TiledCollisionCheck;
import com.mypjgdx.esg.game.objects.Enemy;
import com.mypjgdx.esg.game.objects.Player;
import com.mypjgdx.esg.game.objects.Sword;

public class Level{

    public Player player; // ตัวละครที่ผู้เล่นจะได้ควบคุม
    public List<Enemy> enemies = new ArrayList<Enemy>();
    public List<Sword> swords = new ArrayList<Sword>();
    private CollisionCheck goalCheck;
    public Map map;   // แผนที่ในเกม

    public final int MAX_ENEMY = 5;

    public Level (Map map) {
        this.map = map;
        player = new Player(map.getMapLayer()) ;
        for(int i = 0; i < MAX_ENEMY ;i++){
        	enemies.add(new Enemy(map.getMapLayer(),player ,swords));
        }
        goalCheck = new TiledCollisionCheck(player.bounds, map.getMapLayer(), "goal");
    }

    public void render (SpriteBatch batch, OrthogonalTiledMapRenderer tiledRenderer, ShapeRenderer shapeRenderer) {
        tiledRenderer.setMap(map.getTiledMap());
        tiledRenderer.render();

        batch.begin();
        player.render(batch);
        for (Enemy e: enemies) e.render(batch);
        for (Sword s: swords) s.render(batch);
        batch.end();

        shapeRenderer.begin(ShapeType.Filled);
        player.showHp (shapeRenderer);
        for (Enemy e:enemies) e.showHp(shapeRenderer);
        shapeRenderer.end();
    }

    public void update(float deltaTime) {
        Iterator<Sword>it = swords.iterator();
        Iterator<Enemy>eit = enemies.iterator();
        while(it.hasNext()){
        	Sword s = it.next();
        	if (s.isDespawned()) it.remove();
        }
        while(eit.hasNext()){
        	Enemy e = eit.next();
        	if (!e.isAlive()) eit.remove();
        }
        player.update(deltaTime);
        for(Enemy e: enemies) e.update(deltaTime);
        for(Sword s: swords) s.update(deltaTime);
    }

    public boolean isFinished() {
        return enemies.isEmpty()
                && goalCheck.isCollidesBottom()
                && goalCheck.isCollidesLeft()
                && goalCheck.isCollidesRight()
                && goalCheck.isCollidesTop();
    }

}
