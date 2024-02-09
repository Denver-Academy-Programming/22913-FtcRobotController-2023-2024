package org.firstinspires.ftc.teamcode.prototypev2;

import static org.firstinspires.ftc.teamcode.library.GVars.*;
import static org.firstinspires.ftc.teamcode.library.HardwareControlV2.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.library.GVars;
import org.firstinspires.ftc.teamcode.library.HardwareControlV2;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@TeleOp(name = "Prototype V2 Robot Script (THIS ONE DRIVERS!!!)", group = "Prototype V2 Scripts")
public class PrototypeV2RobotDrive extends LinearOpMode {
    // Give HardwareControlV2 our current OpMode to setup our hardware when "hardware.init" is called.
    final HardwareControlV2 hardware = new HardwareControlV2(this);

    private boolean planeLaunched = false; // Just a indicator that the paper plane launched

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
                telemetry.addData("servoPlaneLauncher Position", servoPlaneLauncher.getPosition());
                telemetry.addData("drive motorFrontLeft", motorFrontLeft.getCurrentPosition());
                telemetry.addData("drive motorFrontRight", motorFrontRight.getCurrentPosition());
                telemetry.addData("drive motorBackLeft", motorBackLeft.getCurrentPosition());
                telemetry.addData("drive motorBackRight", motorBackRight.getCurrentPosition());
                telemetry.addLine(String.format("gamepad1 Right Joystick X:%d Y:%s", gamepad1.right_stick_x, gamepad1.right_stick_y));
                telemetry.addLine(String.format("gamepad1 Left Joystick X:%d Y:%s", gamepad1.left_stick_x, gamepad1.left_stick_y));
                telemetry.addLine(String.format("gamepad2 Right Joystick X:%d Y:%s", gamepad2.right_stick_x, gamepad2.right_stick_y));
                telemetry.addLine(String.format("gamepad2 Left Joystick X:%d Y:%s", gamepad2.left_stick_x, gamepad2.left_stick_y));
                //telemetry.addData("Camera State", visionPortal.getCameraState());
            }
            telemetry.update();
        }

        // Because we don't have waitForStart(), if in init we hit stop, we need to make sure it stops.
        if (isStopRequested()){return;}

        // Reset the currently running timer and start the visionPortal
        scriptRunTime.reset();
        //visionPortal.resumeLiveView();

        while (opModeIsActive()) {

            // Get out AprilTag detections and output information about them
            //telemetryAprilTag();

            // Display on the Driver Hub info about our robot while its running
            telemetry.addData("Status", "Script is running!");
            telemetry.addData("Run Time", scriptRunTime);
            telemetry.addData("Paper Plane Launched?", planeLaunched);
            //telemetry.addData("AprilTag/TensorFlow State", visionPortal.getCameraState());
            telemetry.addData("IN CASE OF EMERGENCY", "PRESS STOP BUTTON ON DRIVER STATION TO STOP!");

            // Handle button inputs for controllers
            if (gamepad1.x) {
                servoPlaneLauncher.setDirection(servoREVERSE);
                servoPlaneLauncher.setPosition(0);
                planeLaunched = true;
                servoPlaneLauncher.close();
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

            // Keep the motorArm from exceeding its limits that we've set in GVars
            if (-gamepad2.right_stick_y > 0) {
                if (motorArmPivot.getCurrentPosition() < GVars.motorArmPivotMaxPosition) {
                    motorArmPivot.setPower(Range.clip(gamepad2.right_stick_x, -GVars.armPivotMaxPower, GVars.armPivotMaxPower));
                } else { // Correct movement back to the max position
                    motorArmPivot.setTargetPosition(motorArmPivotMaxPosition);
                    motorArmPivot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    while (motorArmPivot.getCurrentPosition() != GVars.motorArmPivotMaxPosition) {
                        motorArmPivot.setPower(1);
                    }
                    motorArmPivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    motorArmPivot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }
            } else if (gamepad2.right_stick_y < 0) {
                if (motorArmPivot.getCurrentPosition() > GVars.motorArmPivotMinPosition) {
                    motorArmPivot.setPower(Range.clip(gamepad2.right_stick_x, -GVars.armPivotMaxPower, GVars.armPivotMaxPower));
                } else { // Correct movement back to the min position if for some reason
                    motorArmPivot.setTargetPosition(motorArmPivotMinPosition);
                    motorArmPivot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    while (motorArmPivot.getCurrentPosition() != GVars.motorArmPivotMinPosition) {
                        motorArmPivot.setPower(1);
                    }
                    motorArmPivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    motorArmPivot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }
            } else {
                motorArmPivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }

            // Set our variables with our controllers joystick input
            double y = -gamepad1.left_stick_y;
            double rx = gamepad1.left_stick_x * GVars.teleopMaxTurnScale;
            double x = gamepad1.right_stick_x;

            double frontLeftPower = Range.clip(y - x + rx, -1.0, 1.0) / GVars.teleopMaxMoveScale;
            double frontRightPower = Range.clip(y + x + rx, -1.0, 1.0) / GVars.teleopMaxMoveScale;
            double backLeftPower = Range.clip(y + x - rx, -1.0, 1.0) / GVars.teleopMaxMoveScale;
            double backRightPower = Range.clip(y - x - rx, -1.0, 1.0) /  GVars.teleopMaxMoveScale;

            motorFrontLeft.setPower(frontLeftPower);
            motorFrontRight.setPower(frontRightPower);
            motorBackLeft.setPower(backLeftPower);
            motorBackRight.setPower(backRightPower);

            if (debug) {
                telemetry.addData("motorArm Position", motorArm.getCurrentPosition());
                telemetry.addData("motorArmPivot Position", motorArmPivot.getCurrentPosition());
                telemetry.addData("servoClawPivot1 Position", servoClawPivot1.getPosition());
                telemetry.addData("servoClawPivot2 Position", servoClawPivot2.getPosition());
                telemetry.addData("servoPlaneLauncher Position", servoPlaneLauncher.getPosition());
                telemetry.addData("drive motorFrontLeft", motorFrontLeft.getPower());
                telemetry.addData("drive motorFrontRight", motorFrontRight.getPower());
                telemetry.addData("drive motorBackLeft", motorBackLeft.getPower());
                telemetry.addData("drive motorBackRight", motorBackRight.getPower());
                telemetry.addData("y", y);
                telemetry.addData("x", x);
                telemetry.addData("rx", rx);
                telemetry.addLine(String.format("gamepad1 Right Joystick X:%d Y:%s", gamepad1.right_stick_x, gamepad1.right_stick_y));
                telemetry.addLine(String.format("gamepad1 Left Joystick X:%d Y:%s", gamepad1.left_stick_x, gamepad1.left_stick_y));
                telemetry.addLine(String.format("gamepad2 Right Joystick X:%d Y:%s", gamepad2.right_stick_x, gamepad2.right_stick_y));
                telemetry.addLine(String.format("gamepad2 Left Joystick X:%d Y:%s", gamepad2.left_stick_x, gamepad2.left_stick_y));
                //telemetry.addData("Camera State", visionPortal.getCameraState());
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
