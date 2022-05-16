package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BishopChessComponent extends ChessComponent{

    private static Image BISHOP_WHITE;
    private static Image BISHOP_BLACK;

    private Image bishopImage;

    public void loadResource() throws IOException {
        if (BISHOP_WHITE == null) {
            BISHOP_WHITE = ImageIO.read(new File("./images/white-bishop.png"));
        }

        if (BISHOP_BLACK == null) {
            BISHOP_BLACK = ImageIO.read(new File("./images/black-bishop.png"));
        }
    }

    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                bishopImage = BISHOP_WHITE;
            } else if (color == ChessColor.BLACK) {
                bishopImage = BISHOP_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
    }

    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (Math.abs(source.getX() - destination.getX()) != Math.abs(source.getY() - destination.getY())){
            return false;
        }//不是斜线直接排除
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
        g.drawImage(bishopImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
