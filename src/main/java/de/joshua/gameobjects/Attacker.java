package de.joshua.gameobjects;

import de.joshua.util.Coordinate;
import de.joshua.util.GameObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;


public class Attacker extends GameObject {

    String form;
    public Attacker(String form, double xPositionOutOfMainPlayerTowerCoordinate, double yPositionOutOfMainPlayerTowerCoordinate) {
        super();

        this.form = form;

        setObjectCoordinate(getMainPlayerTowerCoordinate());
        setWidth(getMainPlayerTowerWidth() * 0.8);
        setHeight(getMainPlayerTowerHeight() * 0.8);

        setObjectCoordinate(new Coordinate(getObjectCoordinate().getX() + xPositionOutOfMainPlayerTowerCoordinate, getObjectCoordinate().getY() + yPositionOutOfMainPlayerTowerCoordinate));
    }

    @Override
    public void paintMe(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        paintAttacker(g2d);
    }

    public void paintAttacker(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Ellipse2D circle = null;
        String colour = null;
        switch (form) {
            case "Dreieck":

                break;
            case "Viereck":

                break;
            case "Fünfeck":

                break;
            case "Sechseck":

                break;
            case "Circle":
                circle = new Ellipse2D.Double(getObjectCoordinate().getX(), getObjectCoordinate().getY(), getWidth(), getHeight());
                colour = "#02A272";
                break;
        }

        AffineTransform transform = new AffineTransform();
        g2d.setColor(Color.decode(String.valueOf(colour)));

        Shape transformed = null;
        if (form.equals("Circle")) {
            transformed = transform.createTransformedShape(circle);
        }

        g2d.fill(transformed);
    }
}
