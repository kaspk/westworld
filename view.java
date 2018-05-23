import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;

public class view extends JFrame {
    public final int numberOfFields;
    public final JButton[][] seedButton;
    public final ImageIcon championIcon;
    public final ImageIcon tieGameIcon;
    public final ImageIcon exitIcon;
    public final JTextField nickName1;
    public final JTextField nickName2;
    private final ImageIcon logoIcon;
    private final ImageIcon redXIcon;
    private final ImageIcon blueOIcon;
    private final ImageIcon backIcon;

    public final JButton homeButton;
    public final JButton twoPlayerButton;
    public final JButton botButton;
    public final JButton rematchButton;
    public final JButton exitButton;
    public final JButton switchButton;
    public final JButton bombButton;
    public final JButton oKButton;
    private final ImageIcon bombIcon;

    public final Color yellowColor;
    private final Color turquoiseColor;
    private final Color whiteColor;
    private final Color darkRedColor;
    private final ImageIcon switchIcon;
    public JButton howToButton;
    private final JTextField player1Name;
    private final JTextField player2Name;
    public final JTextField player1Score;
    public final JTextField player2Score;

    public final JTextArea highScoreText;
    private final JTextArea infoText;

    private final JPanel backPanel;
    private final JPanel contentPane;
    private final JLabel topLogo;
    private final JPanel seedButtonPanel;
    private final JPanel menuWindow;
    private final JPanel howToPlayPanel;
    private final JLabel logoLabel;
    private final JLabel howToPlayLabel;
    private final JLabel lblNickNameTitle;
    private final JLabel rexXLabel1;
    private final JLabel blueOLabel1;
    private final JLabel lblNickName1;
    private final JLabel lblNickName2;
    private final JLabel redXLabel2;
    private final JLabel blueOLabel2;
    private final JLabel scoreLabel;
    private final JLabel powerUps;
    private final JLabel highScoreLabel;

    private final String[] difficulties = {"Easy", "Intermediate", "Impossible"};


