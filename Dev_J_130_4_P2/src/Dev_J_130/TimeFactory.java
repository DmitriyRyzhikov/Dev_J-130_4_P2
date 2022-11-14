
package Dev_J_130;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TimeFactory extends Thread{
    
    private final int seconds;
    private final ThreadGroup readers;
    private final ThreadGroup writers;

    public TimeFactory(int seconds, ThreadGroup readers, ThreadGroup writers) {
        this.seconds = seconds;
        this.readers = readers;
        this.writers = writers;
    }
      
    public static int getTimeOut(){
        
        final Random random = new Random();
        int tm = random.nextInt(10);
        if (tm < 3)
            tm = 3;
        return tm;
    } 
    public static String timeNow(){        
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss "));
    }
    @Override
    public void run() {
        int duration = seconds;
        while(duration>0){
             try { TimeUnit.SECONDS.sleep(1); } 
             catch (InterruptedException ex) { }
             duration--;
            }
        System.out.println("Таймер завершил работу. Начат процесс завершения работы Читателей и Писателей.");
        readers.interrupt();
        writers.interrupt();
    }   
}
