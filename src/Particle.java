import bagel.*;
import bagel.util.Point;
import bagel.util.Vector2;

public class Particle {
    private static final Image particleImage = new Image("res\\images\\particle.png");
    private Point location;
    private final Vector2 velocity;
    private final double rotationSpeed;
    private double rotation = 0;
    private final double birthday;

    private static double life = 0;
    private static double xMargin = 1.0;
    private static double yMax = 9;
    private static double yMin = 4;

    private final double r,g,b;
    private static Vector2 scale = new Vector2(1,1);

    private static final boolean isNormalColour = true;
    private static final boolean isNormalScale = true;


    public Particle(Point location){

        this.r = Math.random();
        this.g = Math.random();
        this.b = Math.random();

        double xMax = +xMargin, xMin = -xMargin;
        double x = Math.random() * (xMax - xMin + 1) + xMin;
        double y = Math.random() * (yMax - yMin + 1) + yMin;

        this.location = location;
        this.velocity = new Vector2(
                x*Math.cos(Game.getRocket().getAngle())-y * Math.sin(Game.getRocket().getAngle()),
                x*Math.sin(Game.getRocket().getAngle())+y * Math.cos(Game.getRocket().getAngle())
        );
        this.rotationSpeed = Math.random() * 0.3 - 0.3/2;
        this.birthday = System.currentTimeMillis();
    }

    public void draw(Point location){
        DrawOptions drawOptions;

        double gbColour = 1 - (0.6 * Game.getRocketSpeed()/450);

        double xScale = scale.x * this.getRemainLife();
        double yScale = scale.y * this.getRemainLife();

        if (isNormalColour && isNormalScale){
            drawOptions = new DrawOptions().
                    setRotation(this.getRotation()).
                    setBlendColour( 1, gbColour, gbColour, this.getRemainLife()).
                    setScale(xScale, yScale);
        }else if((!isNormalColour) && isNormalScale){
            drawOptions = new DrawOptions().
                    setRotation(this.getRotation()).
                    setBlendColour( r, g, b, this.getRemainLife()).
                    setScale(xScale, yScale);
        }else if(isNormalColour && (!isNormalScale)){
            drawOptions = new DrawOptions().
                    setRotation(this.getRotation()).
                    setBlendColour( 1, gbColour, gbColour, this.getRemainLife()).
                    setScale(Math.random() * xScale, Math.random() * yScale);
        }else{

            drawOptions = new DrawOptions().
                    setRotation(this.getRotation()).
                    setBlendColour( r, g, b, this.getRemainLife()).
                    setScale(Math.random() * xScale, Math.random() * yScale);
        }

        particleImage.draw(
                location.x,
                location.y,
                drawOptions
        );

    }

    public void update(){
        this.location = new Point(this.location.x + this.getVelocity().x, this.location.y + this.getVelocity().y);
        this.rotation = this.rotation + this.getRotationSpeed();
    }



    public static void setyMax(double yMax) {
        Particle.yMax = yMax;
    }

    public static void setyMin(double yMin) {
        Particle.yMin = yMin;
    }

    public static void setxMargin(double xMargin) {
        Particle.xMargin = xMargin;
    }

    public static void setLife(double life) {
        Particle.life = life;
    }

    public static void setScale(Vector2 scale) {
        Particle.scale = scale;
    }

    public Point getLocation() {
        return location;
    }

    private Vector2 getVelocity(){
        return velocity;
    }

    private double getRotationSpeed(){
        return rotationSpeed;
    }

    private double getRotation() {
        return rotation;
    }

    private double getBirthday() {
        return birthday;
    }

    public static double getLife() {
        return life;
    }

    private double getRemainLife(){
        return 1 - ((System.currentTimeMillis() - this.getBirthday()) / Particle.getLife());
    }

    public static Vector2 getScale() {
        return scale;
    }

    public static double getxMargin() {
        return xMargin;
    }

    public static double getyMax() {
        return yMax;
    }

    public static double getyMin() {
        return yMin;
    }

}
