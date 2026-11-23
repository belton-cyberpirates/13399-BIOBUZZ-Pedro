package org.firstinspires.ftc.teamcode;


public class ASWaitForLauncher extends ASAction {
    ASAuto auto;


    public ASWaitForLauncher(ASAuto auto) {
        this.auto = auto;
    }

    public void onStart() {}

    public boolean isDone() {
        return this.auto.launcher.isAtVelocity();
    }
}