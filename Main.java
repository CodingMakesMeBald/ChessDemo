//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import javax.swing.SwingUtilities;
import view.ChessGameFrame;
import view.StartFrame;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StartFrame a = new StartFrame(1000, 700);
            a.setVisible(true);
            //ChessGameFrame mainFrame = new ChessGameFrame(1000, 760);
            //mainFrame.setVisible(true);
        });
    }
}