package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor implements Switchable, EnergyConsumer {
    private boolean isOn;
    private boolean isPowered;

    public Computer () {
        setAnimation(
            new Animation("sprites/computer.png", 80, 48, 0.2f, Animation.PlayMode.LOOP_PINGPONG));
        getAnimation().pause();
        this.isOn = false;
        this.isPowered = false;
    }

    public int add (int a, int b){
        return a + b;
    }

    public float add (float a, float b) {
        return a + b;
    }

    public int sub (int a, int b) {
        return a + b;
    }

    public float sub (float a, float b) {
        return a + b;
    }

    @Override
    public void setPowered(boolean powered) {
        this.isPowered = powered;
        updateAnimation();
    }

    @Override
    public void turnOn() {
        if (!this.isOn) {
            this.isOn = true;
            updateAnimation();
        }
    }

    @Override
    public void turnOff() {
        if (this.isOn) {
            this.isOn = false;
            updateAnimation();
        }
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    private void updateAnimation () {
        if (isOn && isPowered) {
            getAnimation().play();
        } else {
            getAnimation().pause();
        }
    }
}
