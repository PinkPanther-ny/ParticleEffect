import bagel.*;

public class Game extends AbstractGame {

    private static Rocket rocket;

    private final Font myInfo = new Font("res/fonts/DejaVuSans-Bold.ttf",20);
    private final Font warn = new Font("res/fonts/DejaVuSans-Bold.ttf",60);
    private final Image bg = new Image("res/images/bg4.png");

    public static void main(String[] args) {
        // Create new instance of game and run it
        new Game().run();
    }

    /**
     * Setup the game
     */
    public Game(){
        super(1920,1080,"Rocket");
        rocket = new Rocket();
        // Constructor
    }

    public static Rocket getRocket() {
        return rocket;
    }

    public static double getRocketSpeed(){
        return Game.getRocket().getVelocity().lengthSquared();

    }

    /**
     * Updates the game state approximately 60 times a second, potentially reading from input.
     * @param input The input instance which provides access to keyboard/mouse state information.
     */
    @Override
    protected void update(Input input) {

        bg.drawFromTopLeft(0,0, new DrawOptions().setBlendColour(0.9,0.9,0.9,0.8));
        rocket.draw();
        rocket.update(input);

        for (Engine engine: rocket.getEngines()){
            engine.update(rocket, input);
            engine.draw(rocket);
        }

        drawInfo();

    }

    public void drawInfo(){

        myInfo.drawString("Total Particles         (Q+/W-) : " + Engine.getTotalParticle() +
                        "\nSpawn Cooldown     (E+/R-) : " + Engine.getSpawnCooldown() + "(ms)" +

                        "\nScale                        (O+/P-)" + Math.round(Particle.getScale().x * 10) / 10.0 +
                        "\nFade                         (A+/S-) : " + Math.round(Particle.getLife() / 1000.0 * 10) / 10.0  + "(secs)" +
                        "\nxMargin                   (D+/F-) : " + Math.round(Particle.getxMargin() * 10) / 10.0 +
                        "\nyMax                        (C+/V-) : " + Math.round(Particle.getyMax() * 10) / 10.0 +
                        "\nyMin                         (Z+/X-) : " + Math.round(Particle.getyMin() * 10) / 10.0
                , 20, Window.getHeight()-170
                , new DrawOptions().setBlendColour(0,0,0)
        );

        if (Particle.getLife() >=1000){
            String warnString = "POWER UP";
            warn.drawString(
                    warnString,
                    Window.getWidth()/2.0 - warn.getWidth(warnString)*0.5,
                    Window.getHeight()-160,
                    new DrawOptions().setBlendColour(205/255.0,0,0)
            );
        }
    }

}
