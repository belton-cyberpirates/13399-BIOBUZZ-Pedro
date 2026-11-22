package org.firstinspires.ftc.teamcode;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import static com.pedropathing.api.Paths.*;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;


@Autonomous(name = "Blue Auto", /*preselectTeleOp="Your Drive Code Here",*/ group="blue")
// @Disabled
public class ASBlueAuto extends ASAuto {
    final PoseFactory p = PoseFactory.degrees();

    // Values are in inches, 0-144 (72 is middle of the field)
    private final Pose startPose = p.of(56, 8, 0);
    private final Pose launchPose = p.of(82, 86, 135);
    private final Pose humanPose = p.of(0, 0, 0); // Change name to whatever the human player zone is
    private final Pose parkPose = p.of(0, 0, 0); //TODO


    // Returns the sequence of actions to launch held pollen/nectar
    public ASAction launchSequence(Pose currPose) {
        ASAction[] actions = {
                // Get ready for launching
                new ASSpinLauncher(this),

                // Move to shooting position
                new ASFollowPath(this,
                        path( // Action that tells the robot to move along the given path
                                line( // Add a new bezier line to path (straight line)
                                        currPose, // Line starting position (where the robot starts)
                                        launchPose // Line ending position (where the robot needs to go)
                                )
                        )
                        .linear(currPose, launchPose)
                ),

                // Launch!
                new ASWaitForLauncher(this),
                new ASWait(this, 500),

                // Reset
                new ASStopLauncher(this),
                new ASStopIntake(this),
                new ASStopPusher(this)
        };
        return new ASActionSequence(this, actions);
    }


    public ASAction[] getActions() {
        return new ASAction[] {
                // ======================= AUTO START ======================= //

                // Init
                new ASSetStartingPose(this, startPose), // Tell the robot where it will start the auto

                // Launch!
                launchSequence(startPose),

                // Get ready to intake
                new ASCloseStopper(this),
                new ASSpinIntake(this),
                new ASSpinPusher(this),


                 new ASFollowPath(this,
                        path(
                            line(
                                    startPose,
                                    p.of(55.95, 12.1, 50)
                            )
                        )
                        .linear(startPose.heading(), Math.toRadians(50))
                ),
                
                new ASStopPusher(this),
                new ASStopIntake(this),

                // Move to parking zone
                new ASFollowPath(this,
                        path(
                                line(
                                        p.of(55.95, 12.1, 0),
                                        p.of(55.95, 33.9, 0)
                                ),
                                line(
                                        p.of(55.95, 33.9, 0),
                                        p.of(20.41, 33.9, 0)
                                ),
                                line(
                                        p.of(20.41, 33.9, 0),
                                        p.of(19.6, 123.72, 0)
                                ),
                                line(
                                        p.of(19.6, 123.72, 0),
                                        p.of(7.06, 123.72, 0)
                                )
                        )
                        .constant(Math.toRadians(90))
                ),
                
                // ======================== AUTO END ======================== //
        };
    }
}