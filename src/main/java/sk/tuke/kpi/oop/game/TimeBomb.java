package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class TimeBomb extends AbstractActor {
    private Animation started;
    private Animation done;
    private boolean activated;
    private float time_count;

    public TimeBomb(float time) {
        activated = false;
        time_count = time;

        started = new Animation("sprites/bomb_activated.png", 16, 16, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        done = new Animation("sprites/small_explosion.png", 16, 16, 0.1f, Animation.PlayMode.ONCE);

        setAnimation(new Animation("sprites/bomb.png"));
    }

    public boolean isActivated() {
        return activated;
    }

    private void removeBomb(){
        if(this.getScene() != null) {
            this.getScene().removeActor(this);
        }
    }

    public void detonate(){
        setAnimation(done);
        new When<>(
            () -> getAnimation().getCurrentFrameIndex() == getAnimation().getFrameCount()-1,
            new Invoke<>(this::removeBomb)
        ).scheduleFor(this);
    }

    public void activate() {
        activated = true;
        setAnimation(started);
        new ActionSequence<>(
            new Wait<>(time_count),
            new Invoke<>(this::detonate)
        ).scheduleFor(this);
    }

}
