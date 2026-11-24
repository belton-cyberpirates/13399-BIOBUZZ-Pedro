package org.firstinspires.ftc.teamcode;


public class ASSpinPusherSlow extends ASAction {
    ASAuto auto;

    public ASSpinPusherSlow(ASAuto auto) {
        this.auto = auto;
    }

    public void onStart() {
        auto.intake.SetPusherVelocity(ASBotConfig.AUTO_PUSHER_FAR_VELOCITY);
    }

    public boolean isDone() {
        return true;
    }
}
