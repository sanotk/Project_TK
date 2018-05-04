package com.mypjgdx.esg.desktop;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.StringWriter;

public class TestLauncher {

    public static void main(String[] args) {
        Data data = new Data("Juju", 200, 123.456f, new Vector2(30, 70));
        Json json = new Json(JsonWriter.OutputType.json);
        json.setWriter(new StringWriter());
        System.out.print(json.prettyPrint(data));
        json.setSerializer(Data.class, new DataSerializer());

        String text = "{\n" +
                "\"name\": \"Juju\",\n" +
                "\"hp\": 200,\n" +
                "\"positionX\": 123.456\n" +
                "}";
        Data data2 = json.fromJson(Data.class, text);
        System.out.print(json.prettyPrint(data2));

    }

    static class Data {
        String name;
        int hp;
        float positionX;
        Vector2 dimension;

        public Data() {

        }

        public Data(String name, int hp, float positionX, Vector2 dimension) {
            this.name = name;
            this.hp = hp;
            this.positionX = positionX;
            this.dimension = dimension;
        }

    }
}
