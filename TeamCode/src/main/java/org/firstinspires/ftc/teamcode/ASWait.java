package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.util.ElapsedTime;

// Not on robot yet

public class ASWait extends ASAction {
    ASAuto auto;
    double time;
    ElapsedTime timer;

    public ASWait(ASAuto auto, double time) {
        this.auto = auto;
        
        this.time = time;
    }

    public void onStart() {
        this.timer = new ElapsedTime();
    }
    
    public boolean isDone() {
        return this.timer.milliseconds() >= time;
    }
    
    public void process() {
        auto.telemetry.addData("Wait time remaining", time - timer.milliseconds());
    }

}