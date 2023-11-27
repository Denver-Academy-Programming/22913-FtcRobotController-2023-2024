package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
    This script is for giving access to a set of basic
    robot movement functions that can be used in both
    Autonomous and TeleOp modes.

    Time 0.35F at 0.5 Power = About 90 degree turn
    Time 1 second at 0.5 Power = 1 square on field
*/

public class BasicMoveOperation {
    private static final ElapsedTime scriptRunTime = GVars.scriptRunTime;

    private static final LinearOpMode opMode = HardwareConfig.activeLinearOpMode;

    /**
     * Make the robot rotate to the left.
     * <p>
     * Setting time to 0.35F results in a about 90 degree turn.
     * @param time Time to run operation as a float.
    */
    public static void turnLeft(float time) {
        scriptRunTime.reset();

        opMode.telemetry.addData("Turning Right Time", time);
        opMode.telemetry.update();

        while (opMode.opModeIsActive() && (scriptRunTime.seconds() < time)){
            HardwareConfig.motorFront.setPower(-GVars.autoTurnPower);
            HardwareConfig.motorBack.setPower(GVars.autoTurnPower);
            HardwareConfig.motorLeft.setPower(-GVars.autoTurnPower);
            HardwareConfig.motorRight.setPower(GVars.autoTurnPower);
        }
        motorsStop();
    }

    /**
     * Make the robot rotate to the right.
     * <p>
     * Setting time to 0.35F results in a about 90 degree turn.
     * @param time Time to run operation as a float.
    */
    public static void turnRight(float time) {
        scriptRunTime.reset();
        opMode.telemetry.addData("Turning Right Time", time);
        opMode.telemetry.update();

        while (opMode.opModeIsActive() && (scriptRunTime.seconds() < time)){
            HardwareConfig.motorFront.setPower(GVars.autoTurnPower);
            HardwareConfig.motorBack.setPower(-GVars.autoTurnPower);
            HardwareConfig.motorLeft.setPower(GVars.autoTurnPower);
            HardwareConfig.motorRight.setPower(-GVars.autoTurnPower);
        }
        motorsStop();
    }

    /**
     * Make the robot move forward.
     * <p>
     * Setting time to 1.0F results in moving forward one game field square.
     * @param time Time to run operation as a float.
    */
    public static void moveForward(float time) {
        scriptRunTime.reset();
        opMode.telemetry.addData("Moving Forward Time", time);
        opMode.telemetry.update();

        while (opMode.opModeIsActive() && (scriptRunTime.seconds() < time)){
            HardwareConfig.motorLeft.setPower(GVars.autoMovePower);
            HardwareConfig.motorRight.setPower(GVars.autoMovePower);
        }
        motorsStop();
    }

    /**
     * Make the robot move backward.
     * <p>
     * Setting time to 1.0F results in moving backward one game field square.
     * @param time Time to run operation as a float.
    */
    public static void moveBackward(float time) {
        scriptRunTime.reset();
        opMode.telemetry.addData("Moving Forward Time", time);
        opMode.telemetry.update();

        while (opMode.opModeIsActive() && (scriptRunTime.seconds() < time)){
            HardwareConfig.motorLeft.setPower(-GVars.autoMovePower);
            HardwareConfig.motorRight.setPower(-GVars.autoMovePower);
        }
        motorsStop();
    }

    /**
     * Stops the robot. Mainly used in the basic move operation functions, but can be used else where.
    */
    public static void motorsStop(){
        HardwareConfig.motorFront.setPower(0);
        HardwareConfig.motorBack.setPower(0);
        HardwareConfig.motorLeft.setPower(0);
        HardwareConfig.motorRight.setPower(0);
    }
}
