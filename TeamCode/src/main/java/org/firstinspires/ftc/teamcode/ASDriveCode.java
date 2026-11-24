package org.firstinspires.ftc.teamcode;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp(name="Field Centric (pedro)", group="DriveCodes")
// @Disabled
public class ASDriveCode extends LinearOpMode {

    // Constants
    double INTAKE_SPIT_MULT = .333;
    //double LEAD_ANGLE_MULT = .0003;

    // Hardware Helper Classes
    ASDriveMotors driveMotors;
    ASIntake intake;
    ASLauncher launcher;

    final PoseFactory p = PoseFactory.degrees();

    // Vars
    public static double headingOffset = 0;

    Pose storedPose = p.of(0, 0, 0);
    Pose holdPose;

    boolean prevY = false;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.setMsTransmissionInterval(200);

        driveMotors = new ASDriveMotors(this);
        intake = new ASIntake(this);
        launcher = new ASLauncher(this);

        // Wait for the start button to be pressed
        waitForStart();

        while (opModeIsActive()) {
            // Allow resetting yaw in case of misalignment
            if (gamepad1.dpad_right) {
                headingOffset = driveMotors.heading;
            }

            // Process classes
            driveMotors.process();
            launcher.process();

            // P1 variables
            double leftStickXGP1 = gamepad1.left_stick_x;
            double leftStickYGP1 = gamepad1.left_stick_y;
            double rightStickXGP1 = gamepad1.right_stick_x;
            double rightStickYGP1 = gamepad1.right_stick_y;

            // Get the speed the bot should go with the joystick pushed all the way
            double maxSpeed = calcMaxSpeed(-gamepad1.left_trigger, ASBotConfig.BASE_SPEED, ASBotConfig.MAX_BOOST);

            double turnPower = -gamepad1.right_stick_x;

            // Virtually rotate the joystick by the angle of the robot
            double heading = driveMotors.heading - headingOffset;

            double rotatedX =
                    leftStickXGP1 * Math.cos(heading) -
                            leftStickYGP1 * Math.sin(heading);
            double rotatedY =
                    leftStickXGP1 * Math.sin(heading) +
                            leftStickYGP1 * Math.cos(heading);

            // strafing is slower than rolling, bump horizontal speed
            rotatedX *= ASBotConfig.STRAFE_MULT;

            // set pin locations when corresponding dpad buttons are pressed
            if (gamepad1.dpad_left) {
                storedPose = p.of(
                        driveMotors.GetX(),
                        driveMotors.GetY(),
                        -driveMotors.GetHeading()
                );
            }
            // track pin locations when respective buttons are being pressed
            if (gamepad1.x) {
                driveMotors.Move(
                        storedPose.x(),
                        storedPose.y(),
                        storedPose.heading()
                );
            }
            else if (gamepad1.y) {
                if (!prevY) {
                    holdPose = p.of(
                            driveMotors.GetX(),
                            driveMotors.GetY(),
                            -driveMotors.GetHeading()
                    );
                }
                driveMotors.Move(
                        holdPose.x(),
                        holdPose.y(),
                        holdPose.heading()
                );
            }
            // aim at angle for far goal when respective button pressed
            //else if (gamepad1.b) {
            //    double refX = driveMotors.GetX() - targetX;
            //    double refY = driveMotors.GetY() - targetY;
            //
            //    double tanVelocity = ( refX * driveMotors.getVelY() - refY * driveMotors.getVelX() ) /
            //            Math.sqrt( Math.pow(refX, 2) + Math.pow(refY, 2) );
            //    double angleOffset = tanVelocity * LEAD_ANGLE_MULT + Math.PI/2;
            //
            //    driveMotors.DriveAndAim(
            //            rotatedY,
            //            rotatedX,
            //            targetX,
            //            targetY,
            //            maxSpeed,
            //            angleOffset
            //    );
            //}

            // drive normally when keybinds aren't in use
            else {
                // Set the power of the wheels based off the new joystick coordinates
                // y+x+stick <- [-1,1]
                driveMotors.DriveWithPower(
                        ( rotatedY + rotatedX + ( turnPower )) * maxSpeed, // Back left
                        ( rotatedY - rotatedX + ( turnPower )) * maxSpeed, // Front left
                        (-rotatedY - rotatedX + ( turnPower )) * maxSpeed, // Front right
                        (-rotatedY + rotatedX + ( turnPower )) * maxSpeed  // Back right
                );
            }

            prevY = gamepad1.y;

            // P2 variables
            double leftStickYGP2 = gamepad2.left_stick_y;
            double rightStickYGP2 = gamepad2.right_stick_y;

            // // Intake
            intake.SetPower(leftStickYGP1 < 0 ? leftStickYGP1 : leftStickYGP1 * INTAKE_SPIT_MULT);

            // Pusher
            boolean safe = gamepad1.left_bumper;
            if (launcher.isAtVelocity() || launcher.launcherTargetVelocity < 1100 || gamepad1.right_trigger == 0 || !safe) {
                intake.SetPusherPower(gamepad1.dpad_down ? .6 : -leftStickYGP1);
            }
            else {
                intake.SetPusherPower(0);
            }

            // Launcher adjuster
            if (gamepad1.a) {
                launcher.TightenAdjuster();
            }
            else if (gamepad1.b) {
                launcher.LoosenAdjuster();
            }
            

            // Stopper
            intake.SetStopper(gamepad1.dpad_down || gamepad1.right_trigger > 0.5);

            // Flywheel
            if (gamepad1.dpad_down) {
                launcher.SetVelocity(ASBotConfig.LAUNCHER_DROP_VELOCITY);
            }
            //else if (rightStickYGP1 < -.5) {
            //    launcher.SetVelocity((gamepad1.left_trigger > 0) ? ASBotConfig.LAUNCHER_FAR_VELOCITY : ASBotConfig.LAUNCHER_VELOCITY);
            //}
            //else {
            //    launcher.SetVelocity(ASBotConfig.LAUNCHER_PASSIVE_VELOCITY);
            //}
            else {
                launcher.SetVelocity(ASBotConfig.LAUNCHER_VELOCITY);
            }

            launcher.safe = safe;

            // Telemetry
            telemetry.addData("headingOffset", headingOffset);

            //telemetry.addData("x", aPosX);
            //telemetry.addData("y", aPosY);
            //telemetry.addData("h", aPosHeading);

            telemetry.update();
        }
    }


    /**
     * if boost trigger unpressed, return base_speed,
     * else return base_speed + boost amount
     */
    double calcMaxSpeed(double triggerVal, double baseSpeed, double boostMult) {
        double boostRatio = triggerVal * boostMult;
        double boostSpeed = boostRatio * baseSpeed;

        return baseSpeed + boostSpeed;
    }
}