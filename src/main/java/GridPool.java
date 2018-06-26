import java.util.ArrayList;

public class GridPool {
    private static ArrayList<Grid> available = new ArrayList<>();
    private static ArrayList<Grid> inUse = new ArrayList<>();

    public GridPool() {
        for (int i = 0; i < 5; i++) {
            available.add(new Squared());
            available.add(new Triangular());
            available.add(new Hexagonal());
        }
    }

    public static Grid getGrid(gridType type) {
        if (isAvailable(type)) {
            Grid g = popElement(available, type);
            push(inUse, g);
            return g;
        }
        return createGrid(type);
    }

    private static Grid createGrid(gridType type) {
        Grid grid;
        switch (type) {
            case Squared:
                grid = new Squared();
                break;
            case Triangular:
                grid = new Triangular();
                break;
            case Hexagonal:
                grid = new Hexagonal();
                break;
            default:
                grid = new Squared();
        }
        push(inUse, grid);
        return grid;
    }

    private static void push(ArrayList<Grid> list, Grid obj){
        list.add(obj);
    }

    private static boolean isAvailable(gridType type) {
        for (Grid g : available)
            if (g.getClass().getName().equals(type.toString()))
                return true;
        return false;
    }

    public static void releseGrid(Grid obj){
        cleanUp(obj);
        available.add(popElement(inUse,obj));
    }

    private static void cleanUp(Grid obj){
        obj.reset();
    }

    private static Grid popElement(ArrayList<Grid> list, gridType type){
        for(int i=0;i<list.size();i++){
            if(list.get(i).getClass().getName().equals(type.toString())){
                Grid g = list.get(i);
                list.remove(i);
                return g;
            }
        }
        return null;
    }

    private static Grid popElement(ArrayList<Grid> list, Grid obj){
        list.remove(obj);
        return obj;
    }
}
