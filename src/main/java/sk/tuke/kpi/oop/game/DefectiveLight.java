package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class DefectiveLight extends Light implements Repairable {

    private Disposable action;
    private boolean loop = false;

    public DefectiveLight() {
        super();
    }

    private void brokenLight() {
        if((Math.floor(Math.random() * 20)) == 1) {
            super.toggle();
        }
    }

    private void dispose(){
        action.dispose();
    }

    private void create() {
        action = new Loop<>(new Invoke<Light>(this::brokenLight)).scheduleFor(this);
        loop = false;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        action = new Loop<>(new Invoke<Light>(this::brokenLight)).scheduleFor(this);
    }

    @Override
    public boolean repair() {
        if(loop == false) {
            loop = true;
            new ActionSequence<>(
                new Invoke<>(this::dispose),
                new Wait<>(12),
                new Invoke<>(this::create)
            ).scheduleFor(this);
            return true;
        } else {
            return false;
        }

    }
}
