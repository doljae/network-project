import java.io.FileInputStream;

import javazoom.jl.player.Player;
 // http://www.javazoom.net/javalayer/sources.html JLayer �ٿ�޾Ƽ� jar ����ϱ�
 // src ���� ������ effect �����ؼ� ���� �ֱ�!

class MusicThread extends Thread {
    
   public Player p;
   public String str;
   public int check = 0;
   
   public MusicThread(String str){
        try {
         this.str = str;
      } catch (Exception e) {
         e.printStackTrace();
      }
    }

    public void run(){
        while(check==0){
            try {
               p = new Player(new FileInputStream("src\\effect\\"+str+".mp3"));
            p.play();
         } catch (Exception e1) {
            e1.printStackTrace();
         }
            
        } 
    } 
    public void stopMusic() {
       check=1;
       p.close();
    }
}

public class QuizSound {
   public static void main(String[] args) throws Exception {
        MusicThread t1 = new MusicThread("test"); // Chase�� �ٲ��־���մϴ�

        t1.start();
        Thread.sleep(10000); // if������ �ٲ��־���մϴ�
        t1.stopMusic();
    }
}