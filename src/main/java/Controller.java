import static org.lwjgl.glfw.GLFW.*;

import graphics.Input.*;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Controller {
    private View view;
    private Model model;
    private int codedPos = -1;
    private ArrayList<Button> cardButtons;
    private Checkbox[][] rulesCheckboxes;
    private Slider delaySlider;
    private Selection selection;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        selection = new Selection(0,0,1,1);
        cardButtons = new ArrayList<>();
        rulesCheckboxes = new Checkbox[2][13];
        delaySlider = new Slider(view.rulesX + 50, view.rulesY + 400, 300, 10);
        initButtons();
        initCheckboxes();
    }

    public void run() {

        while (view.shouldRun()) {
            handleEvents();
            update();
            display();
        }
    }

    private void update() {
        model.update();
    }

    private void display() {
        codedPos = view.display(model.getGridValues(),selection);

        for (Checkbox[] checkbox : rulesCheckboxes)
            for (int i = 0; i < model.getRuleSize(); i++)
                checkbox[i].draw();
        for (Button button : cardButtons) button.display();
        delaySlider.draw();
        view.display();
    }

    private void handleEvents() {
        handleSliders();
        handleButtons();
        handleCheckboxes();
        handleSelection();
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
        if (MouseButtonsHandler.isKeyDown(GLFW_MOUSE_BUTTON_LEFT))
            if (codedPos != -1) model.draw(codedPos, true);
        if (MouseButtonsHandler.isKeyDown(GLFW_MOUSE_BUTTON_RIGHT))
            if (codedPos != -1) model.draw(codedPos, false);

        model.incZoom((int) ScrollHandler.wheelMovement());

        //   model.
        if (MouseButtonsHandler.isKeyDown(GLFW_MOUSE_BUTTON_MIDDLE)) {
            model.moveGrid(MouseHandler.xRel(), MouseHandler.yRel());
        }


        KeyboardHandler.clear();
        MouseButtonsHandler.clear();
        ScrollHandler.clear();
        MouseHandler.clear();
    }

    private void handleSelection(){
        if(codedPos!=-1) {
            if (KeyboardHandler.isKeyClicked(GLFW_KEY_LEFT_SHIFT))
                selection.setXY(codedPos);
            if (KeyboardHandler.isKeyDown(GLFW_KEY_LEFT_SHIFT))
                selection.setWH(codedPos);
        }
        if(KeyboardHandler.isKeyDown(GLFW_KEY_LEFT_CONTROL)) {
            if (KeyboardHandler.isKeyClicked(GLFW_KEY_C))
                if (selection.isSelected()) model.setClipboard(selection.getClipboard(model.getGridValues()));
            if (KeyboardHandler.isKeyClicked(GLFW_KEY_V))
                model.pasteClipboard((codedPos - (codedPos%Game.GRIDSIZE)) / Game.GRIDSIZE,codedPos%Game.GRIDSIZE);
        }


    }




    private void handleSliders() {
        if (delaySlider.isFocused((int) MouseHandler.xPos(), (int) MouseHandler.yPos()) && MouseButtonsHandler.isKeyDown(0) && !delaySlider.state()) {
            delaySlider.changeState();
        }
        if (delaySlider.state()) {
            if (!MouseButtonsHandler.isKeyDown(0)) delaySlider.changeState();
            delaySlider.slide((int) MouseHandler.xPos());
            model.setDelay(delaySlider.getPercent()*0.005);
        }

    }

    private void initButtons() {
        addCardButton(404, 2, 150, 30, "Hexagonal", () -> model.setCardIndex(0));
    }

    private void initCheckboxes() {
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 13; j++) {
                int x = i;
                int y = j;
                rulesCheckboxes[i][j] = new Checkbox(view.gridX + view.gridWidth + 20 + 40 * j, view.gridY + 50 + i * 40, 25, (state) -> model.setRule(x, y, state));
            }
        setRulesCheckboxes();
    }

    private void setRulesCheckboxes() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < model.getRuleSize(); j++) {
                rulesCheckboxes[i][j].setState(model.getRule(i, j));
            }
        }
    }

    private void setDelaySlider(){
        delaySlider.setPercent((int)(model.getDelay()/0.005));
    }

    private void addCardButton(int x, int y, int width, int height, String text, ButtonHandler handler) {
        cardButtons.add(new Button(x, y, width, height, text, handler, 0.5f, 0.5f, 0.5f));
    }

    private void handleButtons() {
        if (MouseButtonsHandler.isKeyClicked(GLFW_MOUSE_BUTTON_LEFT)) {
            for (Button button : cardButtons) {
                if (button.isFocused((int) MouseHandler.xPos(), (int) MouseHandler.yPos())) {
                    button.press();
                    setRulesCheckboxes();
                    setDelaySlider();
                }
            }
        }
    }

    private void handleCheckboxes() {
        if (MouseButtonsHandler.isKeyClicked(GLFW_MOUSE_BUTTON_LEFT)) {
            for (Checkbox[] checkbox : rulesCheckboxes)
                for (int i = 0; i < model.getRuleSize(); i++) {
                    if (checkbox[i].isFocused((int) MouseHandler.xPos(), (int) MouseHandler.yPos())) {
                        checkbox[i].press();
                    }
                }
        }
    }

    private void addCard(gridType type) {

        model.addCard(type);
        String name = "";
        int n = model.getCardsAmount() - 1;
        switch (type) {
            case Squared:
                name = "Squared";
                break;
            case Triangular:
                name = "Triangular";
                break;
            case Hexagonal:
                name = "Hexagonal";
                break;
        }
        addCardButton(
                cardButtons.get(cardButtons.size() - 1).getX() + cardButtons.get(cardButtons.size() - 1).getWidth() + 2,
                cardButtons.get(cardButtons.size() - 1).getY(),
                cardButtons.get(cardButtons.size() - 1).getWidth(),
                cardButtons.get(cardButtons.size() - 1).getHeight(),
                name,
                () -> model.setCardIndex(n)
        );
        setRulesCheckboxes();
        setDelaySlider();
    }
}