package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

public class Ripley extends AbstractActor implements Movable {

    public Ripley () {
        super("Ellen");
        setAnimation(
            new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG)
        );
        getAnimation().pause();
    }

    @Override
    public int getSpeed() {
        return 1;
    }

    @Override
    public void startedMoving (Direction direction) {
        this.getAnimation().play();
        this.getAnimation().setRotation(direction.getAngle());
    }

    @Override
    public void stoppedMoving () {
        this.getAnimation().pause();
    }

}
