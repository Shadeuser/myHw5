package myhw5;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.CountDownLatch;


public class MainClass {
    public static CountDownLatch carsStart;
    public static CountDownLatch carsRacing;

    public static final int CARS_COUNT = 4;

    public static void main(String[] args) {

        System.out.println(" ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        carsStart = new CountDownLatch(CARS_COUNT);
        carsRacing = new CountDownLatch(CARS_COUNT);


        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        try {
            carsStart.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println( " ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        for (Car thisCar : cars) {
            new Thread(() -> {
                thisCar.goCar();
            }).start();
        }
        try {
            carsRacing.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(" ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}