package org.firstinspires.ftc.teamcode.prototypev1;

import static org.firstinspires.ftc.teamcode.library.HardwareControl.*;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.library.GVars;
import org.firstinspires.ftc.teamcode.library.HardwareControl;

@Autonomous(name = "Prototype V1 Auto Blue Right", group = "PrototypeV1")
public class AutoBlueRight extends LinearOpMode {
    final HardwareControl hardware = new HardwareControl(this);
    @Override
    public void runOpMode() {
        hardware.init(true, false, false, false);

        telemetry.addData("Status", "Initialized");
        telemetry.addData("Awaiting User Action", "Waiting for user to begin");
        telemetry.update();

        //Waiting for program to begin and user to start the application.
        waitForStart();
        GVars.scriptRunTime.reset();

        moveForward(this, 0.5F);
        turnLeft(this, 0.175F);
        moveForward(this, 1.65F);
    }
}
