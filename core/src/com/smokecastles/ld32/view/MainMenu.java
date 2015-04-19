package com.smokecastles.ld32.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.smokecastles.ld32.GameScreen;
import com.smokecastles.ld32.LD32Game;
import com.smokecastles.ld32.utils.Constants;

/**
 * Created by juanma on 19/04/15.
 */
public class MainMenu implements Screen {
    LD32Game game;
    private Stage stage;
    private Skin skin;
    private Table table;
    private Label title;
    private Label footer;
    private TextButton playButton, aboutButton, exitButton;

    public MainMenu(LD32Game game) {
        this.game = game;
        stage   = new Stage();
        table   = new Table();
        skin    = new Skin(Gdx.files.internal("menu_skin.json"), new TextureAtlas(Gdx.files.internal("textures.atlas")));
        playButton      = new TextButton("Play",skin);
        aboutButton      = new TextButton("About",skin);
        exitButton      = new TextButton("Exit", skin);
        title           = new Label("SquadaBOOM", skin);
        footer           = new Label("2015 Smoke Castles", skin);

        title.setFontScale(4f);
    }


    @Override
    public void show() {
        playButton.addListener(
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.setScreen(new GameScreen(game));
                    }
                }
        );

        exitButton.addListener(
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Gdx.app.exit();
                    }
                }
        );


        // elements are displayed in order
        table.add(title).padBottom(80).row();
        table.add(playButton).size(150, 60).padBottom(20).row();
        table.add(aboutButton).size(150, 60).padBottom(20).row();
        table.add(exitButton).size(150, 60).padBottom(20).row();
        table.add(footer).padTop(80).row();

        table.setFillParent(true); // table as big as the stage and centered inside stage
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        GL20 gl = Gdx.gl;
        gl.glClearColor(Constants.BGCOLOR_R, Constants.BGCOLOR_G, Constants.BGCOLOR_B, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
