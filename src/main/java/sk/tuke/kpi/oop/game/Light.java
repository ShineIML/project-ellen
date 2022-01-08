package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable, EnergyConsumer {
    private Animation light_off;
    private Animation light_on;
    private boolean isOn;
    private boolean isPowered;

    public Light() {
        isOn = false;
        isPowered = false;

        light_off = new Animation("sprites/light_off.png");
        light_on = new Animation("sprites/light_on.png");
        setAnimation(light_off);
    }

    @Override
    public void turnOn() {
        isOn = true;
        updateAnimation();
    }

    @Override
    public void turnOff() {
        isOn = false;
        updateAnimation();
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    public void toggle() {
        isOn = !isOn;
        updateAnimation();
    }


    @Override
    public void setPowered(boolean state) {
        isPowered = state;
        updateAnimation();
    }

    public void updateAnimation() {
        if (isOn && isPowered) {
            setAnimation(light_on);
        }
        else {
            setAnimation(light_off);
        }
    }

}
