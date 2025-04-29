package game;

import gameConstants.Constants;
import java.util.ArrayList;

public class EggSpawner {
    ArrayList<Egg> eggList;
    private int spawnRate = 5;

    public EggSpawner(){
        eggList = new ArrayList<>();
    }

    public void update(){
        spawnEgg();
        for (Egg e : eggList) {
            e.update();
        }
    }

    public void spawnEgg(){
        // Create a random number between 1 and 100
        int randomNumber = (int) (Math.random() * 100) + 1;
        int randomX = (int) (Math.random() * (Constants.FRAME_WIDTH - 200)) + 100;
        

    // Check if the random number is less than 20
    if (randomNumber < spawnRate) {
        Egg e = new Egg(randomX, 0, 40, 50, "egg_01.png");
        eggList.add(e);
    }
    }

    // Getters and Setters
    public ArrayList<Egg> getEggList() {
        return eggList;
    }

    public void setEggList(ArrayList<Egg> eggList) {
        this.eggList = eggList;
    }

    public int getSpawnRate() {
        return spawnRate;
    }

    public void setSpawnRate(int spawnRate) {
        this.spawnRate = spawnRate;
    }

    
    
}