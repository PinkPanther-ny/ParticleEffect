import bagel.DrawOptions;
import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Vector2;

public class Particle {
    private static final Image particleImage = new Image("res\\images\\particle.png");
    private Point location;
    private final Vector2 velocity;
    private final double rotationSpeed;
    private double rotation = 0;
    private final double birthday;
    private static double life = 600;

    private static double xMargin = 2.0;
    private static double yMax = 9;
    private static double yMin = 4;

    private static Vector2 scale = new Vector2(1,1);

    private final Timer keyTimer = new Timer(100);

    public Particle(Point location, Input input, Game game){
        listenToKey(input);

        //Point location = input.getMousePosition();
        double xMax = +xMargin, xMin = -xMargin;
        double x = Math.random() * (xMax - xMin + 1) + xMin;
        double y = Math.random() * (yMax - yMin + 1) + yMin;

        this.location = location;
        //this.velocity = new Vector2(x,y);
        this.velocity = new Vector2(
                x*Math.cos(game.getRocket().getAngle())-y * Math.sin(game.getRocket().getAngle()),
                x*Math.sin(game.getRocket().getAngle())+y * Math.cos(game.getRocket().getAngle())
        );
        this.rotationSpeed = Math.random() * 0.3 - 0.3/2;
        this.birthday = System.currentTimeMillis();
    }

    public static void setLife(double life) {
        Particle.life = life;
    }

    public static void setScale(Vector2 scale) {
        Particle.scale = scale;
    }

    public void draw(){

        particleImage.draw(
                this.location.x,
                this.location.y,
                new DrawOptions().
                        setRotation(this.getRotation()).
                        setBlendColour(1,1,1, this.getRemainLife()).
                        setScale(scale.x * this.getRemainLife(), scale.y * this.getRemainLife())
        );
        this.update();
    }

    private void update(){

        this.location = new Point(this.location.x + this.getVelocity().x, this.location.y + this.getVelocity().y);
        this.rotation = this.rotation + this.getRotationSpeed();
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

    public double getLife() {
        return life;
    }

    public double getXMargin() {
        return xMargin;
    }

    public double getYMax() {
        return yMax;
    }

    public double getYMin() {
        return yMin;
    }

    private double getRemainLife(){
        return 1 - ((System.currentTimeMillis() - this.getBirthday()) / this.getLife());
    }

    public Vector2 getScale() {
        return scale;
    }

    private void listenToKey(Input input){

        /*if(input.isDown(Keys.A) && keyTimer.isCool()){
            life += 100;
        }else if(input.isDown(Keys.S) && keyTimer.isCool() && life > 100){
            life -= 100;
        }else */
        if(input.isDown(Keys.D) && keyTimer.isCool()){
            xMargin += 0.1;
        }else if(input.isDown(Keys.F) && keyTimer.isCool()){
            xMargin -= 0.1;
        }else if(input.isDown(Keys.Z) && keyTimer.isCool()){
            yMin += 0.1;
        }else if(input.isDown(Keys.X) && keyTimer.isCool()){
            yMin -= 0.1;
        }else if(input.isDown(Keys.C) && keyTimer.isCool()){
            yMax += 0.1;
        }else if(input.isDown(Keys.V) && keyTimer.isCool()){
            yMax -= 0.1;
        }else if(input.isDown(Keys.O) && keyTimer.isCool()){
            scale = new Vector2(scale.x + 0.05, scale.y + 0.05);
        }else if(input.isDown(Keys.P) && keyTimer.isCool() && scale.x>0.1){
            scale = new Vector2(scale.x - 0.05, scale.y - 0.05);
        }


    }

}
