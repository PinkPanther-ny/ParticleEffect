import bagel.*;
import bagel.util.Point;
import bagel.util.Vector2;

public class Rocket {
    private static final Image rocketImage = new Image("res\\images\\rocket.png");


    private Point location;
    private Vector2 velocity;
    private double angle;
    private double followSpeed = 1.5;

    private final Timer keyTimer = new Timer(20);

    public void draw(Input input){
        System.out.println(velocity.lengthSquared());
        rocketImage.draw(this.location.x, this.location.y, new DrawOptions().setRotation(angle));

        this.update(input);
    }

    public void update(Input input){
        listenToKey(input);
        Point mouse = input.getMousePosition();
        Vector2 newVelocity = new Vector2(mouse.x - this.location.x, mouse.y - this.location.y);

        this.angle = Math.PI * 0.5 + Math.atan2(velocity.y , velocity.x);
        this.velocity = newVelocity.normalised().mul(newVelocity.length() * 0.02 * followSpeed);
        this.location = new Point(this.location.x + this.velocity.x, this.location.y + this.velocity.y);


        double OldLifeRange = (400 - 0);    // Speed
        double NewLifeRange = (1000 - 600); //  Fade time

        double NewLifeValue = (((this.velocity.lengthSquared() - 0) * NewLifeRange) / OldLifeRange) + 600;
        if (OldLifeRange == 0){NewLifeValue = 600;}
        Particle.setLife(NewLifeValue);

        double OldSizeRange = (400 - 0);    // Speed
        double NewSizeRange = (1.6 - 0.8); // Scale

        double NewSizeValue = (((this.velocity.lengthSquared() - 0) * NewSizeRange) / OldSizeRange) + 0.8;
        Particle.setScale(new Vector2(NewSizeValue, NewSizeValue));


    }

    public Rocket(){


        this.location = new Point(0.5 * Window.getWidth(), Window.getHeight() * 0.5);
        this.velocity = new Vector2();
        this.angle = 0;
    }

    public Point getLocation() {
        return location;
    }
    public Point getSparkLocation() {
        return rotate_point(this.angle, new Point(this.location.x, this.location.y + rocketImage.getHeight()/4.0), this.location);
    }
    public double getHeight(){
        return rocketImage.getHeight();
    }

    private void listenToKey(Input input){

        if(input.isDown(Keys.LEFT) && keyTimer.isCool()){
            location = new Point(location.x - 4, location.y);
        }else if(input.isDown(Keys.RIGHT) && keyTimer.isCool()){
            location = new Point(location.x + 4, location.y);
        }
    }

    public double getAngle() {
        return angle;
    }

    public Point rotate_point(double angle, Point p, Point centerPoint)
    {

        // translate point back to origin:
        p = new Point(p.x - centerPoint.x, p.y - centerPoint.y);

        // rotate point
        double xNew = p.x * Math.cos(angle) - p.y * Math.sin(angle);
        double yNew = p.x * Math.sin(angle) + p.y * Math.cos(angle);

        // translate point back:
        p = new Point(xNew + centerPoint.x, yNew + centerPoint.y);

        return p;
    }

    public Vector2 getVelocity() {
        return velocity;
    }
}
