package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.Objects;

public class Energy extends AbstractActor implements Usable<Ripley> {
    public Energy () {
        setAnimation(new Animation("sprites/energy.png"));
    }

    @Override
    public void useWith(Ripley actor) {
        if (Objects.nonNull(actor) && actor.getHealth().getValue() != actor.getHealth().getMaxValue()) {
            actor.getHealth().restore();
            getScene().removeActor(this);
        }
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }
}
