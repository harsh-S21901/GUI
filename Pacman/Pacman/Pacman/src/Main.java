public class Main {
    public static void main(String[] args) {
        GameModel gameModel = new GameModel(30,30);
        GameView gameView = new GameView();
        GameController gameController = new GameController(gameModel,gameView);
    }

    }
