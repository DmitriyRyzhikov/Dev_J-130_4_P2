
package Dev_J_130;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Processor {
    
    private final Set<Thread> actors = new HashSet<>();
    private final ThreadGroup readers = new ThreadGroup("Читатели");
    private final ThreadGroup writers = new ThreadGroup("Писатели");
        
    public void startProcess (int re, int wr, int seconds){ 
    //создаем и запускаем базу данных
        Database database = new Database(readers, writers);
        System.out.println("База данных готова к работе...");
        
    //создаем и запускаем поток таймера, который определяет продолжительность работы приложения
        Thread timerThread = new TimeFactory(seconds, readers, writers);
        timerThread.start();
        System.out.println("Таймер запущен. Время жизни приложения - " + seconds + " секунд.");
        
    //создаем потоки для читателей и писателей и укладываем их в множество actors
        Thread[] reader = new Thread[re];
        for(int i =0; i<reader.length; i++){        
           reader[i] = new Thread(readers, new Reader(database), "Читатель-" +(i+1));
           actors.add(reader[i]);
           }
        Thread[] writer = new Thread[wr];
        for(int i =0; i<writer.length; i++){        
            writer[i] = new Thread(writers, new Writer(database), "Писатель-" +(i+1));
            actors.add(writer[i]);        
            }
    //с интервалом в 2 секунды запускаем читателей и писателей из множества actors    
        actors.forEach(x -> {    
            System.out.println(TimeFactory.timeNow() + x.getName() + 
                " создан и будет пытаться подключиться к БД.");
            x.start();
            try {
               TimeUnit.SECONDS.sleep(2); } 
            catch (InterruptedException ex) { }
                     });   
        }
}   

