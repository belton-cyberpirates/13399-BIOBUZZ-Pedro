package org.firstinspires.ftc.teamcode;


public class ASLoosenAdjuster extends ASAction {
    ASAuto auto;

    public ASLoosenAdjuster(ASAuto auto) {
        this.auto = auto;
    }

    public void onStart() {
        auto.launcher.LoosenAdjuster();
    }

    public boolean isDone() {
        return true;
    }
}