package com.mypjgdx.esg.desktop;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Field;
import com.mypjgdx.esg.screens.GameScreen4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FieldScanner {

    public static void main(String[] args) {
        List<Field> fields = getFields(GameScreen4.class);
        System.out.println("    @Override");
        System.out.println("    public void write(Json json) {");
        printWrite(fields);
        System.out.println("    }");
        System.out.println();
        System.out.println("    @Override");
        System.out.println("    public void read(Json json, JsonValue jsonData) {");
        printRead(fields);
        System.out.println("    }");
    }

    private static void printWrite(List<Field> fields) {
        for (Field field : fields) {
            if (!ClassReflection.isPrimitive(field.getType())
                    && !ClassReflection.isAssignableFrom(String.class, field.getType())) {
                System.out.println(String.format("//        json.writeValue(\"%s\", %s);", field.getName(), field.getName()));
            } else {
                System.out.println(String.format("        json.writeValue(\"%s\", %s);", field.getName(), field.getName()));
            }
        }
    }

    private static void printRead(List<Field> fields) {
        for (Field field : fields) {
            if (!ClassReflection.isPrimitive(field.getType())
                    && !ClassReflection.isAssignableFrom(String.class, field.getType())) {
                System.out.println(String.format("//        %s = jsonData.get(\"%s\");", field.getName(), field.getName()));
            } else {
                if (ClassReflection.isAssignableFrom(byte.class, field.getType())) {
                    System.out.println(String.format("        %s = jsonData.getByte(\"%s\");", field.getName(), field.getName()));
                }
                else if (ClassReflection.isAssignableFrom(int.class, field.getType())) {
                    System.out.println(String.format("        %s = jsonData.getInt(\"%s\");", field.getName(), field.getName()));
                }
                else if (ClassReflection.isAssignableFrom(long.class, field.getType())) {
                    System.out.println(String.format("        %s = jsonData.getLong(\"%s\");", field.getName(), field.getName()));
                }
                else if (ClassReflection.isAssignableFrom(float.class, field.getType())) {
                    System.out.println(String.format("        %s = jsonData.getFloat(\"%s\");", field.getName(), field.getName()));
                }
                else if (ClassReflection.isAssignableFrom(double.class, field.getType())) {
                    System.out.println(String.format("        %s = jsonData.getDouble(\"%s\");", field.getName(), field.getName()));
                }
                else if (ClassReflection.isAssignableFrom(boolean.class, field.getType())) {
                    System.out.println(String.format("        %s = jsonData.getBoolean(\"%s\");", field.getName(), field.getName()));
                }
                else if (ClassReflection.isAssignableFrom(char.class, field.getType())) {
                    System.out.println(String.format("        %s = jsonData.getChar(\"%s\");", field.getName(), field.getName()));
                }
                else if (ClassReflection.isAssignableFrom(String.class, field.getType())) {
                    System.out.println(String.format("        %s = jsonData.getString(\"%s\");", field.getName(), field.getName()));
                }
            }
        }
    }

    private static List<Field> getFields(Class<?> type) {
        List<Field> fields = new ArrayList<Field>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            Collections.addAll(fields, ClassReflection.getDeclaredFields(c));
        }
        return fields;
    }
}
