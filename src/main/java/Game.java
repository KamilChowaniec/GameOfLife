public class Game {
    private static Model model;
    private static View view;
    private static Controller controller;

    public Game() {
        super();
    }

    public static void main(String[] args) {
       view = new View();
       view.display();
    }
}