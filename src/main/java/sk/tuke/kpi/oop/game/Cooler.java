package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.util.Objects;

public class Cooler extends AbstractActor implements Switchable {
    private boolean isOn;
    private Animation animation;
    private Reactor reactor;

    public Cooler (Reactor reactor){
        this.reactor = reactor;
        this.animation = new Animation("sprites/fan.png", 32, 32, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(this.animation);
        this.animation.pause();
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    @Override
    public void turnOn () {
        if (!isOn()) {
            this.isOn = true;
            this.animation.play();
        }
    }

    @Override
    public void turnOff (){
        if(isOn()){
            this.isOn = false;
            this.animation.pause();
        }
    }

    public Reactor getReactor() {
        return reactor;
    }

    protected void coolReactor (){
        if (Objects.nonNull(reactor) && isOn()) {
            reactor.decreaseTemperature(1);
        }
    }


    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }
}
