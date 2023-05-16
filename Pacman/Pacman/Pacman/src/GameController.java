import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameController  {
    GameModel model;
    GameView view;
    StartWindow startWindow = new StartWindow();

    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        StartWindow startWindow1 = startWindow;
        view.setVisible(true);
        view.gamePanel.addKeyListener(new GameKeyListener());
    }

    class GameKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_UP) {
                view.movingUp[0] = true;
            } else if (keyCode == KeyEvent.VK_DOWN) {
                view.movingDown[0] = true;
            } else if (keyCode == KeyEvent.VK_LEFT) {
                view.movingLeft[0] = true;
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                view.movingRight[0] = true;
            } else if (e.isControlDown() && e.isShiftDown() && keyCode == KeyEvent.VK_Q) {
                view.setVisible(false);
                StartWindow startWindow = new StartWindow();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_UP) {
                view.movingUp[0] = false;
            } else if (keyCode == KeyEvent.VK_DOWN) {
                view.movingDown[0] = false;
            } else if (keyCode == KeyEvent.VK_LEFT) {
                view.movingLeft[0] = false;
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                view.movingRight[0] = false;
            }
        }
    }
}