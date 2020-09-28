package com.mygdx.jrac.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.jrac.Inventory.HeroInventory;
import com.mygdx.jrac.Inventory.InventoryItem;
import com.mygdx.jrac.Jrac;
import com.mygdx.jrac.Objects.NaturalObjects;
import com.mygdx.jrac.Objects.ResourcesAnimations;
import com.mygdx.jrac.Objects.Wood;
import com.mygdx.jrac.features.DestructibleRecArea;
import com.mygdx.jrac.features.ObjectsInteractions;
import com.mygdx.jrac.features.RecRelationships;
import com.mygdx.jrac.hero.Heroes;
import com.mygdx.jrac.maps.Maps;
import com.mygdx.jrac.musics.GameMusics;

import java.util.Random;

public class GameScreen implements Screen {

    //Audios (Music and sounds)
    private final Music mainScreenMusic;

    //Screen Sizes
    public static final int SCREEN_WIDTH = 1200;
    public static final int SCREEN_HEIGHT = 800;

    //Instance of our game
    private final Jrac game;

    //Camera for our game
    private final OrthographicCamera CAM;

    //Our Hero and its info for screen
    private final Heroes HERO;
    private final Texture HERO_TEXTURE;
    int heroVelocity;

    //directions hero is currently moving towards
    int key;

    //old hero's position in case of collision
    float oldHeroPositionX, oldHeroPositionY;

    //current default frame position
    TextureRegion heroCurrentFrame;
    TextureRegion treeCurrentFrame;

    //Track elapsed time for the animation
    float stateTime;

    //used for slower animations
    float destructionStateTime;

    /*Map Stuff*/

    //get map
    public static Maps map;


    //used to render map
    private final OrthogonalTiledMapRenderer backgroundRenderer, foregroundRenderer;

    //tells the renderer how many pixels map to a single world unit
    float unitScale = 1;

    //Map Objects
    private final ShapeRenderer sr;

    //Trees
    NaturalObjects tree;
    Rectangle treeRec;

    //Handles identifying when an object is destructible and hero is near it enough to destroy it
    DestructibleRecArea destructibleObject;
    Rectangle destructibleObjectRec;

    //Handles Relationship between resource and destructible area, so I know which object is being destroyed in specific area
    RecRelationships resDestAreaRel;


    //check if there was any collision
    boolean collided;

    //check if it's destructing
    NaturalObjects resourceBeingExtracted;

    //timer to keep track of texts
    float timer = 0f;

    //Used to generate random numbers
    Random random = new Random();


    public GameScreen(Jrac game){

        //Set up Game
        this.game = game;

        //Load Map
        map = new Maps();
        backgroundRenderer = new OrthogonalTiledMapRenderer(map.getBackgroundMap(), unitScale);
        foregroundRenderer = new OrthogonalTiledMapRenderer(map.getForegroundMap(), unitScale);

        //LOAD OBJECTS
        sr = new ShapeRenderer();


        //Set up Camera
        CAM = new OrthographicCamera();
        CAM.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        CAM.update();

        //Get Hero Texture, positions and animation
        HERO = new Heroes(1, "Charles", 0.2f, 350);
        HERO_TEXTURE = HERO.getTexture();
        heroVelocity = HERO.getVelocity();


        //start stateTime
        stateTime = 0f;

        //state time used for when a object is destroyed
        destructionStateTime = 0f;

        //Set up Audios
        mainScreenMusic = GameMusics.GAMESCREENMUSIC.getMusic();
        mainScreenMusic.setLooping(true);




        //Set Up Resources and Destructible Areas in order to deal with objects interactions (collision and destruction)
        for (MapObject object : map.getForegroundMapLayers().get("trees-trunk").getObjects()){
            if (object instanceof RectangleMapObject){
                
                //Create Resource
                tree = new NaturalObjects(object.getName(), object, true);

                //Create Destructible object based around resource
                treeRec = ((RectangleMapObject) object).getRectangle();

                //create rectangle around resource for destruction
                destructibleObjectRec = new Rectangle(treeRec.x - 10, treeRec.y - 10, treeRec.width + 20, treeRec.height + 20);
                destructibleObject = new DestructibleRecArea(destructibleObjectRec);

                //Create Relationship
                resDestAreaRel = new RecRelationships(destructibleObjectRec, tree);

            }
        }

    }
    @Override
    public void show() {
        //mainScreenMusic.play();
    }

