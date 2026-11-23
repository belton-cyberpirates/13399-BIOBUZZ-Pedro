package org.firstinspires.ftc.teamcode;


import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.function.Function;


public class ASFollowPath extends ASAction {
    ASAuto auto;
    Path path;
    Function<Double, Boolean> endCondition;

    ElapsedTime runTimer = new ElapsedTime();

    public ASFollowPath(ASAuto auto, Path path) {
        this.auto = auto;
        this.path = path;
    }

    public ASFollowPath(ASAuto auto, Path path, Function<Double, Boolean> endCondition) {
        this.auto = auto;
        this.path = path;
        this.endCondition = endCondition;
    }

    public void onStart() {
        runTimer.reset();
        auto.follower.follow(path);
    }

    public boolean isDone() {
        return !auto.follower.isBusy() || (endCondition != null && endCondition.apply(runTimer.milliseconds()));
    }
}
