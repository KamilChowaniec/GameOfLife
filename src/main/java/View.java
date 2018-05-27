public class View {
    private long window;
    private Button buttons;
    private Checkbox checkboxes;

    public View()
    {
        window = Graphics.init(720,720,"GOL");
    }


    public void getEvents() {
        // TODO implement me
    }

    public void display() {
        // TODO implement me
        Graphics.loop();
    }
}