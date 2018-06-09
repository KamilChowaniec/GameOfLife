import static org.lwjgl.glfw.GLFW.*;

import graphics.Input.*;

public class Controller {
    private View view;
    private Model model;
    private int codedPos = -1;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void run() {

        while (view.shouldRun()) {
            clearScreen();
            handleEvents();
            update();
            display();
        }
    }

    private void clearScreen(){
        view.clearScreen();
    }

    private void update(){
        model.update();
    }

    private void display(){
        codedPos = view.display(model.getGridValues());
        view.display();
    }

    private void handleEvents() {
        if (KeyboardHandler.isKeyDown(GLFW_KEY_ESCAPE))
            view.closeWindow();
        if (KeyboardHandler.isKeyClicked(GLFW_KEY_SPACE))
            model.randomize();
        if (KeyboardHandler.isKeyClicked(GLFW_KEY_ENTER))
            model.update();
        if (KeyboardHandler.isKeyClicked(GLFW_KEY_D))
            model.nextCard();
        if (KeyboardHandler.isKeyClicked(GLFW_KEY_A))
            model.prevCard();
        if (KeyboardHandler.isKeyClicked(GLFW_KEY_P))
            model.pause();
        if (KeyboardHandler.isKeyDown(GLFW_KEY_LEFT_CONTROL)) {
            if (KeyboardHandler.isKeyClicked(GLFW_KEY_1))
                model.addCard(gridType.Squared);
            if (KeyboardHandler.isKeyClicked(GLFW_KEY_2))
                model.addCard(gridType.Triangular);
            if (KeyboardHandler.isKeyClicked(GLFW_KEY_3))
                model.addCard(gridType.Hexagonal);

        }
        if(MouseButtonsHandler.isKeyClicked(GLFW_MOUSE_BUTTON_LEFT)){
            if(codedPos!=-1)model.draw(codedPos);
        }
        model.incZoom((int)ScrollHandler.wheelMovement());

     //   model.
        if (MouseButtonsHandler.isKeyDown(GLFW_MOUSE_BUTTON_MIDDLE))
        {
            model.moveGrid(MouseHandler.xRel(),MouseHandler.yRel());
        }





        KeyboardHandler.clear();
        MouseButtonsHandler.clear();
        ScrollHandler.clear();
        //MouseHandler.clear();
    }
}