package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Cooler extends AbstractActor implements Switchable {
    private boolean isOn;
    private Reactor reactor;
    private Animation animation;

    public Cooler(Reactor reactor) {
        this.reactor = reactor;
        isOn = false;

        animation = new Animation("sprites/fan.png", 32, 32, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        animation.pause();
        setAnimation(animation);
    }
    @Override
    public void turnOn() {
        isOn = true;
        animation.play();
    }
    @Override
    public void turnOff() {
        isOn = false;
        animation.pause();
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    public Reactor getHeatedReactor() {
        return reactor;
    }

    private void coolReactor() {
        if(isOn && reactor != null)
            reactor.decreaseTemperature(1);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        new Loop<>(new Invoke<Cooler>(this::coolReactor)).scheduleFor(this);
    }

}
