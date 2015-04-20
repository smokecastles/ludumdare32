package com.smokecastles.ld32.view;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.smokecastles.ld32.entities.Entity;
import com.smokecastles.ld32.entities.Event;
import com.smokecastles.ld32.entities.Observer;
import com.smokecastles.ld32.utils.Assets;

public class SoundBox extends Observer {

    private Music music;
    private Sound charging, explosion, collision, self_damage;

    public SoundBox(){
        music = Assets.levelSong;
        music.setLooping(true);
        music.setVolume(0.65f);

        charging    = Assets.jetplane;
        explosion   = Assets.explosion;
        collision   = Assets.punch;
        self_damage = Assets.heart;
    }

    public void play(){
        music.play();
    }

    public void pause(){
        music.pause();
    }

    public void stop(){
        music.stop();
    }

    public void dispose(){
        music.dispose();
    }

    @Override
    public void onNotify(Entity entity, Event event) {
        switch (event.type())
        {
            case CHARGING:
                charging.play(0.2f);
                break;

            case CHARGING_STOPPED:
                charging.stop();
                break;

            case BOOM:
                explosion.play(1.0f);
                break;

            case HIT_BY_ENEMY:
                collision.play(0.9f);
                break;

            case LIFE_DRAINING:
                self_damage.play(1.0f);
                break;

            case GAME_OVER:
                charging.stop();
                break;
        }
    }

}
