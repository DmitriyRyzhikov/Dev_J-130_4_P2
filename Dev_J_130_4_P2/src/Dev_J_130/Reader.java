
package Dev_J_130;

import java.util.concurrent.TimeUnit;


public class Reader implements Runnable{
    
    private final Database database;

    public Reader(Database database) {
        this.database = database;
    }
    
    

    @Override
    public void run() {
        while(true){
        database.connectionReaders();
        database.reading();
        database.disconnectionReaders();
        int timeOut = Randomer.getTimeOut();
            System.out.println(Thread.currentThread().getName() + " отсоединился. Вернусь через " + timeOut + " секунд.");
            try {
                TimeUnit.SECONDS.sleep(timeOut);
            } catch (InterruptedException ex) {   }
        }      
    }
}
