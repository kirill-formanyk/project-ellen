package sk.tuke.kpi.oop.game.characters;

import java.util.ArrayList;
import java.util.List;

public class Health {
    private int currentValue;
    private int maxValue;
    private List<ExhaustionEffect> effects;
    private boolean alreadyCalled;

    public Health (int value, int maxHealth){
        this.currentValue = value;
        this.maxValue = maxHealth;
        this.effects = new ArrayList<>();
        this.alreadyCalled = false;
    }

    public Health (int maxValue) {
        this(maxValue, maxValue);
    }

    public int getValue() {
        return currentValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void refill (int amount) {
        if (amount < 0) return;
        this.currentValue = Math.min(currentValue + amount, maxValue);
    }

    public void restore () {
        this.currentValue = this.maxValue;
    }

    public void exhaust () {
        this.currentValue = 0;
        if (!alreadyCalled) {
            effects.forEach(ExhaustionEffect::apply);
            effects.clear();
            alreadyCalled = true;
        }
    }

    public void drain (int amount) {
        if (amount < 0) return;
        if (currentValue - amount <= 0) {
            exhaust();
            return;
        }
        this.currentValue -= amount;
    }

    public void onExhaustion (ExhaustionEffect effect) {
        effects.add(effect);
    }

    @FunctionalInterface
    public interface ExhaustionEffect {
        void apply();
    }
}
