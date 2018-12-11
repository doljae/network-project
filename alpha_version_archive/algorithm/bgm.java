import java.io.FileInputStream;

import javazoom.jl.player.Player;
 // http://www.javazoom.net/javalayer/sources.html JLayer 다운받아서 jar 등록하기
 // src 하위 폴더에 effect 생성해서 음원 넣기!

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
        MusicThread t1 = new MusicThread("test"); // Chase로 바꿔주어야합니다

        t1.start();
        Thread.sleep(10000); // if문으로 바꿔주어야합니다
        t1.stopMusic();
    }
}