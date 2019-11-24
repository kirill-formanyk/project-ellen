package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class TimeBomb extends AbstractActor {
    private float timer;
    private boolean isActivated;
    private Animation activatedAnimation;
    private Animation detonationAnimation;

    public TimeBomb (float seconds) {
        this.timer = seconds;
        this.isActivated = false;

        setAnimation(new Animation("sprites/bomb.png"));
        this.activatedAnimation =
            new Animation("sprites/bomb_activated.png", 16, 16, 0.1f, Animation.PlayMode.LOOP);

        this.detonationAnimation =
            new Animation("sprites/small_explosion.png", 16, 16, 0.1f, Animation.PlayMode.ONCE);
    }

    public void activate () {
        this.isActivated = true;
        setAnimation(activatedAnimation);

        new ActionSequence<>(
            new Wait<>(timer),
            new Invoke<>(
                () -> {
                    this.isActivated = false;
                    setAnimation(detonationAnimation);
                }
            ),
            new When<> (
                () -> detonationAnimation.getCurrentFrameIndex() == detonationAnimation.getFrameCount() - 1,
                new Invoke<>(this::removeBombFromScene)
            )
        ).scheduleFor(this);
    }

    public boolean isActivated () {
        return isActivated;
    }

    private void removeBombFromScene () {
        getScene().removeActor(this);
    }
}
