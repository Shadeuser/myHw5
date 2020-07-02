package myhw5;



import javax.imageio.stream.ImageInputStream;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private static boolean weHaveAWinner = false;
    private static Object monitor = new Object();
    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            MainClass.carsStart.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goCar() {
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        MainClass.carsRacing.countDown();
        synchronized (monitor) {
            if (!weHaveAWinner) {
                weHaveAWinner = true;
                System.out.println(this.name + " ОДЕРЖАЛ ПОБЕДУ!");
            }
        }


    }
}
