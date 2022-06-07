package com.duckrace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class DuckRacer implements Serializable {
    // Fields
    private int id;
    private String name;
    private Collection<Reward> rewards = new ArrayList<>(); // initially populated with 25 nulls
    private int currentIndex = 0; // position (slot0 of thr next Reward


    // Constructors
    public DuckRacer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //Business methods
    public void win(Reward reward) {
        rewards.add(reward);
    }

    //Accessor methods

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWins() {
        return rewards.size();
    }
    /**
     * CAUTION: we are returning a DIRECT REFERENCE  to this "sensitive" data.
     *
     */


     public List<Reward> getRewards() {
         return List.copyOf(rewards);
     }
    /**
     * Implement toString()
     * NOTE: the returned string shoul contain id, name, number of wins, and rewards.
     */
    public  String toString() {
        return "DucRacer: id=" + getId() + ", name=" + getName() + ", wins=" + getWins() +
                ", rewards=" + getRewards();
    }





}