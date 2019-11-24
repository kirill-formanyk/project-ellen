package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;

public class FireExtinguisher <Q extends Actor> extends BreakableTool <Q> {
    public FireExtinguisher (){
        super(1);
        setAnimation(new Animation("sprites/extinguisher.png"));
    }

    @Override
    public void useWith (Q actor) {
        if (actor instanceof Reactor) {
            Reactor reactor = (Reactor) actor;
            if (reactor.extinguish()) {
                super.useWith(actor);
            }
        }
    }
}
