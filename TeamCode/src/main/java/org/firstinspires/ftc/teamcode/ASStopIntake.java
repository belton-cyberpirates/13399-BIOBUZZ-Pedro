package org.firstinspires.ftc.teamcode;


public class ASStopIntake extends ASAction {
    ASAuto auto;

    public ASStopIntake(ASAuto auto) {
        this.auto = auto;
    }

    public void onStart() {
        auto.intake.SetIntakeVelocity(0);
    }

    public boolean isDone() {
        return true;
    }
}