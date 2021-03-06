package sk.tuke.kpi.oop.game;

import java.util.Objects;

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

    public static Direction fromAngle(float angle) {
        int angleAsInt = (int) angle;
        switch (angleAsInt) {
            case 0: return NORTH;
            case 45: return NORTH_WEST;
            case 90: return WEST;
            case 135: return SOUTH_WEST;
            case 180: return SOUTH;
            case 225: return SOUTH_EAST;
            case 270: return EAST;
            case 315: return NORTH_EAST;
            default: return NONE;
        }
    }

    public static Direction fromCoordinates(int dx, int dy) {
        for (Direction direction : values()) {
            if (direction.dx == dx && direction.dy == dy) {
                return direction;
            }
        }

        return NONE;
    }

    public float getAngle () {
        switch (this) {
            case NONE : return -1;
            case NORTH_EAST: return 315;
            case EAST: return 270;
            case SOUTH_EAST: return 225;
            case SOUTH: return 180;
            case SOUTH_WEST: return 135;
            case WEST: return 90;
            case NORTH_WEST: return 45;
            case NORTH: return 0;
        }
        return -1;
    }

    public Direction combine (Direction other) {
        if (this.equals(other) || Objects.isNull(other)) return this;

        int combinedX = updateCoordinate(this.dx + other.dx);
        int combinedY = updateCoordinate(this.dy + other.dy);

        Direction combined = Direction.NONE;
        for (Direction direction : values()) {
            if (direction.dx == combinedX && direction.dy == combinedY) {
                combined = direction;
            }
        }
        return combined;
    }

    private int updateCoordinate(int coordinate) {
        if (coordinate >= 1) return 1;
        if (coordinate <= -1) return -1;
        return 0;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
