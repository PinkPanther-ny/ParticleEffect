public class Timer {


    private final double cooldown;
    private double cooldownCount = 0;

    private double preTime = System.currentTimeMillis();

    public Timer(double cooldown){
        this.cooldown = cooldown;
    }


    private void resetCooldownCount() {
        this.cooldownCount = getCooldown();
    }

    private double getCooldown() {
        return cooldown;
    }

    public boolean isCool(){
        if (this.cooldownCount <=0){
            resetCooldownCount();
            return true;
        }
        this.update();
        return false;
    }

    public void update(){
        this.cooldownCount -= System.currentTimeMillis() - this.preTime;
        this.preTime = System.currentTimeMillis();
    }

}
