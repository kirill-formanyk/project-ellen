package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable, EnergyConsumer {
    private boolean isOn;
    private boolean isPowered;
    private Animation animationOn;
    private Animation animationOff;

    public Light (){
        this.isOn = false;
        this.isPowered = false;
        this.animationOn = new Animation("sprites/light_on.png");
        this.animationOff = new Animation("sprites/light_off.png");
        setAnimation(animationOff);
    }

    @Override
    public void turnOn() {
        this.isOn = true;
        updateAnimation();
    }

    @Override
    public void turnOff() {
        this.isOn = false;
        updateAnimation();
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    public void toggle (){
        if (this.isOn()) {
            turnOff();
        } else {
            turnOn();
        }
    }

    @Override
    public void setPowered (boolean powered){
        this.isPowered = powered;
        updateAnimation();
    }

    private void updateAnimation () {
        if (isOn && isPowered) {
            setAnimation(animationOn);
        } else {
            setAnimation(animationOff);
        }
    }
}
