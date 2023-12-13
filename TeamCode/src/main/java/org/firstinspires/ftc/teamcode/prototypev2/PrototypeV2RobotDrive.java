package org.firstinspires.ftc.teamcode.prototypev2;

import static org.firstinspires.ftc.teamcode.library.GVars.*;
import static org.firstinspires.ftc.teamcode.library.HardwareControl.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.library.HardwareControl;

@TeleOp(name = "Prototype V1 Robot Script (THIS ONE DRIVERS!!!)", group = "Prototype V1 Scripts")
public class PrototypeV2RobotDrive extends LinearOpMode {
    final HardwareControl hardware = new HardwareControl(this);

    private boolean planeLaunched = false;

    @Override
    public void runOpMode() {
        hardware.init(true, false, true, false);

        telemetry.addData("Status", "Modules initialized!");
        telemetry.addData("User Action", "Waiting for user to start script...");
        telemetry.update();

        waitForStart();
        scriptRunTime.reset();

        while (opModeIsActive()) {
            // Controller inputs
            float leftRightPower = Range.clip(gamepad1.left_stick_x, teleopMinTurnPower, teleopMaxTurnPower);
            float forwardBackPower = Range.clip(gamepad1.right_stick_y, teleopMinMovePower, teleopMaxMovePower);

            // Display on the Driver Hub info about our robot while its running
            telemetry.addData("Status", "Script is running!");
            telemetry.addData("Run Time", scriptRunTime);
            telemetry.addData("leftRightPower", leftRightPower);
            telemetry.addData("forwardBackPower", forwardBackPower);
            telemetry.addData("Paper Plane Launched?", planeLaunched);
            telemetry.addData("STOP!", "PRESS STOP BUTTON ON DRIVER STATION TO STOP!");
            telemetry.update();

            // Set the motors power to what the direction of which stick is being pressed.
            motorFront.setPower(leftRightPower);
            motorBack.setPower(leftRightPower);
            motorLeft.setPower(forwardBackPower);
            motorRight.setPower(forwardBackPower);

            // Checking if the bumper buttons are pressed to turn the robot.
            if (gamepad1.left_bumper) {
                motorFront.setPower(-teleopMaxTurnPower);
                motorBack.setPower(teleopMaxTurnPower);
                motorLeft.setPower(-teleopMaxTurnPower);
                motorRight.setPower(teleopMaxTurnPower);
            }
            if (gamepad1.right_bumper) {
                motorFront.setPower(teleopMaxTurnPower);
                motorBack.setPower(-teleopMaxTurnPower);
                motorLeft.setPower(teleopMaxTurnPower);
                motorRight.setPower(-teleopMaxTurnPower);
            }

            if (gamepad1.x) {
                servoPlaneLauncher.setPosition(0.4);
                planeLaunched = true;
            }
        }
    }
}
