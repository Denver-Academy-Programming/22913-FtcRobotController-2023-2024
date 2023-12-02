// This is part of the teamcode package that gets put onto our robot
package org.firstinspires.ftc.teamcode.prototypev1;

// Imports for stuff we use
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.library.GVars;
import org.firstinspires.ftc.teamcode.library.HardwareConfig;

// Here we are telling the driver hub this is tele-operated (i.e., driver controlled) operation mode
@Disabled
@TeleOp(name="Paper Plane Launcher", group = "Prototype V1 Scripts")
public class Launcher extends LinearOpMode {
    HardwareConfig hardware = new HardwareConfig(this);

    @Override
    public void runOpMode() {

        hardware.init(false, false, true, false);

        Servo servoPlaneLauncher = hardware.servoPlaneLauncher;

        boolean pressed = false;

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.addData("press", pressed);
            telemetry.update();

            if (gamepad2.x) {
                servoPlaneLauncher.setPosition(0.4);
                pressed = true;
            }
        }
    }
}