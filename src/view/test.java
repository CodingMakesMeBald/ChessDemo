package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class test {
    public static void main(String[] args) {
        Frame frame = new Frame();
        frame.setLocation(400,300);
        frame.setSize(800, 600);
        frame.setBackground(new Color(126, 14, 47));
        Button button = new Button("sleep");

        myListener ml = new myListener();
        button.addActionListener(ml);

        //frame.add(button,BorderLayout.CENTER);
        //frame.pack();//自动填充
        frame.setVisible(true);

        frame.setResizable(false);//不可拉伸

       frame.addWindowListener(new WindowAdapter() {
           @Override
           public void windowClosing(WindowEvent e) {
               System.exit(0);
           }
       });

    }
}

class myListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e){
        System.out.println("李涵睡觉");
    }
}
