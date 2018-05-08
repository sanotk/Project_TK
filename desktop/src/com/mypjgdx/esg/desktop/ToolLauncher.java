package com.mypjgdx.esg.desktop;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.StringWriter;

public class ToolLauncher {

    public static void main(String[] args) {
        Json json = new Json(JsonWriter.OutputType.json);
        StringWriter writer = new StringWriter();
        json.setWriter(writer);
        json.writeObjectStart();
        json.writeValue("name", "Solar Cell");
        json.writeValue("hasOnAnimation", true);
        json.writeValue("frameTime", 0.2f);
        json.writeValue("collisionProperty", "SOLAR_CELL");
        json.writeValue("atlasFile", "solarcell_pack.atlas");
        json.writeObjectEnd();

        String output = json.prettyPrint(writer.toString());
        System.out.println(output);

        JsonReader reader = new JsonReader();
        JsonValue jsonData = reader.parse(output);

        System.out.println(jsonData.get("name").asString());
        System.out.println(jsonData.get("hasOnAnimation").asBoolean());
        System.out.println(jsonData.get("frameTime").asFloat());
        System.out.println(jsonData.get("collisionProperty").asString());
        System.out.println(jsonData.get("atlasFile").asString());
    }
}
