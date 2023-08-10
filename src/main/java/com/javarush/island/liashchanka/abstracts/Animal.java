package com.javarush.island.liashchanka.abstracts;

import com.javarush.island.liashchanka.animal.Position;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import static com.javarush.island.liashchanka.animal.AnimalsEatPersentMap.animalsEatPersentMap;
import static com.javarush.island.liashchanka.constants.Constants.areaX;
import static com.javarush.island.liashchanka.constants.Constants.areaY;

public abstract class Animal {
    private int maxStep;
    private double weight;
     private Position position;
    private String name;
    private String id;

     private boolean isLive = true;

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean isLive) {
        this.isLive = isLive;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMaxStep() {
        return maxStep;
    }

    public void setMaxStep(int maxStep) {
        this.maxStep = maxStep;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Animal(String name, double weight) {
        this.weight = weight;
        this.name = name;
        this.position = new Position(0, 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Animal{" + "hashCode:" + hashCode() +
                ", maxStep=" + maxStep +
                ", weight=" + weight +
                ", isLive=" + isLive +
                ", position=" + position +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }




    public static void move(int maxStep, Animal animal) {


        int x = animal.getPosition().getX();
        int y = animal.getPosition().getY();


        // Генерация рандомного шага от минимального равного 0 до максимального не включая
        //SecureRandom randomStep = new SecureRandom();
        var randomStep = ThreadLocalRandom.current();
        int minStep = 0;
        int step = randomStep.nextInt(maxStep - minStep + 1) + minStep;

        //SecureRandom randomStepXOrY = new SecureRandom();
        var randomStepXOrY = ThreadLocalRandom.current();
        //SecureRandom randomStepRightOrLeft = new SecureRandom();
        var randomStepRightOrLeft = ThreadLocalRandom.current();
        //SecureRandom randomStepTopOrBottom = new SecureRandom();
        var randomStepTopOrBottom = ThreadLocalRandom.current();

        int oneStepXOrY = randomStepXOrY.nextInt(2); // Если 0 движение по Y, если 1 движение по X
        int oneStepRightOrLeft = randomStepRightOrLeft.nextInt(2); // Если 0 движение вправо, если 1 движение по влево
        int oneTopOrBottom = randomStepTopOrBottom.nextInt(2); // Если 0 движение вверх, если 1 движение по вниз


        // System.out.println("Шаг:" + step + ", стартX:" + x + ", стартY:" + y ); // + ", стартX:" + x + ", стартY:" + y
        for (int k = 0; k < step; k++) {
            if (oneStepXOrY == 0) {
                if ((y != areaY - 1) && oneStepRightOrLeft == 0) {
                    y = y + 1;
                } else if (y == 0 && oneStepRightOrLeft == 0) {
                    y = y + 1;
                } else if (y != areaY - 1 && y != 0 && oneStepRightOrLeft == 1) {
                    y = y - 1;
                } else if (y == 0 && oneStepRightOrLeft == 1) {
                    y = y + 1;
                } else {
                    y = y - 1;
                }

            } else {
                if ((x != areaX - 1) && oneTopOrBottom == 0) {
                    x = x + 1;
                } else if (x == 0 && oneTopOrBottom == 0) {
                    x = x + 1;
                } else if (x != areaX - 1 && x != 0 && oneTopOrBottom == 1) {
                    x = x - 1;
                } else if (x == 0 && oneTopOrBottom == 1) {
                    x = x + 1;
                } else {
                    x = x - 1;
                }
            }
            oneStepXOrY = randomStepXOrY.nextInt(2); // Выбираем рандомно направление движение по X или Y
            oneStepRightOrLeft = randomStepRightOrLeft.nextInt(2); // Если 0 движение вправо, если 1 движение по влево
            oneTopOrBottom = randomStepTopOrBottom.nextInt(2); // Если 0 движение вверх, если 1 движение по вниз

        }
        animal.setPosition(new Position(x, y));
        // Движение животного на один шаг и запись в историю
            animal.setMaxStep(animal.getMaxStep() + 1);

    }

    public static void eat(Animal iEatAnimal, Animal meEat) {
        // HashMap<String, HashMap<String, Integer>>
        if (iEatAnimal.isLive) {
            if (meEat.isLive) {
                for (var AnimalNameAndNameToEat : animalsEatPersentMap().entrySet()) {
                    var iEatString = AnimalNameAndNameToEat.getKey();
                    if (iEatString.equals(iEatAnimal.getName())) {

                        for (var animalNameToEatAndPersent : AnimalNameAndNameToEat.getValue().entrySet()) {
                            if (meEat.getName().equals(animalNameToEatAndPersent.getKey())) {
                               // SecureRandom randomAnimal = new SecureRandom();
                                var randomAnimal = ThreadLocalRandom.current();
                                int randomAnimalToEat = randomAnimal.nextInt(100); // Рандом на сколько мы съедим
                                if (randomAnimalToEat < animalNameToEatAndPersent.getValue()) { // На сколько процентов мы его съедим
                                    meEat.setLive(false);
                                    iEatAnimal.setMaxStep(0); // Если поел обнуляем максимальное количество шагов не поевши
                                }
                            }
                        }

                    }
                }
            }
        }

    }


}