    @Override
    public void render(float delta) {

        //Accumulate Elapsed State Time
        stateTime += Gdx.graphics.getDeltaTime();

        // timer that makes sure some texts (destructing.. for now) shows for at least 2 seconds
        timer += Gdx.graphics.getDeltaTime();

        //Background Set Up
        Gdx.gl.glClearColor(0,0,0,0.1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //Set currentFrame to default position
        heroCurrentFrame = HERO.getCurrentDefaultPosition();

        //Update cam every time render is called
        CAM.position.x = this.HERO.getHeroPositionX();
        CAM.position.y = this.HERO.getHeroPositionY();
        CAM.update();

        //update inventory positions in the screen everytime render is called
        this.HERO.getHeroInventory().setPositionXAccordinglyWHeroAndScreen(this.HERO);
        this.HERO.getHeroInventory().setPositionYAccordinglyWHeroAndScreen(this.HERO, SCREEN_HEIGHT);

        //set the TiledMapRendered view based on what the camera sees, and render the map
        backgroundRenderer.setView(CAM);
        foregroundRenderer.setView(CAM);

        //render background map
        backgroundRenderer.render();

        sr.setProjectionMatrix(CAM.combined);

        // Collisions Feature

        //save current hero's positions in case of collision
        oldHeroPositionX = this.HERO.getHeroPositionX();
        oldHeroPositionY = this.HERO.getHeroPositionY();

        //class that checks collisions
        ObjectsInteractions interactions;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){

            //update key (direction from where hero is running)

            key = Input.Keys.RIGHT;

            //move hero to the right accordingly with its velocity and the delta time
            this.HERO.setHeroPositionX(this.HERO.getHeroPositionX() + heroVelocity * delta );


            //check for collision
            interactions = new ObjectsInteractions(NaturalObjects.allNaturalObjects, this.HERO, game.batch);
            collided = interactions.checkForCollision();

            //check for collision with resources laying around and pick them up if that happens
            if (!Wood.allWood.isEmpty()){
                interactions = new ObjectsInteractions(Wood.getAllWood(), this.HERO, game.batch);
                interactions.checkForResourcesPickUp();
            }

            //update current frame accordingly to the state time
            heroCurrentFrame = HERO.getRightMovementAnimationById(HERO.getHeroId()).getKeyFrame(stateTime, true);

            //Now After the hero moved to the right we want to set his currentPosition to its default right position frame
            HERO.setCurrentDefaultPosition(HERO.getDefaultPositionRightById(HERO.getHeroId()));
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){

            //move hero to the left accordingly with its velocity and the delta time
            this.HERO.setHeroPositionX(this.HERO.getHeroPositionX() - heroVelocity * delta);

            //check for collision
            interactions = new ObjectsInteractions(NaturalObjects.allNaturalObjects, this.HERO, game.batch);
            collided = interactions.checkForCollision();

            //check for collision with resources laying around and pick them up if that happens
            if (!Wood.allWood.isEmpty()){
                interactions = new ObjectsInteractions(Wood.getAllWood(), this.HERO);
                interactions.checkForResourcesPickUp();
            }

            //update current frame accordingly to the state time
            heroCurrentFrame = HERO.getLeftMovementAnimationById(HERO.getHeroId()).getKeyFrame(stateTime, true);

            //Now After the hero moved to the right we want to set his currentPosition to its default right position frame
            HERO.setCurrentDefaultPosition(HERO.getDefaultPositionLeftById(HERO.getHeroId()));
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.UP)){

            //move hero up accordingly with its velocity and the delta time
            this.HERO.setHeroPositionY(this.HERO.getHeroPositionY() + heroVelocity * delta);

            //check for collision
            interactions = new ObjectsInteractions(NaturalObjects.allNaturalObjects, this.HERO, game.batch);
            collided = interactions.checkForCollision();

            //check for collision with resources laying around and pick them up if that happens
            if (!Wood.allWood.isEmpty()){
                interactions = new ObjectsInteractions(Wood.getAllWood(), this.HERO);
                interactions.checkForResourcesPickUp();
            }

            //update current frame accordingly to the state time
            heroCurrentFrame = HERO.getForwardMovementAnimationById(HERO.getHeroId()).getKeyFrame(stateTime, true);

            //Now After the hero moved up we want to set his currentPosition to its default forward position frame
            HERO.setCurrentDefaultPosition(HERO.getDefaultPositionForwardById(HERO.getHeroId()));

        }

        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){

            //move hero up accordingly with its velocity and the delta time
            this.HERO.setHeroPositionY(this.HERO.getHeroPositionY() - heroVelocity * delta);

            //check for collision
            interactions = new ObjectsInteractions(NaturalObjects.allNaturalObjects, this.HERO, game.batch);
            collided = interactions.checkForCollision();

            //check for collision with resources laying around and pick them up if that happens
            if (!Wood.allWood.isEmpty()){
                interactions = new ObjectsInteractions(Wood.getAllWood(), this.HERO);
                interactions.checkForResourcesPickUp();
            }

            //update current frame accordingly to the state time
            heroCurrentFrame = HERO.getBackMovementAnimationById(HERO.getHeroId()).getKeyFrame(stateTime, true);

            //Now After the hero moved down we want to set his currentPosition to its default back position frame
            HERO.setCurrentDefaultPosition(HERO.getDefaultPositionBackById(HERO.getHeroId()));
        }

        // MOUSE INPUTS

        else if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){

            //check if any destructible object is near by
            interactions = new ObjectsInteractions(DestructibleRecArea.allDestructibleRecAreas, this.HERO);

            //get resourceBeingExtracted if there is any
            resourceBeingExtracted = interactions.checkForDestruction(map);
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            ClickListener newClick;
            InputEvent event;

            event = new InputEvent();
            newClick = new ClickListener();

            for (InventoryItem item : HeroInventory.inventoryItems) {

                newClick.clicked(event, item.getPositionX(), item.getPositionY());

            }

        }


        //get hero's to his old position if there was any collision
        if (collided){
            this.HERO.setHeroPositionX(oldHeroPositionX);
            this.HERO.setHeroPositionY(oldHeroPositionY);
        }

        //Forgot what this does, converts cam coordinates to batch coordinates bc they are different for some reason
        game.batch.setProjectionMatrix(CAM.combined);

        game.batch.begin();

        /*                                   Resources Animations                                                          **/



        /*                                   BATCH RESOURCES DRAW                                                          **/

        for (Wood wood : Wood.allWood) {

            if (wood.getState().equals("being-dropped")){

                destructionStateTime += Gdx.graphics.getDeltaTime();

                treeCurrentFrame = ResourcesAnimations.TREEDEXPLOSION.getAnimation().getKeyFrame(destructionStateTime, false);;
                game.batch.draw(treeCurrentFrame, wood.getWoodRec().x - (wood.getWoodRec().getWidth() / 2), wood.getWoodRec().y);

                if (ResourcesAnimations.TREEDEXPLOSION.getAnimation().isAnimationFinished(destructionStateTime)){
                    wood.setState("dropped");

                    //reset destructionStateTime
                    destructionStateTime = 0f;
                }
            }
            else{
                game.batch.draw(wood.getTex(), wood.getPositionX(), wood.getPositionY());
            }
        }


        /*                                   BATCH HERO DRAW                                                               **/

        //Draw our hero's animation in its currentFrame if the game has started already.
        game.batch.draw(heroCurrentFrame, this.HERO.getHeroPositionX(), this.HERO.getHeroPositionY());


        //stop drawing
        game.batch.end();

        //render foreground map
        foregroundRenderer.render();

        /*                                   BATCH MAIN SCREEN DRAW                                                               **/

        game.batch.begin();

        //inventory
        game.batch.draw(HERO.getHeroInventory().getTex(), HERO.getHeroInventory().getPositionX(), HERO.getHeroInventory().getPositionY());

        //inventory items
        for (InventoryItem item: HeroInventory.inventoryItems){

            //centralize item texture inside the rectangle
            float spaceRoom = 5;

            //if there's an item there
            if (!item.getIsEmpty()){
                game.batch.draw(item.getTex(), item.getPositionX() + spaceRoom, item.getPositionY() + spaceRoom);
            }
        }
        /*                                   BATCH TEXTS DRAW                                                          **/

        //Draw Texts Around Here
        if (resourceBeingExtracted != null){

            game.font.draw(game.batch, "Extracting Resource '" + resourceBeingExtracted.getName() + "'" +
            "(" + (int) resourceBeingExtracted.getLife() + ")",
            this.HERO.getHeroPositionX(), this.HERO.getHeroPositionY() + this.HERO.getHero().getHeight() + game.font.getLineHeight());

            //reset resourceBeingExtracted to null
            resourceBeingExtracted = null;
        }

        game.batch.end();
        /*                                   DEBUGGING PURPOSES                                                         **/

