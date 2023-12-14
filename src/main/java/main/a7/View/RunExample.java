package main.a7.View;

import main.a7.Controller.Controller;

public class RunExample extends Command {
    private final Controller controller;

    public RunExample(String key, String desc, Controller controller) {
        super(key, desc);
        this.controller = controller;
    }

    @Override
    public String toString() {
        return this.getKey() + " " + this.getDescription();
    }

    public Controller getController() {
        return this.controller;
    }

    @Override
    public void execute() {
        try {
            controller.allStep();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException ignored) {

        }
    }
}
