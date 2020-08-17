import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;

public class Engine {

    private double angle;
    private final ArrayList<Particle> particles;
    private static double spawnCooldown = 0.5;
    private static int totalParticle = 20;

    private Timer particleTimer = new Timer(spawnCooldown);
    private final Timer keyTimer = new Timer(100);


    public Engine(double angle){
        this.angle = angle;
        particles = new ArrayList<>();

    }

    public void update(Rocket rocket, Input input){
        if (angle<0) {
            angle -= 0.05;
        }else{
            angle += 0.05;
        }
        listenToKey(rocket, input);
        Point sparkLocation = rocket.getSparkLocation();
        Particle p = new Particle(sparkLocation);
        p.update();
        if (particles.size() < totalParticle && particleTimer.isCool()){
            particles.add(p);

        }else if (particles.size() == totalParticle && particleTimer.isCool()){
            particles.remove(0);
            particles.add(p);

        }else{
            // if modify the totalParticle number this will happen
            while (particles.size() > totalParticle){ particles.remove(0); }
        }
    }

    public void draw(Rocket rocket){
        for (Particle particle : particles) {
            particle.draw(rotate_point(angle, particle.getLocation(), rocket.getLocation()));
            particle.update();
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

    private void listenToKey(Rocket rocket, Input input){

        if(input.isDown(Keys.Q) && keyTimer.isCool()){
            Engine.setTotalParticle(Engine.getTotalParticle()+1);

        }else if(input.isDown(Keys.W) && keyTimer.isCool() && Engine.getTotalParticle()>1){
            Engine.setTotalParticle(Engine.getTotalParticle()-1);

        }else if(input.isDown(Keys.E) && keyTimer.isCool()){
            Engine.setSpawnCooldown(Engine.getSpawnCooldown() + 10);
            for(Engine engine: rocket.getEngines()){
                engine.setParticleTimer(new Timer(Engine.getSpawnCooldown()));
            }

        }else if(input.isDown(Keys.R) && keyTimer.isCool() && Engine.getSpawnCooldown()>2){
            Engine.setSpawnCooldown(Engine.getSpawnCooldown() - 2);
            for(Engine engine: rocket.getEngines()){
                engine.setParticleTimer(new Timer(Engine.getSpawnCooldown()));
            }
        }


        if(input.isDown(Keys.D) && keyTimer.isCool()){
            Particle.setxMargin(Particle.getxMargin() + 0.05);
        }else if(input.isDown(Keys.F) && keyTimer.isCool() && Particle.getxMargin() - 0.05 >= 0){
            Particle.setxMargin(Particle.getxMargin() - 0.05);
        }else if(input.isDown(Keys.Z) && keyTimer.isCool()){
            Particle.setyMin(Particle.getyMin() + 0.1);
        }else if(input.isDown(Keys.X) && keyTimer.isCool()){
            Particle.setyMin(Particle.getyMin() - 0.1);
        }else if(input.isDown(Keys.C) && keyTimer.isCool()){
            Particle.setyMax(Particle.getyMax() + 0.1);
        }else if(input.isDown(Keys.V) && keyTimer.isCool()){
            Particle.setyMax(Particle.getyMax() - 0.1);
        }else if(input.isDown(Keys.O) && keyTimer.isCool()){
            Particle.setScale(Particle.getScale().add(new Vector2(0.05, 0.05)));
        }else if(input.isDown(Keys.P) && keyTimer.isCool() && Particle.getScale().x > 0.1){
            Particle.setScale(Particle.getScale().add(new Vector2(-0.05, -0.05)));
        }

    }




    public void setParticleTimer(Timer particleTimer) {
        this.particleTimer = particleTimer;
    }

    public static int getTotalParticle() {
        return totalParticle;
    }

    public static void setTotalParticle(int totalParticle) {
        Engine.totalParticle = totalParticle;
    }

    public static double getSpawnCooldown() {
        return spawnCooldown;
    }

    public static void setSpawnCooldown(double spawnCooldown) {
        Engine.spawnCooldown = spawnCooldown;
    }
}
