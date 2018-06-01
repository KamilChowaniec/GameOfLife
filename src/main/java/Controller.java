import static org.lwjgl.glfw.GLFW.*;

public class Controller
{
    private View view;
    private Model model;
    private double[] mousePosition;
    private boolean[] keys;
    private boolean[] mouseButtons;

    public Controller(Model model, View view)
    {
        this.model = model;
        this.view = view;
        mousePosition=view.getMousePosition();
        keys=view.getKeys();
        mouseButtons=view.getMouseButtons();
    }

    public void run()
    {
        while(view.shouldRun())
        {
            handleEvents();
            model.update();
            //TODO: sth=model.getGridValues();
            view.display();
        }
    }

    private void handleEvents()
    {
        System.out.println("Mouse pos:x=" + mousePosition[0] + " y=" + mousePosition[1]);
        System.out.println("Up arrow pressed:"+keys[GLFW_KEY_UP]);
        System.out.println("LMB:"+mouseButtons[GLFW_MOUSE_BUTTON_2]+"RMB:"+mouseButtons[GLFW_MOUSE_BUTTON_2]);
    }
}