package org.firstinspires.ftc.teamcode;


public class ASCloseStopper extends ASAction {
    ASAuto auto;

    public ASCloseStopper(ASAuto auto) {
        this.auto = auto;
    }

    public void onStart() {
        auto.intake.CloseStopper();
    }

    public boolean isDone() {
        return true;
    }
}