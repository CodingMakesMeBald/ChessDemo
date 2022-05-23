package controller;

import view.ChessGameFrame;
import view.Chessboard;

import static view.ChessGameFrame.remainingTime;

public class Timers extends Thread{
    final int InitialTime = 30;
    public int Time = InitialTime;
    public int counting;
    public void run(){
        TimeDelete();

    }

    public  void TimeDelete(){
        while (Time >= 0){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            counting++;
            if(counting == 10){
                Time--;
                counting = 0;
            }
            ChessGameFrame.timer.setText("Remaining Time: " + remainingTime);
            if(Time <= 0){
                Chessboard.swapColor();
                Time = InitialTime;
            }
        }
    }



    public String toString(){
        return String.valueOf(this.Time);
    }
}