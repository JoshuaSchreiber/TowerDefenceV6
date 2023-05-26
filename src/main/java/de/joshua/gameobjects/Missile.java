package de.joshua.gameobjects;

import de.joshua.util.Coordinate;
import de.joshua.util.GameObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class Missile extends GameObject {
    double yMovingDistance;
    boolean missileRotate = true;
    double canonDirection = 0;

    public Missile(double movingAngel) {
        super();
        setMovingAngle(movingAngel);
        setObjectCoordinate(getMainPlayerTowerCoordinate());
        setWidth(getMainPlayerTowerWidth());
        setHeight(getMainPlayerTowerHeight());
    }

    @Override
    public void paintMe(java.awt.Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);
        AffineTransform transform = new AffineTransform();
        setObjectCoordinate(new Coordinate(getMainPlayerTowerCoordinate().getX() + getWidth()*0.375 + getMainPlayerTowerCanonLength() + getMovingDistance() * 10,
                getMainPlayerTowerCoordinate().getY() + getHeight()*0.32));
        Ellipse2D missileShape = new Ellipse2D.Double(getObjectCoordinate().getX(), getObjectCoordinate().getY(),10, 10);

        if (missileRotate) {
            transform.rotate(getMovingAngle(), getMainPlayerTowerCoordinate().getX() + getWidth() * 0.4, getMainPlayerTowerCoordinate().getY() + getHeight() * 0.4);
            canonDirection = canonDirection + getMovingAngle();
        }else{
            transform.rotate(canonDirection, getMainPlayerTowerCoordinate().getX() + getWidth() * 0.4, getMainPlayerTowerCoordinate().getY() + getHeight() * 0.4);
        }

        Shape transformedMissileShape = transform.createTransformedShape(missileShape);
        g2d.fill(transformedMissileShape);
    }
}