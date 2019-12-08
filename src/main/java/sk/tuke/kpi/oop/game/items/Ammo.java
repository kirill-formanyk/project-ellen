package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.Objects;

public class Ammo extends AbstractActor implements Usable<Ripley> {
    public Ammo () {
        setAnimation(new Animation("sprites/ammo.png"));
    }

    @Override
    public void useWith(Ripley actor) {
        if (Objects.nonNull(actor) && !actor.hasFullAmmunition()) {
            actor.setAmmo(Math.min(actor.getAmmo() + 50, 500));
            getScene().removeActor(this);
        }
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }
}
