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

    // The time the script has been running for. This can't be directly
    // modified being a "final" variable. Use the built in reset and get
    // functions in order to work with the variable.
    public static final ElapsedTime scriptRunTime = new ElapsedTime();

    // Powers for turning and moving back and forth.
    public static double teleopMaxTurnPower = 0.5; // Speed used for turning while running in TeleOp.
    public static double teleopMaxMovePower = 0.5; // Speed used for moving while running in TeleOp.
    public static final double autoMaxMovePower = 1.0; // Speed used for moving while running in Autonomous.
    public static final double autoMaxTurnPower = 1.0; // Speed used for turning while running in Autonomous.

    public static final int motorArmPivotMinPosition = -185;
    public static final int motorArmPivotMaxPosition = 1350;

    public static final DcMotorSimple.Direction motorFORWARD = DcMotorSimple.Direction.FORWARD;
    public static final DcMotorSimple.Direction motorREVERSE = DcMotorSimple.Direction.REVERSE;

    public static final Servo.Direction servoFORWARD = Servo.Direction.FORWARD;
    public static final Servo.Direction servoREVERSE = Servo.Direction.REVERSE;


}
