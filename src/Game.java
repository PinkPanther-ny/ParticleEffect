import bagel.*;

import java.util.ArrayList;

public class Game extends AbstractGame {

    private final ArrayList<Particle> particles;
    private final ArrayList<Particle> mouseParticles;
    private static Rocket rocket;
    private int totalParticle = 50;
    private double spawnCooldown = 0.5;
    private Timer particleTimer = new Timer(spawnCooldown);
    private final Timer keyTimer = new Timer(100);

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

        particles = new ArrayList<>();
        mouseParticles = new ArrayList<>();
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
        rocket.draw(input);
        if (rocket.getLocation().y < -rocket.getHeight()){
            rocket = new Rocket();
        }

        if (particles.size() < totalParticle && particleTimer.isCool()){
            particles.add(new Particle(rocket.getSparkLocation(), input));

        }else if (particles.size() == totalParticle && particleTimer.isCool()){
            particles.remove(0);
            particles.add(new Particle(rocket.getSparkLocation(), input));

        }else{
            while (particles.size()>totalParticle){
                particles.remove(0);
            }

        }

        if (input.isDown(MouseButtons.LEFT)){
            mouseParticles.add(new Particle(input.getMousePosition(), input));

        }

        for (Particle particle : particles) {

            particle.draw();

        }
        for (Particle particle : mouseParticles) {

            particle.draw();

        }


        if(input.isDown(Keys.Q) && keyTimer.isCool()){
            totalParticle += 1;
        }else if(input.isDown(Keys.W) && keyTimer.isCool() && totalParticle>1){
            totalParticle -= 1;
        }else if(input.isDown(Keys.E) && keyTimer.isCool()){
            spawnCooldown += 10;
            particleTimer = new Timer(spawnCooldown);
        }else if(input.isDown(Keys.R) && keyTimer.isCool() && spawnCooldown>2){
            spawnCooldown -= 2;
            particleTimer = new Timer(spawnCooldown);
        }




        myInfo.drawString("Total Particles         (Q+/W-) : " + totalParticle +
                                "\nSpawn Cooldown     (E+/R-) : " + spawnCooldown + "(ms)" +
                                "\nScale                        (O+/P-)" + Math.round(particles.get(particles.size()-1).getScale().x * 10) / 10.0 +
                                "\nFade                         (A+/S-) : " + Math.round(particles.get(particles.size()-1).getLife() / 1000.0 * 10) / 10.0  + "(secs)" +
                                "\nxMargin                   (D+/F-) : " + Math.round(particles.get(particles.size()-1).getXMargin() * 10) / 10.0 +
                                "\nyMax                        (C+/V-) : " + Math.round(particles.get(particles.size()-1).getYMax() * 10) / 10.0 +
                                "\nyMin                         (Z+/X-) : " + Math.round(particles.get(particles.size()-1).getYMin() * 10) / 10.0
                , 20, Window.getHeight()-170
                , new DrawOptions().setBlendColour(0,0,0)
        );

        if (particles.get(particles.size()-1).getLife() >=1000){
            String warnString = "POWER UP";
            warn.drawString(warnString, Window.getWidth()/2.0 - warn.getWidth(warnString)*0.5, Window.getHeight()-160, new DrawOptions().setBlendColour(205/255.0,0,0));
        }

    }

}
