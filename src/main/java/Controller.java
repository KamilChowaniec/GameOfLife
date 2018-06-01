
public class Controller
{
    private View view;
    private Model model;
    private double[] mousePosition;
    private boolean[] keys;

    public Controller(Model model, View view)
    {
        this.model = model;
        this.view = view;
        mousePosition=view.getMousePosition();
        keys=view.getKeys();
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
        System.out.println("Space pressed:"+keys[32]);
    }
}