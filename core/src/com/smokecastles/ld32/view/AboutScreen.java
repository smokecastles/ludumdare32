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
import com.badlogic.gdx.utils.Align;
import com.smokecastles.ld32.GameScreen;
import com.smokecastles.ld32.LD32Game;
import com.smokecastles.ld32.utils.Constants;

/**
 * Created by juanma on 19/04/15.
 */
public class AboutScreen implements Screen {
    LD32Game game;
    private Stage stage;
    private Skin skin;
    private Table tableInside;
    private Table table;
    private Label aboutTitle;
    private Label text1;
    private Label text2;
    private TextButton backButton;
    private TextButton softwareButton;

    public AboutScreen(LD32Game game) {
        this.game = game;
        stage   = new Stage();
        table   = new Table();
        tableInside = new Table();
        skin    = new Skin(Gdx.files.internal("menu_skin.json"), new TextureAtlas(Gdx.files.internal("textures.atlas")));
        backButton = new TextButton("Back",skin);
        softwareButton = new TextButton("Software",skin);
        aboutTitle = new Label("About", skin);
        aboutTitle.setFontScale(2f);
        text1 = new Label("Oh, thanks for your interest, take this extra life!\n" +
                "Just kidding... we didn't have time to implement it!\n", skin);
        text1.setAlignment(Align.center);

        text2 = new Label("Our first game, made for Ludum Dare 32 by:\n\nJuanma Reyes\nPablo Diaz", skin);
        text2.setAlignment(Align.center);
    }

    @Override
    public void show() {
        backButton.addListener(
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.setScreen(new MainMenu(game));
                    }
                }
        );

        softwareButton.addListener(
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.setScreen(new SoftwareUsedScreen(game));
                    }
                }
        );

        // elements are displayed in order
        table.add(aboutTitle).padTop(80).row();
        table.add(text1).padTop(20).padBottom(20).row();

        table.add(text2).padBottom(80).row();
        tableInside.add(backButton).size(180, 60).padBottom(80).padRight(20);
        tableInside.add(softwareButton).size(180, 60).padBottom(80).padLeft(20);
        table.add(tableInside).row();

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
