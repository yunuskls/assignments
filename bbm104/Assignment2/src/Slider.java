import java.util.ArrayList;

public class Slider {           //slides the gameGrid
    public Slider(ArrayList<ArrayList<Jewel>> bigList) {
        for (int i = bigList.size()-1; i >= 1; i--){
            for (int j = bigList.get(0).size()-1; j>=0; j--){
                if (bigList.get(i).get(j).getName().equals(" ")){
                    if (bigList.get(i-1).get(j).getName().equals(" ")){
                        if (i >= 3){
                            bigList.get(i).set(j, bigList.get(i-3).get(j));
                            bigList.get(i-3).set(j, new Space());
                        }
                    }
                    else {
                        bigList.get(i).set(j, bigList.get(i-1).get(j));
                        bigList.get(i-1).set(j, new Space());
                    }
                }

            }
        }
    }
}
