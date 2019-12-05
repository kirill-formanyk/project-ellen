package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Ripley extends AbstractActor implements Movable, Keeper<Collectible> {
    private Backpack backpack;
    private int energy;
    private int ammo;

    private static final int MAX_AMMO = 500;

    public Ripley () {
        super("Ellen");
        this.energy = 100;
        this.ammo = 0;
        this.backpack = new Backpack("Ripley's backpack", 10);
        setAnimation(
            new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG)
        );
        getAnimation().pause();
    }

    @Override
    public Backpack getBackpack () {
        return this.backpack;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public boolean hasFullAmmunition () {
        return this.ammo == MAX_AMMO;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public boolean hasFullEnergy () {
        return energy == 100;
    }

    @Override
    public int getSpeed() {
        return 1;
    }

    @Override
    public void startedMoving (Direction direction) {
        this.getAnimation().play();
        this.getAnimation().setRotation(direction.getAngle());
    }

    @Override
    public void stoppedMoving () {
        this.getAnimation().pause();
    }

}
