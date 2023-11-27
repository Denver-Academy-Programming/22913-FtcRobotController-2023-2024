package org.firstinspires.ftc.teamcode.library;

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
    public final static ElapsedTime scriptRunTime = new ElapsedTime();

    // Powers for turning and moving back and forth.
    public static float teleopTurnPower = 0.5F; // Speed used for turning while running in TeleOp.
    public static float teleopMovePower = 0.5F; // Speed used for moving while running in TeleOp.
    public static float autoTurnPower = 1.0F; // Speed used for turning while running in Autonomous.
    public static float autoMovePower = 1.0F; // Speed used for moving while running in Autonomous.
}
