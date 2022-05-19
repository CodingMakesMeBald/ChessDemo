package controller;

import view.Chessboard;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GameController {
    private Chessboard chessboard;

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public List<String> loadGameFromFile(String path) {
        try {
            List<String> chessData = Files.readAllLines(Path.of(path));
            chessboard.loadGame(chessData);
            return chessData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Chessboard getChessboard() {
        return chessboard;
    }

    public void initialize(){
        chessboard.setCurrentColor();
        chessboard.initialize();
    }

    public void JFileChooserTest(){
        JFileChooser jFileChooser = new JFileChooser();
    }

}
