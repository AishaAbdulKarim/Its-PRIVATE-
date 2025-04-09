package game;

import java.util.ArrayList;

public class EggSpawner {
    ArrayList<Egg> eggList;

    public EggSpawner(){
        eggList = new ArrayList<Egg>();
    }

    public void update(){
        for (Egg e : eggList) {
            e.update();
        }
    }

    public ArrayList<Egg> getEggList() {
        return eggList;
    }

    public void setEggList(ArrayList<Egg> eggList) {
        this.eggList = eggList;
    }

    
}
