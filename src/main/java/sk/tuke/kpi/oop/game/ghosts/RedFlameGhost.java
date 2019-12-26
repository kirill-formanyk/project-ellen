package sk.tuke.kpi.oop.game.ghosts;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public class RedFlameGhost extends Ghost {
    public RedFlameGhost (Behaviour<? super Ghost> behaviour) {
        super("red flame ghost", 100, behaviour);
        setAnimation(new Animation(
            "sprites/red_flame_ghost.png", 54, 54, 0.1f, Animation.PlayMode.LOOP
        ));
    }

    @Override
    public int getSpeed () {
        return 1;
    }

    @Override
    public void setAttackAnimation() {

    }
}
