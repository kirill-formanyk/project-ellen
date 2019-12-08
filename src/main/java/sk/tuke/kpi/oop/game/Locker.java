package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.items.Usable;

public class Locker extends AbstractActor implements Usable<Ripley> {
    private boolean opened;

    public Locker(){
        setAnimation(new Animation("sprites/locker.png"));
        this.opened = false;
    }

    @Override
    public void useWith(Ripley actor) {
        if (!opened) {
            getScene().addActor(new Hammer(), this.getPosX() + 16, this.getPosY() + 16);
            this.opened = true;
        }
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }
}
