package com.mypjgdx.esg.game.objects.characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayDeque;

public class PlayerStalkerPosition {

    private static final Color[] colors = new Color[] {
            Color.NAVY,
            Color.FIREBRICK,
            Color.GOLD,
            Color.FOREST,
            Color.TEAL,
            Color.PURPLE,
    };

    private static final int POSITION_COUNT = 5;

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

    private class IntPosition{
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


}
