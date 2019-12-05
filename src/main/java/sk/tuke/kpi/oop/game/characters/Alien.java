package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

public class Alien extends AbstractActor implements Movable {
    public Alien() {
        super("Alien");
        setAnimation(new Animation("sprites/alien.png"));
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
