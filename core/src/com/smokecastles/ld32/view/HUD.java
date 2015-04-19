package com.smokecastles.ld32.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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

    private Skin skin;
    private Table getReadyTable;
    private Label getReadyTitle, explanation;

    static Image[] array_full_life  = new Image[Player.INITIAL_HEALTH];
    static Image[] array_empty_life = new Image[Player.INITIAL_HEALTH];

    public HUD(SpriteBatch batch_){
        batch               = batch_;
        Camera camera       = new OrthographicCamera();
        Viewport viewport   = new FitViewport(Constants.NATIVE_WIDTH, Constants.NATIVE_HEIGHT, camera );
        stage               = new Stage( viewport, batch );

        for(int i=0; i<Player.INITIAL_HEALTH;i++){
            array_full_life[i]  = new Image(Assets.player_life_unit);
            array_empty_life[i] = new Image(Assets.player_life_unit2 );
        }

        rootTable = new Table();
        rootTable.setDebug(true);
        rootTable.setFillParent(true);
      //  rootTable.setWidth(Constants.NATIVE_WIDTH);
      //  rootTable.setHeight(Constants.NATIVE_HEIGHT);
        rootTable.pad(10);
       // rootTable.top().left();

        table = new Table();
        table.setDebug(true);
        for (int i=0; i<Player.INITIAL_HEALTH; i++){
                table.add(array_full_life[i]).pad(10);
        }
        rootTable.add(table).left().row();
       // table.top().left();

        skin    = new Skin(Gdx.files.internal("menu_skin.json"), new TextureAtlas(Gdx.files.internal("textures.atlas")));


        // GET READY
        getReadyTitle = new Label("Get Ready!", skin);
        getReadyTitle.setFontScale(2f);
        getReadyTable = new Table();
        getReadyTable.add(getReadyTitle);

       // rootTable.row();
        rootTable.add(getReadyTable).expand().row();

//        explanation = new Label("Controls: Press arrow and space to load gun", skin);
//        explanation.setFontScale(1f);
//        //rootTable.row();
//        rootTable.add(getReadyTable);

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
            case LIFE_DRAINING:
                table.clearChildren();
                for (int i=0; i<Player.INITIAL_HEALTH; i++){
                    if (i<((Player) entity).health()){
                        table.add(array_full_life[i]).pad(10);
                    } else {
                        table.add(array_empty_life[i]).pad(10);
                    }
                }
                break;
        }
    }

    public void showGetReady(boolean show) {
        getReadyTable.setVisible(show);
    }
}
