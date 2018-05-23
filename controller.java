import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

class controller implements ActionListener, WindowListener {
    public static view myView;


    controller() {
        myView = new view(this);
        if (!myView.isVisible()) {
            myView.setVisible(true);
        }
        model myModel = new model();
        AI myAI = new AI();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //<editor-fold desc="Exit seedButton">
        if (e.getSource().equals(myView.exitButton)) {
            myView.dispose();
            System.exit(0);
        }
        //</editor-fold>

        //<editor-fold desc="Restart seedButton">
        else if (e.getSource().equals(myView.rematchButton)) {
            model.newRound();
        }
        //</editor-fold>

        //<editor-fold desc="Home seedButton">
        else if (e.getSource().equals(myView.homeButton)) {
            myView.homeScreen();
        }
        //</editor-fold>

        //<editor-fold desc="Two player mode">
        else if (e.getSource().equals(myView.twoPlayerButton)) {
            //<editor-fold desc="Player names">
            if (myView.nickName1.getText().equals(""))
                model.player1Name = "Player 1";
            else
                model.player1Name = myView.nickName1.getText();
            if (myView.nickName2.getText().equals(""))
                model.player2Name = "Player 2";
            else
                model.player2Name = myView.nickName2.getText();
            //</editor-fold>
            model.player1Wins = 0;
            model.player2Wins = 0;
            model.player1Power = 0;
            model.player2Power = 0;
            model.newRound();
            myView.gameScreen();
            model.AIActive = false;
        }
        //</editor-fold>

        //<editor-fold desc="Bot player mode">
        else if (e.getSource().equals(myView.botButton)) {
            String difficulty = null;
            try {
                difficulty = myView.showRdbOptionDialog("Choose difficulty", "Difficulty").toString();
            } catch (Exception ignored) {
            }
            //<editor-fold desc="Player names">
            if (myView.nickName1.getText().equals(""))
                model.player1Name = "Player 1";
            else
                model.player1Name = myView.nickName1.getText();
            //</editor-fold>

            if (difficulty != null) {
                //<editor-fold desc="Bot names">
                switch (difficulty) {
                    case "Easy":
                        model.player2Name = "Easy Bot";
                        break;
                    case "Intermediate":
                        model.player2Name = "Intermediate Bot";
                        break;
                    case "Impossible":
                        model.player2Name = "Impossible Bot";
                        break;
                }
                //</editor-fold>
                AI.difficulty = difficulty;
                model.player1Wins = 0;
                model.player2Wins = 0;
                model.player1Power = 0;
                model.player2Power = 0;
                model.newRound();
                myView.gameScreen();
                model.AIActive = true;
            }
        }
        //</editor-fold>

        //<editor-fold desc="Bomb power-up seedButton">
        else if (e.getSource().equals(myView.bombButton)) {
            model.activateBombPower();
        }
        //</editor-fold>

        //<editor-fold desc="Switch power-up seedButton">
        else if (e.getSource().equals(myView.switchButton)) {
            model.activateSwitchPower();
        }
        //</editor-fold>

        //<editor-fold desc="How to button">
        else if (e.getSource().equals(myView.howToButton)) {
            controller.myView.howToScreen();
        }
        if (e.getSource().equals(myView.oKButton)) {
            myView.homeScreen();
        }
        //</editor-fold>

        //<editor-fold desc="Seed seedButton">
        else {
            for (int i = 0; i < myView.seedButton.length; i++) {
                for (int j = 0; j < myView.seedButton.length; j++) {
                    if (e.getSource().equals(myView.seedButton[i][j])) {
                        model.seedArea(i, j);
                        model.checkForWon();
                    }
                }
            }
        }
        //</editor-fold>

    }


    @Override
    public void windowClosing(WindowEvent e) {
        String message = "Are you sure you want to quit?";
        String tilte = "Exiting game";
        int result = myView.showOptionDialog(message, tilte, myView.exitIcon);
        if (result == JOptionPane.YES_OPTION) {
            myView.dispose();
            System.exit(0);
        }
    }

    //<editor-fold desc="Unused windowListeners">
    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
    //</editor-fold>
}