    view(controller c) {
        int WIDTH = 900;
        int HEIGHT = 600;
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(c);
        this.setBounds(100, 100, WIDTH, HEIGHT + 20);

        //<editor-fold desc="Colors">
        whiteColor = new Color(255, 255, 255);
        yellowColor = new Color(255, 225, 0);
        turquoiseColor = new Color(64, 224, 208);
        darkRedColor = new Color(139, 0, 0);
        //</editor-fold>

        //<editor-fold desc="Icons">
        championIcon = new ImageIcon("resources/images/champion.png");
        tieGameIcon = new ImageIcon("resources/images/tieGame.jpg");
        logoIcon = new ImageIcon("resources/images/logo.png");
        backIcon = new ImageIcon("resources/images/back.png");
        exitIcon = new ImageIcon("resources/images/exitbtn.png");
        bombIcon = new ImageIcon("resources/images/bomb.png");
        switchIcon = new ImageIcon("resources/images/switch.png");
        redXIcon = new ImageIcon("resources/images/redX.png");
        blueOIcon = new ImageIcon("resources/images/blueO.png");
        //</editor-fold>

        contentPane = new JPanel();
        contentPane.setBackground(whiteColor);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        //<editor-fold desc="High score">
        highScoreLabel = new JLabel("High score");
        highScoreLabel.setFont(new Font("Tahoma", Font.BOLD, 19));
        highScoreLabel.setBackground(turquoiseColor);
        highScoreLabel.setBounds(742, 380, 200, 24);

        highScoreText = new JTextArea();
        highScoreText.setEditable(false);
        highScoreText.setLineWrap(true);
        highScoreText.setWrapStyleWord(true);
        highScoreText.setFont(new Font("Tahoma", Font.BOLD, 12));
        highScoreText.setBorder(BorderFactory.createEmptyBorder());
        highScoreText.setBackground(turquoiseColor);
        highScoreText.setBounds(706, 415, 200, 150);
        highScoreText.setColumns(10);
        //</editor-fold>

        //<editor-fold desc="How-To window">
        howToPlayPanel = new JPanel();
        howToPlayPanel.setBackground(turquoiseColor);
        howToPlayPanel.setBounds(0, 0, WIDTH, HEIGHT);
        howToPlayPanel.setLayout(null);

        howToPlayLabel = new JLabel("How to Play");
        howToPlayLabel.setFont(new Font("Verdana", Font.BOLD, 40));
        howToPlayLabel.setBounds(310, 11, 287, 58);

        infoText = new JTextArea();
        infoText.setEditable(false);
        infoText.setBorder(new LineBorder(darkRedColor, 4));
        infoText.setFont(new Font("Microsoft Tai Le", Font.BOLD, 15));
        infoText.setLineWrap(true);
        infoText.setWrapStyleWord(true);
        //<editor-fold desc="Game rules">
        String gameRules = "Rules are simple, first player to get three in a row wins the round. " +
                "\n\nYou can choose to play against a friend, but if your friends is not" +
                "available the AI-bot will play with you day and night." +
                "\n\nApproved rows:" +
                "\n  - Horizontal" +
                "\n  - Vertical" +
                "\n  - Diagonal" +
                "\n\nWhen a player wins a certain amount of times they will unlock powerful abilities:" +
                "\n- Fields Of Fire: Will unlock after tree wins, once used you have to win another three matches to enable this power again." +
                "The Fields Of Fire gives the player a highly-explosive yet unpredictable bomb that will turn three already played fields into ashes, " +
                "remember the outcome might not be as you expected..." +
                "\n- Orb Of Deception: Will unlock after five wins, once used you have to win another five matches to enable this power again." +
                "The Orb Of Deception grants the player the magical ability to fool the opponent and flip the board. Yours is now mine, and mine is now yours..." +
                "\n\n\n\n\n© Kaspar E. Kielland & Tommy Dang, 2018 University of Agder";
        //</editor-fold>
        infoText.setText(gameRules);
        infoText.setBackground(whiteColor);
        infoText.setBounds(45, 72, 789, 459);

        oKButton = new JButton("Got it");
        oKButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        oKButton.setBackground(yellowColor);
        oKButton.setBounds(332, 535, 274, 50);
        oKButton.addActionListener(c);
        //</editor-fold>

        //<editor-fold desc="Rematch button">
        rematchButton = new JButton("REMATCH");
        rematchButton.setBackground(yellowColor);
        rematchButton.setFont(new Font("Tahoma", Font.BOLD, 27));
        rematchButton.setBounds(313, 517, 256, 38);
        rematchButton.addActionListener(c);
        //</editor-fold>

        backPanel = new JPanel();
        backPanel.setBackground(turquoiseColor);
        backPanel.setBounds(0, 0, WIDTH, HEIGHT);

        topLogo = new JLabel("");
        topLogo.setBackground(turquoiseColor);
        topLogo.setIcon(logoIcon);
        topLogo.setBounds(240, -18, 600, 160);

        //<editor-fold desc="Home button">
        homeButton = new JButton("");
        homeButton.addActionListener(c);
        homeButton.setBackground(turquoiseColor);
        homeButton.setIcon(backIcon);
        homeButton.setForeground(turquoiseColor);
        homeButton.setBounds(0, 0, 38, 38);
        homeButton.setBorderPainted(false);
        homeButton.setFocusPainted(false);
        homeButton.setContentAreaFilled(false);
        homeButton.addActionListener(c);
        //</editor-fold>

        //<editor-fold desc="Exit-button">
        exitButton = new JButton("");
        exitButton.setForeground(turquoiseColor);
        exitButton.setIcon(exitIcon);
        exitButton.setBackground(turquoiseColor);
        exitButton.setBounds(WIDTH - 55, HEIGHT - 55, 49, 49);
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.addActionListener(c);
        //</editor-fold>

        //<editor-fold desc="Power-Ups">
        powerUps = new JLabel("Power-Ups");
        powerUps.setBackground(turquoiseColor);
        powerUps.setFont(new Font("Tahoma", Font.BOLD, 19));
        powerUps.setBounds(63, 251, 109, 24);

        bombButton = new JButton("");
        bombButton.setForeground(turquoiseColor);
        bombButton.setIcon(bombIcon);
        bombButton.setBounds(91, 286, 50, 50);
        bombButton.setBorderPainted(false);
        bombButton.setFocusPainted(false);
        bombButton.setContentAreaFilled(false);
        bombButton.addActionListener(c);
        bombButton.setEnabled(false);

        switchButton = new JButton("");
        switchButton.setForeground(turquoiseColor);
        switchButton.setIcon(switchIcon);
        switchButton.setBounds(83, 352, 50, 50);
        switchButton.setBorderPainted(false);
        switchButton.setFocusPainted(false);
        switchButton.setContentAreaFilled(false);
        switchButton.addActionListener(c);
        switchButton.setEnabled(false);
        //</editor-fold>

        //<editor-fold desc="Score">
        scoreLabel = new JLabel("Match score");
        scoreLabel.setFont(new Font("Tahoma", Font.BOLD, 19));
        scoreLabel.setBackground(turquoiseColor);
        scoreLabel.setBounds(735, 125, 200, 24);


        player1Name = new JTextField();
        player1Name.setEditable(false);
        player1Name.setFont(new Font("Tahoma", Font.BOLD, 12));
        player1Name.setBackground(turquoiseColor);
        player1Name.setForeground(Color.RED);
        player1Name.setBounds(706, 171, 115, 20);
        player1Name.setColumns(10);
        player1Name.setBorder(BorderFactory.createEmptyBorder());

        player1Score = new JTextField();
        player1Score.setEditable(false);
        player1Score.setFont(new Font("Tahoma", Font.BOLD, 12));
        player1Score.setBackground(turquoiseColor);
        player1Score.setForeground(Color.RED);
        player1Score.setBounds(837, 171, 20, 20);
        player1Score.setColumns(10);
        player1Score.setText("0");
        player1Score.setBorder(BorderFactory.createEmptyBorder());


        player2Name = new JTextField();
        player2Name.setEditable(false);
        player2Name.setFont(new Font("Tahoma", Font.BOLD, 12));
        player2Name.setBackground(turquoiseColor);
        player2Name.setForeground(Color.BLUE);
        player2Name.setBounds(706, 202, 115, 20);
        player2Name.setColumns(10);
        player2Name.setText("");
        player2Name.setBorder(BorderFactory.createEmptyBorder());

        player2Score = new JTextField();
        player2Score.setEditable(false);
        player2Score.setFont(new Font("Tahoma", Font.BOLD, 12));
        player2Score.setBackground(turquoiseColor);
        player2Score.setForeground(Color.BLUE);
        player2Score.setBounds(837, 202, 20, 20);
        player2Score.setColumns(10);
        player2Score.setText("0");
        player2Score.setBorder(BorderFactory.createEmptyBorder());
        //</editor-fold>

        //<editor-fold desc="Seed button">
        seedButtonPanel = new JPanel();
        seedButtonPanel.setBackground(turquoiseColor);
        seedButtonPanel.setBorder(new LineBorder(darkRedColor, 4));
        seedButtonPanel.setBounds(182, 126, 514, 388);
        numberOfFields = 3;
        seedButtonPanel.setLayout(new GridLayout(numberOfFields, numberOfFields, 0, 0));
        seedButton = new JButton[numberOfFields][numberOfFields];
        for (int i = 0; i < seedButton.length; i++) {
            for (int j = 0; j < seedButton.length; j++) {
                JPanel[][] buttonPanel;
                buttonPanel = new JPanel[numberOfFields][numberOfFields];
                buttonPanel[i][j] = new JPanel();
                buttonPanel[i][j].setBackground(yellowColor);
                buttonPanel[i][j].setBorder(new SoftBevelBorder(BevelBorder.RAISED));
                seedButtonPanel.add(buttonPanel[i][j]);

                seedButton[i][j] = new JButton();
                seedButton[i][j].setText("");
                seedButton[i][j].setFont(new Font("Tahoma", Font.BOLD, 80));
                seedButton[i][j].addActionListener(c);
                buttonPanel[i][j].setLayout(new CardLayout(0, 0));
                seedButton[i][j].setBackground(yellowColor);
                buttonPanel[i][j].add(seedButton[i][j]);
            }
        }
        //</editor-fold>

        //<editor-fold desc="Menu window">
        menuWindow = new JPanel();
        menuWindow.setBackground(turquoiseColor);
        menuWindow.setBounds(0, 0, WIDTH, HEIGHT);
        menuWindow.setLayout(null);

        logoLabel = new JLabel("");
        logoLabel.setIcon(logoIcon);
        logoLabel.setBounds(255, 51, 500, 152);

        lblNickNameTitle = new JLabel("Nickname");
        lblNickNameTitle.setFont(new Font("Tahoma", Font.BOLD, 21));
        lblNickNameTitle.setBounds(397, 276, 135, 24);

        nickName1 = new JTextField();
        nickName1.setBounds(327, 300, 254, 24);
        nickName1.setColumns(10);
        lblNickName1 = new JLabel("Player 1: ");
        lblNickName1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNickName1.setBounds(270, 300, 100, 24);

        nickName2 = new JTextField();
        nickName2.setBounds(327, 333, 254, 24);
        nickName2.setColumns(10);
        lblNickName2 = new JLabel("Player 2: ");
        lblNickName2.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNickName2.setBounds(270, 333, 100, 24);

        twoPlayerButton = new JButton("Play against a friend");
        twoPlayerButton.setBackground(yellowColor);
        twoPlayerButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        twoPlayerButton.setBounds(297, 368, 319, 62);
        twoPlayerButton.addActionListener(c);

        botButton = new JButton("Play against Mr. Bot");
        botButton.setBackground(yellowColor);
        botButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        botButton.setBounds(297, 434, 319, 62);
        botButton.addActionListener(c);

        howToButton = new JButton("How to play");
        howToButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        howToButton.setBackground(yellowColor);
        howToButton.setBounds(297, 502, 319, 62);
        howToButton.addActionListener(c);

        rexXLabel1 = new JLabel("");
        rexXLabel1.setIcon(redXIcon);
        rexXLabel1.setBounds(60, 146, 150, 150);

        blueOLabel1 = new JLabel("");
        blueOLabel1.setIcon(blueOIcon);
        blueOLabel1.setBounds(103, 411, 128, 115);

        redXLabel2 = new JLabel("");
        redXLabel2.setIcon(redXIcon);
        redXLabel2.setBounds(653, 403, 128, 123);

        blueOLabel2 = new JLabel("");
        blueOLabel2.setIcon(blueOIcon);
        blueOLabel2.setBounds(677, 146, 130, 115);
        //</editor-fold>


        homeScreen();
    }

