package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class QueenChessComponent extends ChessComponent{

    private static Image QUEEN_WHITE;
    private static Image QUEEN_BLACK;

    private Image queenImage;

    public void loadResource() throws IOException {
        if (QUEEN_WHITE == null) {
            QUEEN_WHITE = ImageIO.read(new File("./images/white-queen.png"));
        }

        if (QUEEN_BLACK == null) {
            QUEEN_BLACK = ImageIO.read(new File("./images/black-queen.png"));
        }
    }

    private void initiateQueenImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                queenImage = QUEEN_WHITE;
            } else if (color == ChessColor.BLACK) {
                queenImage = QUEEN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateQueenImage(color);
    }

    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (source.getX() == destination.getX()) {
            int row = source.getX();
            for (int col = Math.min(source.getY(), destination.getY()) + 1;
                 col < Math.max(source.getY(), destination.getY()); col++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else if (source.getY() == destination.getY()) {
            int col = source.getY();
            for (int row = Math.min(source.getX(), destination.getX()) + 1;
                 row < Math.max(source.getX(), destination.getX()); row++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else if (Math.abs(source.getX() - destination.getX()) != Math.abs(source.getY() - destination.getY())){//不同行列且不共斜线
            return false;
        }

        if (destination.getX() - source.getX() < 0 && destination.getY() - source.getY() < 0){//往左上移动(-, -)
            for (int i = 1; i < Math.abs(destination.getX() - source.getX()); i++) {
                if (!(chessComponents[source.getX() - i][source.getY() - i] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        }
        if (destination.getX() - source.getX() < 0 && destination.getY() - source.getY() > 0){//往右上方移动(-, +)
            for (int i = 1; i < Math.abs(destination.getX() - source.getX()); i++) {
                if (!(chessComponents[source.getX() - i][source.getY() + i] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        }
        if (destination.getX() - source.getX() > 0 && destination.getY() - source.getY() < 0){//往左下方移动(+, -)
            for (int i = 1; i < Math.abs(destination.getX() - source.getX()); i++) {
                if (!(chessComponents[source.getX() + i][source.getY() - i] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        }
        if (destination.getX() - source.getX() > 0 && destination.getY() - source.getY() > 0){//往右下方移动(+, +)
            for (int i = 1; i < Math.abs(destination.getX() - source.getX()); i++) {
                if (!(chessComponents[source.getX() + i][source.getY() + i] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(queenImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
