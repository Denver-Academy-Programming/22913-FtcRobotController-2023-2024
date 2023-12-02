package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;

/*
    This script is for giving access to a set of basic
    robot movement functions that can be used in both
    Autonomous and TeleOp modes.

    Time 0.175F at 1.0 Power = About 90 degree turn
    Time 0.5F second at 1 Power = 1 square on field
*/

public class MoveOperations {
    private final ElapsedTime scriptRunTime = GVars.scriptRunTime;

    public static LinearOpMode opMode = null;

    public MoveOperations(LinearOpMode currentOpMode) {opMode = currentOpMode;}
    private final HardwareConfig hardware = new HardwareConfig(opMode);
    //private final LinearOpMode opMode = HardwareConfig.opMode;


//    public static final double autoMaxMovePower = GVars.autoMaxMovePower; // Speed used for moving while running in Autonomous.
//    public static final double autoMaxTurnPower = GVars.autoMaxTurnPower; // Speed used for turning while running in Autonomous.

    /**
     * Make the robot rotate to the left.
     * Meant for Autonomous scripts but can be used with TeleOp ones.
     * <p>
     * Setting time to 0.175F at 1.0 autoTurnPower results in a about 90 degree turn.
     * @param time Time to run operation as a float.
    */
    public void turnLeft(float time) {
        scriptRunTime.reset();

        BlocksOpModeCompanion.telemetry.addData("Turning Right Time", time);
        BlocksOpModeCompanion.telemetry.update();

        while (opMode.opModeIsActive() && (scriptRunTime.seconds() < time)){
            hardware.motorFront.setPower(-GVars.autoMaxTurnPower);
            hardware.motorBack.setPower(GVars.autoMaxTurnPower);
            hardware.motorLeft.setPower(-GVars.autoMaxTurnPower);
            hardware.motorRight.setPower(GVars.autoMaxTurnPower);
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
    public void turnRight(float time) {
        scriptRunTime.reset();
        BlocksOpModeCompanion.telemetry.addData("Turning Right Time", time);
        BlocksOpModeCompanion.telemetry.update();

        while (opMode.opModeIsActive() && (scriptRunTime.seconds() < time)) {
            hardware.motorFront.setPower(GVars.autoMaxTurnPower);
            hardware.motorBack.setPower(-GVars.autoMaxTurnPower);
            hardware.motorLeft.setPower(GVars.autoMaxTurnPower);
            hardware.motorRight.setPower(-GVars.autoMaxTurnPower);
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
    public void moveForward(float time) {
        scriptRunTime.reset();
        BlocksOpModeCompanion.telemetry.addData("Moving Forward Time", time);
        BlocksOpModeCompanion.telemetry.update();

        while (opMode.opModeIsActive() && (scriptRunTime.seconds() < time)){
            hardware.motorLeft.setPower(GVars.autoMaxMovePower);
            hardware.motorRight.setPower(GVars.autoMaxMovePower);
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
    public void moveBackward(float time) {
        scriptRunTime.reset();
        BlocksOpModeCompanion.telemetry.addData("Moving Forward Time", time);
        BlocksOpModeCompanion.telemetry.update();

        while (opMode.opModeIsActive() && (scriptRunTime.seconds() < time)){
            hardware.motorLeft.setPower(-GVars.autoMaxMovePower);
            hardware.motorRight.setPower(-GVars.autoMaxMovePower);
        }
        motorsStop();
    }

    /**
     * Stops the robot.
     * Meant for Autonomous scripts but can be used with TeleOp ones.
    */
    public void motorsStop(){
        hardware.motorFront.setPower(0);
        hardware.motorBack.setPower(0);
        hardware.motorLeft.setPower(0);
        hardware.motorRight.setPower(0);
    }
}
