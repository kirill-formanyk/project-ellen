package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.map.SceneMap;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.List;
import java.util.Objects;

public class Door extends AbstractActor implements Openable, Usable<Actor> {
    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);

    private boolean opened;
    private Orientation orientation;

    public Door (Orientation orientation) {
        this("door", orientation);
    }

    public Door (String name, Orientation orientation) {
        super(name);
        this.orientation = orientation;
        this.opened = false;

        Animation verticalAnimation = new Animation("sprites/vdoor.png", 16, 32, 0.1f);
        Animation horizontalAnimation = new Animation("sprites/hdoor.png", 32, 16, 0.1f);
        verticalAnimation.pause();
        horizontalAnimation.pause();

        setAnimation(this.orientation == Orientation.VERTICAL ? verticalAnimation : horizontalAnimation);
    }

    @Override
    public void open() {
        getAnimation().setPlayMode(Animation.PlayMode.ONCE);
        getAnimation().play();
        this.opened = true;
        changeTilesTypeTo(MapTile.Type.CLEAR);

        publishMessage(DOOR_OPENED);
    }

    @Override
    public void close() {
        getAnimation().setPlayMode(Animation.PlayMode.ONCE_REVERSED);
        getAnimation().play();
        this.opened = false;
        changeTilesTypeTo(MapTile.Type.WALL);

        publishMessage(DOOR_CLOSED);
    }

    private void publishMessage(Topic<Door> message) {
        getScene().getMessageBus().publish(message, this);
    }

    private void changeTilesTypeTo (MapTile.Type type) {
        getDoorTiles().forEach(
            tile -> tile.setType(type)
        );
    }

    private List<MapTile> getDoorTiles () {
        SceneMap map = Objects.requireNonNull(getScene()).getMap();
        int firstTileX = this.getPosX() / map.getTileWidth();
        int firstTileY = this.getPosY() / map.getTileHeight();

        if (this.orientation == Orientation.VERTICAL) {
            return List.of(
                map.getTile(firstTileX, firstTileY),
                map.getTile(firstTileX, firstTileY + 1)
            );
        } else {
            return List.of(
                map.getTile(firstTileX, firstTileY),
                map.getTile(firstTileX + 1, firstTileY)
            );
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        changeTilesTypeTo(MapTile.Type.WALL);
    }

    @Override
    public boolean isOpen() {
        return opened;
    }

    @Override
    public void useWith(Actor actor) {
        if (opened) {
            close();
        } else {
            open();
        }
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }

    public enum Orientation {
        VERTICAL,
        HORIZONTAL
    }
}
