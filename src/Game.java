import bagel.*;

import java.util.ArrayList;

public class Game extends AbstractGame {

    private final ArrayList<Particle> particles;
    private Rocket rocket;
    private int totalParticle = 50;
    private double spawnCooldown = 0.5;
    private Timer particleTimer = new Timer(spawnCooldown);
    private final Timer keyTimer = new Timer(100);

    private final Font myInfo = new Font("res/fonts/DejaVuSans-Bold.ttf",20);
    private final Image bg = new Image("res/images/bg3.png");

    public static void main(String[] args) {
        // Create new instance of game and run it
        new Game().run();
    }

    /**
     * Setup the game
     */
    public Game(){
        particles = new ArrayList<>();
        rocket = new Rocket();
        // Constructor
    }

    public Rocket getRocket() {
        return rocket;
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
            particles.add(new Particle(rocket.getSparkLocation(), input, this));

        }else if (particles.size() == totalParticle && particleTimer.isCool()){
            particles.remove(0);
            particles.add(new Particle(rocket.getSparkLocation(), input, this));

        }else{
            while (particles.size()>totalParticle){
                particles.remove(0);
            }

        }

        for (Particle particle : particles) {

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
                , 20, 600
                , new DrawOptions().setBlendColour(0,0,0)
        );
    }

}
