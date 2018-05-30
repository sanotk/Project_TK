package com.mypjgdx.esg.game.objects.characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayDeque;

public class PlayerStalkerPosition implements Json.Serializable {

    private static final Color[] colors = new Color[] {
            Color.NAVY,
            Color.FIREBRICK,
            Color.GOLD,
            Color.FOREST,
            Color.TEAL,
            Color.PURPLE,
    };

    private static final int POSITION_COUNT = 6;

    private Player player;
    private ArrayDeque<IntPosition> positions;

    public PlayerStalkerPosition(Player player) {
        this.player = player;
        this.positions = new ArrayDeque<IntPosition>();
    }

    public void update() {
        final float playerWalkingBoundsCenterX =   player.walkingBounds.x + player.walkingBounds.width / 2;
        final float playerWalkingBoundsCenterY =   player.walkingBounds.y + player.walkingBounds.height / 2;
        final int newPlayerX = (50 * ((int) playerWalkingBoundsCenterX/ 50)) + 25;
        final int newPlayerY = (50 * ((int) playerWalkingBoundsCenterY / 50)) + 25;

        if (positions.isEmpty()
                || positions.getFirst().x != newPlayerX
                || positions.getFirst().y != newPlayerY) {
            final IntPosition newPosition = new IntPosition(newPlayerX, newPlayerY);
            positions.remove(newPosition);
            positions.addFirst(newPosition);

            if (positions.size() > POSITION_COUNT + 1) {
                positions.removeLast();
            }
        }
    }

    public void debug(ShapeRenderer renderer) {
        int i = 0;
        boolean firstSkipped = false;
        for (IntPosition position : positions) {
            if (!firstSkipped) {
                firstSkipped = true;
                continue;
            }
            renderer.setColor(colors[i % colors.length]);
            renderer.circle(position.x, position.y, 10);
            i++;
        }
    }

    public class IntPosition {
        public final int x;
        public final int y;

        private IntPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            IntPosition that = (IntPosition) o;

            if (x != that.x) return false;
            return y == that.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }

    public ArrayDeque<IntPosition> getPositions() {
        return positions;
    }

    @Override
    public void write(Json json) {
        int i = 0;
        json.writeValue("size", positions.size());
        for (IntPosition position : positions) {
            json.writeValue("x" + i, position.x);
            json.writeValue("y" + i, position.y);
            i++;
        }
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        int size = jsonData.getInt("size");
        positions.clear();
        for (int i = 0; i < size; i++) {
            positions.add(new IntPosition(jsonData.getInt("x" + i), jsonData.getInt("y" + i)));
        }
    }
}
