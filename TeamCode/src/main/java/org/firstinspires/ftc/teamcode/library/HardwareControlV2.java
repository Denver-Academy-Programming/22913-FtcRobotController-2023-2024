package org.firstinspires.ftc.teamcode.library;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.concurrent.TimeUnit;

/*
    This library script is used to setup and control certain hardware
    functions for the Prototype V2 Robot. Similar to GVars, hardware
    can be retrieved from this library script to be used globally throughout
    TeleOp or Autonomous OpModes.
*/
public class HardwareControlV2 {
    /*
       Get access to the methods in OpMode defining a LinearOpMode
       variable then using a constructor to define the activeOpMode
       with the opMode. This allows accessing LinearOpMode operations
       while in a class that doesn't extend LinearOpMode.
    */
    public static LinearOpMode opMode;
    public HardwareControlV2(LinearOpMode currentOpMode) {opMode = currentOpMode;}

    private static final double autoMaxMovePower = GVars.autoMaxMovePower; // Speed used for moving while running in Autonomous.
    private static final double autoMaxTurnPower = GVars.autoMaxTurnPower; // Speed used for turning while running in Autonomous.

    // Motors
    public static DcMotor motorFrontLeft = null; // Control Hub: Port 0
    public static DcMotor motorFrontRight = null; // Control Hub: Port 1
    public static DcMotor motorBackLeft = null; // Control Hub: Port 2
    public static DcMotor motorBackRight = null; // Control Hub: Port 3
    public static DcMotor motorArm = null; // Extension Hub: Port 0
    public static DcMotor motorArmPivot = null; // Extension Hub: Port 1

    // Servos
    public static Servo servoPlaneLauncher = null; // Control Hub: Port 0
    public static Servo servoClawPivot = null; // Control Hub: Port 1
    public static Servo servoClaw = null; // Control Hub: Port 2


    // Webcam/AprilTags
    public static AprilTagProcessor aprilTag = null;
    public static WebcamName webcam = null;
    public static VisionPortal visionPortal = null;

    public static TfodProcessor tfod = null;

    /*
      All four motors front, back, left, and right.
      Grab them all from the hardwareMap and set their
      direction so that the driving works correctly.
    */
    private void driveInit() {
        motorFrontLeft = opMode.hardwareMap.get(DcMotor.class, "motorFrontLeft"); // Control Hub Port: 0
        motorFrontRight = opMode.hardwareMap.get(DcMotor.class, "motorFrontRight");// Control Hub Port: 1
        motorBackLeft = opMode.hardwareMap.get(DcMotor.class, "motorBackLeft"); // Control Hub Port: 2
        motorBackRight = opMode.hardwareMap.get(DcMotor.class, "motorBackRight"); // Control Hub Port: 3
        motorFrontLeft.setDirection(GVars.motorREVERSE);
        motorFrontRight.setDirection(GVars.motorREVERSE);
        motorBackLeft.setDirection(GVars.motorFORWARD);
        motorBackRight.setDirection(GVars.motorREVERSE);
    }

    private void armInit() {
        motorArmPivot = opMode.hardwareMap.get(DcMotor.class, "motorArmPivot");
        motorArmPivot.setDirection(GVars.motorFORWARD);
        motorArmPivot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorArmPivot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorArmPivot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorArmPivot.setTargetPosition(0);

        motorArm = opMode.hardwareMap.get(DcMotor.class, "motorArm");
        motorArm.setDirection(GVars.motorFORWARD);
        motorArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorArm.setTargetPosition(0);

        servoClawPivot = opMode.hardwareMap.get(Servo.class, "servoClawPivot");
        servoClawPivot.scaleRange(0.0, 0.3);
        servoClawPivot.setPosition(0.0);

        servoClaw = opMode.hardwareMap.get(Servo.class, "servoClaw");
        servoClaw.scaleRange(0.0, 0.3);
        servoClaw.setPosition(0.0);
    }

    private void launcherInit() {
        servoPlaneLauncher = opMode.hardwareMap.get(Servo.class, "servoPlaneLauncher");
        servoPlaneLauncher.setDirection(Servo.Direction.REVERSE);
        servoPlaneLauncher.scaleRange(0.4, 5.0);
        servoPlaneLauncher.setPosition(5.0);
    }

