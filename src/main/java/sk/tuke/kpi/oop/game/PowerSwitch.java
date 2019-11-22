package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;

import java.util.Objects;

public class PowerSwitch extends AbstractActor {
    private Switchable device;

    public PowerSwitch(Switchable device){
        this.device = device;
        setAnimation(new Animation("sprites/switch.png"));
    }

    public Switchable getDevice() {
        return device;
    }

    public void switchOn () {
        if (Objects.nonNull(device)) {
            device.turnOn();
            this.getAnimation().setTint(Color.WHITE);
        }
    }

    public void switchOff () {
        if (Objects.nonNull(device)) {
            device.turnOff();
            this.getAnimation().setTint(Color.GRAY);
        }
    }

    public void toggle (){
        if (Objects.nonNull(device) && device.isOn())   {
            switchOff();
        } else {
            switchOn();
        }
    }
}
