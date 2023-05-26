package de.joshua.gameobjects;

import de.joshua.util.GameObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.*;
import java.util.ArrayList;

public class Tower extends GameObject {
    ArrayList<Missile> missile = new ArrayList<>();
    ArrayList<Attacker> attacker = new ArrayList<>();
    int removedMissiles = 0;
    double rotationInDegree = 0;

    public Tower() {
        super();
        setObjectCoordinate(getMainPlayerTowerCoordinate());
        setWidth(getMainPlayerTowerWidth());
        setHeight(getMainPlayerTowerHeight());

        for(int i = 0; i != 50; i++){
            missile.add(new Missile(getMovingAngle()));
        }
        createAttacker("Circle", -200, -200);
    }

    @Override
    public void paintMe(java.awt.Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        paintTower(g2d);
    }

    public void setDeltaMovingAngle(String direction, double changeMovingAngle){
        if(direction.equals("+")){
            setMovingAngle(getMovingAngle() + changeMovingAngle);
        }else{
            setMovingAngle(getMovingAngle() - changeMovingAngle);
        }
    }

    private void paintTower(Graphics2D g2d) {
        Ellipse2D towerBody = new Ellipse2D.Double(getObjectCoordinate().getX(), getObjectCoordinate().getY(), getWidth()*0.8, getHeight()*0.8);

        RoundRectangle2D tankCannonPipe = new RoundRectangle2D.Double(getObjectCoordinate().getX() + getWidth()*(0.75/2),
                getObjectCoordinate().getY() + getHeight() *(0.16/0.5),
                getHeight() * 0.75, getWidth()
                * 0.16, 5, 5);

        double f = 0.4;
        RoundRectangle2D tankTurret = new RoundRectangle2D.Double(getObjectCoordinate().getX() + getWidth() * f/2,
                getObjectCoordinate().getY() + getHeight() * (f/2),
                getWidth() * f, getHeight() * f, 15, 8);


        AffineTransform transform = new AffineTransform();
        transform.rotate(getMovingAngle(), towerBody.getCenterX(), towerBody.getCenterY());

        g2d.setColor(Color.decode("#02A272"));
        Shape transformed = transform.createTransformedShape(towerBody);
        g2d.fill(transformed);
        g2d.setColor(Color.DARK_GRAY);
        transformed = transform.createTransformedShape(tankCannonPipe);
        g2d.fill(transformed);
        transformed = transform.createTransformedShape(tankTurret);
        g2d.fill(transformed);

        if (missile.size() > 0) {
            missile.get(0).paintMe(g2d);
        }
        for(int i = 0; i < missile.size()-1; i++){
            if(missile.get(i).getMovingDistance() >= 5) {
                missile.get(i+1).paintMe(g2d);
            }
        }

        for (Attacker value : attacker) {
            value.paintMe(g2d);
        }
    }

    public void shoot() {
        if (getMovingAngle() > 2 * Math.PI) {
            setMovingAngle(getMovingAngle() - 2 * Math.PI);
        }

        rotationInDegree = (getMovingAngle()/(2 * Math.PI) * 360);

        for(int i = 0; i < missile.size()-1; i++){
            if(missile.get(i).getMovingDistance() > 0){      // If the missile starts to fly away it stops rotating
                missile.get(i).missileRotate = false;
            }
        }

        if (missile.size() > 0) {       // ShootRange increasing, missile[0], needs to start before the others so that the loop can work
            missile.get(0).setMovingDistance(missile.get(0).getMovingDistance() + 1);
            missile.get(0).setMovingAngle(getMovingAngle());
        }
        for(int i = 0; i < missile.size()-1; i++){
            if(missile.get(i).getMovingDistance() >= 5){         // Increasing the ShootRange for everything other than null
                missile.get(i+1).setMovingDistance(missile.get(i + 1).getMovingDistance() + 1);
                missile.get(i+1).setMovingAngle(getMovingAngle());
            }
        }


        for(int i = 0; i < missile.size(); i++){
            if(missile.get(i).getMovingDistance() == 100){       // If the missile is out of the window it gets removed, and a new Missile gets added
                missile.remove(i);
                missile.add(new Missile(getMovingAngle()));
            }
            if(removedMissiles > 0){        // If missiles got removed because of the attackers, new ones get added here
                missile.add(new Missile(getMovingAngle()));
                removedMissiles--;
            }
        }
    }

    public void createAttacker(String form, double xPositionOutOfMainPlayerTowerCoordinate, double yPositionOutOfMainPlayerTowerCoordinate){
        if(form.equals("Circle")){
            attacker.add(new Attacker("Circle", xPositionOutOfMainPlayerTowerCoordinate, yPositionOutOfMainPlayerTowerCoordinate));
        }
    }

    public void attackerTouchesCannonball(){
        for(int i = 0; i < missile.size(); i++) {
            for (int z = 0; z < attacker.size(); z++) {
                if(missile.get(i).touches(attacker.get(z))){
                    missile.remove(i);
                }
            }
        }
    }
}
