package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.util.Objects;

public class Controller extends AbstractActor {
    private Reactor reactor;

    public Controller(Reactor reactor){
        this.reactor = reactor;
        setAnimation(new Animation("sprites/switch.png"));
    }

    public void toggle (){
        if (Objects.nonNull(reactor)) {
            if (reactor.isRunning()) reactor.turnOff();
            else reactor.turnOn();
        }
    }
}
