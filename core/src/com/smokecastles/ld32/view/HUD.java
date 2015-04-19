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
    private Table rootTable, lifeTable;

    private Skin skin;
    private Label message, explanation;

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
//        rootTable.setDebug(true);
        rootTable.setFillParent(true);
        //rootTable.pad(10);

        lifeTable = new Table();
        lifeTable.pad(10);
//        lifeTable.setDebug(true);
        for (int i=0; i<Player.INITIAL_HEALTH; i++){
                lifeTable.add(array_full_life[i]).pad(10);
        }
        rootTable.add(lifeTable).left().row(); // add life lifeTable to the left and go to next row

        // screen messages
        skin    = new Skin(Gdx.files.internal("menu_skin.json"), new TextureAtlas(Gdx.files.internal("textures.atlas")));
        message = new Label("Get Ready!!", skin);
        message.setFontScale(2f);
        rootTable.add(message).expand().row(); // add message, expand and go to next row

        explanation = new Label("Press arrows to control and hold space to load gun!!", skin);
        explanation.setFontScale(0.8f);
        rootTable.add(explanation);
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
                lifeTable.clearChildren();
                for (int i=0; i<Player.INITIAL_HEALTH; i++){
                    if (i<((Player) entity).health()){
                        lifeTable.add(array_full_life[i]).pad(10);
                    } else {
                        lifeTable.add(array_empty_life[i]).pad(10);
                    }
                }
                break;

            case WIN:
                message.setText("You Win!!");
                message.setVisible(true);
                break;

            case GAME_OVER:
                message.setText("Game Over!!");
                message.setVisible(true);
                break;
        }
    }

    public void showMessage(boolean show) {
        message.setVisible(show);
        explanation.setVisible(show);
    }
}
