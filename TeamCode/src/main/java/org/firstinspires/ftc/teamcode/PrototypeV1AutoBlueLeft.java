package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Prototype V1 Auto Blue Left", group="PrototypeV1")
public class PrototypeV1AutoBlueLeft extends LinearOpMode {
    private DcMotor motorFront = null;
    private DcMotor motorBack = null;
    private DcMotor motorLeft = null;
    private DcMotor motorRight = null;
    private final ElapsedTime runtime = new ElapsedTime();

    float rightLeftPower = 0.5f;
    float forwardBackPower = 0.5f;
    float turningPower = 0.5f;

    @Override
    public void runOpMode() {

        //Initialize and setup the modules that are linked to the control hub.
        //Setting the motors up so that the robot can function
        //Setting the left motor to reverse due to its position
        //relative to the right motors placement so that it can move forward.
        try {
            //All four motors front, back, left, and right.
            //Grab them all from the hardwareMap and set their
            //direction so that the driving works correctly.
            motorFront = hardwareMap.get(DcMotor.class, "motorFront");
            motorBack = hardwareMap.get(DcMotor.class, "motorBack");
            motorLeft = hardwareMap.get(DcMotor.class, "motorLeft");
            motorRight = hardwareMap.get(DcMotor.class, "motorRight");

            motorFront.setDirection(DcMotorSimple.Direction.FORWARD);
            motorBack.setDirection(DcMotorSimple.Direction.REVERSE);
            motorLeft.setDirection(DcMotorSimple.Direction.FORWARD);
            motorRight.setDirection(DcMotorSimple.Direction.REVERSE);
        }   catch(IllegalArgumentException e) {
            throw new RuntimeException(
                    "A error occurred in the initialization of the script  \n" +
                    "Please check you have the right motor control scheme set on the \n" +
                    "Driver Hub so it knows which modules are connected. \n" +
                    "Full traceback: \n" + e

            );
        }

        telemetry.addData("Status", "Hot Stand by");
        telemetry.addData("Awaiting User Action", "Waiting for user to begin");
        telemetry.update();

        //Waiting for program to begin and user to start the application.
        waitForStart();

        moveForward(0.5F);
        motorStop();
        turnRight(0.35F);
        motorStop();
        moveForward(1.3F);
        motorStop();
//        moveBackward(0.15F * 2);
//        motorStop();
//        turnRight(0.35F);
//        motorStop();
//        moveForward(0.25F * 2);
//        motorStop();
//        turnRight(0.35F);
//        motorStop();
//        moveForward(1.0F * 2);
//        motorStop();
//        turnRight(0.35F * 2);
//        motorStop();
//        moveForward(1.15F * 2);
//        motorStop();
//        moveBackward(0.15F * 2);
//        motorStop();
//        turnLeft(0.35F);
//        motorStop();
//        moveForward(0.5F * 2);
//        motorStop();
//        turnRight(0.175F * 2);
//        motorStop();
//        moveForward(0.25F * 2);
//        motorStop();
    }
    // Time 0.35F = About 90 degree turn
    private void turnLeft(float time){
        runtime.reset();
        telemetry.addData("Turning Right Time", time);
        telemetry.update();

        while (opModeIsActive() && (runtime.seconds() < time)){
            motorFront.setPower(-turningPower);
            motorBack.setPower(turningPower);
            motorLeft.setPower(-turningPower);
            motorRight.setPower(turningPower);
        }
    }
    // Time 0.35F = About 90 degree turn
    private void turnRight(float time){
        runtime.reset();
        telemetry.addData("Turning Right Time", time);
        telemetry.update();

        while (opModeIsActive() && (runtime.seconds() < time)){
            motorFront.setPower(turningPower);
            motorBack.setPower(-turningPower);
            motorLeft.setPower(turningPower);
            motorRight.setPower(-turningPower);
        }
    }

    private void moveForward(float time){
        runtime.reset();
        telemetry.addData("Moving Forward Time", time);
        telemetry.update();

        while (opModeIsActive() && (runtime.seconds() < time)){
            motorLeft.setPower(forwardBackPower);
            motorRight.setPower(forwardBackPower);
        }
    }

    private void moveBackward(float time){
        runtime.reset();
        telemetry.addData("Moving Backward Time", time);
        telemetry.update();

        while (opModeIsActive() && (runtime.seconds() < time)){
            motorLeft.setPower(forwardBackPower);
            motorRight.setPower(forwardBackPower);
        }
    }

    private void motorStop(){
        motorFront.setPower(0);
        motorBack.setPower(0);
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }
}
