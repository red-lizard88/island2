package com.javarush.island.liashchanka.animals;

import com.javarush.island.liashchanka.abstracts.Animal;
import com.javarush.island.liashchanka.abstracts.Herbivorous;
import com.javarush.island.liashchanka.abstracts.Predatory;

public class Wolf extends Animal implements Predatory {
    public Wolf() {


        super("Волк", 50);

    }


}
