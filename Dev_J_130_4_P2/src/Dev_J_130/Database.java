
package Dev_J_130;

import java.util.concurrent.TimeUnit;

public class Database {
    
    private int readersCount = 0;
    private boolean writerConnected;
     
    public synchronized void connectionReaders(){
        while(writerConnected) {
            try {
                System.out.println(Randomer.timeNow() + getThreadName() + " не может подключиться к базе данных. База занята писателем. Ждем.");
                wait(); } 
            catch (InterruptedException ex) {  }
            }
        readersCount++;
        System.out.println(Randomer.timeNow() + getThreadName() + " подключился к базе данных. Сейчас к ней подключено читателей - " + 
               readersCount + " и писателей - " + (writerConnected? 1:0)); 
    } 
    public synchronized void connectionWriters(){        
        while(writerConnected || readersCount>0) {
            try {
                System.out.println(Randomer.timeNow() + getThreadName() + " не может подключиться к базе данных. База занята. Ждем.");
                wait(); } 
            catch (InterruptedException ex) {  }
            }            
        writerConnected = true;
        System.out.println(Randomer.timeNow() + getThreadName() + " подключился к базе данных. Сейчас к ней подключено читателей - " + 
               readersCount + " и писателей - " + (writerConnected? 1:0)); 
    } 
       
    public synchronized void disconnectionReaders(){
        readersCount--;
        if(readersCount==0)
            notifyAll();               
        System.out.println(Randomer.timeNow() + getThreadName() + " отключился от базы данных. Сейчас к ней подключено читателей - " + 
               readersCount + " и писателей - " + (writerConnected? 1:0));     
    }
    public synchronized void disconnectionWriters(){        
        writerConnected = false;
        notifyAll();        
        System.out.println(Randomer.timeNow() + getThreadName() + " отключился от базы данных. Сейчас к ней подключено читателей - " + 
               readersCount + " и писателей - " + (writerConnected? 1:0));
                
    }
    public void reading(){
       int waitTime = Randomer.getTimeOut();
       System.out.println(Randomer.timeNow() + getThreadName() + " начал читать. Будет читать " + waitTime + " секунд.");
           try {
               TimeUnit.SECONDS.sleep(waitTime);
           } catch (InterruptedException ex) { }
       System.out.println(Randomer.timeNow() + getThreadName() + " закончил читать.");       
    }
    
    public void writing(){
       int waitTime = Randomer.getTimeOut();    
       System.out.println(Randomer.timeNow() + getThreadName() + " начал писать. Будет писать " + waitTime + " секунд.");    
       try {
               TimeUnit.SECONDS.sleep(waitTime);
           } catch (InterruptedException ex) { }
       System.out.println(Randomer.timeNow() + getThreadName() + " закончил писать.");     
  }

    private String getThreadName(){
        String threadName = Thread.currentThread().getName();         
        return threadName;
    }
}