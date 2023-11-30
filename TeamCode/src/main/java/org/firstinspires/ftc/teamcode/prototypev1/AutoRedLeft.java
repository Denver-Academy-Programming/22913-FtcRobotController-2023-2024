package org.firstinspires.ftc.teamcode.prototypev1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.library.BasicMoveOperations;
import org.firstinspires.ftc.teamcode.library.HardwareConfig;

@Autonomous(name = "Prototype V1 Auto Red Left", group="PrototypeV1")
public class AutoRedLeft extends LinearOpMode {
    HardwareConfig hardware = new HardwareConfig(this);

    @Override
    public void runOpMode() {
        hardware.init();

        telemetry.addData("Status", "Initialized");
        telemetry.addData("Awaiting User Action", "Waiting for user to begin");
        telemetry.update();

        //Waiting for program to begin and user to start the application.
        waitForStart();

        BasicMoveOperations.moveForward(0.5F);
        BasicMoveOperations.turnRight(0.175F);
        BasicMoveOperations.moveForward(1.65F);
    }
}