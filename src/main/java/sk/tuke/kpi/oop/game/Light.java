package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor {
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

    public void toggle (){
        if (isPowered) {
            this.isOn = !this.isOn;
            updateAnimation();
        }
    }

    public void setElectricityFlow (boolean electricityFlow){
        this.isPowered = electricityFlow;
    }

    private void updateAnimation () {
        if (isOn && isPowered) {
            setAnimation(animationOn);
        } else {
            setAnimation(animationOff);
        }
    }
}
