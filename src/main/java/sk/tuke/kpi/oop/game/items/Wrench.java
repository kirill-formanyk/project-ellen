package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.DefectiveLight;

import java.util.Objects;

public class Wrench extends BreakableTool<DefectiveLight> implements Collectible {
    public Wrench () {
        super(2);
        setAnimation(new Animation("sprites/wrench.png"));
    }

    @Override
    public void useWith (DefectiveLight light) {
            if (Objects.nonNull(light) && light.repair()) {
                super.useWith(light);
            }
        }

    @Override
    public Class<DefectiveLight> getUsingActorClass() {
        return DefectiveLight.class;
    }
}
