
package Dev_J_130;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Randomer {
    
    public static int getTimeOut(){
        
        final Random random = new Random();
        int tm = random.nextInt(15);
        if (tm < 5)
            tm = 5;
        return tm;
    }
    public static int getNumberOfProduct()
    {
        final Random random = new Random();
        int np = random.nextInt(10);
        if (np==0)
            np = 1;
        return np;
    } 
    public static String timeNow(){
        
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss "));
    }
}
