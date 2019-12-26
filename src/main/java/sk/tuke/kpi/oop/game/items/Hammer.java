package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Repairable;

import java.util.Objects;

public class Hammer extends BreakableTool <Repairable> implements Collectible {
    public Hammer (int remainingUses) {
        super (remainingUses);
        setAnimation(new Animation("sprites/hammer.png"));
    }

    public Hammer (){
        this(1);
    }

    @Override
    public void useWith (Repairable actor) {
        if (Objects.nonNull(actor) && actor.repair()) {
            super.useWith(actor);
        }
    }

    @Override
    public Class<Repairable> getUsingActorClass() {
        return Repairable.class;
    }
}
