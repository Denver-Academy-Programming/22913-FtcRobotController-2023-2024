package org.firstinspires.ftc.teamcode.prototypev2;

import static org.firstinspires.ftc.teamcode.library.GVars.*;
import static org.firstinspires.ftc.teamcode.library.HardwareControlV2.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.library.GVars;
import org.firstinspires.ftc.teamcode.library.HardwareControlV2;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@TeleOp(name = "Prototype V2 Robot Script (THIS ONE DRIVERS!!!)", group = "Prototype V2 Scripts")
public class PrototypeV2RobotDrive extends LinearOpMode {
    // Give HardwareControlV2 our current OpMode to setup our hardware when "hardware.init" is called.
    final HardwareControlV2 hardware = new HardwareControlV2(this);

    private boolean planeLaunched = false; // Just a indicator that the paper plane launched
    private boolean clawOpen = false;
    double frontLeftPower = 0;
    double frontRightPower = 0;
    double backLeftPower = 0;
    double backRightPower = 0;
    double motorArmPower = 0;
    double motorArmPivotPower = 0;
    double motorClawPivotPower = 0;
    double y = 0;
    double x = 0;
    double rx = 0;
    double speedLevel = 0;

    @Override
    public void runOpMode() {
        // Initialize the hardware for the robot
        hardware.init(true, true, true, false);

        /*
            Display the current positions for all motors and servos while in Init.
            Helpful for debugging and making sure stuff is working.
            We don't use waitForStart() instead using this loop so we can have
            debug info.
        */
        while (opModeInInit()) {
            telemetry.addData("Status", "Modules initialized!");
            telemetry.addData("User Action", "Waiting for user to start script...");
            if (debug) {
                telemetry.addData("motorArm Position", motorArm.getCurrentPosition());
                telemetry.addData("motorArmPivot Position", motorArmPivot.getCurrentPosition());
                //telemetry.addData("motorClawPivot Position", motorClawPivot.getCurrentPosition());
                telemetry.addData("servoClawPivot1 Position", servoClawPivot1.getPosition());
                telemetry.addData("servoClawPivot2 Position", servoClawPivot2.getPosition());
                telemetry.addData("servoClaw1 Position", servoClaw1.getPosition());
                telemetry.addData("servoClaw2 Position", servoClaw2.getPosition());
                telemetry.addData("servoPlaneLauncher Position", servoPlaneLauncher.getPosition());
                telemetry.addData("frontLeftPower", frontLeftPower);
                telemetry.addData("frontRightPower", frontRightPower);
                telemetry.addData("backLeftPower", backLeftPower);
                telemetry.addData("backRightPower", backRightPower);
                telemetry.addData("y", y);
                telemetry.addData("x", x);
                telemetry.addData("rx", rx);
                telemetry.addLine(String.format("gamepad1 Right Joystick X:%f Y:%f", gamepad1.right_stick_x, gamepad1.right_stick_y));
                telemetry.addLine(String.format("gamepad1 Left Joystick X:%f Y:%f", gamepad1.left_stick_x, gamepad1.left_stick_y));
                telemetry.addLine(String.format("gamepad2 Right Joystick X:%f Y:%f", gamepad2.right_stick_x, gamepad2.right_stick_y));
                telemetry.addLine(String.format("gamepad2 Left Joystick X:%f Y:%f", gamepad2.left_stick_x, gamepad2.left_stick_y));
                //telemetry.addData("AprilTag/TensorFlow/Camera State", visionPortal.getCameraState());
            }
            telemetry.update();
        }

        // Because we don't have waitForStart(), if in init we hit stop, we need to make sure it stops.
        if (isStopRequested()){return;}

        // Reset the currently running timer and start the visionPortal
        GVars.scriptRunTime.reset();
        //visionPortal.resumeLiveView();

        while (opModeIsActive()) {

            // Get out AprilTag detections and output information about them
            //telemetryAprilTag();

            // Handle button inputs for controllers
            if (gamepad1.x) {
                servoPlaneLauncher.setPosition(1);
                planeLaunched = true;
            } else if (gamepad1.left_bumper) {
                ; // Add speed changing with bumpers
            }

            // Disable our visionPortal manually to not use up our hardware resources
//            if (gamepad2.x &&
//                    (visionPortal.getCameraState() == VisionPortal.CameraState.CAMERA_DEVICE_READY) ||
//                    (visionPortal.getCameraState() == VisionPortal.CameraState.STREAMING)) {
//                if (visionPortal.getCameraState() == VisionPortal.CameraState.STREAMING) {
//                    visionPortal.stopLiveView();
//                } else {
//                    visionPortal.resumeLiveView();
//                }
//            }

            // Opening and closing the servoClaws
            if (gamepad2.x && (servoClaw1.getPosition() == 0.7)) {
                // Open both claws
                servoClaw1.setDirection(GVars.servoFORWARD);
                servoClaw2.setDirection(GVars.servoREVERSE);
                servoClaw1.setPosition(0);
                servoClaw2.setPosition(0.7);
                clawOpen = false;
            } else if (gamepad2.y && (servoClaw1.getPosition() == 0)) {
                // Close both claws
                servoClaw1.setDirection(GVars.servoREVERSE);
                servoClaw2.setDirection(GVars.servoFORWARD);
                servoClaw1.setPosition(0.7);
                servoClaw2.setPosition(0);
                clawOpen = true;
            // Opening and closing the servoClawPivot servo
            } else if (gamepad2.dpad_down && (servoClawPivot1.getPosition() == 0)) {
                // Open it facing forward
                servoClawPivot1.setPosition(0.3);
            } else if (gamepad2.dpad_up && (servoClawPivot1.getPosition() == 0.3)) {
                // Close it facing it up
                servoClawPivot1.setPosition(0);
            } else if (gamepad2.dpad_left && (servoClawPivot2.getPosition() == 0)) {
                // Open it facing forward
                servoClawPivot2.setPosition(0.8);
            } else if (gamepad2.dpad_right && (servoClawPivot2.getPosition() == 0.8)) {
                // Close it facing it up
                servoClawPivot2.setPosition(0);
            }

            // Keep the motorArm from exceeding its limits that we've set in GVars
            motorArmPower = Range.clip(-gamepad2.left_stick_y, -GVars.armMaxPower, GVars.armMaxPower);
            if (((motorArmPower > 0) && (motorArm.getCurrentPosition() < GVars.motorArmMaxPosition)) ||
                    ((motorArmPower < 0) && motorArm.getCurrentPosition() > GVars.motorArmMinPosition)) {
                motorArm.setPower(motorArmPower);
            } else {
                motorArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                motorArm.setPower(0);
            }

            // Keep the motorArmPivot from exceeding its limits that we've set in GVars
            motorArmPivotPower = Range.clip(-gamepad2.right_stick_y, -GVars.armPivotMaxPower, GVars.armPivotMaxPower);
            if (((motorArmPivotPower > 0) && (motorArmPivot.getCurrentPosition() < GVars.motorArmPivotMaxPosition)) ||
                    ((motorArmPivotPower < 0) && (motorArmPivot.getCurrentPosition() > GVars.motorArmPivotMinPosition))) {
                motorArmPivot.setPower(motorArmPivotPower);
            } else {
                motorArmPivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                motorArmPivot.setPower(0);
            }

            // Keep the motorClawPivot from exceeding its limits that we've set in GVars
//            motorClawPivotPower = Range.clip(-gamepad2.right_stick_y, -GVars.clawPivotMaxPower, GVars.clawPivotMaxPower);
//            if (((motorClawPivotPower > 0) && (motorClawPivot.getCurrentPosition() < GVars.motorClawPivotMaxPosition)) ||
//                    ((motorClawPivotPower < 0) && (motorClawPivot.getCurrentPosition() > GVars.motorClawPivotMinPosition))) {
//                motorClawPivot.setPower(motorClawPivotPower);
//            } else {
//                motorClawPivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//                motorClawPivot.setPower(0);
//            }

            // Set our variables with our controllers joystick input
            y = -gamepad1.left_stick_y;
            rx = gamepad1.right_stick_x * GVars.teleopMaxTurnScale;
            x = gamepad1.left_stick_x;

            frontLeftPower = Range.clip(y - rx + x, -1.0, 1.0) / GVars.teleopMaxMoveScale;
            frontRightPower = Range.clip(y + rx + x, -1.0, 1.0) / GVars.teleopMaxMoveScale;
            backLeftPower = Range.clip(y + rx - x, -1.0, 1.0) / GVars.teleopMaxMoveScale;
            backRightPower = Range.clip(y - rx - x, -1.0, 1.0) /  GVars.teleopMaxMoveScale;

            motorFrontLeft.setPower(frontLeftPower);
            motorFrontRight.setPower(frontRightPower);
            motorBackLeft.setPower(backLeftPower);
            motorBackRight.setPower(backRightPower);

            // Display on the Driver Hub info about our robot while its running
            telemetry.addData("Status", "Script is running!");
            telemetry.addLine(String.format("Run Time: %.2f", GVars.scriptRunTime.seconds()));
            telemetry.addData("Paper Plane Launched?", planeLaunched);
            telemetry.addData("Claw Open?", clawOpen);
            //telemetry.addData("AprilTag/TensorFlow/Camera State", visionPortal.getCameraState());
            telemetry.addData("IN CASE OF EMERGENCY", "PRESS STOP BUTTON ON DRIVER STATION TO STOP!");

            if (debug) {
                telemetry.addData("motorArm Position", motorArm.getCurrentPosition());
                telemetry.addData("motorArmPivot Position", motorArmPivot.getCurrentPosition());
                //telemetry.addData("motorClawPivot Position", motorClawPivot.getCurrentPosition());
                telemetry.addData("servoClawPivot1 Position", servoClawPivot1.getPosition());
                telemetry.addData("servoClawPivot2 Position", servoClawPivot2.getPosition());
                telemetry.addData("servoClaw1 Position", servoClaw1.getPosition());
                telemetry.addData("servoClaw2 Position", servoClaw2.getPosition());
                telemetry.addData("servoPlaneLauncher Position", servoPlaneLauncher.getPosition());
                telemetry.addData("frontLeftPower", frontLeftPower);
                telemetry.addData("frontRightPower", frontRightPower);
                telemetry.addData("backLeftPower", backLeftPower);
                telemetry.addData("backRightPower", backRightPower);
                telemetry.addData("y", y);
                telemetry.addData("x", x);
                telemetry.addData("rx", rx);
                telemetry.addLine(String.format("gamepad1 Right Joystick X:%f Y:%f", gamepad1.right_stick_x, gamepad1.right_stick_y));
                telemetry.addLine(String.format("gamepad1 Left Joystick X:%f Y:%f", gamepad1.left_stick_x, gamepad1.left_stick_y));
                telemetry.addLine(String.format("gamepad2 Right Joystick X:%f Y:%f", gamepad2.right_stick_x, gamepad2.right_stick_y));
                telemetry.addLine(String.format("gamepad2 Left Joystick X:%f Y:%f", gamepad2.left_stick_x, gamepad2.left_stick_y));
                //telemetry.addData("AprilTag/TensorFlow/Camera State", visionPortal.getCameraState());
            }

            // Apply the power from our joystick inputs to the movement function
            //moveRobot(drive, strafe, turn);
            //setManualExposure(6, 250);  // Use low exposure time to reduce motion blur.
            telemetry.update();
            //sleep(20); // Make sure CPU doesn't work too hard.
        }
    }

     /*
       Add telemetry about AprilTag detections.
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
