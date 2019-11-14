package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Hammer extends AbstractActor {
    private int remainingUses;

    public Hammer (){
        this(1);
    }

    public Hammer (int remainingUses) {
        this.remainingUses = remainingUses;
        setAnimation(new Animation("sprites/hammer.png"));
    }

    public void use (){
        if (remainingUses > 0) remainingUses--;
        if (remainingUses == 0) this.getScene().removeActor(this);
    }

}
