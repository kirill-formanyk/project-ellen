package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.scenarios.FirstSteps;

public class Main {
    public static void main(String[] args) {
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 500, 700);
        Game game = new GameApplication(windowSetup);

        Scene scene = new World("World");
        FirstSteps firstStepsScenario = new FirstSteps();
        scene.addListener(firstStepsScenario);

        game.addScene(scene);
        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);
        game.start();
    }
}
