package com.smokecastles.ld32.desktop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

/**
 * Created by juanma on 18/04/15.
 */
public class Packer {
    private static final boolean DEBUG = false;

    public static void main(String args[]) {
        TexturePacker.Settings tpSettings = new TexturePacker.Settings();
        tpSettings.filterMag = Texture.TextureFilter.Linear;
        tpSettings.filterMin = Texture.TextureFilter.Linear;
        tpSettings.duplicatePadding = true;
        tpSettings.debug = DEBUG;
        TexturePacker.process(tpSettings, "../ludumdare32_assets", "android/assets", "textures");
    }
}
