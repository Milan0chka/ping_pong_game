
import static org.junit.Assert.*;

import com.example.ping_pong.controller.LabController;
import org.junit.Before;
import org.junit.Test;
import com.example.ping_pong.model.Game;
import com.example.ping_pong.controller.BallManager;
import com.example.ping_pong.view.LabCanvas;
public class BallManagerTest {
    LabCanvas canvas = new LabCanvas(600, 600);
    LabController labController = new LabController(canvas);
    Game game = labController.getGame();
    BallManager manager = new BallManager(game, canvas, labController);

    @Before
    public void initialise()
    {
        game.setWidth(650);
        game.setHeigh(550);
        game.setScoreLimit(10);
    }

    @Test
    public void testIsVerticalCollision(){
        game.getBall().setPositionY(0);
        assertTrue(manager.isVerticalCollision());

        game.getBall().setPositionY(game.getHeigh());
        assertTrue(manager.isVerticalCollision());

        game.getBall().setPositionY(200);
        assertFalse(manager.isVerticalCollision());
    }

    @Test
    public void testIsRacketCollisionWhenBallMovesLeft(){
        game.getBall().setPositionX(game.getPlayer1().getRacket().getPositionX() + game.getBall().getRadius());
        game.getBall().setPositionY(game.getPlayer1().getRacket().getPositionY() + game.getBall().getRadius());
        game.getBall().setDirectionX(-1);

        assertTrue(manager.isRacketCollision(game.getPlayer1().getRacket(), game.getBall()));
    }

    @Test
    public void testIsRacketCollisionWhenBallMovesRight(){
        game.getBall().setPositionX(game.getPlayer2().getRacket().getPositionX());
        game.getBall().setPositionY(game.getPlayer2().getRacket().getPositionY() + game.getBall().getRadius());
        game.getBall().setDirectionX(1);

        assertTrue(manager.isRacketCollision(game.getPlayer2().getRacket(), game.getBall()));
    }

    @Test
    public void testIsRacketCollisionWhenBallInCenter(){
        game.getBall().setPositionX(200);
        game.getBall().setPositionY(200);

        game.getBall().setDirectionX(-1);
        assertFalse(manager.isRacketCollision(game.getPlayer1().getRacket(), game.getBall()));

        game.getBall().setDirectionX(1);
        assertFalse(manager.isRacketCollision(game.getPlayer2().getRacket(), game.getBall()));
    }
    @Test
    public void testEndOfGame() {
        game.getPlayer1().setScore(7);
        game.getPlayer2().setScore(11);
// score is 11-7 up to 10
        assertTrue(manager.isGameEnded());
// score is 7-7 up to 10
        game.getPlayer2().setScore(7);
        assertFalse(manager.isGameEnded());
    }
    @Test
    public void testHasPlayer2Scored(){
        game.getBall().setPositionX(5);
        assertTrue(manager.hasPlayer2Scored(game.getBall()));
        game.getBall().setPositionX(200);
        assertFalse(manager.hasPlayer2Scored(game.getBall()));
    }

    @Test
    public void testHasPlayer1Scored(){
        game.getBall().setPositionX(game.getWidth());
        assertTrue(manager.hasPlayer1Scored(game.getBall()));
        game.getBall().setPositionX(200);
        assertFalse(manager.hasPlayer1Scored(game.getBall()));
    }
}
