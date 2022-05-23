package controller;

import view.Chessboard;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GameController {
    public static Chessboard chessboard;
    private static String path = "D:\\南方科技大学\\OneDrive - 南方科技大学\\大一下\\JAVA\\ChessDemo\\ChessDemo\\resource";
    private static String filenameTemp;


    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public List<String> loadGameFromFile(String path) {

        String p = path;
        File tempFile = new File(p.trim());
        String fileName = tempFile.getName();
        if (!fileName.endsWith(".txt")){
            JOptionPane.showMessageDialog(null, "文件格式错误！\n错误代码:104");
            return null;
        }

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
        Chessboard.sb.setLength(0);
        chessboard.setCurrentColor();
        chessboard.initialize();
    }

    public void JFileChooserTest(){
        JFileChooser jFileChooser = new JFileChooser();
    }

    public static void Save() throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String dates = dateFormat.format(new Date());
        creatTxtFile(dates);
        chessboard.data(Chessboard.sb);
        String A = Chessboard.sb.toString();
        writeTxtFile(A);
    }


    public static  boolean creatTxtFile(String name) throws IOException {
        File file = new File(path+"/save");
        //校验该目录是否存在，如果存在则直接写入、否则新建文件夹并写入文件
        if (!file.exists() && !file.isDirectory()) {
            System.out.println("不存在");
            file.mkdir();
        }else {
            System.out.println("目录存在");
        }
        boolean flag = false;
        //在这里注意file是\斜杠，得通过\\进行转换
        filenameTemp = file+"\\" + name + ".txt";
        File filename = new File(filenameTemp);
        if (!filename.exists()) {
            filename.createNewFile();
            flag = true;
        }
        return flag;
    }

    public static boolean writeTxtFile(String newStr) throws IOException {
        // 先读取原有文件内容，然后进行写入操作
        boolean flag = false;
        String filein = newStr + "\r\n";
        String temp = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            // 文件路径
            File file = new File(filenameTemp);
            // 将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();

            // 保存该文件原有的内容
            for (int j = 1; (temp = br.readLine()) != null; j++) {
                buf = buf.append(temp);
                // System.getProperty("line.separator")
                // 行与行之间的分隔符 相当于“\n”
                buf = buf.append(System.getProperty("line.separator"));
            }
            buf.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            flag = true;
        } catch (IOException e1) {
            // TODO 自动生成 catch 块
            throw e1;
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return flag;

    }

    public List<String> backGameFromFile(String path,int c) {
        try {
            List<String> chessData = Files.readAllLines(Path.of(path));
            chessboard.backGame(chessData,c);
            return chessData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
