
package Dev_J_130;

import java.util.concurrent.TimeUnit;

public class Writer implements Runnable{
    
    private final Database database;
    
    public Writer(Database database){
        this.database = database;
    }

    @Override
    public void run() {
       while(true) {
        database.connectionWriters();
        database.writing();
        database.disconnectionWriters();
        int timeOut = Randomer.getTimeOut();
            System.out.println(Thread.currentThread().getName() + " отсоединился. Вернусь через " + timeOut + " секунд.");
            try {
                TimeUnit.SECONDS.sleep(timeOut);
            } catch (InterruptedException ex) {   }
        }
    }
    
}
