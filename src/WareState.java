public abstract class WareState {

    protected static WareContext context;

    protected WareState() {
        //context = LibContext.instance();
    }

    public abstract void run();
}
