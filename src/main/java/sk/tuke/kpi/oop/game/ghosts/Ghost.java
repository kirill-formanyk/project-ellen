package sk.tuke.kpi.oop.game.ghosts;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Enemy;
import sk.tuke.kpi.oop.game.characters.Health;

import java.util.Objects;

public abstract class Ghost extends AbstractActor implements Alive, Movable, Enemy {
    private Health health;
    private Behaviour<? super Ghost> behaviour;

    public Ghost (String name, int initialHealth, Behaviour<? super Ghost> behaviour) {
        super(name);
        this.health = new Health(initialHealth);
        this.behaviour = behaviour;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if (Objects.nonNull(behaviour)) {
            behaviour.setUp(this);
        }

        this.health.onExhaustion(this::die);
        attackActorsOn(scene);


    }

    public void attackActorsOn(@NotNull Scene scene) {
        //this.setAnimation(attackAnimation);
        scene.getActors()
            .stream()
            .filter(actor -> actor instanceof Alive)
            .filter(actor -> !(actor instanceof Enemy))
            .map(actor -> (Alive) actor)
            .forEach(actor -> new Loop<>(
                new When<>(
                    () -> actor.intersects(this),
                    new Invoke<>(() -> {
                        setAttackAnimation();
                        actor.getHealth().drain(1);
                    })
                )).scheduleFor(this)
            );
    }

    public abstract void setAttackAnimation ();

    @Override
    public Health getHealth () {
        return this.health;
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

    public void die() {
        getScene().removeActor(this);
    }
}
