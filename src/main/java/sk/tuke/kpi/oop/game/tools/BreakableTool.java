package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.framework.AbstractActor;

public abstract class BreakableTool extends AbstractActor {
    private int remainingUses;

    protected BreakableTool (int remainingUses) {
        this.remainingUses = remainingUses;
    }

    public int getRemainingUses() {
        return remainingUses;
    }

    public void use () {
        if (remainingUses > 0) remainingUses--;
        if (remainingUses == 0) this.getScene().removeActor(this);
    }
}
