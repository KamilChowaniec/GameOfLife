import static org.lwjgl.glfw.GLFW.*;
import graphics.Input.*;
public class Controller
{
    private View view;
    private Model model;

    public Controller(Model model, View view)
    {
        this.model = model;
        this.view = view;
    }

    public void run()
    {

        while(view.shouldRun())
        {
            view.clearScreen();
            handleEvents();
            //model.update();
            //view.display(model.getGridValues());
            view.display();
        }
    }

    private void handleEvents()
    {
        if (KeyboardHandler.isKeyDown(GLFW_KEY_ESCAPE))
            view.closeWindow();
        if (KeyboardHandler.isKeyClicked(GLFW_KEY_SPACE))
            model.randomize();
        if (KeyboardHandler.isKeyClicked(GLFW_KEY_ENTER))
            model.update();

        KeyboardHandler.clear();
        MouseButtonsHandler.clear();
    }
}