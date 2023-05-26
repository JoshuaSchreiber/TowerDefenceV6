package de.joshua.util;

import de.joshua.util.window.GameWindow;

public abstract class GameObject {
    private Coordinate objectCoordinate;
    private double width;
    private double height;
    private double movingAngle = 0;
    private double movingDistance = 0;
    private final static Coordinate mainPlayerTowerCoordinate = new Coordinate(GameWindow.width / 2 - 50, GameWindow.height / 2 - 50);
    private final static double mainPlayerTowerWidth = 100;
    private final static double mainPlayerTowerHeight = 100;
    private final static double mainPlayerTowerCanonLength = mainPlayerTowerHeight * 0.75;

    public GameObject() {
    }
    public double getMainPlayerTowerCanonLength(){
        return mainPlayerTowerCanonLength;
    }
    public Coordinate getMainPlayerTowerCoordinate(){return mainPlayerTowerCoordinate;}
    public double getMainPlayerTowerWidth(){
        return mainPlayerTowerWidth;
    }
    public double getMainPlayerTowerHeight(){
        return mainPlayerTowerHeight;
    }
    public Coordinate getObjectCoordinate() {
        return objectCoordinate;
    }

    public void setObjectCoordinate(Coordinate objectCoordinate) {
        this.objectCoordinate = objectCoordinate;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getMovingAngle() {
        return movingAngle;
    }

    public void setMovingAngle(double movingAngle) {
        this.movingAngle = movingAngle;
    }

    public double getMovingDistance() {
        return movingDistance;
    }

    public void setMovingDistance(double movingDistance) {
        this.movingDistance = movingDistance;
    }


    public boolean isLeftOf(GameObject that) {
        return this.getObjectCoordinate().getX() + this.getWidth() < that.getObjectCoordinate().getX();
    }

    public boolean isAbove(GameObject that) {
        return this.getObjectCoordinate().getY() + this.getHeight() < that.getObjectCoordinate().getY();
    }

    public boolean touches(GameObject that) {
        if(this.isLeftOf(that)) return false;
        if(that.isLeftOf(this)) return false;
        if(this.isAbove(that))  return false;
        if(that.isAbove(this))  return false;

        return true;
    }


    public static Coordinate polarToCartesianCoordinates(double angle) {

        // X-Achse zeigt nach Osten, Y-Achse zeigt nach SÃ¼den beim Zeichnen
        double x = Math.cos(angle);
        double y = Math.sin(angle);

        return new Coordinate(x, y);
    }

    public void moveGameObject() {

        Coordinate direction = polarToCartesianCoordinates(movingAngle);

        objectCoordinate.setX(objectCoordinate.getX() + direction.getX() * movingDistance);
        objectCoordinate.setY(objectCoordinate.getY() + direction.getY() * movingDistance);
    }

    public Coordinate hallo(GameObject gameObject, double movingAngle){
        double x = gameObject.getObjectCoordinate().getX();
        double y = gameObject.getObjectCoordinate().getY();

        double rX = x * Math.cos(movingAngle) - y * Math.sin(movingAngle);
        double rY = x * Math.sin(movingAngle) + y * Math.cos(movingAngle);

        return new Coordinate(rX, rY);
    }

    protected abstract void paintMe(java.awt.Graphics g);
}