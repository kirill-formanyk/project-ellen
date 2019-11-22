package sk.tuke.kpi.oop.game;

import java.util.Objects;

public class SmartCooler extends Cooler {
    public SmartCooler(Reactor reactor) {
        super(reactor);
    }

    @Override
    protected void coolReactor() {
        Reactor reactor = super.getReactor();

        if (Objects.isNull(reactor) || !reactor.isOn()) return;

        if (reactor.getTemperature() >= 2500) {
            turnOn();
        }

        if (reactor.getTemperature() <= 1500) {
            turnOff();
        }

        if (isOn()) {
            reactor.decreaseTemperature(1);
        }
    }
}
