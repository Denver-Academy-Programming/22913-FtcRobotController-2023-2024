package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
    GVars is short for Global Variables. Global Variables are used
    throughout a program that needs to reuses the same set of data
    repeatedly. The purpose of this script is to store those
    variables so we have access to them in our TeleOp and Autonomous
    robot scripts.
 */

public class GVars {

    // Enable to see debug telemetry messages when running OpModes
    public static final boolean debug = true;

    /*
       The time the script has been running for. This can't be directly
       modified being a "final" variable. Use the built in reset and get
       functions in order to work with the variable.
     */
    public static final ElapsedTime scriptRunTime = new ElapsedTime();

    // Powers for the movement of our robot during both TeleOp and Autonomous modes.
    public static double teleopMaxTurnScale = 0.75; // Speed used for turning while running in TeleOp.
    public static double teleopMaxMoveScale = 1.2; // Speed used for moving while running in TeleOp.
    // Both are made final because we don't plan to adjust speed while in Autonomous mode.
    public static final double autoMaxMovePower = 1.0; // Speed used for moving while running in Autonomous.
    public static final double autoMaxTurnPower = 1.0; // Speed used for turning while running in Autonomous.
    public static final double armMaxPower = 0.5;
    public static final double armPivotMaxPower = 0.5;

    /*
        Min and max positions for our claws functions.
        These limits will prevent breaking anything.
    */
    public static final int motorArmPivotMinPosition = -200 ;
    public static final int motorArmPivotMaxPosition = 490;
    public static final int motorArmMinPosition = 0;
    public static final int motorArmMaxPosition = 0;

    // Makes it easier to define forward and reverse directions for the motors and servos.
    public static final DcMotorSimple.Direction motorFORWARD = DcMotorSimple.Direction.FORWARD;
    public static final DcMotorSimple.Direction motorREVERSE = DcMotorSimple.Direction.REVERSE;
    public static final Servo.Direction servoFORWARD = Servo.Direction.FORWARD;
    public static final Servo.Direction servoREVERSE = Servo.Direction.REVERSE;
}
