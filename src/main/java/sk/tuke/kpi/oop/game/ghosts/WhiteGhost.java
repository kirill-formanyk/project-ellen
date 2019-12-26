package sk.tuke.kpi.oop.game.ghosts;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Enemy;

public class WhiteGhost extends Ghost {
    protected Animation normalAnimation;
    protected Animation attackAnimation;

    public WhiteGhost (Behaviour<? super Ghost> behaviour) {
        super("white ghost", 60, behaviour);
        this.normalAnimation = new Animation(
            "sprites/white_ghost.png", 50, 50, 0.1f, Animation.PlayMode.LOOP
        );
        this.attackAnimation = new Animation(
            "sprites/white_ghost_attack.png", 50, 50, 0.1f, Animation.PlayMode.LOOP
        );
        setAnimation(normalAnimation);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        scene.getActors()
            .stream()
            .filter(actor -> actor instanceof Alive)
            .filter(actor -> !(actor instanceof Enemy))
            .map(actor -> (Alive) actor)
            .forEach(actor -> new Loop<>(
                new When<>(
                    () -> !actor.intersects(this),
                    new Invoke<>(() -> setAnimation(normalAnimation))
                )).scheduleFor(this)
            );
    }

    @Override
    public void attackActorsOn (@NotNull Scene scene) {
        super.attackActorsOn(scene);
    }

    @Override
    public void setAttackAnimation () {
        setAnimation(attackAnimation);
    }

    @Override
    public int getSpeed() {
        return 1;
    }
}
