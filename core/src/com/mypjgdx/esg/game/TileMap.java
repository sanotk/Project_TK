package com.mypjgdx.esg.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.IntArray;

/**
 * คลาสสร้างเองเพื่อใช้เก็บข้อมูลเกี่ยวกับตำแหน่งการจัดวาง Tile
 * (ยังไม่สมบูรณ์ ออกแบบไว้ใช้ชั่วคราวก่อน)
 *
 * @author S-Kyousuke
 */

public class TileMap {

    private List<IntArray> data;

    public final int row;
    public final int column;

    public TileMap(FileHandle mapFile) {
        data = new ArrayList<IntArray>(); //สร้างออปเจ็ค mapFile ขึ้นมา ใช้เก็บอาเรย์แมพ
        String mapStringData = mapFile.readString(); //อ่าน map เป็นค่าสตริงละเก็บในตัวแปร mapStringData

        for (String rowString : mapStringData.split("\\r?\\n")) { //ให้ทำการวนลูปจบแล้วขึ้นบรรทัดใหม่
            IntArray rowInt = new IntArray();//เป็น ArrayInt ทีเก็บค่าตัวเลขแต่ละตัวใน 1 แถว
            for (String tileNum : rowString.split(",")) { //วนลูปเก็บค่าทีละตัวจนจบบรรทัด
                rowInt.add(Integer.parseInt(tileNum.replaceAll("\\s+","")));
            }
            data.add(rowInt);//เพิ่มตัวเลขเข้าไป
        }

        row = data.size();
        column =  data.get(0).size;
    }

    public int getId(int row, int column) {
        return data.get(row).get(column);
    }
}
