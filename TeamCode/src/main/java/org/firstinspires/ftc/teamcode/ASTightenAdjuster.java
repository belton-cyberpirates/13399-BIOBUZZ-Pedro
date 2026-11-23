package org.firstinspires.ftc.teamcode;


public class ASTightenAdjuster extends ASAction {
    ASAuto auto;

    public ASTightenAdjuster(ASAuto auto) {
        this.auto = auto;
    }

    public void onStart() {
        auto.launcher.TightenAdjuster();
    }

    public boolean isDone() {
        return true;
    }
}