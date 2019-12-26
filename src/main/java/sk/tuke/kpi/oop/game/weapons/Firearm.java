package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm {
    private int currentValue;
    private int maxAmmo;

    public Firearm(int initialValue, int maxAmmo) {
        this.currentValue = initialValue;
        this.maxAmmo = maxAmmo;
    }

    public Firearm(int initialValue) {
        this(initialValue, initialValue);
    }

    public int getValue() {
        return currentValue;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public void reload (int amount) {
        this.currentValue = Math.min(currentValue + amount, maxAmmo);
    }

    public Fireable fire () {
        if (this.currentValue == 0) return null;
        this.currentValue--;
        return createBullet();
    }

    public abstract Fireable createBullet ();
}
