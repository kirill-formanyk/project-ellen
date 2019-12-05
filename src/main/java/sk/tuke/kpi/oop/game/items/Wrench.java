package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.DefectiveLight;

public class Wrench <Q extends Actor> extends BreakableTool<Q> implements Collectible {
    public Wrench () {
        super(2);
        setAnimation(new Animation("sprites/wrench.png"));
    }

    @Override
    public void useWith (Q actor) {
        if (actor instanceof DefectiveLight) {
            DefectiveLight light = (DefectiveLight) actor;
            if (light.repair()) {
                super.useWith(actor);
            }
        }
    }
}
