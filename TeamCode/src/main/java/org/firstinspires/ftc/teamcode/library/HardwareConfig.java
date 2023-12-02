package org.firstinspires.ftc.teamcode.library;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

/*
    This script is similar to the GVars library script,
    but revolves around handling and setting up the the
    hardware for the script.
*/
public class HardwareConfig {
    /*
       Get access to the methods in OpMode defining a LinearOpMode
       variable then using a constructor to define the activeOpMode
       with the opMode. This allows accessing LinearOpMode operations
       while in a class that doesn't extend LinearOpMode.
    */
    public static LinearOpMode opMode = null;
    public HardwareConfig(LinearOpMode currentOpMode) {opMode = currentOpMode;}

    // Motors
    public DcMotor motorFront = null;
    public DcMotor motorBack = null;
    public DcMotor motorLeft = null;
    public DcMotor motorRight = null;
    public DcMotor motorArm = null;

    // Servos
    public Servo servoClaw = null;
    public Servo servoPlaneLauncher = null;

    // Webcam
    public WebcamName webcam = null;
    public VisionPortal visionPortal = null;

    /*
      All four motors front, back, left, and right.
      Grab them all from the hardwareMap and set their
      direction so that the driving works correctly.
    */
    private void driveInit() {
        motorFront = BlocksOpModeCompanion.hardwareMap.get(DcMotor.class, "motorFront");
        motorBack = BlocksOpModeCompanion.hardwareMap.get(DcMotor.class, "motorBack");
        motorLeft = BlocksOpModeCompanion.hardwareMap.get(DcMotor.class, "motorLeft");
        motorRight = BlocksOpModeCompanion.hardwareMap.get(DcMotor.class, "motorRight");
        motorFront.setDirection(GVars.REVERSE);
        motorBack.setDirection(GVars.FORWARD);
        motorRight.setDirection(GVars.FORWARD);
        motorLeft.setDirection(GVars.REVERSE);
    }

    private void armInit() {
        motorArm = BlocksOpModeCompanion.hardwareMap.get(DcMotor.class, "motorArm");
        motorArm.setDirection(GVars.FORWARD);
        servoClaw = BlocksOpModeCompanion.hardwareMap.get(Servo.class, "servoClaw");
        servoClaw.scaleRange(0.0, 0.3);
        servoClaw.setPosition(0.0);
    }

    private void launcherInit() {
        servoPlaneLauncher = BlocksOpModeCompanion.hardwareMap.get(Servo.class, "servoPlaneLauncher");
        servoPlaneLauncher.scaleRange(0.4, 5.0);
        servoPlaneLauncher.setPosition(5.0);
    }

    private void aprilTagsInit() {
        webcam = BlocksOpModeCompanion.hardwareMap.get(WebcamName.class, "Webcam 1");

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

            if (drive) {
                driveInit();
            }

            // Motor and servo for the arm and claw
            if (arm) {
                armInit();
            }

            if (launcher) {
                launcherInit();
            }

            // Webcam
            if (apriltags) {
                aprilTagsInit();
            }

            BlocksOpModeCompanion.telemetry.addData("HardwareConfig completed!", "Below are enabled hardware...");
            BlocksOpModeCompanion.telemetry.addData("Drive", drive);
            BlocksOpModeCompanion.telemetry.addData("Arm", arm);
            BlocksOpModeCompanion.telemetry.addData("Launcher", launcher);
            BlocksOpModeCompanion.telemetry.addData("AprilTags", apriltags);
            BlocksOpModeCompanion.telemetry.addData("Current OpMode Type", opMode);
            BlocksOpModeCompanion.telemetry.addLine();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(
                    "A error occurred in the initialization of the script \n" +
                            "Please check you have the right motor control scheme set on the \n" +
                            "Driver Hub so it knows which modules are connected. \n" +
                            "Full traceback: \n" + e
            );
        }
    }
}