//        sr.begin(ShapeRenderer.ShapeType.Line);
//        sr.rect(this.HERO.getHeroPositionX(), this.HERO.getHeroPositionY(), this.HERO.getHERO_WIDTH(), this.HERO.getHERO_HEIGHT());
//
//        //inventory stuff
//        sr.rect(this.HERO.getHeroInventory().getInventoryRec().x, this.HERO.getHeroInventory().getInventoryRec().y,
//                this.HERO.getHeroInventory().getInventoryRec().width, this.HERO.getHeroInventory().getInventoryRec().height);
//
//        for (InventoryItem item : HeroInventory.inventoryItems){
//            sr.rect(item.getItemRec().x, item.getItemRec().y, item.getItemRec().width, item.getItemRec().height);
//        }
//        sr.end();
//
//
//
//        //render lines around current objects (resources and destructible rectangle areas)
//        for (MapObject resource : NaturalObjects.allNaturalObjects){
//
//            if (resource instanceof  RectangleMapObject){
//                //Get Rectangle around resource
//                Rectangle rec = ((RectangleMapObject) resource).getRectangle();
//
//                //render
//                sr.begin(ShapeRenderer.ShapeType.Line);
//                sr.rect(rec.x, rec.y, rec.width, rec.height);
//                sr.end();
//            }
//        }
//
//        for (Rectangle destructRecArea : DestructibleRecArea.allDestructibleRecAreas) {
//
//            //render line around rectangles
//            sr.begin(ShapeRenderer.ShapeType.Line);
//            sr.rect(destructRecArea.x, destructRecArea.y, destructRecArea.width, destructRecArea.height);
//            sr.end();
//        }
//
//        for (Wood wood : Wood.allWood) {
//
//            //render line around rectangles
//            sr.begin(ShapeRenderer.ShapeType.Line);
//            sr.rect(wood.getWoodRec().x, wood.getWoodRec().y, wood.getWoodRec().width, wood.getWoodRec().height);
//            sr.end();
//        }


    }


    @Override
    public void resize(int width, int height) {

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
        HERO_TEXTURE.dispose();
        mainScreenMusic.dispose();
        foregroundRenderer.dispose();
        backgroundRenderer.dispose();
    }

}

