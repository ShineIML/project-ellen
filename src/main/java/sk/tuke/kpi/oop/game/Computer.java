package sk.tuke.kpi.oop.game;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor implements EnergyConsumer {
    private int sumint;
    private float sumfloat;
    private int subint;
    private float subfloat;
    private boolean isPowered;


    public Computer() {

        this.sumint = 0;
        this.sumfloat = 0;
        this.subint = 0;
        this.subfloat = 0;
        this.isPowered = false;

        setAnimation(new Animation("sprites/computer.png", 80, 48, 0.2f, Animation.PlayMode.LOOP_PINGPONG));
    }

    public int add(int i, int j) {
        if(isPowered) {
            this.sumint = i + j;
            return this.sumint;
        } else {
            return 0;
        }
    }

    public float add(float i, float j) {
        if(isPowered) {
            this.sumfloat = i + j;
            return this.sumfloat;
        } else {
            return 0;
        }
    }

    public int sub(int i, int j) {
        if(isPowered) {
            this.subint = i - j;
            return this.subint;
        } else {
            return 0;
        }
    }

    public float sub(float i, float j) {
        if(isPowered) {
            this.subfloat = i - j;
            return this.subfloat;
        } else {
            return 0;
        }
    }

    @Override
    public void setPowered(boolean state) {
        this.isPowered = state;
        if(!isPowered) {
            getAnimation().stop();
        } else {
            getAnimation().play();
        }
    }
}
