
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
            try{
                database.connection();               
                database.works();
                database.disconnection();
                int timeOut = TimeFactory.getTimeOut();
                System.out.println(TimeFactory.timeNow() + Thread.currentThread().getName() +
                       " отключен от БД. Вернется через " + timeOut + " секунд(ы).");               
                TimeUnit.SECONDS.sleep(timeOut);
                } 
            catch (InterruptedException ex) { break; }     
        }
        System.out.println(TimeFactory.timeNow() + Thread.currentThread().getName() + " завершил работу в приложении."); 
    }   
}
