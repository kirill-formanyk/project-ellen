package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.scenarios.MissionImpossible;

public class Main {
    public static void main(String[] args) {
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 500);
        Game game = new GameApplication(windowSetup);

        Scene scene = new World("World", "maps/mission-impossible.tmx", new MissionImpossible.Factory());
        MissionImpossible missionImpossibleScenario = new MissionImpossible();
        scene.addListener(missionImpossibleScenario);

        game.addScene(scene);
        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);
        game.start();
    }
}