    public void setCursor(String filename) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorIcon = toolkit.getImage(filename);
        Dimension bestSize = toolkit.getBestCursorSize(40, 40);
        Image cursorIconScaledInstance = cursorIcon.getScaledInstance(bestSize.width, bestSize.height, Image.SCALE_FAST);
        int centerX = bestSize.width / 2;
        int centerY = bestSize.height / 2;
        Point hotSpot = new Point(centerX, centerY);
        Cursor cursor = toolkit.createCustomCursor(cursorIconScaledInstance, hotSpot, "cursor");
        seedButtonPanel.setCursor(cursor);
    }

    public void gameScreen() {
        menuWindow.setVisible(false);
        contentPane.add(backPanel);
        backPanel.setLayout(null);
        backPanel.add(topLogo);
        backPanel.add(seedButtonPanel);
        backPanel.add(rematchButton);
        backPanel.add(homeButton);
        backPanel.add(exitButton);
        backPanel.add(scoreLabel);
        backPanel.add(bombButton);
        backPanel.add(switchButton);
        backPanel.add(player1Name);
        backPanel.add(player2Name);
        backPanel.add(player1Score);
        backPanel.add(player2Score);
        backPanel.add(powerUps);
        backPanel.add(highScoreLabel);
        backPanel.add(highScoreText);

        player1Score.setText(Integer.toString(model.player1Wins));
        player2Score.setText(Integer.toString(model.player2Wins));
        player1Name.setText(model.player1Name);
        player2Name.setText(model.player2Name);
        backPanel.setVisible(true);
    }

    public void homeScreen() {
        backPanel.setVisible(false);
        howToPlayPanel.setVisible(false);
        contentPane.add(menuWindow);
        menuWindow.add(logoLabel);
        menuWindow.add(botButton);
        menuWindow.add(twoPlayerButton);
        menuWindow.add(howToButton);
        menuWindow.add(exitButton);
        menuWindow.add(lblNickNameTitle);
        menuWindow.add(nickName1);
        menuWindow.add(lblNickName1);
        menuWindow.add(nickName2);
        menuWindow.add(lblNickName2);
        menuWindow.add(rexXLabel1);
        menuWindow.add(blueOLabel1);
        menuWindow.add(redXLabel2);
        menuWindow.add(blueOLabel2);
        menuWindow.setVisible(true);
    }

    public void howToScreen() {
        backPanel.setVisible(false);
        menuWindow.setVisible(false);
        contentPane.add(howToPlayPanel);
        howToPlayPanel.add(howToPlayLabel);
        howToPlayPanel.add(infoText);
        howToPlayPanel.add(oKButton);
        howToPlayPanel.setVisible(true);

    }



    public int showOptionDialog(String message, String title, Icon icon) {
        return JOptionPane.showOptionDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, null, JOptionPane.NO_OPTION);
    }

    public Object showRdbOptionDialog(String message, String title) {
        return JOptionPane.showInputDialog(this, message, title, JOptionPane.PLAIN_MESSAGE, null, difficulties, "Intermediate");
    }
}
