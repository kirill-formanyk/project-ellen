package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public class SpitterAlien extends Alien {
    public SpitterAlien(Behaviour<? super Alien> behaviour) {
        super("spitter alien", 50, behaviour);
        setAnimation(new Animation("sprites/spitter_alien.png", 32, 32, 0.07f, Animation.PlayMode.LOOP));
        getAnimation().pause();
    }

    @Override
    public int getSpeed () {
        return 2;
    }
}
