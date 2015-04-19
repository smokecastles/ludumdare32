package com.smokecastles.ld32.view;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.smokecastles.ld32.entities.Entity;
import com.smokecastles.ld32.entities.Event;
import com.smokecastles.ld32.entities.Observer;
import com.smokecastles.ld32.entities.Player;
import com.smokecastles.ld32.utils.Assets;
import com.smokecastles.ld32.utils.Constants;

public class HUD extends Observer {

    private Stage stage;
    private SpriteBatch batch;
    private Table rootTable, table;

    static Image[] array_full_life  = new Image[12];
    static Image[] array_empty_life = new Image[12];

    public HUD(SpriteBatch batch_){
        batch               = batch_;
        Camera camera       = new OrthographicCamera();
        Viewport viewport   = new FitViewport(Constants.NATIVE_WIDTH, Constants.NATIVE_HEIGHT, camera );
        stage               = new Stage( viewport, batch );

        for(int i=0; i<12;i++){
            array_full_life[i]  = new Image(Assets.player_life_unit);
            array_empty_life[i] = new Image(Assets.player_life_unit2 );
        }

        rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.pad(10);
//        rootTable.setDebug(true);

        table     = new Table();
        rootTable.add(table);
        rootTable.top().left();
//        table.setDebug(true);
    }

    public void show() {
        stage.addActor(rootTable);
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }

    @Override
    public void onNotify(Entity entity, Event event) {
        switch (event.type())
        {
            case HIT_BY_ENEMY:
                table.clearChildren();
                for (int i=0; i< ((Player) entity).INITIAL_HEALTH ; i++){
                    if (i<((Player) entity).health()){
                        table.add(array_full_life[i]).pad(10);
                    } else {
                        table.add(array_empty_life[i]).pad(10);
                    }
                }
                break;
            default:
                break;
        }
    }
}
