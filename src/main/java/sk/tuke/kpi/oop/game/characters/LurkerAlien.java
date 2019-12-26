package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public class LurkerAlien extends Alien {
    public LurkerAlien(Behaviour<? super Alien> behaviour) {
        super("lurker alien", 75, behaviour);
        setAnimation(new Animation("sprites/lurker_alien.png", 32, 32, 0.05f, Animation.PlayMode.LOOP));
        getAnimation().pause();
    }

    @Override
    public int getSpeed () {
        return 3;
    }
}
