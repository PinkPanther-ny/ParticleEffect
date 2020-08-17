import bagel.*;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;

public class Rocket {
    private static final Image rocketImage = new Image("res\\images\\rocket.png");
    private final ArrayList<Engine> engines = new ArrayList<>();

    private Point location;
    private Vector2 velocity;
    private double angle;

    private static final double followSpeed = 2.2;

    private static final boolean isChangeFadeTime = true;
    private static final boolean isChangeScale = true;

    public Rocket(){

        this.location = new Point(0.5 * Window.getWidth(), Window.getHeight() * 0.5);
        this.velocity = new Vector2();
        this.angle = 0;
        setEngines();
    }

    public void setEngines() {

        engines.add(new Engine(-Math.PI * 1/8.0));
        engines.add(new Engine(Math.PI * 1/8.0));

        //engines.add(new Engine(0));
    }

    public void draw(){
        rocketImage.draw(this.location.x, this.location.y,
                new DrawOptions().setRotation(angle)
        );
    }

    public void update(Input input){
        Point mouse = input.getMousePosition();
        Vector2 newVelocity = new Vector2(mouse.x - this.location.x, mouse.y - this.location.y);

        this.angle = Math.PI * 0.5 + Math.atan2(velocity.y , velocity.x);
        this.velocity = newVelocity.normalised().mul(newVelocity.length() * 0.02 * followSpeed);
        this.location = new Point(this.location.x + this.velocity.x, this.location.y + this.velocity.y);

        double OldMax = 600, OldMin = 0;
        if (isChangeFadeTime) {
            double OldLifeRange = (OldMax - OldMin);    // Speed
            double NewLifeMax = 1000, NewLifeMin = 600;
            double NewLifeRange = (NewLifeMax - NewLifeMin); //  Fade time
            double NewLifeValue = (((this.velocity.lengthSquared() - OldMin) * NewLifeRange) / OldLifeRange) + NewLifeMin;
            if (NewLifeValue > 1000) {
                NewLifeValue = 1000;
            }
            Particle.setLife(NewLifeValue);
        }

        if (isChangeScale) {
            double maxScale = 2.2;
            double OldSizeRange = (OldMax - OldMin);    // Speed
            double NewSizeMax = 1.7, NewSizeMin = 1.0;
            double NewSizeRange = (NewSizeMax - NewSizeMin); // Scale
            double NewSizeValue = (((this.velocity.lengthSquared() - OldMin) * NewSizeRange) / OldSizeRange) + NewSizeMin;
            if (NewSizeValue > maxScale) {
                NewSizeValue = maxScale;
            }
            Particle.setScale(new Vector2(NewSizeValue, NewSizeValue));
        }

    }




    public Point rotate_point(double angle, Point p, Point centerPoint) {

        // translate point back to origin:
        p = new Point(p.x - centerPoint.x, p.y - centerPoint.y);

        // rotate point
        double xNew = p.x * Math.cos(angle) - p.y * Math.sin(angle);
        double yNew = p.x * Math.sin(angle) + p.y * Math.cos(angle);

        // translate point back:
        p = new Point(xNew + centerPoint.x, yNew + centerPoint.y);

        return p;
    }

    public ArrayList<Engine> getEngines() {
        return engines;
    }

    public Point getSparkLocation() {
        return rotate_point(this.angle, new Point(this.location.x, this.location.y + rocketImage.getHeight()/4.0), this.location);
    }

    public double getAngle() {
        return angle;
    }

    public Point getLocation() {
        return location;
    }

    public Vector2 getVelocity() {
        return velocity;
    }
}
