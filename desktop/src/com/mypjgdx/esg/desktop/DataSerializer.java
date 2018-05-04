package com.mypjgdx.esg.desktop;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class DataSerializer implements Json.Serializer<TestLauncher.Data> {

    @Override
    public void write(Json json, TestLauncher.Data object, Class knownType) {
        json.writeValue("name", object.name);
        json.writeValue("hp", object.hp);
        json.writeValue("positionX", object.positionX);
    }

    @Override
    public TestLauncher.Data read(Json json, JsonValue jsonData, Class type) {
        TestLauncher.Data data = new TestLauncher.Data();
        data.name = jsonData.getString("name");
        data.hp =  jsonData.getInt("hp");
        data.positionX =  jsonData.getFloat("positionX");
        data.dimension = new Vector2();
        return data;
    }
    public class PlayerS implements Json.Serializer<TestLauncher.Data> {

        @Override
        public void write(Json json, TestLauncher.Data object, Class knownType) {
            json.writeValue("name", object.name);
            json.writeValue("hp", object.hp);
            json.writeValue("positionX", object.positionX);
        }

        @Override
        public TestLauncher.Data read(Json json, JsonValue jsonData, Class type) {
            TestLauncher.Data data = new TestLauncher.Data();
            data.name = jsonData.getString("name");
            data.hp =  jsonData.getInt("hp");
            data.positionX =  jsonData.getFloat("positionX");
            data.dimension = new Vector2();
            return data;
        }
    }

    public class Enemy implements Json.Serializer<TestLauncher.Data> {

        @Override
        public void write(Json json, TestLauncher.Data object, Class knownType) {
            json.writeValue("name", object.name);
            json.writeValue("hp", object.hp);
            json.writeValue("positionX", object.positionX);
        }

        @Override
        public TestLauncher.Data read(Json json, JsonValue jsonData, Class type) {
            TestLauncher.Data data = new TestLauncher.Data();
            data.name = jsonData.getString("name");
            data.hp =  jsonData.getInt("hp");
            data.positionX =  jsonData.getFloat("positionX");
            data.dimension = new Vector2();
            return data;
        }
    }
}
