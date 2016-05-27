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
import com.mypjgdx.esg.game.objects.Beam;
import com.mypjgdx.esg.game.objects.Bullet;
import com.mypjgdx.esg.game.objects.Enemy;
import com.mypjgdx.esg.game.objects.Enemy2;
import com.mypjgdx.esg.game.objects.Enemy3;
import com.mypjgdx.esg.game.objects.Item;
import com.mypjgdx.esg.game.objects.Player;
import com.mypjgdx.esg.game.objects.SolarCell;
import com.mypjgdx.esg.game.objects.Trap;

public class Level{

    public Player player; // ตัวละครที่ผู้เล่นจะได้ควบคุม
    public Item item;
    public SolarCell solarcell;
    public List<Enemy> enemies = new ArrayList<Enemy>();
    public List<Enemy2> enemies2 = new ArrayList<Enemy2>();
    public List<Enemy3> enemies3 = new ArrayList<Enemy3>();
    public List<Bullet> bullets = new ArrayList<Bullet>();
    public List<Beam> beams = new ArrayList<Beam>();
    public List<Trap> traps = new ArrayList<Trap>();
    private CollisionCheck goalCheck;
    public Map map;   // แผนที่ในเกม

    public final int MAX_ENEMY = 3;
    public final int MAX_ENEMY2 = 2;
    public final int MAX_ENEMY3 = 1;

    public Level (Map map) {
        this.map = map;
        player = new Player(map.getMapLayer(), item) ;
        for(int i = 0; i < MAX_ENEMY ;i++){
        	enemies.add(new Enemy(map.getMapLayer(),player ,bullets ,traps ,beams));
        }
        for(int i = 0; i < MAX_ENEMY2 ;i++){
        	enemies2.add(new Enemy2(map.getMapLayer(),player ,bullets ,traps ,beams));
        }
        for(int i = 0; i < MAX_ENEMY3 ;i++){
        	enemies3.add(new Enemy3(map.getMapLayer(),player ,bullets ,traps ,beams));
        }

        goalCheck = new TiledCollisionCheck(player.bounds, map.getMapLayer(), "goal");
    }

    public void render (SpriteBatch batch, OrthogonalTiledMapRenderer tiledRenderer, ShapeRenderer shapeRenderer) {
        tiledRenderer.setMap(map.getTiledMap());
        tiledRenderer.render();

        batch.begin();
        for (Bullet s: bullets) s.render(batch);
        for (Beam b: beams) b.render(batch);
        for (Trap t: traps) t.render(batch);
        player.render(batch);
        for (Enemy e: enemies) e.render(batch);
        for (Enemy2 e: enemies2) e.render(batch);
        for (Enemy3 e: enemies3) e.render(batch);
        batch.end();

        shapeRenderer.begin(ShapeType.Filled);
        player.showHp (shapeRenderer);
        for (Enemy e:enemies) e.showHp(shapeRenderer);
        for (Enemy2 e:enemies2) e.showHp(shapeRenderer);
        for (Enemy3 e:enemies3) e.showHp(shapeRenderer);
        shapeRenderer.end();
    }

    public void update(float deltaTime) {
        Iterator<Bullet>it = bullets.iterator();
        Iterator<Beam>bit = beams.iterator();
        Iterator<Trap>tit = traps.iterator();
        Iterator<Enemy>eit = enemies.iterator();
        Iterator<Enemy2>e2it = enemies2.iterator();
        Iterator<Enemy3>e3it = enemies3.iterator();

        while(it.hasNext()){
        	Bullet s = it.next();
        	if (s.isDespawned()) it.remove();
        }
        while(bit.hasNext()){
        	Beam b = bit.next();
        	if (b.isDespawned()) bit.remove();
        }
        while(tit.hasNext()){
        	Trap t = tit.next();
        	if (t.isDespawned()) tit.remove();
        }
        while(eit.hasNext()){
        	Enemy e = eit.next();
        	if (!e.isAlive()) eit.remove();
        }
        while(e2it.hasNext()){
        	Enemy2 e = e2it.next();
        	if (!e.isAlive()) e2it.remove();
        }
        while(e3it.hasNext()){
        	Enemy3 e = e3it.next();
        	if (!e.isAlive()) e3it.remove();
        }
        player.update(deltaTime);

        for(Enemy e: enemies) e.update(deltaTime);
        for(Enemy2 e: enemies2) e.update(deltaTime);
        for(Enemy3 e: enemies3) e.update(deltaTime);
        for(Bullet s: bullets) s.update(deltaTime);
        for(Beam b: beams) b.update(deltaTime);
        for(Trap t: traps) t.update(deltaTime);
    }

    public boolean isFinished() {
        return enemies.isEmpty()
                && goalCheck.isCollidesBottom()
                && goalCheck.isCollidesLeft()
                && goalCheck.isCollidesRight()
                && goalCheck.isCollidesTop();
    }

}
