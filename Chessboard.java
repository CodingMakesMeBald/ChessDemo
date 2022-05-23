package view;


import Music.Music;
import Music.Click;
import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;

import static view.ChessGameFrame.remainingTime;


/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */

    private static final int CHESSBOARD_SIZE = 8;//全局常量

    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];//二维数组代表棋盘，数组成员为棋子抽象类
    private  static ChessColor currentColor = ChessColor.WHITE;//目前行棋方
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);//鼠标点击
    private final int CHESS_SIZE;//棋子大小
    private static JLabel statusLabel;
    private int chessround;
    int countRound = 0;
    static int r = 0;
    public static StringBuilder sb = new StringBuilder();

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    public void setCurrentColor(){
        currentColor = ChessColor.WHITE;
        statusLabel.setText(currentColor.toString());
    }

    public ChessComponent[][] getChessboard() {
        return chessComponents;
    }

    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout  .设置布局？
        setSize(width, height);
        CHESS_SIZE = width / 8;//棋盘大小
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

        initialize();

    }



    public void initialize(){


        clickController.setFirst(null);
        initiateEmptyChessboard();
        ChessComponent.round = 0;



        // FIXME: Initialize chessboard for testing only.
        //rook
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
        //king
        initKingOnBoard(0, 4, ChessColor.BLACK);
        initKingOnBoard(7, 4, ChessColor.WHITE);
        //pawn
        initPawnOnBoard(1, 0, ChessColor.BLACK);
        initPawnOnBoard(1, 1, ChessColor.BLACK);
        initPawnOnBoard(1, 2, ChessColor.BLACK);
        initPawnOnBoard(1, 3, ChessColor.BLACK);
        initPawnOnBoard(1, 4, ChessColor.BLACK);
        initPawnOnBoard(1, 5, ChessColor.BLACK);
        initPawnOnBoard(1, 6, ChessColor.BLACK);
        initPawnOnBoard(1, 7, ChessColor.BLACK);
        initPawnOnBoard(6, 0, ChessColor.WHITE);
        initPawnOnBoard(6, 1, ChessColor.WHITE);
        initPawnOnBoard(6, 2, ChessColor.WHITE);
        initPawnOnBoard(6, 3, ChessColor.WHITE);
        initPawnOnBoard(6, 4, ChessColor.WHITE);
        initPawnOnBoard(6, 5, ChessColor.WHITE);
        initPawnOnBoard(6, 6, ChessColor.WHITE);
        initPawnOnBoard(6, 7, ChessColor.WHITE);
        //knight
        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(0, 6, ChessColor.BLACK);
        initKnightOnBoard(7, 1, ChessColor.WHITE);
        initKnightOnBoard(7, 6, ChessColor.WHITE);
        //bishop
        initBishopOnBoard(0, 2, ChessColor.BLACK);
        initBishopOnBoard(0, 5, ChessColor.BLACK);
        initBishopOnBoard(7, 2, ChessColor.WHITE);
        initBishopOnBoard(7, 5, ChessColor.WHITE);
        //queen
        initQueenOnBoard(0, 3, ChessColor.BLACK);
        initQueenOnBoard(7, 3, ChessColor.WHITE);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessComponents[i][j].chessComponent = null;
            }

        }

        repaint();

    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void passPawn(ChessComponent chess1, ChessComponent chess2){

    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {

        String filepath = "D:\\南方科技大学\\OneDrive - 南方科技大学\\大一下\\JAVA\\ChessDemo\\ChessDemo\\music\\click(1).wav";
        Click musicObject = new Click();
        musicObject.playMusic(filepath);

        // Note that chess1 has higher priority, 'destroys' chess2 if exists.


        if (chess2 instanceof KingChessComponent && chess2.getChessColor() == ChessColor.BLACK){
            JOptionPane.showMessageDialog(null, "WHITE WIN!");
            this.setCurrentColor();
            Chessboard.swapColor();
            this.initialize();
            return;
        }
        if (chess2 instanceof KingChessComponent && chess2.getChessColor() == ChessColor.WHITE){
            JOptionPane.showMessageDialog(null, "BLACK WIN!");
            this.setCurrentColor();
            Chessboard.swapColor();
            this.initialize();
            return;
        }

        remainingTime.Time = 30;
        remainingTime.counting = 0;
        ChessComponent.round++;//回合数加一

        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        if (chess1 instanceof PawnChessComponent){//兵每走一步其步数加一
            chess1.counter ++;
            chess1.counter1 = ChessComponent.round;
        }

        //升变
        if ( chess2.getChessboardPoint().getX() == 0 && chess1 instanceof PawnChessComponent
                && chess1.getChessColor() == ChessColor.WHITE){
            remove(chess1);

            int sl = JOptionPane.showOptionDialog(null, "Please choose the chess  ", "choose",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,   null, new String[]{"Queen","Rook","Knight","Bishop"}, "Queen");
            sl = sl==-1 ? 0 : sl;
            switch (sl){
                case 0:
                    add(chess1 = new QueenChessComponent(chess1.getChessboardPoint(), chess1.getLocation(), chess1.getChessColor(),clickController, CHESS_SIZE));
                    break;

                case 1:
                    add(chess1 = new RookChessComponent(chess1.getChessboardPoint(), chess1.getLocation(), chess1.getChessColor(),clickController, CHESS_SIZE));
                    break;

                case 2:
                    add(chess1 = new KnightChessComponent(chess1.getChessboardPoint(), chess1.getLocation(), chess1.getChessColor(),clickController, CHESS_SIZE));
                    break;

                case 3:
                    add(chess1 = new BishopChessComponent(chess1.getChessboardPoint(), chess1.getLocation(), chess1.getChessColor(),clickController, CHESS_SIZE));
                    break;
            }
        }

        if ( chess2.getChessboardPoint().getX() == 7 && chess1 instanceof PawnChessComponent
                && chess1.getChessColor() == ChessColor.BLACK){
            remove(chess1);

            int sl = JOptionPane.showOptionDialog(null, "Please choose the chess  ", "choose",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,   null, new String[]{"Queen","Rook","Knight","Bishop"}, "Queen");
            sl = sl==-1 ? 0 : sl;
            switch (sl){
                case 0:
                    add(chess1 = new QueenChessComponent(chess1.getChessboardPoint(), chess1.getLocation(), chess1.getChessColor(),clickController, CHESS_SIZE));
                    break;

                case 1:
                    add(chess1 = new RookChessComponent(chess1.getChessboardPoint(), chess1.getLocation(), chess1.getChessColor(),clickController, CHESS_SIZE));
                    break;

                case 2:
                    add(chess1 = new KnightChessComponent(chess1.getChessboardPoint(), chess1.getLocation(), chess1.getChessColor(),clickController, CHESS_SIZE));
                    break;

                case 3:
                    add(chess1 = new BishopChessComponent(chess1.getChessboardPoint(), chess1.getLocation(), chess1.getChessColor(),clickController, CHESS_SIZE));
                    break;
            }
        }


        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        chess1.atOnce = false;


        chess1.repaint();
        chess2.repaint();
    }

    public void initiateEmptyChessboard() {
        remainingTime.Time = 30;
        remainingTime.counting = 0;
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }

    }

    public static void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        statusLabel.setText(currentColor.toString());
    }

    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.counter = 0;
        chessComponent.counter1 = 0;
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessData) {
        chessData.forEach(System.out::println);
        initiateEmptyChessboard();

        if (chessData.get(chessData.size() - 1).length() != 1
                || !chessData.get(chessData.size() - 1).equals("b") &&
                !chessData.get(chessData.size() - 1).equals("w") ){
            JOptionPane.showMessageDialog(null, "缺少行棋方！\n错误代码:103");
            return;
        }

        for (int i = chessData.size() - 9; i < chessData.size() - 1; i++){///////////
            if (chessData.get(i).length() != 8){
                JOptionPane.showMessageDialog(null, "棋盘错误！\n错误代码:101");
                return;
            }
        }



        for (int i = chessData.size() - 9; i < chessData.size() - 1; i++){
            for (int j = 0; j < chessData.get(i).length(); j++) {
                if (chessData.get(i).charAt(j) == 'R' || chessData.get(i).charAt(j) == 'Q'
                        || chessData.get(i).charAt(j) == 'K' || chessData.get(i).charAt(j) == 'P'
                        || chessData.get(i).charAt(j) == 'N' || chessData.get(i).charAt(j) == 'B'
                        || chessData.get(i).charAt(j) == 'r' || chessData.get(i).charAt(j) == 'q'
                        || chessData.get(i).charAt(j) == 'k' || chessData.get(i).charAt(j) == 'p'
                        || chessData.get(i).charAt(j) == 'n' || chessData.get(i).charAt(j) == 'b'
                        || chessData.get(i).charAt(j) == '_'){
                }else {
                    JOptionPane.showMessageDialog(null, "棋子错误！\n错误代码:102");
                    return;
                }
            }
        }




        for (int i = 0; i < chessData.size(); i++) {
            if (chessData.get(i).length() == 8){
                for (int j = 0; j < chessData.get(i).length(); j++) {
                    if (chessData.get(i).charAt(j) == 'R'){
                        initRookOnBoard(i, j, ChessColor.BLACK);
                    }else if (chessData.get(i).charAt(j) == 'Q'){
                        initQueenOnBoard(i, j, ChessColor.BLACK);
                    }else if (chessData.get(i).charAt(j) == 'K'){
                        initKingOnBoard(i, j, ChessColor.BLACK);
                    }else if (chessData.get(i).charAt(j) == 'P'){
                        initPawnOnBoard(i, j, ChessColor.BLACK);
                    }else if (chessData.get(i).charAt(j) == 'N'){
                        initKnightOnBoard(i, j, ChessColor.BLACK);
                    }else if (chessData.get(i).charAt(j) == 'B'){
                        initBishopOnBoard(i, j, ChessColor.BLACK);
                    }else if (chessData.get(i).charAt(j) == 'r'){
                        initRookOnBoard(i, j, ChessColor.WHITE);
                    }else if (chessData.get(i).charAt(j) == 'q'){
                        initQueenOnBoard(i, j, ChessColor.WHITE);
                    }else if (chessData.get(i).charAt(j) == 'k'){
                        initKingOnBoard(i, j, ChessColor.WHITE);
                    }else if (chessData.get(i).charAt(j) == 'p'){
                        initPawnOnBoard(i, j, ChessColor.WHITE);
                    }else if (chessData.get(i).charAt(j) == 'n'){
                        initKnightOnBoard(i, j, ChessColor.WHITE);
                    }else if (chessData.get(i).charAt(j) == 'b'){
                        initBishopOnBoard(i, j, ChessColor.WHITE);
                    }else if (chessData.get(i).charAt(j) == '_'){
                        continue;
                    }
                }
            }
            if (chessData.get(i).length() == 1){
                if (chessData.get(i).charAt(0) == 'b'){
                    setCurrentColor();
                    swapColor();
                }else if (chessData.get(i).charAt(0) == 'w'){
                    setCurrentColor();
                }
            }


        }


        repaint();
    }

    public void data(StringBuilder sb){

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (chessComponents[i][j] instanceof KingChessComponent){
                    if (chessComponents[i][j].getChessColor() == ChessColor.BLACK){
                        sb.append('K');
                    }
                    else{
                        sb.append('k');
                    }
                }
                if (chessComponents[i][j] instanceof QueenChessComponent){
                    if (chessComponents[i][j].getChessColor() == ChessColor.BLACK){
                        sb.append('Q');
                    }
                    else{
                        sb.append('q');
                    }
                }
                if (chessComponents[i][j] instanceof BishopChessComponent){
                    if (chessComponents[i][j].getChessColor() == ChessColor.BLACK){
                        sb.append('B');
                    }
                    else{
                        sb.append('b');
                    }
                }
                if (chessComponents[i][j] instanceof RookChessComponent){
                    if (chessComponents[i][j].getChessColor() == ChessColor.BLACK){
                        sb.append('R');
                    }
                    else{
                        sb.append('r');
                    }
                }
                if (chessComponents[i][j] instanceof KnightChessComponent){
                    if (chessComponents[i][j].getChessColor() == ChessColor.BLACK){
                        sb.append('N');
                    }
                    else{
                        sb.append('n');
                    }
                }
                if (chessComponents[i][j] instanceof PawnChessComponent){
                    if (chessComponents[i][j].getChessColor() == ChessColor.BLACK){
                        sb.append('P');
                    }
                    else{
                        sb.append('p');
                    }
                }
                if (chessComponents[i][j] instanceof EmptySlotComponent){
                    sb.append('_');
                }
            }
            if (i < 7){
                sb.append('\n');
            }


        }
        sb.append("\n");

        if (currentColor == ChessColor.WHITE){
            sb.append('w');
        }
        else if (currentColor == ChessColor.BLACK){
            sb.append('b');
        }




    }

    public void changeRound() {
        countRound++;
        ///
        data(sb);
    }

    public static void saveGame(String filePath){
        String str = String.valueOf(sb);

        try {

            File file = new File(filePath);

            PrintStream ps = new PrintStream(new FileOutputStream(file));

            ps.println(str);// 往文件里写入字符串

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static boolean creatTxtFile(String countRound) throws IOException {
        String filenameTemp;
        boolean flag = false;
        filenameTemp = "file\\" + countRound + ".txt";
        File filename = new File(filenameTemp);
        if (!filename.exists()) {
            filename.createNewFile();
            flag = true;
        }
        return flag;
    }

    public void backGame(List<String> chessData, int c) {
        chessData.forEach(System.out::println);
        initiateEmptyChessboard();


        for (int i = chessData.size()-9*c-10; i < chessData.size()-9*c-2; i++) {
            for (int j = 0; j < chessData.get(i).length(); j++) {
                if (chessData.get(i).charAt(j) == 'R'){
                    initRookOnBoard(i-chessData.size()+10+9*c, j, ChessColor.BLACK);
                }else if (chessData.get(i).charAt(j) == 'Q'){
                    initQueenOnBoard(i-chessData.size()+10+9*c, j, ChessColor.BLACK);
                }else if (chessData.get(i).charAt(j) == 'K'){
                    initKingOnBoard(i-chessData.size()+10+9*c, j, ChessColor.BLACK);
                }else if (chessData.get(i).charAt(j) == 'P'){
                    initPawnOnBoard(i-chessData.size()+10+9*c, j, ChessColor.BLACK);
                }else if (chessData.get(i).charAt(j) == 'N'){
                    initKnightOnBoard(i-chessData.size()+10+9*c, j, ChessColor.BLACK);
                }else if (chessData.get(i).charAt(j) == 'B'){
                    initBishopOnBoard(i-chessData.size()+10+9*c, j, ChessColor.BLACK);
                }else if (chessData.get(i).charAt(j) == 'r'){
                    initRookOnBoard(i-chessData.size()+10+9*c, j, ChessColor.WHITE);
                }else if (chessData.get(i).charAt(j) == 'q'){
                    initQueenOnBoard(i-chessData.size()+10+9*c, j, ChessColor.WHITE);
                }else if (chessData.get(i).charAt(j) == 'k'){
                    initKingOnBoard(i-chessData.size()+10+9*c, j, ChessColor.WHITE);
                }else if (chessData.get(i).charAt(j) == 'p'){
                    initPawnOnBoard(i-chessData.size()+10+9*c, j, ChessColor.WHITE);
                }else if (chessData.get(i).charAt(j) == 'n'){
                    initKnightOnBoard(i-chessData.size()+10+9*c, j, ChessColor.WHITE);
                }else if (chessData.get(i).charAt(j) == 'b'){
                    initBishopOnBoard(i-chessData.size()+10+9*c, j, ChessColor.WHITE);
                }else if (chessData.get(i).charAt(j) == '_'){
                    continue;
                }
            }
        }
        repaint();
    }
}
