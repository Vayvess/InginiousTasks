import java.util.List;
import java.awt.Color;

import java.util.Comparator;
import java.util.List;

public class Sorter {


    /*
     * Should sort the list and order the Flower by color, in this specific order : red, blue, green
     */
    public static void sortFlowerByColor(List<Flower> list){
        list.sort(new Comparator<Flower>() {
            @Override
            public int compare(Flower a, Flower b) {
                Color aColor = a.getPetalColor(), bColor = b.getPetalColor();
                int redDiff = aColor.getRed() - bColor.getRed();
                if ( redDiff == 0){
                    int blueDiff = aColor.getBlue() - bColor.getBlue();
                    if ( blueDiff == 0){
                        return aColor.getGreen() - bColor.getGreen();
                    }
                    return blueDiff;
                }
                return redDiff;
            }
        });
    }
    /*
     * Should sort the Plant by name only
     */
    public static void sortPlantByName(List<Plant> list){
        list.sort(new Comparator<Plant>() {
            @Override
            public int compare(Plant a, Plant b) {
                String aName = a.getName(), bName = b.getName();
                for (int i = 0; i < Math.min(aName.length(), bName.length()); i++) {
                    int letterDiff = aName.charAt(i) - bName.charAt(i);
                    if (letterDiff != 0){ return letterDiff; };
                }
                return bName.length() - aName.length();
            }
        });
    }

    /*
     * Should sort the list and order the Tree by height
     */
    public static void sortTreeByHeight(List<Tree> list){
        list.sort(new Comparator<Tree>() {
            @Override
            public int compare(Tree a, Tree b) {
                return a.getHeight() - b.getHeight();
            }
        });
    }
}
