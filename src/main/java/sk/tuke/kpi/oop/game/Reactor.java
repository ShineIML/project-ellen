package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable, Repairable {

    private int temperature;
    private int damage;
    private boolean turnmode;
    private boolean wofire;
    private Set<EnergyConsumer> devices;
    private Animation offreactor;
    private Animation on;
    private Animation hot;
    private Animation broken;
    private Animation unfired;

    public Reactor() {
        temperature = 0;
        damage = 0;
        turnmode = false;
        wofire = false;
        devices = new HashSet<>();

        offreactor = new Animation("sprites/reactor.png");
        on = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        hot = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        broken = new Animation("sprites/reactor_broken.png", 80, 80, 0.01f, Animation.PlayMode.LOOP_PINGPONG);
        unfired = new Animation("sprites/reactor_extinguished.png");
        setAnimation(offreactor);
    }

    public int getTemperature() {
        return temperature;
    }

    public int getDamage() {
        return this.damage;
    }

    public void checkDamage() {

        double i = 2000;
        this.damage = 0;
        while (i < this.temperature) {
            this.damage = this.damage + 1;
            if (this.damage >= 100) {
                this.damage = 100;
            }
            i = i + 40;
        }

    }

    public void checkTemperature() {
        for (int i = 0; i < 50; i++) {
            if (this.damage >= 0 && this.damage < 33) {
                this.temperature = this.temperature - 40;
            } else if (this.damage >= 33 && this.damage < 66) {
                this.temperature = (int) (this.temperature - (40 * 1.5));
            } else if (this.damage >= 66 && this.damage < 100) {
                this.temperature = this.temperature - (40 * 2);
            }
        }
    }

    public void isIncrement(int increment) {
        if (this.damage >= 33 && this.damage < 66 && increment >= 0 && this.temperature >= 0) {
            this.temperature += (increment * 1.5);
        } else if (this.damage >= 66 && this.damage < 100 && increment >= 0 && this.temperature >= 0) {
            this.temperature += (increment * 2);
        } else if (this.damage >= 0 && this.damage < 33 && increment >= 0 && this.temperature >= 0) {
            this.temperature += increment;
        } else if (increment < 0 || this.damage == 100) {
            this.temperature = this.temperature;
        } else if (this.damage == 100) {
            this.turnmode = false;
        } else {
            this.temperature = 0;
        }
    }

    public void increaseTemperature(int increment) {

        if(this.turnmode == true) {

            checkDamage();

            isIncrement(increment);

            checkDamage();

            updateAnimation();
        }
    }

    public void isDecrement(int decrement) {
        if(this.turnmode == true) {
            if (this.damage >= 50 && this.damage < 100 && decrement >= 0 && this.temperature >= 0) {
                this.temperature = this.temperature - (decrement / 2);

            } else if (this.damage == 100 && decrement >= 0 && this.temperature >= 0) {
                this.temperature = this.temperature;
            } else if (this.damage >= 0 && this.damage < 50 && decrement >= 0 && this.temperature >= 0) {
                this.temperature -= decrement;
            } else if (decrement < 0) {
                this.temperature = this.temperature;
            } else {
                this.temperature = 0;
            }

            if (this.temperature < 0) {
                this.temperature = 0;
            }
        }
    }

    public void decreaseTemperature(int decrement) {

            isDecrement(decrement);

            updateAnimation();
    }

    public void updateAnimation() {

        double actual = getTemperature();

        if(actual >= 4000 && actual < 6000 && this.turnmode == true) {

            setAnimation(hot);

        } else if(actual >= 6000 && this.turnmode == true) {

            setAnimation(broken);

        } else if(actual >= 0 && actual < 4000 && this.turnmode == true) {

            setAnimation(on);

        } else {

            setAnimation(offreactor);
        }
    }


    @Override
    public void turnOn() {
        this.turnmode = true;
        if(!devices.isEmpty()) {
            for(EnergyConsumer device : devices) {
                device.setPowered(damage != 100);
            }
        }
        updateAnimation();
    }

    @Override
    public void turnOff() {
        this.turnmode = false;
        if(!devices.isEmpty()) {
            for(EnergyConsumer device : devices) {
                device.setPowered(false);
            }
        }
        updateAnimation();
    }

    @Override
    public boolean isOn() {
        return this.turnmode;
    }

    public void addDevice(EnergyConsumer device) {
        if(device == null) {
            return;
        }

        devices.add(device);
        if (turnmode) {
            device.setPowered(true);
        } else {
            device.setPowered(false);
        }
   }

    public void removeDevice(EnergyConsumer device) {
        if(devices.contains(device) == false) {
            return;
        }

        devices.remove(device);
        device.setPowered(false);
    }

    public boolean extinguish() {
        if(getDamage() == 100 && wofire == false) {
            temperature = 4000;
            wofire = true;
            setAnimation(unfired);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean repair() {
        if (!extinguish() && damage > 0 && damage < 100) {
            damage = damage - 50;
            int newTemperature = 2000 + (int) Math.round(damage * 0.025);
            temperature = Math.min(temperature, newTemperature);

            damage = Math.max(damage, 0);
            updateAnimation();
            return true;
        } else if (damage == 0) {
            temperature = temperature / 2;
            return true;
        }
        return false;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        scene.scheduleAction(new PerpetualReactorHeating(1), this);
    }
}


