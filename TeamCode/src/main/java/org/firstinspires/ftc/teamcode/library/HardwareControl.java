package org.firstinspires.ftc.teamcode.library;

import static org.firstinspires.ftc.teamcode.library.GVars.*;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

/*
    This script is similar to the GVars library script,
    but revolves around handling and setting up the the
    hardware for the script.
*/
public class HardwareControl {
    /*
       Get access to the methods in OpMode defining a LinearOpMode
       variable then using a constructor to define the activeOpMode
       with the opMode. This allows accessing LinearOpMode operations
       while in a class that doesn't extend LinearOpMode.
    */
    public LinearOpMode opMode;
    public HardwareControl(LinearOpMode currentOpMode) {opMode = currentOpMode;}

    // Motors
    public static DcMotor motorFront = null;
    public static DcMotor motorBack = null;
    public static DcMotor motorLeft = null;
    public static DcMotor motorRight = null;
    public static DcMotor motorArm = null;

    // Servos
    public static Servo servoClaw = null;
    public static Servo servoPlaneLauncher = null;

    // Webcam
    public WebcamName webcam = null;
    public VisionPortal visionPortal = null;

    /*
      All four motors front, back, left, and right.
      Grab them all from the hardwareMap and set their
      direction so that the driving works correctly.
    */
    private void driveInit() {
        motorFront = opMode.hardwareMap.get(DcMotor.class, "motorFront");
        motorBack = opMode.hardwareMap.get(DcMotor.class, "motorBack");
        motorLeft = opMode.hardwareMap.get(DcMotor.class, "motorLeft");
        motorRight = opMode.hardwareMap.get(DcMotor.class, "motorRight");
        motorFront.setDirection(GVars.FORWARD);
        motorBack.setDirection(GVars.REVERSE);
        motorLeft.setDirection(GVars.FORWARD);
        motorRight.setDirection(GVars.REVERSE);
    }

    private void armInit() {
        motorArm = opMode.hardwareMap.get(DcMotor.class, "motorArm");
        motorArm.setDirection(GVars.FORWARD);
        servoClaw = opMode.hardwareMap.get(Servo.class, "servoClaw");
        servoClaw.scaleRange(0.0, 0.3);
        servoClaw.setPosition(0.0);
    }

    private void launcherInit() {
        servoPlaneLauncher = opMode.hardwareMap.get(Servo.class, "servoPlaneLauncher");
        servoPlaneLauncher.scaleRange(0.4, 5.0);
        servoPlaneLauncher.setPosition(5.0);
    }

    private void aprilTagsInit() {
        webcam = opMode.hardwareMap.get(WebcamName.class, "Webcam 1");

        // Create the AprilTag processor.
        AprilTagProcessor aprilTag = new AprilTagProcessor.Builder()
                .setDrawCubeProjection(true)
                .build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Grab webcam from hardware map and set it's settings.
        builder.setCamera(webcam);
        builder.setCameraResolution(new Size(640, 480)); // Set camera resolution.
        builder.enableLiveView(true); // Enable live view.
        builder.addProcessor(aprilTag); // Set and enable the processor.

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Disable or re-enable the aprilTag processor at any time.
        visionPortal.setProcessorEnabled(aprilTag, true);
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
    This script is for giving access to a set of basic
    robot movement functions that can be used in both
    Autonomous and TeleOp modes.

    Time 0.175F at 1.0 Power = About 90 degree turn
    Time 0.5F second at 1 Power = 1 square on field
*/

//    private final HardwareControl hardware = new HardwareControl(opMode);
    //private final LinearOpMode opMode = HardwareControl.opMode;


//    public static final double autoMaxMovePower = GVars.autoMaxMovePower; // Speed used for moving while running in Autonomous.
//    public static final double autoMaxTurnPower = GVars.autoMaxTurnPower; // Speed used for turning while running in Autonomous.

    /**
     * Make the robot rotate to the left.
     * Meant for Autonomous scripts but can be used with TeleOp ones.
     * <p>
     * Setting time to 0.175F at 1.0 autoTurnPower results in a about 90 degree turn.
     * @param time Time to run operation as a float.
     */
    public static void turnLeft(LinearOpMode opMode, float time) {
        scriptRunTime.reset();

        opMode.telemetry.addData("Turning Right Time", time);
        opMode.telemetry.update();

        while (opMode.opModeIsActive() && (scriptRunTime.seconds() < time)){
            motorFront.setPower(-GVars.autoMaxTurnPower);
            motorBack.setPower(GVars.autoMaxTurnPower);
            motorLeft.setPower(-GVars.autoMaxTurnPower);
            motorRight.setPower(GVars.autoMaxTurnPower);
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
        scriptRunTime.reset();
        opMode.telemetry.addData("Turning Right Time", time);
        opMode.telemetry.update();

        while (opMode.opModeIsActive() && (scriptRunTime.seconds() < time)) {
            motorFront.setPower(GVars.autoMaxTurnPower);
            motorBack.setPower(-GVars.autoMaxTurnPower);
            motorLeft.setPower(GVars.autoMaxTurnPower);
            motorRight.setPower(-GVars.autoMaxTurnPower);
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
        scriptRunTime.reset();
        opMode.telemetry.addData("Moving Forward Time", time);
        opMode.telemetry.update();

        while (opMode.opModeIsActive() && (scriptRunTime.seconds() < time)){
            motorLeft.setPower(GVars.autoMaxMovePower);
            motorRight.setPower(GVars.autoMaxMovePower);
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
        scriptRunTime.reset();
        opMode.telemetry.addData("Moving Forward Time", time);
        opMode.telemetry.update();

        while (opMode.opModeIsActive() && (scriptRunTime.seconds() < time)){
            motorLeft.setPower(-GVars.autoMaxMovePower);
            motorRight.setPower(-GVars.autoMaxMovePower);
        }
        motorsStop();
    }

    /**
     * Stops the robot.
     * Meant for Autonomous scripts but can be used with TeleOp ones.
     */
    public static void motorsStop(){
        motorFront.setPower(0);
        motorBack.setPower(0);
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }
}
