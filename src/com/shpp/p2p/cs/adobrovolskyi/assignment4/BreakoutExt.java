package com.shpp.p2p.cs.adobrovolskyi.assignment4;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * . Artem Dobrovolskyi @2018
 */
public class BreakoutExt extends WindowProgram {
    /**
     * Width and height of application window in pixels
     */
    public static final int APPLICATION_WIDTH = 700;
    public static final int APPLICATION_HEIGHT = 600;

    /**
     * Dimensions of game board (usually the same)
     */
    private static final int WIDTH = APPLICATION_WIDTH;
    //   private static final int HEIGHT = APPLICATION_HEIGHT;

    /**
     * Dimensions of the paddle
     */
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 10;

    /**
     * Offset of the paddle up from the bottom
     */
    private static final int PADDLE_Y_OFFSET = 30;

    /**
     * Number of bricks per row
     */
    private static final int NBRICKS_PER_ROW = 10;

    /**
     * Number of rows of bricks
     */
    private static final int NBRICK_ROWS = 10;

    /**
     * Separation between bricks
     */
    private static final int BRICK_SEP = 4;

    /**
     * Width of a brick
     */
    private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

    /**
     * Height of a brick
     */
    private static final int BRICK_HEIGHT = 8;

    /**
     * Radius of the ball in pixels
     */
    private static final int BALL_RADIUS = 10;

    /**
     * Offset of the top brick row from the top
     */
    private static final int BRICK_Y_OFFSET = 70;

    /**
     * Number of turns
     */
    private static final int NTURNS = 3;

    /*Write the name of the game*/
    private GLabel gameName;
    /*Add information panel to the game world*/
    private GRect gameInfo;
    /*Add a paddle to the game world*/
    private GRect paddle;
    /*Add a ball to the game world*/
    private GOval ball;
    /*Add score to the game world*/
    private GLabel score;
    /*Add lives to the game world*/
    private GLabel lives;
    /*Set the direction of the ball*/
    private double vx, vy;
    /*Set the ball speed*/
    private int ballSpeed = 10;
    /*Set the bricks counter*/
    private int bricksCounter = 100;
    /*Set the score counter*/
    private int scoreCounter = 0;
    /*Set the lives counter*/
    private int livesCounter = 3;
    /*Set the touches counter*/
    private int touchCounter = 0;

