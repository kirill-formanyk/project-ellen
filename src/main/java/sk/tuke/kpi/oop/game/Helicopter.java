package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.util.Objects;

public class Helicopter extends AbstractActor {
    private Player target;

    public Helicopter (){
        setAnimation(new Animation("sprites/heli.png", 64 , 64, 0.1f, Animation.PlayMode.LOOP));
    }

    public void searchAndDestroy(){
        if (Objects.nonNull(getScene()) && Objects.isNull(target)) {
            target = (Player) getScene().getLastActorByName("Player");
        } else {
            int playerX = target.getPosX();
            int playerY = target.getPosY();

            int newX = (playerX > getPosX()) ? getPosX() + 1 : getPosX() - 1;
            int newY = (playerY > getPosY()) ? getPosY() + 1 : getPosY() - 1;

            this.setPosition(newX, newY);

            if (this.intersects(target)) {
                int newEnergy = target.getEnergy() - 1;
                target.setEnergy(newEnergy);
            }
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::searchAndDestroy)).scheduleFor(this);
    }
}
