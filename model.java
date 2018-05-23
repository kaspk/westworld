import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class model {
    public static boolean AIActive;

    //<editor-fold desc="Player attributes">
    public static boolean isPlayer2Turn;
    private static boolean isPlayer1Turn;
    private static boolean player1Won;
    private static boolean player2Won;
    public static int player1Wins = 0;
    public static int player2Wins = 0;
    public static int player1Power = 0;
    public static int player2Power = 0;
    public static String player1Name;
    public static String player2Name;
    //</editor-fold>

    private static JButton[][] button = controller.myView.seedButton;
    public static int[][] board = new int[3][3];

    private static double startTime;
    private static double endTime;
    private static String time;
    private static String highScore = "";


    model() {
        isPlayer1Turn = false;
        isPlayer2Turn = true;
        player1Won = false;
        player2Won = false;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = 0;
            }
        }
        if (highScore.equals("")) {
            highScore = getHighscore();
            updateHighscoreLabel();
        }
    }

    public static void seedArea(int i, int j) {
        if (emptyBoard()) {
            startTime = getStartTime();
        }
        button[i][j].setEnabled(false);
        button[i][j].setOpaque(true);

        if (isPlayer1Turn) {
            button[i][j].setBackground(Color.RED);
            button[i][j].setText("X");
            board[i][j] = 2;
            if (!checkForWon() && !completedBoard()) {
                isPlayer2Turn = !isPlayer2Turn;
                isPlayer1Turn = !isPlayer1Turn;
                if (AIActive)
                    controller.myView.setCursor((String) null);
                else
                    controller.myView.setCursor("resources/images/blueO.png");
            }
        } else if (isPlayer2Turn) {
            button[i][j].setBackground(Color.BLUE);
            button[i][j].setText(("O"));
            board[i][j] = 1;
            if (!checkForWon() && !completedBoard()) {
                isPlayer2Turn = !isPlayer2Turn;
                isPlayer1Turn = !isPlayer1Turn;
                controller.myView.setCursor("resources/images/redX.png");
            }
        }
        ifWon();
        enablePowers();
    }

    private static int checkColumns(JButton button[][], int currentColumn, String playerMark) {
        int checker = 0;
        for (int i = 0; i < button.length; i++) {
            if (button[currentColumn][i].getText().equals(playerMark)) {
                checker++;
            }
        }
        return checker;
    }

    private static int checkRows(JButton button[][], int currentRow, String playerMark) {
        int checker = 0;
        for (JButton[] aButton : button) {
            if (aButton[currentRow].getText().equals(playerMark)) {
                checker++;
            }
        }
        return checker;
    }

    public static boolean checkForWon() {
        int xInDiagonal = 0, oInDiagonal = 0;
        //<editor-fold desc="Checking for win bottom-left <-> top-right">
        if (button[2][0].getText().equals("X") && button[1][1].getText().equals("X") && button[0][2].getText().equals("X")) {
            player1Won = true;
            player2Won = false;
        } else if (button[2][0].getText().equals("O") && button[1][1].getText().equals("O") && button[0][2].getText().equals("O")) {
            player1Won = false;
            player2Won = true;
        }
        //</editor-fold>

        for (int i = 0; i < button.length; i++) {
            //<editor-fold desc="Checking for win top-left <-> bottom-right">
            if (button[i][i].getText().equals("X")) {
                xInDiagonal++;
                if (xInDiagonal == button.length) {
                    player1Won = true;
                    player2Won = false;
                    break;
                }
            }
            if (button[i][i].getText().equals("O")) {
                oInDiagonal++;
                if (oInDiagonal == button.length) {
                    player1Won = false;
                    player2Won = true;
                    break;
                }
            }
            //</editor-fold>

            for (int j = 0; j < button.length; j++) {
                //<editor-fold desc="Checking for win in rows">
                if (button[j][i].getText().equals("X")) {
                    if (checkRows(button, j, "X") == button.length) {
                        player1Won = true;
                        player2Won = false;
                        break;
                    }
                } else if (button[j][i].getText().equals("O")) {
                    if (checkRows(button, j, "O") == button.length) {
                        player1Won = false;
                        player2Won = true;
                        break;
                    }
                }
                //</editor-fold>

                //<editor-fold desc="Checking for win in columns">
                if (button[i][j].getText().equals("X")) {
                    if (checkColumns(button, j, "X") == button.length) {
                        player1Won = true;
                        player2Won = false;
                        break;
                    }
                } else if (button[i][j].getText().equals("O")) {
                    if (checkColumns(button, j, "O") == button.length) {
                        player1Won = false;
                        player2Won = true;
                        break;
                    }
                }
                //</editor-fold>
            }
        }

        return player1Won || player2Won;
    }

    private static void ifWon() {
        //<editor-fold desc="Does something if somebody won">
        if (checkForWon()) {
            endTime = getEndTime();
            time = millisecondsToSeconds(getTime());
            checkScore();
            setScore();
            updateHighscoreLabel();
        }
        if (player1Won) {
            String message = "Congratulations " + player1Name + "!\n" + player1Name +
                    " won against " + player2Name + " in " + time +
                    " seconds\nDo you want to play another round?";
            String title = player1Name + " wins this round!";
            int result = controller.myView.showOptionDialog(message, title, controller.myView.championIcon);
            if (result == JOptionPane.YES_OPTION) {
                player1Wins++;
                player1Power++;
                controller.myView.player1Score.setText(Integer.toString(player1Wins));
                newRound();
            } else
                controller.myView.homeScreen();
        } else if (player2Won) {
            String message = "Congratulations " + player2Name + "!\n" + player2Name +
                    " won against " + player1Name + " in " + time +
                    " seconds!\nDo you want to play another round?";
            String title = player2Name + " wins this round!";
            int result = controller.myView.showOptionDialog(message, title, controller.myView.championIcon);
            if (result == JOptionPane.YES_OPTION) {
                player2Wins++;
                player2Power++;
                controller.myView.player2Score.setText(Integer.toString(player2Wins));
                newRound();
            } else
                controller.myView.homeScreen();

        } else if (completedBoard()) {
            String message = "It's a tie\nDo you want to play again?";
            String title = "It's a tie";
            int result = controller.myView.showOptionDialog(message, title, controller.myView.tieGameIcon);
            if (result == JOptionPane.YES_OPTION) {
                newRound();
            } else
                controller.myView.homeScreen();

        }
        //</editor-fold>
    }

    private static boolean completedBoard() {
        int increase = 0;
        for (JButton[] aButton : button) {
            for (int j = 0; j < button.length; j++) {
                if (!aButton[j].getText().equals(""))
                    increase++;
                if (increase == button.length * button.length)
                    return true;
            }
        }
        return false;
    }

    private static boolean emptyBoard() {
        int increase = 0;
        for (int i = 0; i < button.length; i++) {
            for (int j = 0; j < button.length; j++) {
                if (button[i][j].getText().equals("") && board[i][j] == 0)
                    increase++;
                if (increase == button.length * button.length)
                    return true;
            }
        }
        return false;
    }

    private static void wipeBoard() {
        for (int i = 0; i < button.length; i++) {
            for (int j = 0; j < button.length; j++) {
                JButton currentButton = button[i][j];
                currentButton.setText("");
                currentButton.setEnabled(true);
                currentButton.setBackground(controller.myView.yellowColor);
                board[i][j] = 0;
            }
        }
    }

    static void newRound() {
        isPlayer1Turn = !isPlayer1Turn;
        isPlayer2Turn = !isPlayer2Turn;

        if (AIActive) {
            isPlayer1Turn = true;
            isPlayer2Turn = false;
            AI.end = false;
            AI.counter = 1;
        }

        if (isPlayer1Turn) {
            controller.myView.setCursor("resources/images/redX.png");
        } else
            controller.myView.setCursor("resources/images/blueO.png");

        player1Won = false;
        player2Won = false;
        while (!emptyBoard())
            wipeBoard();
    }


    private static void enablePowers() {
        if (player1Power >= 5) {
            if (isPlayer1Turn)
                controller.myView.switchButton.setEnabled(true);
            else
                controller.myView.switchButton.setEnabled(false);
        } else if (player2Power >= 5) {
            if (isPlayer2Turn)
                controller.myView.switchButton.setEnabled(true);
            else
                controller.myView.switchButton.setEnabled(false);
        } else
            controller.myView.switchButton.setEnabled(false);

        if (player1Power >= 3) {
            if (isPlayer1Turn)
                controller.myView.bombButton.setEnabled(true);
            else
                controller.myView.bombButton.setEnabled(false);
        } else if (player2Power >= 3) {
            if (isPlayer2Turn)
                controller.myView.bombButton.setEnabled(true);
            else
                controller.myView.bombButton.setEnabled(false);
        } else
            controller.myView.bombButton.setEnabled(false);
    }

    public static void activateSwitchPower() {
        if ((player1Power >= 5 && isPlayer1Turn) || (player2Power >= 5 && isPlayer2Turn)) {
            for (int i = 0; i < button.length; i++) {
                for (int j = 0; j < button.length; j++) {
                    if (button[i][j].getText().equals("O")) {
                        theSwitch("X", 2, Color.RED, i, j);
                    } else if (button[i][j].getText().equals("X")) {
                        theSwitch("O", 1, Color.BLUE, i, j);
                    }
                }
            }
            if (isPlayer1Turn)
                player1Power -= 5;
            else if (isPlayer2Turn)
                player2Power -= 5;
            controller.myView.switchButton.setEnabled(false);
        }
    }

    public static void activateBombPower() {
        if ((player1Power >= 3 && isPlayer1Turn) || (player2Power >= 3 && isPlayer2Turn)) {
            Random rand = new Random();
            int numberOfBombs = 0;

            if (getUnavailableStates().size() == 0) {
                numberOfBombs = -1;
            } else if (getUnavailableStates().size() == 2) {
                numberOfBombs = 2;
            } else if (getUnavailableStates().size() == 1) {
                numberOfBombs = 1;
            } else if (getUnavailableStates().size() >= 3) {
                numberOfBombs = 3;
            }
            for (int i = 0; i < numberOfBombs; i++) {
                int randRow = rand.nextInt(3);
                int randColumn = rand.nextInt(3);

                while (button[randRow][randColumn].isEnabled()) {
                    randRow = rand.nextInt(3);
                    randColumn = rand.nextInt(3);
                }
                theBomb(randRow, randColumn);
            }
            if (isPlayer1Turn)
                player1Power -= 3;
            else if (player2Power >= 3)
                player2Power -= 3;

            controller.myView.bombButton.setEnabled(false);
        }
    }

    private static void theSwitch(String toSeed, int toBoardSeed, Color color, int i, int j) {
        button[i][j].setBackground(color);
        button[i][j].setText(toSeed);
        board[i][j] = toBoardSeed;
    }

    private static void theBomb(int row, int column) {
        button[row][column].setText("");
        button[row][column].setEnabled(true);
        button[row][column].setBackground(controller.myView.yellowColor);
        board[row][column] = 0;
    }

    private static java.util.List<Point> getUnavailableStates() {
        List<Point> unavailablePoints = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (board[i][j] == 1 || board[i][j] == 2) {
                    unavailablePoints.add(new Point(i, j));
                }
            }
        }
        return unavailablePoints;
    }

    private static String getHighscore() {
        // format   Winner vs. Loser:number of seconds
        FileReader readfile;
        BufferedReader reader = null;
        try {
            readfile = new FileReader("resources/files/highscore.txt");
            reader = new BufferedReader(readfile);
            return reader.readLine();
        } catch (IOException e) {
            return "Nobody:0";
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void setScore() {
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter("resources/files/highscore.txt"));
            output.write(highScore);
            output.close();

        } catch (IOException ex1) {
            System.err.printf("ERROR: Writing score to file: %s\n", ex1);
        }
    }

    private static long getStartTime() {
        return System.currentTimeMillis();
    }

    private static long getEndTime() {
        return System.currentTimeMillis();
    }

    private static double getTime() {
        return (endTime - startTime);
    }

    private static String millisecondsToSeconds(double pTime) {
        return String.format("%.2f", pTime / 1000);
    }

    private static void checkScore() {
        if (highScore.equals("") || highScore == null)
            return;
        if (Double.parseDouble(time) < Double.parseDouble(highScore.split(":")[1])) {
            if (player1Won) {
                highScore = player1Name + "(W) VS " + player2Name + "(L):" + time;
            } else if (player2Won) {
                highScore = player2Name + "(W) VS " + player1Name + "(L):" + time;
            }
        }
    }

    private static void updateHighscoreLabel() {
        controller.myView.highScoreText.setText("Match:\n" + highScore.split(":")[0] + "\n\nTime:\n" + highScore.split(":")[1] + " seconds");
    }

}