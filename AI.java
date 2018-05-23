
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AI {
    private static final int boardSize = controller.myView.numberOfFields;
    private static final JButton[][] button = controller.myView.seedButton;
    public static int counter = 1;
    public static boolean end = false;
    public static String difficulty;
    private static int[][] board = model.board;
    private static Point computersMove;
    private static String firstBotMove;


    AI() {
        AIMakeMove();
    }

    private static void AIMakeMove() {
        while (!end) {
            if (model.AIActive) {
                if (model.isPlayer2Turn) {
                    switch (difficulty) {
                        case "Easy":
                            easy();
                            break;
                        case "Intermediate":
                            intermediate();
                            break;
                        case "Impossible":
                            minimax(0, 1);
                            setSeed(computersMove);
                            break;
                    }
                } else
                    sleep(100);
            } else {
                sleep(5000);
            }
        }
    }

    private static void easy() {
        Random rand = new Random();
        List<Point> pointsAvailable = getAvailableStates();
        int randNumber = rand.nextInt(pointsAvailable.size());
        computersMove = pointsAvailable.get(randNumber);
        setSeed(computersMove);
    }

    private static void intermediate() {
        int bordMid = boardSize / 2;

        Random rand = new Random();
        int randNumber = rand.nextInt(4) + 1;

        //<editor-fold desc="First move">
        if (counter == 1) {
            if (randNumber == 1 && button[0][0].isEnabled()) {
                //Top-left corner
                model.seedArea(0, 0);
                firstBotMove = "Top-Left";
            } else if (randNumber == 2 && button[0][boardSize - 1].isEnabled()) {
                //Top-right corner
                model.seedArea(0, boardSize - 1);
                firstBotMove = "Top-Right";
            } else if (randNumber == 3 && button[boardSize - 1][0].isEnabled()) {
                //Bottom-left corner
                model.seedArea(boardSize - 1, 0);
                firstBotMove = "Bottom-Left";
            } else if (randNumber == 4 && button[boardSize - 1][boardSize - 1].isEnabled()) {
                //Bottom-right corner
                model.seedArea(boardSize - 1, boardSize - 1);
                firstBotMove = "Bottom-Right";
            } else {
                //Choses middle if all other options fail (extremely unlikely)
                model.seedArea(bordMid, bordMid);
                firstBotMove = "Middle";
            }
        }
        //</editor-fold>

        //<editor-fold desc="Second move">
        if (counter == 2) {
            int second[] = intermediateMove();
            if (second[0] != 5) {
                //System.out.println("CHECK");
                model.seedArea(second[0], second[1]);
            }
            else {
                int secondMy[] = intermediateMyMove();
                if (secondMy[0] != 5) {
                    model.seedArea(secondMy[0], secondMy[1]);
                } else {
                    if (button[bordMid][bordMid].isEnabled())
                        model.seedArea(bordMid, bordMid);
                    else {
                        switch (firstBotMove) {
                            case "Top-Left":
                                model.seedArea(0, boardSize - 1);
                                break;
                            case "Top-Right":
                                model.seedArea(boardSize - 1, boardSize - 1);
                                break;
                            case "Bottom-Left":
                                model.seedArea(boardSize - 1, boardSize - 1);
                                break;
                            case "Bottom-Right":
                                model.seedArea(0, boardSize - 1);
                                break;
                        }
                    }
                }
            }
        }
        //</editor-fold>

        //<editor-fold desc="Remaining moves">
        if (counter >= 3) {
            if (!end) {
                int second[] = intermediateMove();
                if (second[0] != 5) {
                    //System.out.println("CHECK");
                    model.seedArea(second[0], second[1]);
                } else {
                    int secondMy[] = intermediateMyMove();
                    if (secondMy[0] != 5) {
                        model.seedArea(secondMy[0], secondMy[1]);
                    } else {
                        secondMy = noOtherOption();
                        model.seedArea(secondMy[0], secondMy[1]);
                    }
                }
            }
            end = getWon();
        }
        //</editor-fold>

        counter++;
    }

    private static int[] intermediateMove() {
        int[] ret = new int[2];
        ret[0] = 5;
        if (button[0][0].getText().equals("O") && button[0][1].getText().equals("O") && button[0][2].isEnabled()) {
            ret[0] = 0;
            ret[1] = 2;
        } else if (button[0][0].getText().equals("O") && button[1][0].getText().equals("O") && button[2][0].isEnabled()) {
            ret[0] = 2;
            ret[1] = 0;
        } else if (button[0][0].getText().equals("O") && button[1][1].getText().equals("O") && button[2][2].isEnabled()) {
            ret[0] = 2;
            ret[1] = 2;
        } else if (button[0][1].getText().equals("O") && button[0][2].getText().equals("O") && button[0][0].isEnabled()) {
            ret[0] = 0;
            ret[1] = 0;
        } else if (button[0][1].getText().equals("O") && button[1][1].getText().equals("O") && button[2][1].isEnabled()) {
            ret[0] = 2;
            ret[1] = 1;
        } else if (button[0][2].getText().equals("O") && button[1][1].getText().equals("O") && button[2][0].isEnabled()) {
            ret[0] = 2;
            ret[1] = 0;
        } else if (button[0][2].getText().equals("O") && button[1][2].getText().equals("O") && button[2][2].isEnabled()) {
            ret[0] = 2;
            ret[1] = 2;
        } else if (button[1][0].getText().equals("O") && button[1][1].getText().equals("O") && button[1][2].isEnabled()) {
            ret[0] = 1;
            ret[1] = 2;
        } else if (button[1][0].getText().equals("O") && button[2][0].getText().equals("O") && button[0][0].isEnabled()) {
            ret[0] = 0;
            ret[1] = 0;
        } else if (button[2][0].getText().equals("O") && button[1][1].getText().equals("O") && button[0][2].isEnabled()) {
            ret[0] = 0;
            ret[1] = 2;
        } else if (button[2][1].getText().equals("O") && button[1][1].getText().equals("O") && button[0][1].isEnabled()) {
            ret[0] = 0;
            ret[1] = 1;
        } else if (button[2][2].getText().equals("O") && button[1][1].getText().equals("O") && button[0][0].isEnabled()) {
            ret[0] = 0;
            ret[1] = 0;
        } else if (button[1][1].getText().equals("O") && button[1][2].getText().equals("O") && button[1][0].isEnabled()) {
            ret[0] = 1;
            ret[1] = 0;
        } else if (button[2][2].getText().equals("O") && button[1][2].getText().equals("O") && button[0][2].isEnabled()) {
            ret[0] = 0;
            ret[1] = 2;
        } else if (button[2][0].getText().equals("O") && button[2][1].getText().equals("O") && button[2][2].isEnabled()) {
            ret[0] = 2;
            ret[1] = 2;
        } else if (button[2][1].getText().equals("O") && button[2][2].getText().equals("O") && button[2][0].isEnabled()) {
            ret[0] = 2;
            ret[1] = 0;
        } else if (button[0][0].getText().equals("O") && button[0][2].getText().equals("O") && button[0][1].isEnabled()) {
            ret[0] = 0;
            ret[1] = 1;
        } else if (button[0][0].getText().equals("O") && button[2][0].getText().equals("O") && button[1][0].isEnabled()) {
            ret[0] = 1;
            ret[1] = 0;
        } else if (button[0][2].getText().equals("O") && button[2][2].getText().equals("O") && button[1][2].isEnabled()) {
            ret[0] = 1;
            ret[1] = 2;
        } else if (button[2][0].getText().equals("O") && button[2][2].getText().equals("O") && button[2][1].isEnabled()) {
            ret[0] = 2;
            ret[1] = 1;
        } else if (button[0][0].getText().equals("O") && button[2][2].getText().equals("O") && button[1][1].isEnabled()) {
            ret[0] = 1;
            ret[1] = 1;
        } else if (button[0][2].getText().equals("O") && button[2][0].getText().equals("O") && button[1][1].isEnabled()) {
            ret[0] = 1;
            ret[1] = 1;
        } else if (button[0][1].getText().equals("O") && button[2][1].getText().equals("O") && button[1][1].isEnabled()) {
            ret[0] = 1;
            ret[1] = 1;
        } else if (button[1][0].getText().equals("O") && button[1][2].getText().equals("O") && button[1][1].isEnabled()) {
            ret[0] = 1;
            ret[1] = 1;
        }
        return ret;

    }

    private static int[] intermediateMyMove() {
        int[] ret = new int[2];
        ret[0] = 5;
        if (button[0][0].getText().equals("X") && button[0][1].getText().equals("X") && button[0][2].isEnabled()) {
            ret[0] = 0;
            ret[1] = 2;
        } else if (button[0][0].getText().equals("X") && button[1][0].getText().equals("X") && button[2][0].isEnabled()) {
            ret[0] = 2;
            ret[1] = 0;
        } else if (button[0][0].getText().equals("X") && button[1][1].getText().equals("X") && button[2][2].isEnabled()) {
            ret[0] = 2;
            ret[1] = 2;
        } else if (button[0][1].getText().equals("X") && button[0][2].getText().equals("X") && button[0][0].isEnabled()) {
            ret[0] = 0;
            ret[1] = 0;
        } else if (button[0][1].getText().equals("X") && button[1][1].getText().equals("X") && button[2][1].isEnabled()) {
            ret[0] = 2;
            ret[1] = 1;
        } else if (button[0][2].getText().equals("X") && button[1][1].getText().equals("X") && button[2][0].isEnabled()) {
            ret[0] = 2;
            ret[1] = 0;
        } else if (button[0][2].getText().equals("X") && button[1][2].getText().equals("X") && button[2][2].isEnabled()) {
            ret[0] = 2;
            ret[1] = 2;
        } else if (button[1][0].getText().equals("X") && button[1][1].getText().equals("X") && button[1][2].isEnabled()) {
            ret[0] = 1;
            ret[1] = 2;
        } else if (button[1][0].getText().equals("X") && button[2][0].getText().equals("X") && button[0][0].isEnabled()) {
            ret[0] = 0;
            ret[1] = 0;
        } else if (button[2][0].getText().equals("X") && button[1][1].getText().equals("X") && button[0][2].isEnabled()) {
            ret[0] = 0;
            ret[1] = 2;
        } else if (button[2][1].getText().equals("X") && button[1][1].getText().equals("X") && button[0][1].isEnabled()) {
            ret[0] = 0;
            ret[1] = 1;
        } else if (button[2][2].getText().equals("X") && button[1][1].getText().equals("X") && button[0][0].isEnabled()) {
            ret[0] = 0;
            ret[1] = 0;
        } else if (button[1][1].getText().equals("X") && button[1][2].getText().equals("X") && button[1][0].isEnabled()) {
            ret[0] = 1;
            ret[1] = 0;
        } else if (button[2][2].getText().equals("X") && button[1][2].getText().equals("X") && button[0][2].isEnabled()) {
            ret[0] = 0;
            ret[1] = 2;
        } else if (button[2][0].getText().equals("X") && button[2][1].getText().equals("X") && button[2][2].isEnabled()) {
            ret[0] = 2;
            ret[1] = 2;
        } else if (button[2][1].getText().equals("X") && button[2][2].getText().equals("X") && button[2][0].isEnabled()) {
            ret[0] = 2;
            ret[1] = 0;
        } else if (button[0][0].getText().equals("X") && button[0][2].getText().equals("X") && button[0][1].isEnabled()) {
            ret[0] = 0;
            ret[1] = 1;
        } else if (button[0][0].getText().equals("X") && button[2][0].getText().equals("X") && button[1][0].isEnabled()) {
            ret[0] = 1;
            ret[1] = 0;
        } else if (button[0][2].getText().equals("X") && button[2][2].getText().equals("X") && button[1][2].isEnabled()) {
            ret[0] = 1;
            ret[1] = 2;
        } else if (button[2][0].getText().equals("X") && button[2][2].getText().equals("X") && button[2][1].isEnabled()) {
            ret[0] = 2;
            ret[1] = 1;
        } else if (button[0][0].getText().equals("X") && button[2][2].getText().equals("X") && button[1][1].isEnabled()) {
            ret[0] = 1;
            ret[1] = 1;
        } else if (button[0][2].getText().equals("X") && button[2][0].getText().equals("X") && button[1][1].isEnabled()) {
            ret[0] = 1;
            ret[1] = 1;
        } else if (button[0][1].getText().equals("X") && button[2][1].getText().equals("X") && button[1][1].isEnabled()) {
            ret[0] = 1;
            ret[1] = 1;
        } else if (button[1][0].getText().equals("X") && button[1][2].getText().equals("X") && button[1][1].isEnabled()) {
            ret[0] = 1;
            ret[1] = 1;
        }
        return ret;
    }

    private static int[] noOtherOption() {
        int[] ret = new int[2];
        ret[0] = 5;
        while (ret[0] == 5) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (button[i][j].isEnabled()) {
                        ret[0] = i;
                        ret[1] = j;
                        break;
                    }
                }
            }
        }


        return ret;
    }

    private static void sleep(long milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean getWon() {
        return model.checkForWon();
    }

    private static List<Point> getAvailableStates() {
        List<Point> availablePoints = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (board[i][j] == 0) {
                    availablePoints.add(new Point(i, j));
                }
            }
        }
        return availablePoints;
    }

    private static int minimax(int depth, int turn) {
        if (emulateOWin()) return +10;
        if (emulateXWin()) return -10;

        List<Point> pointsAvailable = getAvailableStates();
        if (pointsAvailable.isEmpty()) return 0;

        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

        for (int i = 0; i < pointsAvailable.size(); i++) {
            Point point = pointsAvailable.get(i);
            if (turn == 1) {
                placeAMove(point, 1);
                int currentScore = minimax(depth + 1, 2);
                max = Math.max(currentScore, max);

                if (currentScore >= 0) {
                    if (depth == 0)
                        computersMove = point;
                }
                if (currentScore == 1) {
                    board[point.x][point.y] = 0;
                    break;
                }
                if (i == pointsAvailable.size() - 1 && max < 0) {
                    if (depth == 0)
                        computersMove = point;
                }
            } else if (turn == 2) {
                placeAMove(point, 2);
                int currentScore = minimax(depth + 1, 1);
                min = Math.min(currentScore, min);
                if (min == -1) {
                    board[point.x][point.y] = 0;
                    break;
                }
            }
            board[point.x][point.y] = 0; //Reset this point
        }
        return turn == 1 ? max : min;
    }

    private static void placeAMove(Point point, int player) {
        board[point.x][point.y] = player;   //player = 1 for AI, 2 for user
    }

    private static void setSeed(Point point) {
        model.seedArea(point.x, point.y);
    }

    private static boolean emulateOWin() {
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 1) || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 1)) {
            //System.out.println("X Diagonal Win");
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            if (((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 1)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 1))) {
                // System.out.println("X Row or Column win");
                return true;
            }
        }
        return false;
    }

    private static boolean emulateXWin() {
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 2) || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 2)) {
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 2)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 2)) {
                return true;
            }
        }

        return false;
    }
}

class Point extends java.awt.Point {

    int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}


