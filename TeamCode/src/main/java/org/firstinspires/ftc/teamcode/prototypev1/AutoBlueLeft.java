package org.firstinspires.ftc.teamcode.prototypev1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.library.MoveOperations;
import org.firstinspires.ftc.teamcode.library.HardwareConfig;

@Autonomous(name = "Prototype V1 Auto Blue Left", group = "PrototypeV1")
public class AutoBlueLeft extends LinearOpMode {
    HardwareConfig hardware = new HardwareConfig(this);
    MoveOperations moveOperation = new MoveOperations(this);

    @Override
    public void runOpMode() {
        hardware.init(true, false, false, false);

        telemetry.addData("Status", "Initialized");
        telemetry.addData("Awaiting User Action", "Waiting for user to begin");
        telemetry.update();

        //Waiting for program to begin and user to start the application.
        waitForStart();

        moveOperation.moveForward(0.5F);
        moveOperation.turnLeft(0.175F);
        moveOperation.moveForward(0.65F);
    }
}
