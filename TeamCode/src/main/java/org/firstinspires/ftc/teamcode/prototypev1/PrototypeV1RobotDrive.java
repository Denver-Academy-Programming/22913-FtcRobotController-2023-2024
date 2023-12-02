package org.firstinspires.ftc.teamcode.prototypev1;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.library.GVars;
import org.firstinspires.ftc.teamcode.library.HardwareConfig;

@TeleOp(name = "Prototype V1 Robot Script (THIS ONE DRIVERS!!!)", group = "Prototype V1 Scripts")
public class PrototypeV1RobotDrive extends LinearOpMode {
    HardwareConfig hardware = new HardwareConfig(this);

    private boolean planeLaunched = false;

    @Override
    public void runOpMode() {
        hardware.init(true, false, true, false);

        telemetry.addData("Status", "Modules initialized!");
        telemetry.addData("User Action", "Waiting for user to start script...");
        telemetry.update();

        waitForStart();
        GVars.scriptRunTime.reset();

        while (opModeIsActive()) {
            // Controller inputs
            float leftRightPower = Range.clip(gamepad1.left_stick_x, GVars.teleopMinTurnPower, GVars.teleopMaxTurnPower);
            float forwardBackPower = Range.clip(gamepad1.right_stick_y, GVars.teleopMinMovePower, GVars.teleopMaxMovePower);

            // Display on the Driver Hub info about our robot while its running
            telemetry.addData("Status", "Script is running!");
            telemetry.addData("Run Time", GVars.scriptRunTime);
            telemetry.addData("leftRightPower", leftRightPower);
            telemetry.addData("forwardBackPower", forwardBackPower);
            telemetry.addData("Paper Plane Launched?", planeLaunched);
            telemetry.addData("STOP", "PRESS B ON EITHER CONTROLLER TO STOP!");
            telemetry.update();

            // Set the motors power to what the direction of which stick is being pressed.
            hardware.motorFront.setPower(leftRightPower);
            hardware.motorBack.setPower(leftRightPower);
            hardware.motorLeft.setPower(forwardBackPower);
            hardware.motorRight.setPower(forwardBackPower);

            // Checking if the bumper buttons are pressed to turn the robot.
            if (gamepad1.left_bumper) {
                hardware.motorFront.setPower(-GVars.teleopMaxTurnPower);
                hardware.motorBack.setPower(GVars.teleopMaxTurnPower);
                hardware.motorLeft.setPower(-GVars.teleopMaxTurnPower);
                hardware.motorRight.setPower(GVars.teleopMaxTurnPower);
            }
            if (gamepad1.right_bumper) {
                hardware.motorFront.setPower(GVars.teleopMaxTurnPower);
                hardware.motorBack.setPower(-GVars.teleopMaxTurnPower);
                hardware.motorLeft.setPower(GVars.teleopMaxTurnPower);
                hardware.motorRight.setPower(-GVars.teleopMaxTurnPower);
            }

            if (gamepad1.x) {
                hardware.servoPlaneLauncher.setPosition(0.4);
                planeLaunched = true;
            }

            // Pressing B on the controller will stop the TeleOP script.
            if (gamepad1.b || gamepad2.b) {
                requestOpModeStop();
            }
        }
    }
}
