package models;

public class Entity {
    private int level, health;
    private double speed, power, strength;

    public Entity(int level, int health, double speed, double power, double strength) {
        this.level = level;
        this.health = health;
        this.speed = speed;
        this.power = power;
        this.strength = strength;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public void setStats(double power, double speed, double strength){
        setPower(power);
        setSpeed(speed);
        setStrength(strength);
    }
}
