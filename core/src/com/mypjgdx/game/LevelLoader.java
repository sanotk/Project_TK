package com.mypjgdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.IntArray;

public class LevelLoader {

    public static List<IntArray> loadMap(FileHandle mapFile) {

        List<IntArray> mapData = new ArrayList<IntArray>(); //สร้างออปเจ็ค mapFile ขึ้นมา ใช้เก็บอาเรย์แมพ
        String mapStringData = mapFile.readString(); //อ่าน map เป็นค่าสตริงละเก็บในตัวแปร mapStringData

        for (String rowString : mapStringData.split("\\r?\\n")) { //ให้ทำการวนลูปจบแล้วขึ้นบรรทัดใหม่
            IntArray rowInt = new IntArray();//เป็น ArrayInt ทีเก็บค่าตัวเลขแต่ละตัวใน 1 แถว
            for (String tileNum : rowString.split(",")) { //วนลูปเก็บค่าทีละตัวจนจบบรรทัด
                rowInt.add(Integer.parseInt(tileNum.replaceAll("\\s+","")));
            }
            mapData.add(rowInt);//เพิ่มตัวเลขเข้าไป
        }
        return mapData;
    }

}
