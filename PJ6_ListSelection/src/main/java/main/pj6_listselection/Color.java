package main.pj6_listselection;

import java.util.HashMap;
import java.util.Map;

public class Color {
    private static final Map<Integer,String> colorMap = new HashMap<>();
    static {
        //define color mapping
        colorMap.put(1,"RED");
        colorMap.put(2,"GREEN");
        colorMap.put(3,"BLUE");
        colorMap.put(4,"YELLOW");
        colorMap.put(5,"PURPLE");
        colorMap.put(6,"ORANGE");
        colorMap.put(7,"PINK");
        colorMap.put(8,"BROWN");
        colorMap.put(9,"GRAY");
        colorMap.put(10,"BLACK");
        colorMap.put(11,"WHITE");
    }

    public static String getColor(int ID, String colorName){
        colorName = colorName.toLowerCase();
        return colorMap.get(ID);
    }
}
