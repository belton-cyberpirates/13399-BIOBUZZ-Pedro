package org.firstinspires.ftc.teamcode;

import com.pedropathing.math.Pose;


public class ASSetStartingPose extends ASAction {
    ASAuto auto;
    Pose pose;

    public ASSetStartingPose(ASAuto auto, Pose pose) {
        this.auto = auto;
        this.pose = pose;
    }

    public void onStart() {
        auto.follower.setPose(pose);
    }

    public boolean isDone() {
        return true;
    }
}
