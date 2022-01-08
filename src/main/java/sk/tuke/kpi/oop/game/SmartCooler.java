package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class SmartCooler extends Cooler {

    public SmartCooler(Reactor heatedReactor) {
        super(heatedReactor);

        setAnimation(new Animation("sprites/fan.png", 32, 32, 0.2f, Animation.PlayMode.LOOP_PINGPONG));
    }

    private void smartCoolReactor(){
        if(getHeatedReactor() == null)
            return;

        if(getHeatedReactor().getTemperature() < 1500)
            super.turnOff();
        else if(getHeatedReactor().getTemperature() > 2500)
            super.turnOn();
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<Cooler>(this::smartCoolReactor)).scheduleFor(this);
    }
}
