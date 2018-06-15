public class Toolset {
    private Tool state;
    private Button[] tools;

    public Toolset(){
        state = Tool.Draw;
        tools = new Button[Tool.values().length];
    }

    public Tool getTool() {
        return state;
    }
}