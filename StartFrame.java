package view;

import javax.swing.*;
import java.awt.*;

public class StartFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGTH;
    JButton start;

    public StartFrame(int width, int height){
        setTitle("Start");
        this.WIDTH = width;
        this.HEIGTH = height;
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addBackground();



        JButton start = new JButton("Start Game");
        start.setLocation(2 * WIDTH / 5, 2 * HEIGTH / 3);

        start.setSize(200, 60);
        start.setFont(new Font("Rockwell", Font.BOLD, 20));
        start.setVisible(true);
        start.addActionListener(e ->{
            this.dispose();
            ChessGameFrame mainFrame = new ChessGameFrame(1000, 760);
            mainFrame.setVisible(true);
        });

        this.add(start);

    }

    private void addBackground(){
        JPanel imPanel = (JPanel) this.getContentPane();
        imPanel.setOpaque(false);// 设置面板透明
        ImageIcon icon1 = new ImageIcon("images\\start.jpeg");// 背景图
        JLabel label = new JLabel(icon1);// 向标签中加入图片
        label.setBounds(0, 0, getWidth(), getHeight());// 设置标签与窗口一样大
        icon1.setImage(icon1.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));// 图片自适应窗口大小
        getLayeredPane().add(label, Integer.valueOf(Integer.MIN_VALUE));// 标签添加到面板
    }




}
