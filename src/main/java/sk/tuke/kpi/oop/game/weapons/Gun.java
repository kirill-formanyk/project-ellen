package sk.tuke.kpi.oop.game.weapons;

public class Gun extends Firearm {
    public Gun (int currentValue, int maxAmmo){
        super(currentValue, maxAmmo);
    }

    public Gun (int maxAmmo){
        this(maxAmmo, maxAmmo);
    }

    @Override
    public Fireable createBullet() {
        return new Bullet();
    }
}
