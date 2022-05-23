package view;

import Music.Music;
import controller.GameController;
import controller.Timers;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

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
    JLabel roundLabel;
    public static JLabel timer;
    public static Timers remainingTime = new Timers();
    int count2 = 0;



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
        addBackButton();
        addLoadButton();
        addCurrentPlayerLabel();
        addResetButton();
        addSaveButton();

        //addBackground();

        String filepath = "D:\\南方科技大学\\OneDrive - 南方科技大学\\大一下\\JAVA\\ChessDemo\\ChessDemo\\music\\music.wav";
        Music musicObject = new Music();
        musicObject.playMusic(filepath);

        Time();
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
        addBackground();
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {//行棋方（冒号后）
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

    private void addBackground(){
        JPanel imPanel = (JPanel) this.getContentPane();
        imPanel.setOpaque(false);// 设置面板透明
        ImageIcon icon1 = new ImageIcon("images\\bg.jpeg");// 背景图
        JLabel label = new JLabel(icon1);// 向标签中加入图片
        label.setBounds(0, 0, getWidth(), getHeight());// 设置标签与窗口一样大
        icon1.setImage(icon1.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));// 图片自适应窗口大小
        getLayeredPane().add(label, Integer.valueOf(Integer.MIN_VALUE));// 标签添加到面板
    }

    public void Time(){
        remainingTime.start();
        timer=new JLabel("Remaining Time: " + remainingTime);
        timer.setLocation(760, 760 / 10+40);
        timer.setSize(200, 60);
        timer.setFont(new Font("Rockwell", Font.BOLD, 20));
        timer.setVisible(true);
        add(timer);
    }

    public void addSaveButton(){
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH, HEIGTH / 10 + 480);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.addActionListener((e) ->
                {
                    JOptionPane.showMessageDialog(null, "保存成功！");
                    try {
                        GameController.Save();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
        );
        add(button);
    }

    private void addBackButton() {
        JButton button = new JButton("back");
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {

            count2++;
            System.out.println("Click back");
            System.out.println(count2);
            try {
                Chessboard.creatTxtFile("xxx");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            String FailPath = "D:\\南方科技大学\\OneDrive - 南方科技大学\\大一下\\JAVA\\ChessDemo\\ChessDemo\\file\\xxx.txt";

            Chessboard.saveGame(FailPath);
            String path = "D:\\南方科技大学\\OneDrive - 南方科技大学\\大一下\\JAVA\\ChessDemo\\ChessDemo\\file\\xxx.txt";
            gameController.backGameFromFile(path,count2);
        });
    }








}