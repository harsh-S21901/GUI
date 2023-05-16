public class Main {
    public static void main(String[] args) {
        //StartWindow startWindow = new StartWindow();
//        PacmanGame game = new PacmanGame(20 , 30);
        GameModel gameModel = new GameModel(30,30);
        GameView gameView = new GameView();
        GameController gameController = new GameController(gameModel,gameView);
    }

    }
