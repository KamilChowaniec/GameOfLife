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
            view.getEvents();
            handleEvents();
            model.update();
            //TODO: sth=model.getGridValues();
            view.display();
        }
    }

    private void handleEvents()
    {
        // TODO implement me
    }
}