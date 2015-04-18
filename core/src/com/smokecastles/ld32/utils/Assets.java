package com.smokecastles.ld32.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by juanma on 18/04/15.
 */
public class Assets {
    public static TextureRegion playerNormal;

    public static void load() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("textures.atlas"));

        playerNormal   = new TextureRegion(atlas.findRegion("char2_blue"));
    }
}
