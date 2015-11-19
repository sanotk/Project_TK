package com.mypjgdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.IntArray;

public class LevelLoader {
	public static List<IntArray> loadMap(FileHandle mapFile){
		List<IntArray> mapData = new ArrayList<IntArray>();
		String mapStringData = mapFile.readString();
		for (String rowString : mapStringData.split("\\r?\\n"))
	    {
	        IntArray rowInt = new IntArray();
	        for(String tileNum : rowString.split(",")){
	        	rowInt.add(Integer.parseInt(tileNum.replaceAll("\\s+","")));
	        }
	        mapData.add(rowInt);
	    }
		return mapData;
	}



}
