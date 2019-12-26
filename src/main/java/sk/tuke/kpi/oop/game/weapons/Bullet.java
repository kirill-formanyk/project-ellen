package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Enemy;

import java.util.Objects;

public class Bullet extends AbstractActor implements Fireable {
    private Animation animation;

    public Bullet () {
        this.animation = new Animation("sprites/bullet.png", 16, 16);
        setAnimation(animation);
    }

    @Override
    public int getSpeed() {
        return 4;
    }

    @Override
    public void startedMoving(Direction direction) {
        animation.setRotation(direction.getAngle());
    }

    @Override
    public void collidedWithWall() {
        Scene scene = getScene();
        if (Objects.nonNull(scene)) {
            scene.cancelActions(this);
            scene.removeActor(this);
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new Invoke<>(this::hit)
        ).scheduleFor(this);
    }

    private void hit () {
        Scene scene = getScene();
        if (Objects.nonNull(scene)) {
            scene.getActors().forEach(
                actor -> {
                    if (actor.intersects(this) && actor instanceof Enemy) {
                        Alive aliveActor = (Alive) actor;
                        aliveActor.getHealth().drain(10);
                        getScene().removeActor(this);
                    }
                }
            );
        }
    }
}
