package sk.tuke.kpi.oop.game.openables;

import sk.tuke.kpi.gamelib.Actor;

public class LockedDoor extends Door {
    private boolean locked;

    public LockedDoor () {
        super();
        this.locked = true;
    }

    public void lock () {
        this.locked = true;
        close();
    }

    public void unlock () {
        this.locked = false;
        open();
    }

    @Override
    public void useWith (Actor actor) {
        if (!locked) {
            super.useWith(actor);
        }
    }

    public boolean isLocked () {
        return this.locked;
    }
}
