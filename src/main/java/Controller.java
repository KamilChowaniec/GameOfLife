import static org.lwjgl.glfw.GLFW.*;

import graphics.Input.*;

import java.util.ArrayList;

public class Controller {
    private View view;
    private Model model;
    private int codedPos = -1;
    private ArrayList<Button> cardButtons;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        cardButtons = new ArrayList<>();
        initButtons();
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
        for(Button button : cardButtons) button.display();
        view.display();
    }

    private void handleEvents() {
        handleButtons();
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
                addCard(gridType.Squared);
            if (KeyboardHandler.isKeyClicked(GLFW_KEY_2))
                addCard(gridType.Triangular);
            if (KeyboardHandler.isKeyClicked(GLFW_KEY_3))
                addCard(gridType.Hexagonal);

        }
        if(MouseButtonsHandler.isKeyClicked(GLFW_MOUSE_BUTTON_LEFT)){
            if(codedPos!=-1)model.draw(codedPos);
        }
        model.incZoom((int)ScrollHandler.wheelMovement(),MouseHandler.getMousePosition());

     //   model.
        if (MouseButtonsHandler.isKeyDown(GLFW_MOUSE_BUTTON_MIDDLE))
        {
            model.moveGrid(MouseHandler.xRel(),MouseHandler.yRel());
        }





        KeyboardHandler.clear();
        MouseButtonsHandler.clear();
        ScrollHandler.clear();
        MouseHandler.clear();
    }

    private void initButtons(){
        addCardButton(404, 2, 150, 30,"" + model.getCardsAmount(),()->model.setCardIndex(0));
    }

    private void addCardButton(int x, int y, int width, int height, String text, ButtonHandler handler){
        cardButtons.add(new Button(x,y,width,height,text,handler,0.5f,0.5f,0.5f));
    }
    private void handleButtons(){
        if(MouseButtonsHandler.isKeyClicked(GLFW_MOUSE_BUTTON_LEFT)){
            for(Button button : cardButtons){
                if(button.isFocused((int)MouseHandler.xPos(),(int)MouseHandler.yPos())){
                    button.press();
                }
            }
        }
    }

    private void addCard(gridType type){
        model.addCard(type);
        int n = model.getCardsAmount()-1;
        addCardButton(cardButtons.get(cardButtons.size()-1).getX()+cardButtons.get(cardButtons.size()-1).getWidth()+2,cardButtons.get(cardButtons.size()-1).getY(),cardButtons.get(cardButtons.size()-1).getWidth(),cardButtons.get(cardButtons.size()-1).getHeight(),"" + cardButtons.size()+1,()->model.setCardIndex(n));
    }
}