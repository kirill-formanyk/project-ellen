package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.Objects;

public class Mine extends AbstractActor {
    private float timer;
    private boolean activated;
    private Animation activatedAnimation;
    private Animation explosionAnimation;

    public Mine (float timer){
        this.timer = timer;
        activated = false;
        this.activatedAnimation =
            new Animation("sprites/mine_activated.png", 15,14,0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.explosionAnimation =
            new Animation("sprites/large_explosion.png", 32, 32, 0.1f, Animation.PlayMode.ONCE);
        setAnimation(new Animation("sprites/mine_deactivated.png"));
    }

    public Mine (){
        this(3);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        scene.getActors().forEach(
            actor -> {
                if (actor instanceof Alive) {
                    new When<>(
                        () -> actor.intersects(this),
                        new Invoke<>(this::activate)
                    ).scheduleFor(this);
                }
            }
        );
    }

    public void activate () {
        if (activated) return;
        this.activated = true;
        setAnimation(activatedAnimation);

        new ActionSequence<>(
            new Wait<>(timer),
            new Invoke<>(() -> {
                this.activated = false;
                setAnimation(explosionAnimation);
                drainPlayerHealthIfIntersects();
            }),
            new When<>(
                () -> explosionAnimation.getCurrentFrameIndex() == explosionAnimation.getFrameCount() - 1,
                new Invoke<>(() -> getScene().removeActor(this))
            )
        ).scheduleFor(this);
    }

    private void drainPlayerHealthIfIntersects () {
        Ripley ellen = getScene().getFirstActorByType(Ripley.class);
        if (Objects.nonNull(ellen) && ellen.intersects(this)) {
            ellen.getHealth().drain(25);
        }
    }
}
