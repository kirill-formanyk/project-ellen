package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Enemy;

public class Flame extends AbstractActor {
    private Flame (Animation animation) {
        setAnimation(animation);
    }

    public static Flame create (Color color) {
        return new Flame(color.animation);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        scene.getActors().forEach(
            actor -> {
                if (actor instanceof Alive && !(actor instanceof Enemy)) {
                    Alive aliveActor = (Alive) actor;
                    new Loop<>(
                        new When<>(
                            () -> actor.intersects(this),
                            new Invoke<>(() -> aliveActor.getHealth().drain(2))
                        )
                    ).scheduleFor(this);
                }
            }
        );
    }

    public enum Color {
        RED (new Animation("sprites/red_flame.png", 14, 45, 0.03f, Animation.PlayMode.LOOP)),
        DARKBLUE (new Animation("sprites/dark_blue_flame.png", 14, 45, 0.03f, Animation.PlayMode.LOOP)),
        LIGHTBLUE (new Animation("sprites/light_blue_flame.png", 14, 41, 0.03f, Animation.PlayMode.LOOP)),
        GREEN (new Animation("sprites/green_flame.png", 13, 39, 0.03f, Animation.PlayMode.LOOP));

        private Animation animation;

        Color (Animation animation) {
            this.animation = animation;
        }
    }
}
