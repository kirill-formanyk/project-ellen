package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class FireExtinguisher extends AbstractActor {
    private int remainingUses;

    public FireExtinguisher (){
        this.remainingUses = 1;
        setAnimation(new Animation("sprites/extinguisher.png"));
    }

    public int getRemainingUses() {
        return remainingUses;
    }

    public void use (){
        if (remainingUses > 0) remainingUses--;
        if (remainingUses == 0) this.getScene().removeActor(this);
    }
}
