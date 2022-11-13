
package Dev_J_130;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Processor{
    
    private final Set<Thread> actors = new HashSet<>();
    private final ThreadGroup readers = new ThreadGroup("Читатели");
    private final ThreadGroup writers = new ThreadGroup("Писатели");
    
    
    public void start(int re, int wr){
        
    //создаем и запускаем базу данных
       Database database = new Database();
        System.out.println("База данных готова к работе...");
    //создаем потоки для читателей и писателей

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
    actors.forEach(x -> {
    
        x.start();
        System.out.println(Randomer.timeNow() + x.getName() + 
                " создан и приступил к выполнению своей работы");
           try {
               TimeUnit.SECONDS.sleep(2);
           } catch (InterruptedException ex) { }
    });   
    }
    }   

