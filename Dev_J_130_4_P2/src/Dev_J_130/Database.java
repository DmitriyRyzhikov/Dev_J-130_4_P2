
package Dev_J_130;

import java.util.concurrent.TimeUnit;

public class Database {
    
    private int readersCount = 0;
    private boolean writerConnected;
    private final String readers;
    private final String writers;

    public Database(ThreadGroup readers, ThreadGroup writers) {
        this.readers = readers.getName();
        this.writers = writers.getName();
    }
    
    public synchronized void connection() throws InterruptedException{
        if(getThreadGroupName().equals(readers)){
           while(writerConnected) {
            stateWait();  }
        readersCount++;
        }
        if(getThreadGroupName().equals(writers)){
           while(writerConnected || readersCount>0) {
            stateWait();  }           
        writerConnected = true;
        }
        System.out.println(TimeFactory.timeNow() + getThreadName() + " подключился к БД. Сейчас к ней подключено читателей - " + 
               readersCount + " и писателей - " + (writerConnected? 1:0));  
    }

    public synchronized void disconnection(){
        if(getThreadGroupName().equals(readers)){
           readersCount--;
            if(readersCount==0)
               sendNotify();
            else
                System.out.println(TimeFactory.timeNow() + getThreadName() + " отключился от БД. Сейчас к ней подключено читателей - " + 
               readersCount + " и писателей - " + (writerConnected? 1:0)); 
        }
        if(getThreadGroupName().equals(writers)){
           writerConnected = false;
           sendNotify();
        }
    }
    public void works() throws InterruptedException{
        int waitTime = TimeFactory.getTimeOut();
        System.out.println(TimeFactory.timeNow() + getThreadName() + " начал действовать. Будет" 
               + readOrWrite() + waitTime + " секунд(ы).");
        TimeUnit.SECONDS.sleep(waitTime);
        System.out.println(TimeFactory.timeNow() + getThreadName() + " закончил" + readOrWrite());     
    }
    
    private String getThreadName(){
        String threadName = Thread.currentThread().getName();         
        return threadName;
    }
    private String getThreadGroupName(){
        String threadGroupName = Thread.currentThread().getThreadGroup().getName();         
        return threadGroupName;
    }
    private void sendNotify(){
        notifyAll();
        System.out.println(TimeFactory.timeNow() + getThreadName() + " отключился от БД. Сейчас к ней подключено читателей - " + 
               readersCount + " и писателей - " + (writerConnected? 1:0) + "\nБД свободна. Уведомление отправлено.");    
    }
    private void stateWait() throws InterruptedException{
            System.out.println(TimeFactory.timeNow() + getThreadName() + 
                    " не может подключиться к базе данных. БД занята. Ждет уведомления.");
            wait();   
    }
    private String readOrWrite(){
        return (getThreadGroupName().equals(readers))? " читать " : " писать ";
    }
}