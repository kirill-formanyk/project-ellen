package sk.tuke.kpi.oop.game;

import com.badlogic.gdx.Gdx;
import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;
import sk.tuke.kpi.oop.game.tools.FireExtinguisher;
import sk.tuke.kpi.oop.game.tools.Hammer;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable {
    private int temperature;
    private int damage; //повреждение
    private Animation normalAnimation;
    private Animation hotAnimation; // перегретый реактор
    private Animation brokenAnimation; // сломанный реактор
    private Animation animationOff;
    private Animation animationExtinguished;

    private boolean isOn; // true - on, false - off
    private Set<EnergyConsumer> devices;

    public Reactor() {
        this.temperature = 0;
        this.damage = 0;
        this.isOn = false;
        this.devices = new HashSet<>();

        this.normalAnimation =
            new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.hotAnimation =
            new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        this.brokenAnimation =
            new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.animationOff = new Animation("sprites/reactor.png");
        this.animationExtinguished = new Animation("sprites/reactor_extinguished.png");

        setAnimation(animationOff);
    }

    public int getTemperature() {
        return temperature;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        PerpetualReactorHeating heatingAction = new PerpetualReactorHeating(1);
        heatingAction.scheduleFor(this);
    }

    public void extinguishWith (FireExtinguisher extinguisher){
        if (isBroken() && Objects.nonNull(extinguisher)) {
            extinguisher.use();
            temperature = 4000;
            setAnimation(animationExtinguished);
        }
    }

    public void addDevice (EnergyConsumer device) {
        devices.add(device);
        if (isOn() && !isDamaged()){
            device.setPowered(true);
        }
    }

    public void removeLight(EnergyConsumer device){
        devices.remove(device);
        device.setPowered(false);
    }

    @Override
    public void turnOn() {
        this.isOn = true;
        devices.forEach(
            device -> {
                if (Objects.nonNull(device)) device.setPowered(true);
            }
        );
        updateAnimation();
    }

    @Override
    public void turnOff () {
        this.isOn = false;
        devices.forEach(
            device -> {
                if (Objects.nonNull(device)) device.setPowered(false);
            }
        );
        updateAnimation();
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    public void increaseTemperature (int increment) {
        if (increment < 0 || isBroken() || !isOn()) return;

        if (damage >= 33 && damage < 66) {
            temperature += Math.round(1.5 * increment);
        } else if (damage >= 66){
            temperature += 2 * increment;
        } else {
            temperature += increment;
        }

        calculateDamage(temperature - 2000);

        if (isBroken()) {
            turnOff();
        }

        updateAnimation();

    }

    public void decreaseTemperature (int decrement){
        if (decrement < 0 || isBroken() || !isOn()) return;

        if(damage >= 50){
            temperature = Math.max((temperature - (int) Math.round(decrement * 0.5)), 0);
        } else {
            temperature = Math.max((temperature - decrement), 0);
        }

        updateAnimation();
    }

    private void updateAnimation() {
        if (!isOn() && !isBroken()) {
            setAnimation(animationOff);
        } else {
            float newFrameDuration;

            if (!isOverheated() && !isBroken()) {
                newFrameDuration = (float) (0.15f - (damage * 0.001));
                setAnimation(normalAnimation);

                final float finalFrameDuration = newFrameDuration;
                Gdx.app.postRunnable(() -> normalAnimation.setFrameDuration(finalFrameDuration));
            }

            if (isOverheated() && !isBroken()) {
                newFrameDuration = (float) (0.12f - (damage * 0.001));
                setAnimation(hotAnimation);

                final float finalFrameDuration = newFrameDuration;
                Gdx.app.postRunnable(() -> hotAnimation.setFrameDuration(finalFrameDuration));
            }

            if (isBroken()) {
                setAnimation(brokenAnimation);
            }
        }
    }

    public void repairWith (Hammer hammer) {
        if (Objects.isNull(hammer) || !isDamaged() || isBroken()) return;
        hammer.use();

        int difference = damage - 50;
        int recalculatedTemperature = difference * 40 + 2000;

        damage = Math.max(difference, 0);
        temperature = Math.min(recalculatedTemperature, temperature);
        updateAnimation();

    }

    private boolean isOverheated() {
        return temperature >= 4000 && temperature < 6000;
    }

    private boolean isDamaged () {
        return damage > 0;
    }

    private boolean isBroken() {
        return damage == 100;
    }

    private void calculateDamage (int overheat) {
        if (overheat > 0) {
            int newDamage = (overheat <= 4000) ? (int) Math.round(overheat * 0.025) : 100;
            damage = Math.max(newDamage, damage);
        }
    }
}