    /**
     * Runs the Breakout program.
     */
    public void run() {
        while (true) {
            try {
                addGameName();
                addGameWorld();
                for (int i = 0; i < NTURNS; i++) {
                    setLastLiveCounterNewColor();
                    gameStart();
                    if (bricksCounter == 0) {
                        ball.setVisible(false);
                        printWin();
                        waitForClick();
                        removeAll();
                        break;
                    }
                }
                if (bricksCounter > 0) {
                    removeAll();
                    printGameOver();
                    waitForClick();
                    removeAll();
                    setGameCounters();
                } else {
                    setGameCounters();
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

    }

    /**
     * Set game values to the initial values
     */
    private void setGameCounters() {
        livesCounter = 3;
        bricksCounter = 100;
        scoreCounter = 0;
    }

    /**
     * Run the game
     */
    private void gameStart() {
        addBall();
        waitForClick();
        remove(gameName);
        ballDirection();
        ballSpeed = 10;
        touchCounter = 0;
        while (true) {
            setNewBallSpeed();
            ballMove();
            if (ball.getY() >= getHeight()) {
                livesCounter--;
                break;
            }
            if (bricksCounter == 0) {
                break;
            }
            setScoreCounterNewColor();
            setLastLiveCounterNewColor();
        }
        removeGameInfo();
        addGameInfo();
    }

    /**
     * Add all objects to the game world
     */
    private void addGameWorld() {
        addPaddle();
        addBricks();
        addGameInfo();
    }

    /**
     * Add all information to the game world
     */
    private void addGameInfo() {
        addScore();
        addLives();
        addInfoPanel();
    }

    /**
     * Remove all information from repaint
     */
    private void removeGameInfo() {
        remove(score);
        remove(lives);
        remove(gameInfo);
    }

    /**
     * Write the name of the game
     */
    private void addGameName() {
        gameName = new GLabel(">>BREAKOUT<<");
        gameName.setFont("TimesNewRoman-50");
        gameName.setColor(Color.MAGENTA);
        gameName.setLocation(getWidth() / 2.0 - gameName.getWidth() / 2.0, gameName.getAscent());
        add(gameName);
    }

    /**
     * Write the number of score (points)
     */
    private void addScore() {
        score = new GLabel("Score: " + scoreCounter);
        score.setFont("TimesNewRoman-20");
        score.setColor(Color.RED);
        score.setLocation(0, score.getAscent());
        add(score);
    }

    /**
     * Add lives counter to the game world
     */
    private void addLives() {
        lives = new GLabel("Lives: " + livesCounter);
        lives.setFont("TimesNewRoman-20");
        lives.setColor(Color.BLUE);
        lives.setLocation(0, score.getAscent() * 2);
        add(lives);
    }

    /**
     * Add information panel to the game world
     */
    private void addInfoPanel() {
        gameInfo = new GRect(0, 0, score.getWidth() + score.getAscent(), score.getAscent() * 2.5);
        gameInfo.setColor(Color.WHITE);
        add(gameInfo);
    }

    /**
     * Set new color to lives Counter
     */
    private void setLastLiveCounterNewColor() {
        if (livesCounter == 1) {
            lives.setColor(Color.RED);
        }
    }

    /**
     * Set new color to score Counter
     */
    private void setScoreCounterNewColor() {
        if (scoreCounter >= 25 && scoreCounter < 50) {
            score.setColor(Color.ORANGE);
        }
        if (scoreCounter >= 50 && scoreCounter < 75) {
            score.setColor(Color.CYAN);
        }
        if (scoreCounter >= 75) {
            score.setColor(Color.GREEN);
        }
    }

    /**
     * Set new speed of the ball
     */
    private void setNewBallSpeed() {
        if (touchCounter == 5) {
            ballSpeed = 7;
        }
        if (touchCounter >= 50) {
            ballSpeed = 5;
        }
    }

    /**
     * Write "<> You Win!!! <>" when user win
     */
    private void printWin() {
        GLabel Win = new GLabel("<> You Win!!! <>");
        Win.setFont("TimesNewRoman-80");
        Win.setLocation(getWidth() / 2.0 - Win.getWidth() / 2.0, getHeight() / 2.0 - Win.getAscent());
        Win.setColor(Color.RED);
        add(Win);
    }

    /**
     * Write ">< Game Over ><" when user lose
     */
    private void printGameOver() {
        GLabel gameOver = new GLabel(">< Game Over ><");
        gameOver.setFont("TimesNewRoman-80");
        gameOver.setLocation(getWidth() / 2.0 - gameOver.getWidth() / 2.0, getHeight() / 2.0 - gameOver.getAscent());
        gameOver.setColor(Color.RED);
        add(gameOver);
    }


    /**
     * Add a paddle to the game world
     */
    private void addPaddle() {
        double xCenter = getWidth() / 2.0 - PADDLE_WIDTH / 2.0;
        double y = getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT;
        paddle = new GRect(xCenter, y, PADDLE_WIDTH, PADDLE_HEIGHT);
        paddle.setFilled(true);
        add(paddle);
        addMouseListeners();
    }

    /**
     * Add a ball to the game world
     */
    private void addBall() {
        double xStartPoint = getWidth() / 2.0 - BALL_RADIUS;
        double yStartPoint = getHeight() / 2.0 - BALL_RADIUS;
        ball = new GOval(xStartPoint, yStartPoint, BALL_RADIUS * 2, BALL_RADIUS * 2);
        ball.setFilled(true);
        add(ball);
    }

    /**
     * Set the position of the paddle relative to the mouse cursor
     *
     * @param mouseEvent Mouse coordinates
     */
    public void mouseMoved(MouseEvent mouseEvent) {
        if ((mouseEvent.getX() < (getWidth() - (PADDLE_WIDTH / 2.0))) && (mouseEvent.getX() > (PADDLE_WIDTH / 2.0))) {
            paddle.setLocation(mouseEvent.getX() - PADDLE_WIDTH / 2.0,
                    getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
        }
    }

    /**
     * Set the first direction of the ball
     */
    private void ballDirection() {
        vy = 5.0;
        RandomGenerator rgen = RandomGenerator.getInstance();
        vx = rgen.nextDouble(1.0, 3.0);
        if (rgen.nextBoolean(0.5)) {
            vx = -vx;
        }
    }

    /**
     * Set the direction of movement of the ball after contact with objects or walls
     */
    private void ballMove() {
        ball.move(vx, vy);

        /* check for walls */
        if ((ball.getX() - vx <= 0 && vx < 0) || (ball.getX() + vx >= (getWidth() - BALL_RADIUS * 2) && vx > 0)) {
            vx = -vx;
        }
        if ((ball.getY() - vy <= 0 && vy < 0)) {
            vy = -vy;
        }

        GObject getTouch = getCollidingObject();

        if (getTouch == paddle) {                         /* check for paddle */
            vy = -vy;
        } else if (getTouch == gameInfo) {                /* check for information panel */
            if (ball.getY() >= gameInfo.getHeight()) {
                vy = -vy;
            }
            if (ball.getX() >= gameInfo.getWidth()) {
                vx = -vx;
            }
        } else if (getTouch != null) {                    /* check for brick */
            remove(getTouch);
            bricksCounter--;
            scoreCounter++;
            touchCounter++;
            vy = -vy;
            removeGameInfo();
            addGameInfo();
        }
        pause(ballSpeed);
    }

    /**
     * Set the contact points of the ball with objects
     *
     * @return Return the contact points of the ball with objects
     */
    private GObject getCollidingObject() {
        if ((getElementAt(ball.getX(), ball.getY())) != null) {
            return getElementAt(ball.getX(), ball.getY());
        } else if (getElementAt((ball.getX() + BALL_RADIUS * 2), ball.getY()) != null) {
            return getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY());
        } else if (getElementAt(ball.getX(), (ball.getY() + BALL_RADIUS * 2)) != null) {
            return getElementAt(ball.getX(), ball.getY() + BALL_RADIUS * 2);
        } else if (getElementAt((ball.getX() + BALL_RADIUS * 2), (ball.getY() + BALL_RADIUS * 2)) != null) {
            return getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY() + BALL_RADIUS * 2);
        } else {
            return null;
        }
    }

    /**
     * Add a grid of bricks to the game world
     */
    private void addBricks() {
        for (int row = 0; row < NBRICK_ROWS; row++) {
            for (int col = 0; col < NBRICKS_PER_ROW; col++) {
                double xCenter = getWidth() / 2.0 - (NBRICKS_PER_ROW * BRICK_WIDTH) / 2.0 - ((NBRICKS_PER_ROW - 1) * BRICK_SEP) / 2.0 + col * BRICK_WIDTH + col * BRICK_SEP;
                double y = BRICK_Y_OFFSET + row * BRICK_HEIGHT + row * BRICK_SEP;
                GRect brick = new GRect(xCenter, y, BRICK_WIDTH, BRICK_HEIGHT);
                add(brick);
                brick.setFilled(true);
                if (row < 2) {
                    brick.setColor(Color.RED);
                }
                if (row == 2 || row == 3) {
                    brick.setColor(Color.ORANGE);
                }
                if (row == 4 || row == 5) {
                    brick.setColor(Color.YELLOW);
                }
                if (row == 6 || row == 7) {
                    brick.setColor(Color.GREEN);
                }
                if (row == 8 || row == 9) {
                    brick.setColor(Color.CYAN);
                }
            }
        }
    }
}