package org.firstinspires.ftc.teamcode.prototypev1;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Prototype V1 Robot Script (THIS ONE DRIVERS!!!)", group = "Prototype V1 Scripts")
public class PrototypeV1RobotDrive extends LinearOpMode {

    // Define Variables
    // Time the script has been running for.
    ElapsedTime runtime = new ElapsedTime();

    // Motors
    DcMotor motorFront = null;
    DcMotor motorBack = null;
    DcMotor motorLeft = null;
    DcMotor motorRight = null;

    // Power for motors bound to the controller left and right sticks.
    // leftRightPower is for the front and back motors for moving left and right.
    // forwardBackPower is for the left and right motors for moving forward and back.
    // turnPower is the the power used when turning.
    float leftRightPower;
    float forwardBackPower;
    float turnPower = 0.5F;

    @Override
    public void runOpMode() {
        // Initialize and setup the modules connected to the Control Hub.
        // Also deliberately sets the right motor  in reverse because its
        // physically placed in a reverse position to the left motor.
        try {
            // All four motors, front, back, left, and right.
            // Grab them all from the hardwareMap and set their
            // direction so driving works as intended.
            motorFront = hardwareMap.get(DcMotor.class, "motorFront");
            motorBack = hardwareMap.get(DcMotor.class, "motorBack");
            motorLeft = hardwareMap.get(DcMotor.class, "motorLeft");
            motorRight = hardwareMap.get(DcMotor.class, "motorRight");
            motorFront.setDirection(DcMotorSimple.Direction.REVERSE);
            motorBack.setDirection(DcMotorSimple.Direction.FORWARD);
            motorLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            motorRight.setDirection(DcMotorSimple.Direction.FORWARD);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(
                "An error occurred with the initialization of the script!\n" +
                "Please check you have the right control scheme set on the\n" +
                "Driver Hub so it knows which modules are connected.\n" +
                "Full traceback:\n" + e
            );
        }
        telemetry.addData("Status", "Modules initialized!");
        telemetry.addData("User Action", "Waiting for user to start script...");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            // Controller inputs
            leftRightPower = gamepad1.left_stick_x;
            forwardBackPower = gamepad1.right_stick_y;

            // Display on the Driver Hub info about our robot while its running
            telemetry.addData("Status", "Script is running!");
            telemetry.addData("Run Time", runtime.toString());
            telemetry.addData("leftRightPower", leftRightPower);
            telemetry.addData("forwardBackPower", forwardBackPower);
            telemetry.update();

            // Set the motors power to what the direction of which stick is being pressed.
            motorFront.setPower(leftRightPower * 0.75);
            motorBack.setPower(leftRightPower * 0.75);
            motorLeft.setPower(forwardBackPower * -0.75);
            motorRight.setPower(forwardBackPower * -0.75);

            // Checking if the bumper buttons are pressed to turn the robot.
            if (gamepad1.left_bumper) {
                motorFront.setPower(-turnPower);
                motorBack.setPower(turnPower);
                motorLeft.setPower(-turnPower);
                motorRight.setPower(turnPower);
            }
            if (gamepad1.right_bumper) {
                motorFront.setPower(turnPower);
                motorBack.setPower(-turnPower);
                motorLeft.setPower(turnPower);
                motorRight.setPower(-turnPower);
            }

            // Pressing B on the controller will stop the TeleOP script.
            if (gamepad1.b) {
                requestOpModeStop();
            }
        }
    }
}
