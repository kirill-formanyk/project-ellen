package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.gamelib.map.MapMarker;
import sk.tuke.kpi.oop.game.Cooler;
import sk.tuke.kpi.oop.game.Reactor;
import sk.tuke.kpi.oop.game.tools.Hammer;

import java.util.Map;

public class TrainingGameplay extends Scenario {
    @Override
    public void setupPlay(@NotNull Scene scene) {
        Map <String, MapMarker> markers = scene.getMap().getMarkers();

        Reactor reactor = new Reactor();
        MapMarker reactorMarker1 = markers.get("reactor-area-1");
        scene.addActor(reactor, reactorMarker1.getPosX(), reactorMarker1.getPosY());

        Cooler cooler = new Cooler(reactor);
        MapMarker coolerMarker1 = markers.get("cooler-area-1");
        scene.addActor(cooler, coolerMarker1.getPosX(), coolerMarker1.getPosY());

        new ActionSequence<>(
            new Wait<>(5),
            new Invoke<>(cooler::turnOn)
        ).scheduleFor(cooler);

        Hammer hammer = new Hammer();
        scene.addActor(hammer, 50, -40);
    }
}
