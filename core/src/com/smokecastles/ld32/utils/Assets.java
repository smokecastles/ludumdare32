package com.smokecastles.ld32.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static TextureRegion playerNormal;
    public static TextureRegion player_life_unit;
    public static TextureRegion player_life_unit2;
    public static TextureRegion enemyBig;

    public static void load() {
        TextureAtlas atlas  = new TextureAtlas(Gdx.files.internal("textures.atlas"));

        playerNormal        = new TextureRegion(atlas.findRegion("char2_blue"));
        player_life_unit    = new TextureRegion(atlas.findRegion("foe1_red"));
        player_life_unit2    = new TextureRegion(atlas.findRegion("foe1_blue"));

        enemyBig    = new TextureRegion(atlas.findRegion("foe2_blue"));
    }
}
