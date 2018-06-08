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
           model.update();
//            view.display(model.getGridValues());
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
        if(KeyboardHandler.isKeyClicked(GLFW_KEY_A))
            model.nextCard();
        if(KeyboardHandler.isKeyClicked(GLFW_KEY_D))
            model.prevCard();
        if(KeyboardHandler.isKeyDown(GLFW_KEY_LEFT_CONTROL)){
            if(KeyboardHandler.isKeyClicked(GLFW_KEY_1))
                model.addCard(0);
            if(KeyboardHandler.isKeyClicked(GLFW_KEY_2))
                model.addCard(1);
            if(KeyboardHandler.isKeyClicked(GLFW_KEY_3))
                model.addCard(2);

        }




        KeyboardHandler.clear();
        MouseButtonsHandler.clear();
    }
}