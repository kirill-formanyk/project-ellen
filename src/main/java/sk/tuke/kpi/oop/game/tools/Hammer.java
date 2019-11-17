package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;

public class Hammer extends BreakableTool {
    public Hammer (int remainingUses) {
        super (remainingUses);
        setAnimation(new Animation("sprites/hammer.png"));
    }

    public Hammer (){
        this(1);
    }
}
