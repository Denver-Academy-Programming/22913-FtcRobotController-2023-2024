package org.firstinspires.ftc.teamcode.prototypev2;

import static org.firstinspires.ftc.teamcode.library.GVars.*;
import static org.firstinspires.ftc.teamcode.library.HardwareControlV2.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.library.HardwareControlV2;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@TeleOp(name = "Prototype V2 Robot Script (THIS ONE DRIVERS!!!)", group = "Prototype V2 Scripts")
public class PrototypeV2RobotDrive extends LinearOpMode {
    final HardwareControlV2 hardware = new HardwareControlV2(this);

    private boolean planeLaunched = false;
    private boolean detectionOn = true;

    // Movement power variables
    double drive = 0; // Desired forward power/speed (-1 to +1)
    double strafe = 0; // Desired strafe power/speed (-1 to +1)
    double turn = 0; // Desired turning power/speed (-1 to +1)

    @Override
    public void runOpMode() {
        hardware.init(true, true, true, true);

        telemetry.addData("Status", "Modules initialized!");
        telemetry.addData("User Action", "Waiting for user to start script...");
        telemetry.update();

        waitForStart();
        scriptRunTime.reset();
        visionPortal.resumeLiveView();

        while (opModeIsActive()) {

            telemetryAprilTag();

            // Set our variables with our controllers joystick input
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.1;
            double rx = gamepad1.right_stick_x;

            double armPower = -gamepad2.right_stick_y;

            // Display on the Driver Hub info about our robot while its running
            telemetry.addData("Status", "Script is running!");
            telemetry.addData("Run Time", scriptRunTime);
            telemetry.addData("Paper Plane Launched?", planeLaunched);
            telemetry.addData("AprilTag/TensorFlow Running?", detectionOn);
            telemetry.addData("Manual", "Drive %5.2f, Strafe %5.2f, Turn %5.2f ", drive, strafe, turn);
            telemetry.addData("STOP!", "PRESS STOP BUTTON ON DRIVER STATION TO STOP!");

            // Checking if the bumper buttons are pressed to turn the robot.
//            if (gamepad1.left_bumper) {
//                motorFrontLeft.setPower(-teleopMaxTurnPower);
//                motorFrontRight.setPower(teleopMaxTurnPower);
//                motorBackLeft.setPower(-teleopMaxTurnPower);
//                motorBackRight.setPower(teleopMaxTurnPower);
//            }
//            if (gamepad1.right_bumper) {
//                motorFrontLeft.setPower(teleopMaxTurnPower);
//                motorFrontRight.setPower(-teleopMaxTurnPower);
//                motorBackLeft.setPower(teleopMaxTurnPower);
//                motorBackRight.setPower(-teleopMaxTurnPower);
//            }

            if (gamepad1.x) {
                servoPlaneLauncher.setPosition(0.4);
                planeLaunched = true;
            }

            if (gamepad2.x) {
                if (detectionOn) {
                    visionPortal.stopLiveView();
                    detectionOn = false;
                } else {
                    visionPortal.resumeLiveView();
                    detectionOn = true;
                }
            }

            if (gamepad2.left_bumper) {
                motorArmPivot.setTargetPosition(motorArmPivotMinPosition);
            }

            if (gamepad2.right_bumper) {
                motorArmPivot.setTargetPosition(motorArmPivotMaxPosition);
            }




            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only if at least one is out of the range [-1, 1]
          double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
          double frontLeftPower = (y - x - rx) / denominator;
          double backLeftPower = (y + x - rx) / denominator;
          double frontRightPower = (y + x + rx) / denominator;
          double backRightPower = (y - x +  rx) / denominator;

//            double frontLeftPower = y;
//            double frontRightPower = y;
//            double backLeftPower = y;
//            double backRightPower = y;

            /* Direction motor movement
            Front: All motors forward
            Back: All motors back
            Left:

            */

            telemetry.addData("drive y", y);
            telemetry.addData("drive x", x);
            telemetry.addData("drive rx", rx);
            telemetry.addData("motorArm Position", motorArm.getCurrentPosition());
            telemetry.addData("motorArmPiviot Position", motorArmPivot.getCurrentPosition());
            telemetry.addData("servoPlaneLauncher Position", servoPlaneLauncher.getPosition());
//            telemetry.addData("drive frontLeftPower", frontLeftPower);
//            telemetry.addData("drive frontRightPower", frontRightPower);
//            telemetry.addData("drive backLeftPower", backLeftPower);
//            telemetry.addData("drive backRightPower", backRightPower);

            motorFrontLeft.setPower(frontLeftPower);
            motorFrontRight.setPower(frontRightPower);
            motorBackLeft.setPower(backLeftPower);
            motorBackRight.setPower(backRightPower);

            // Forwards and Back on Left Stick using the Y-axis
//            frontLeftAsDcMotor.setPower(-gamepad1.left_stick_y);
//            backLeftAsDcMotor.setPower(-gamepad1.left_stick_y);
//            frontRightAsDcMotor.setPower(gamepad1.left_stick_y);
//            backRightAsDcMotor.setPower(gamepad1.left_stick_y);
//            // Strafing on Right Stick using the X-axis
//            // Move the stick right and left for the robot to slide right and left
//            frontLeftAsDcMotor.setPower(gamepad1.right_stick_x);
//            backLeftAsDcMotor.setPower(-gamepad1.right_stick_x);
//            frontRightAsDcMotor.setPower(gamepad1.right_stick_x);
//            backRightAsDcMotor.setPower(-gamepad1.right_stick_x);
//            // Turning on Left Joystick using X-Axis
//            frontLeftAsDcMotor.setPower(gamepad1.left_stick_x);
//            backLeftAsDcMotor.setPower(gamepad1.left_stick_x);
//            frontRightAsDcMotor.setPower(gamepad1.left_stick_x);
//            backRightAsDcMotor.setPower(gamepad1.left_stick_x);

            //motorArmPivot.setPower(armPower);

            // Apply the power from our joystick inputs to the movement function
            //moveRobot(drive, strafe, turn);
            setManualExposure(6, 250);  // Use low exposure time to reduce motion blur
            telemetry.update();
            sleep(20);
        }
    }

     /*
     * Add telemetry about AprilTag detections.
     */
    private void telemetryAprilTag() {

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            } else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }

        // Add "key" information to telemetry
        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        telemetry.addLine("RBE = Range, Bearing & Elevation");

    }
}
