package org.firstinspires.ftc.teamcode.library;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
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
    public static LinearOpMode activeLinearOpMode = null;
    public HardwareConfig(LinearOpMode linearOpMode) {activeLinearOpMode = linearOpMode;}

    /*
       Similar to activeLinearOpMode but instead of access to LinearOpMode
       this gives access to OpMode. Only use OpMode if you know how setup
       each individual piece of a OpMode, normally LinearOpMode is there
       to simplify this process for you.
    */
    public static OpMode activeOpMode = null;
    public HardwareConfig(OpMode opMode) {activeOpMode = opMode;}

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
    public static WebcamName webcam = null;
    public static VisionPortal visionPortal = null;

    public void init() {

        /*
           Initialize and setup the modules that are linked to the control hub.
           Setting the motors up so that the robot can function
           Setting the left motor to reverse due to its position
           relative to the right motors placement so that it can move forward.
        */
        try {
            /*
               All four motors front, back, left, and right.
               Grab them all from the hardwareMap and set their
               direction so that the driving works correctly.
            */

            motorFront = activeLinearOpMode.hardwareMap.get(DcMotor.class, "motorFront");
            motorBack = activeLinearOpMode.hardwareMap.get(DcMotor.class, "motorBack");
            motorLeft = activeLinearOpMode.hardwareMap.get(DcMotor.class, "motorLeft");
            motorRight = activeLinearOpMode.hardwareMap.get(DcMotor.class, "motorRight");
            motorArm = activeLinearOpMode.hardwareMap.get(DcMotor.class, "servoArm");
            motorFront.setDirection(DcMotorSimple.Direction.REVERSE);
            motorBack.setDirection(DcMotorSimple.Direction.FORWARD);
            motorRight.setDirection(DcMotorSimple.Direction.FORWARD);
            motorLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            motorArm.setDirection(DcMotorSimple.Direction.FORWARD);

            // Servos for the claw of the servo arm and the servo for the paper plane launcher
            servoClaw = activeLinearOpMode.hardwareMap.get(Servo.class, "servoPlaneLauncher");
            servoClaw.scaleRange(0.0, 0.3);
            servoClaw.setPosition(0.0);
            servoPlaneLauncher = activeLinearOpMode.hardwareMap.get(Servo.class, "servoPlaneLauncher");
            servoPlaneLauncher.scaleRange(0.0, 0.5);
            servoPlaneLauncher.setPosition(0.0);
            servoPlaneLauncher = activeLinearOpMode.hardwareMap.get(Servo.class, "servoPlaneLauncher");
            servoPlaneLauncher.scaleRange(0.4, 5.0);
            servoPlaneLauncher.setPosition(5.0);

            // Webcam
            webcam = activeLinearOpMode.hardwareMap.get(WebcamName.class, "Webcam 1");

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
