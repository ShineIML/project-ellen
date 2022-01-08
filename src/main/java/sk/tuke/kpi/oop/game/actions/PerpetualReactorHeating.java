package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Reactor;

public class PerpetualReactorHeating extends AbstractAction<Reactor> {
    private int deltaTemperature;

    public PerpetualReactorHeating(int deltaTemperature) {
        this.deltaTemperature = deltaTemperature;
    }


    @Override
    public void execute(float deltaTime) {
        if(getActor() != null)
            getActor().increaseTemperature(deltaTemperature);
    }

    @Override
    public @NotNull Disposable scheduleOn(@NotNull Scene scene) {
        return super.scheduleOn(scene);
    }

    @Override
    public @NotNull Disposable scheduleFor(@NotNull Reactor reactor) {
        return super.scheduleFor(reactor);
    }

}
