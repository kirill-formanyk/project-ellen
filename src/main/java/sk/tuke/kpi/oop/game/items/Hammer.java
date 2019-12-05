package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;

public class Hammer <Q extends Actor> extends BreakableTool <Q> implements Collectible {
    public Hammer (int remainingUses) {
        super (remainingUses);
        setAnimation(new Animation("sprites/hammer.png"));
    }

    public Hammer (){
        this(1);
    }

    @Override
    public void useWith (Q actor) {
        if (actor instanceof Reactor) {
            Reactor reactor = (Reactor) actor;
            if (reactor.repair()) {
                super.useWith(actor);
            }
        }
    }
}
