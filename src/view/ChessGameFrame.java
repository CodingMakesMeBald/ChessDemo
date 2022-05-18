package view;

import Music.Music;
import controller.GameController;

import javax.swing.*;
import java.awt.*;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    JLabel statusLabel;


    public ChessGameFrame(int width, int height) {
        setTitle("Chess"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addLabel();//create label
        addChessboard();//set label
        addHelloButton();
        addLoadButton();
        addCurrentPlayerLabel();
        addResetButton();

        String filepath = "D:\\南方科技大学\\OneDrive - 南方科技大学\\大一下\\JAVA\\ChessDemo\\ChessDemo\\resource\\music.wav";
        Music musicObject = new Music();
        musicObject.playMusic(filepath);
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        chessboard.setStatusLabel(statusLabel);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        add(chessboard);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        statusLabel = new JLabel("WHITE");
        statusLabel.setLocation(350, 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    public JLabel getStatusLabel(){
        return statusLabel;
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示 Hello, world!
     */

    private void addHelloButton() {
        JButton button = new JButton("Show Hello Here");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this,"Input Path here");
            gameController.loadGameFromFile(path);
        });
    }

    private void addCurrentPlayerLabel(){
        JLabel currentPlayer = new JLabel("CurrentPlayer:");
        currentPlayer.setLocation(200, 10 );
        currentPlayer.setSize(200, 60);
        currentPlayer.setFont(new Font("Rockwell", Font.BOLD, 20));//字体
        add(currentPlayer);
    }


    private void addResetButton()  {
        JButton button = new JButton("Reset");
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.addActionListener((e) ->
                {
                    System.out.println("Reset Game!! ");
                    gameController.initialize();
                }
        );
        add(button);
    }


}