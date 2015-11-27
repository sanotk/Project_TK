package com.mypjgdx.esg.utils;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.mypjgdx.esg.game.TileMap;

/**
 * คลาสสร้างเองเพื่อใช้โหลดข้อมูล  TileMap จากไฟล์ CSV
 *
 * @author S-Kyousuke
 */

public class TileMapLoader extends SynchronousAssetLoader<TileMap, TileMapLoader.TileMapParameter> {

    public TileMapLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    static public class TileMapParameter extends AssetLoaderParameters<TileMap> { }

    @Override
    public TileMap load(AssetManager assetManager, String fileName, FileHandle file, TileMapParameter parameter) {
        return new TileMap(file);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, TileMapParameter parameter) {
        return null;
    }


}
