package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

public class Alien extends AbstractActor implements Movable, Alive, Enemy {
    private Animation animation;
    private Health health;

    public Alien(String name, int initialHealth) {
        super(name);
        this.health = new Health(initialHealth);
        this.animation =
            new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP);
        setAnimation(animation);
        animation.pause();
    }

    public Alien(int initialHealth) {
        this("alien", initialHealth);
    }

    public Alien() {
        this("alien", 50);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        this.health.onExhaustion(this::die);
        scene.getActors().forEach(
            actor -> {
                if (actor instanceof Alive && !(actor instanceof Enemy)) {
                    Alive aliveActor = (Alive) actor;
                    new Loop<>(
                        new When<> (
                            () -> actor.intersects(this),
                            new Invoke<>(() -> aliveActor.getHealth().drain(1))
                        )
                    ).scheduleFor(this);
                }
            }
        );
    }

    public void die () {
        getScene().removeActor(this);
    }

    @Override
    public Health getHealth () {
        return this.health;
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
