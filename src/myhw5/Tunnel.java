package myhw5;


import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;

public class Tunnel extends Stage {
    public static ArrayBlockingQueue abq = new ArrayBlockingQueue((int) (MainClass.CARS_COUNT / 2));


    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                abq.put(c.getName());
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                abq.take();
                System.out.println( c.getName() + " закончил этап: " + description);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}