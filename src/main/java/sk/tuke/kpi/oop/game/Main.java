package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.scenarios.EscapeRoom;

public class Main {
    public static void main(String[] args) {
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 500);
        Game game = new GameApplication(windowSetup);

        Scene scene = new World("World", "maps/escape-room/escape-room.tmx", new EscapeRoom.Factory());
        EscapeRoom escapeRoomScenario = new EscapeRoom();
        scene.addListener(escapeRoomScenario);

        game.addScene(scene);
        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);
        game.start();
    }
}