    private void aprilTagsInit() {
        webcam = opMode.hardwareMap.get(WebcamName.class, "Webcam 1");

        // Create the TensorFlow processor.
        tfod = TfodProcessor.easyCreateWithDefaults();

        // Create the AprilTag processor.
        aprilTag = new AprilTagProcessor.Builder()
                .setDrawCubeProjection(true)
                .setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                .build();

        VisionProcessor[] processors = {tfod, aprilTag};

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Grab webcam from hardware map and set it's settings.
        builder.setCamera(webcam);
        builder.setCameraResolution(new Size(640, 480)); // Set camera resolution.
        builder.enableLiveView(true); // Enable live view.
        builder.setAutoStopLiveView(true); // Automatically disable the AprilTag and TensorFlow processors when LiveView is disabled.
        builder.addProcessors(processors); // Add our TensorFlow and AprilTag processors.

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Enable both the TensorFlow and AprilTag processors.
        visionPortal.setProcessorEnabled(processors[0], true);
        visionPortal.setProcessorEnabled(processors[1], true);
    }

    public void init(boolean drive, boolean arm, boolean launcher, boolean apriltags) {
        /*
           Initialize and setup the modules that are linked to the control hub.
           Setting the motors up so that the robot can function
           Setting the left motor to reverse due to its position
           relative to the right motors placement so that it can move forward.
        */
        try {

            // Motors for driving the robot around
            if (drive) {
                driveInit();
            }

            // Motor and servo for the arm and claw
            if (arm) {
                armInit();
            }

            // Servo for the paper plane launcher
            if (launcher) {
                launcherInit();
            }

            // Webcam and VisionPortal setup for AprilTags
            if (apriltags) {
                aprilTagsInit();
            }

            opMode.telemetry.addData("HardwareControl completed!", "Below are enabled hardware...");
            opMode.telemetry.addData("Drive", drive);
            opMode.telemetry.addData("Arm", arm);
            opMode.telemetry.addData("Launcher", launcher);
            opMode.telemetry.addData("AprilTags", apriltags);
            opMode.telemetry.addData("Current OpMode Type", opMode);
            opMode.telemetry.addLine();
           //opMode.telemetry.update();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(
                    "A error occurred in the initialization of the script \n" +
                            "Please check you have the right motor control scheme set on the \n" +
                            "Driver Hub so it knows which modules are connected. \n" +
                            "Full traceback: \n" + e
            );
        }
    }


    /*
        These functions are for giving access to a set of basic
        robot movement functions that can be used in both
        Autonomous and TeleOp modes.

        Time 0.175F at 1.0 Power = About 90 degree turn
        Time 0.5F second at 1 Power = 1 square on field
    */

    /**
     * Make the robot rotate to the left.
     * Meant for Autonomous scripts but can be used with TeleOp ones.
     * <p>
     * Setting time to 0.175F at 1.0 autoTurnPower results in a about 90 degree turn.
     * @param time Time to run operation as a float.
     */
    public static void turnLeft(LinearOpMode opMode, float time) {
        GVars.scriptRunTime.reset();

        opMode.telemetry.addData("Turning Right Time", time);
        opMode.telemetry.update();

        while (opMode.opModeIsActive() && (GVars.scriptRunTime.seconds() < time)){
            motorFrontLeft.setPower(-autoMaxTurnPower);
            motorFrontRight.setPower(autoMaxTurnPower);
            motorBackLeft.setPower(-autoMaxTurnPower);
            motorBackRight.setPower(autoMaxTurnPower);
        }
        motorsStop();
    }

    /**
     * Make the robot rotate to the right.
     * Meant for Autonomous scripts but can be used with TeleOp ones.
     * <p>
     * Setting time to 0.175F at 1.0 autoTurnPower results in a about 90 degree turn.
     * @param time Time to run operation as a float.
     */
    public static void turnRight(LinearOpMode opMode, float time) {
        GVars.scriptRunTime.reset();
        opMode.telemetry.addData("Turning Right Time", time);
        opMode.telemetry.update();

        while (opMode.opModeIsActive() && (GVars.scriptRunTime.seconds() < time)) {
            motorFrontLeft.setPower(autoMaxTurnPower);
            motorFrontRight.setPower(-autoMaxTurnPower);
            motorBackLeft.setPower(autoMaxTurnPower);
            motorBackRight.setPower(-autoMaxTurnPower);
        }
        motorsStop();
    }

    /**
     * Make the robot move forward.
     * Meant for Autonomous scripts but can be used with TeleOp ones.
     * <p>
     * Setting time to 0.5F at 1.0 autoMovePower results in moving forward one game field square.
     * @param time Time to run operation as a float.
     */
    public static void moveForward(LinearOpMode opMode, float time) {
        GVars.scriptRunTime.reset();
        opMode.telemetry.addData("Moving Forward Time", time);
        opMode.telemetry.update();

        while (opMode.opModeIsActive() && (GVars.scriptRunTime.seconds() < time)){
            motorFrontLeft.setPower(autoMaxMovePower);
            motorFrontRight.setPower(autoMaxMovePower);
            motorBackLeft.setPower(autoMaxMovePower);
            motorBackRight.setPower(autoMaxMovePower);
        }
        motorsStop();
    }

    /**
     * Make the robot move backward.
     * Meant for Autonomous scripts but can be used with TeleOp ones.
     * <p>
     * Setting time to 0.5F at 1.0 autoMovePower results in moving backward one game field square.
     * @param time Time to run operation as a float.
     */
    public static void moveBackward(LinearOpMode opMode, float time) {
        GVars.scriptRunTime.reset();
        opMode.telemetry.addData("Moving Forward Time", time);
        opMode.telemetry.update();

        while (opMode.opModeIsActive() && (GVars.scriptRunTime.seconds() < time)){
            motorFrontLeft.setPower(-autoMaxMovePower);
            motorFrontRight.setPower(-autoMaxMovePower);
            motorBackLeft.setPower(-autoMaxMovePower);
            motorBackRight.setPower(-autoMaxMovePower);
        }
        motorsStop();
    }

    /**
     * Stops the robot.
     * Meant for Autonomous scripts but can be used with TeleOp ones.
     */
    public static void motorsStop(){
        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);
        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
    }

    /**
     * Apply the power from our joystick inputs
     * <p>
     * Positive X is forward
     * <p>
     * Positive Y is strafe left
     * <p>
     * Positive Yaw is counter-clockwise
     */
    public static void moveRobot(double x, double y, double yaw) {
        // Calculate drive powers
        double leftFrontPower    =  x -y -yaw;
        double rightFrontPower   =  x +y +yaw;
        double leftBackPower     =  x +y -yaw;
        double rightBackPower    =  x -y +yaw;

        // Normalize drive powers to be less than 1.0
        double max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        // Make sure the drive powers do not go over the max
        if (max > 1.0) {
            leftFrontPower /= max;
            rightFrontPower /= max;
            leftBackPower /= max;
            rightBackPower /= max;
        }

        // Send powers to the motors
        motorFrontLeft.setPower(leftFrontPower);
        motorFrontRight.setPower(rightFrontPower);
        motorBackLeft.setPower(leftBackPower);
        motorBackRight.setPower(rightBackPower);
    }
    /*
      Manually set the camera gain and exposure.
      This can only be called after the webcam initializes.
    */
    public static void setManualExposure(int exposureMS, int gain) {

        // We can't adjust the exposure if the camera isn't on, so just return
        if (visionPortal == null) {
            return;
        }

        // Make sure camera is streaming before we try to set the exposure controls
        if (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
            opMode.telemetry.addData("Camera", "Waiting");
            opMode.telemetry.update();
            while (!opMode.isStopRequested() && (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING)) {
                opMode.sleep(20);
            }
            opMode.telemetry.addData("Camera", "Ready");
            opMode.telemetry.update();
        }

        // Set camera controls unless we are stopping.
        if (!opMode.isStopRequested())
        {
            ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
            if (exposureControl.getMode() != ExposureControl.Mode.Manual) {
                exposureControl.setMode(ExposureControl.Mode.Manual);
                opMode.sleep(50);
            }
            exposureControl.setExposure(exposureMS, TimeUnit.MILLISECONDS);
            opMode.sleep(20);
            GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
            gainControl.setGain(gain);
            opMode.sleep(20);
        }
    }
}
