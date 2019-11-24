package sk.tuke.kpi.oop.game;

public enum Direction {
    NORTH (0, 1),
    EAST (1, 0),
    SOUTH (0, -1),
    WEST (-1, 0),
    NORTH_WEST (-1, 1),
    NORTH_EAST (1, 1),
    SOUTH_WEST (-1, -1),
    SOUTH_EAST (1, -1),
    NONE (0, 0);

    private final int dx;
    private final int dy;

    Direction (int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
