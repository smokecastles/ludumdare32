package com.smokecastles.ld32.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static TextureRegion playerNormal;
    public static TextureRegion playerHit;
    public static TextureRegion player_life_unit;
    public static TextureRegion player_life_unit2;
    public static TextureRegion enemyBig;
    public static TextureRegion enemyBigHit;

    public static Music levelSong;
    public static Sound explosion, heart, jetplane, noise, punch;

    public static void load() {
        TextureAtlas atlas  = new TextureAtlas(Gdx.files.internal("textures.atlas"));

        playerNormal        = new TextureRegion(atlas.findRegion("char2_blue"));
        playerHit           = new TextureRegion(atlas.findRegion("char2_red"));
        player_life_unit    = new TextureRegion(atlas.findRegion("foe1_red"));
        player_life_unit2   = new TextureRegion(atlas.findRegion("foe1_blue"));

        enemyBig    = new TextureRegion(atlas.findRegion("foe2_blue"));
        enemyBigHit = new TextureRegion(atlas.findRegion("foe2_red"));

        levelSong   = Gdx.audio.newMusic(Gdx.files.internal("sfx/2nd.ogg"));
        explosion   = Gdx.audio.newSound(Gdx.files.internal("sfx/explosion.ogg"));
        heart       = Gdx.audio.newSound(Gdx.files.internal("sfx/heart.ogg"));
        jetplane    = Gdx.audio.newSound(Gdx.files.internal("sfx/jetplane.ogg"));
        noise       = Gdx.audio.newSound(Gdx.files.internal("sfx/noise.ogg"));
        punch       = Gdx.audio.newSound(Gdx.files.internal("sfx/punch.ogg"));

    }
}
