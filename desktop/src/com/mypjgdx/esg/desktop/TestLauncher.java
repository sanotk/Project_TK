package com.mypjgdx.esg.desktop;

public class TestLauncher {

    public static void main(String[] args) {
        String[] names = new String[] {
                "arrowCoun",
                "trapCount",
                "swordWaveCount",
                "questScreen1",
                "questScreen2",
                "questScreen3",
                "questScreen4",
                "questScreen5",
                "questScreen6",
                "quest_window_1",
                "quest_window_2",
                "quest_window_3",
                "quest_window_4",
                "quest_window_5",
                "quest_window_6",
                "quest1",
                "quest2",
                "quest3",
                "quest4",
                "quest5",
                "quest6",
                "dead",
                "invulnerable",
                "knockback",
                "lastInvulnerableTime",
                "invulnerableTime",
                "movingSpeed",
                "viewDirection"
        };

        for (String name : names) {
            System.out.println(name + " = player.get(\"" + name + "\");");
        }
    }
}
