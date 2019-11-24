package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Ripley extends AbstractActor {
    public Ripley () {
        super("Ellen");
        setAnimation(
            new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG)
        );
        getAnimation().pause();
    }
}
