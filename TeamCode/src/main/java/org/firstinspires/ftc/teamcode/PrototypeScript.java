package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class PrototypeScript extends LinearOpMode {

    // Define Variable
    // Motors
    DcMotor motorFront;
    DcMotor motorBack;
    DcMotor motorLeft;
    DcMotor motorRight;

    // Servo
    Servo armServo;

    double motorRightPower = gamepad1.right_stick_y;
    double motorLeftPower = gamepad1.left_stick_y;
    public void runOpMode() {
        // Initialize and setup the modules connected to the Control Hub.
        // Also deliberately sets the right motor in reverse because its
        // physically placed in a reverse position to the left motor.
        try {
            // All four motors, front, back, left, and right
            // Grab them all from the hardwareMap and set their
            // direction so driving works as intended.
            motorFront = hardwareMap.get(DcMotor.class, "motorNorth");
            motorBack = hardwareMap.get(DcMotor.class, "motorSouth");
            motorLeft = hardwareMap.get(DcMotor.class, "motorEast");
            motorRight = hardwareMap.get(DcMotor.class, "motorWest");
            motorFront.setDirection(DcMotorSimple.Direction.FORWARD);
            motorBack.setDirection(DcMotorSimple.Direction.REVERSE);
            motorLeft.setDirection(DcMotorSimple.Direction.FORWARD);
            motorRight.setDirection(DcMotorSimple.Direction.REVERSE);

            // Servo for the arm
            // Grab it from the hardwareMap and set its range and direction.
            // Also set it to a initial open position.
            armServo = hardwareMap.get(Servo.class, "armServo");
            armServo.scaleRange(90, 180);
            armServo.setPosition(90);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(
                    "An error occurred with the initialization of the script!\n" +
                            "Please check you have the right control scheme set on the\n" +
                            "Driver Hub so it knows which modules are connected.\n" +
                            "Full traceback:\n" + e
            );
        }

        telemetry.addData("Status:", "Modules initialized!");
        telemetry.update();

        while (opModeIsActive()) {
            // Pressing B on the controller will stop the TeleOP script.
            if (gamepad1.b) {
                requestOpModeStop();
            }
        }
    }
}
