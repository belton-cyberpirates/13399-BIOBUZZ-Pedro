package org.firstinspires.ftc.teamcode;


public class ASSetColor extends ASAction {
    ASAuto auto;
    ASAuto.Color color;

    public ASSetColor(ASAuto auto, ASAuto.Color color) {
        this.auto = auto;
        this.color = color;
    }

    public void onStart() {
        auto.SetColor(color);
    }

    public boolean isDone() {
        return true;
    }
}
