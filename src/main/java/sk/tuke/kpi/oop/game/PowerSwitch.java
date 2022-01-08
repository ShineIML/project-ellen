package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;

public class PowerSwitch extends AbstractActor {
    private Switchable device;

    public PowerSwitch(Switchable device) {
        if (device != null) {
            this.device = device;
        }

        setAnimation(new Animation("sprites/switch.png"));
    }

    public Switchable getDevice() {
        return device;
    }

    public void switchOn(){
        if (device != null) {
            device.turnOn();
            getAnimation().setTint(Color.WHITE);
        }
    }

    public void switchOff(){
        if (device != null) {
            device.turnOff();
            getAnimation().setTint(Color.GRAY);
        }
    }

    public void toggle() {
        if (getDevice() == null) {
            return;
        }

        if (getDevice().isOn()) {
            device.turnOff();
            getAnimation().setTint(Color.GRAY);
        }
        else if (!getDevice().isOn()) {
            device.turnOn();
            getAnimation().setTint(Color.WHITE);
        }
    }
}
