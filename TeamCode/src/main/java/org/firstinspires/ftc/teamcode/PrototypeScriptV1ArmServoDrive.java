package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Prototype V1 Robot Script Arm Servo and Drive", group = "Prototype Scripts")
public class PrototypeScriptV1ArmServoDrive extends LinearOpMode {

    // Define Variables
    // Time the script has been running for.
    ElapsedTime runtime = new ElapsedTime();

    // Motors
    DcMotor motorFront = null;
    DcMotor motorBack = null;
    DcMotor motorLeft = null;
    DcMotor motorRight = null;

    // Servo
    Servo armServo = null;

    // Boolean to check if the arm is already open or not.
    boolean armServoOpen = true;

    @Override
    public void runOpMode() {
        // Power for motors bound to the controller left and right sticks.
        // leftRightPower is for the front and back motors for moving left and right.
        // forwardBackPower is for the left and right motors for moving forward and back.
        double leftRightPower;
        double forwardBackPower;

        // Initialize and setup the modules connected to the Control Hub.
        // Also deliberately sets the right motor  in reverse because its
        // physically placed in a reverse position to the left motor.
        //try {
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

        // Servo for the arm.
        // Grab it from the hardwareMap and set its range and direction.
        // Also set it to a initial open position.
        armServo = hardwareMap.get(Servo.class, "armServo");
        armServo.scaleRange(0.0, 0.5);
        armServo.setPosition(0.0);
//        } catch (IllegalArgumentException e) {
//            throw new RuntimeException(
//                "An error occurred with the initialization of the script!\n" +
//                "Please check you have the right control scheme set on the\n" +
//                "Driver Hub so it knows which modules are connected.\n" +
//                "Full traceback:\n" + e
//            );
//        }
        telemetry.addData("Status:", "Modules initialized!");
        telemetry.addData("User Action:", "Waiting for user to start script...");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            // Controller inputs
            leftRightPower = gamepad1.left_stick_x;
            forwardBackPower = gamepad1.right_stick_y;

            // Display on the Driver Hub info about our robot while its running
            telemetry.addData("Servo:", armServo.getPosition());
            telemetry.addData("Run Time:", runtime.toString());
            telemetry.addData("Status:", "Script is running!");
            telemetry.addData("leftRightPower:", leftRightPower);
            telemetry.addData("forwardBackPower:", forwardBackPower);
            telemetry.addData("Arm Open:", armServoOpen);
            telemetry.addData("RightPower:", gamepad1.dpad_right);
            telemetry.addData("LeftPower:", gamepad1.dpad_left);
            telemetry.addData("ForwardPower:", gamepad1.dpad_up);
            telemetry.addData("BackPower:", gamepad1.dpad_down);

            // Set the motors power to what the direction of which stick is being pressed.
            motorFront.setPower(leftRightPower / 2);
            motorBack.setPower(leftRightPower / 2);
            motorLeft.setPower(forwardBackPower / -2);
            motorRight.setPower(forwardBackPower / -2);


            if (gamepad1.dpad_left) {
                motorLeft.setPower(1);
            }
            if (gamepad1.dpad_right) {
                motorRight.setPower(1);
            }
            if (gamepad1.dpad_down) {
                motorBack.setPower(1);
            }
            if(gamepad1.dpad_up) {
                motorFront.setPower(1);
            }
            // Pressing X will open the servo arm.
            if (gamepad1.x && !armServoOpen) {
                armServo.setPosition(0.0);
                armServoOpen = true;
            }

            // Pressing Y will close the servo arm.
            if (gamepad1.y && armServoOpen) {
                armServo.setPosition(0.5);
                armServoOpen = false;
            }

            // Pressing B on the controller will stop the TeleOP script.
            if (gamepad1.b) {
                requestOpModeStop();
            }
        }
    }
}
