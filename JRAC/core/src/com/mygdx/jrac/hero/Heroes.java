package com.mygdx.jrac.hero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.jrac.Inventory.HeroInventory;
import com.mygdx.jrac.maps.Maps;
import com.badlogic.gdx.math.Rectangle;

public class Heroes {

    Rectangle hero;

    //Width/Height of hero to be used in the rectangle object
    private final int HERO_WIDTH = 70;
    private final int HERO_HEIGHT = 70;

    //hero id, important for getting image and animation so far
    private int heroId;

    //hero name
    private String name;

    //tracks how much life is left for our hero
    private int life;

    //how much damage he causes to its enemies
    private float damagePower;

    //hero Texture to use whenever game is paused or something like that
    private Texture texture;

    //checks if hero is carrying a resource or not
    private boolean isCarryingResource;
    private String nameOfResourceBeingCarried = "";


    //default positions
    private TextureRegion currentDefaultPosition;


    //how fast our hero is
    private int velocity;

    //deals with inventory
    HeroInventory heroInventory;

    public Heroes (int id, String name, float damagePower, int velocity){

        //creates a rectangle object for our hero
        this.hero = new Rectangle();

        //set hero id, name, damage power, life, and velocity
        this.heroId = id;
        this.name = name;
        this.damagePower = damagePower;
        this.life = 100;
        this.velocity = velocity;

        //get texture and animations from another classes through id number
        this.texture = getTextureById(id);

        //set currentDefaultPosition to The Right
        setCurrentDefaultPosition(getDefaultPositionRightById(heroId));

        //Set Hero in the Screen when first created
        this.hero.x = ((Maps.MAP_WIDTH / 2) - (HERO_WIDTH / 2));
        this.hero.y = ((Maps.MAP_HEIGHT/ 2) / 2);

        //Set rectangle (hero) dimensions
        this.hero.width = HERO_WIDTH;
        this.hero.height = HERO_HEIGHT;

        //create inventory for Hero
        heroInventory = new HeroInventory("Inventory", this);

    }

    /* SETTERS */

    //Set Hero's position X
    public void setHeroPositionX(float positionX){

        this.hero.x = positionX;
    }

    public void setHeroPositionY(float positionY){

        this.hero.y = positionY;
    }

    public void setCurrentDefaultPosition(TextureRegion currentDefaultPositionInTheGame) {
        this.currentDefaultPosition = currentDefaultPositionInTheGame;
    }




    /* END OF SETTERS */



    /* GET PROPERTIES BY ID */

    /* THE ENUM CLASSES 'HeroesImages' and 'Heroes Animations' contain the defaults
     *  images and animations for each specific hero. The methods below get get those properties by Id */

    public Texture getTextureById(int heroId){

        if (heroId == 1){
            return HeroesImages.GREENHERO.getTexture();
        }

        //gotta figure it out how to return an error or something
        return new Texture(Gdx.files.internal("badlogic.jpg"));
    }


    public Animation<TextureRegion> getRightMovementAnimationById(int heroId) {


        if (heroId == 1){

            return HeroesAnimations.GREENHERO.getRightMovement();
        }

        //gotta figure it out how to return an error or something
        return HeroesAnimations.GREENHERO.getRightMovement();
    }

    public Animation<TextureRegion> getLeftMovementAnimationById(int heroId) {

        if (heroId == 1){
            return HeroesAnimations.GREENHERO.getLeftMovement();
        }


        //gotta figure it out how to return an error or something
        return HeroesAnimations.GREENHERO.getLeftMovement();
    }

    public Animation<TextureRegion> getForwardMovementAnimationById(int heroId) {

        if (heroId == 1){
            return HeroesAnimations.GREENHERO.getForwardMovement();
        }

        //gotta figure it out how to return an error or something
        return HeroesAnimations.GREENHERO.getForwardMovement();
    }

    public Animation<TextureRegion> getBackMovementAnimationById(int heroId) {

        if (heroId == 1){
            return HeroesAnimations.GREENHERO.getBackMovement();
        }

        //gotta figure it out how to return an error or something
        return HeroesAnimations.GREENHERO.getBackMovement();
    }

    public TextureRegion getDefaultPositionRightById(int heroId) {

        if (heroId == 1){
            return HeroesAnimations.GREENHERO.getDefaultPositionRight();
        }

        //gotta figure it out how to return an error or something
        return HeroesAnimations.GREENHERO.getDefaultPositionRight();
    }

    public TextureRegion getDefaultPositionLeftById(int heroId) {

        if (heroId == 1){
            return HeroesAnimations.GREENHERO.getDefaultPositionLeft();
        }

        //gotta figure it out how to return an error or something
        return HeroesAnimations.GREENHERO.getDefaultPositionLeft();
    }

    public TextureRegion getDefaultPositionForwardById(int heroId) {

        if (heroId == 1){
            return HeroesAnimations.GREENHERO.getDefaultPositionForward();
        }

        //gotta figure it out how to return an error or something
        return HeroesAnimations.GREENHERO.getDefaultPositionForward();
    }

    public TextureRegion getDefaultPositionBackById(int heroId) {

        if (heroId == 1){
            return HeroesAnimations.GREENHERO.getDefaultPositionBack();
        }

        //gotta figure it out how to return an error or something
        return HeroesAnimations.GREENHERO.getDefaultPositionBack();
    }





    /* END OF GET PROPERTIES BY ID */


    /* GETTERS */

    //Animations

    public TextureRegion getCurrentDefaultPosition() {
        return currentDefaultPosition;
    }

    public HeroInventory getHeroInventory() {
        return heroInventory;
    }


    public Rectangle getHero() {
        return hero;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getVelocity() {
        return velocity;
    }

    public int getHERO_WIDTH() {
        return HERO_WIDTH;
    }

    public int getHERO_HEIGHT() {
        return HERO_HEIGHT;
    }

    public int getHeroId() {
        return heroId;
    }

    public String getName() {
        return name;
    }

    public int getLife() {
        return life;
    }

    public float getDamagePower() {
        return this.damagePower;
    }

    //get hero's positions
    public float getHeroPositionX(){ return this.hero.x; }

    public float getHeroPositionY(){ return this.hero.y; }

    public String getNameOfResourceBeingCarried() {
        return nameOfResourceBeingCarried;
    }

    /* END OF GETTERS */
}
