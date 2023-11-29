// This is part of the teamcode package that gets put onto our robot
package org.firstinspires.ftc.teamcode.prototypev1;

// Imports for stuff we use
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.library.HardwareConfig;

// Here we are telling the driver hub this is tele-operated (i.e., driver controlled) operation mode
@TeleOp(name="Paper Plane Launcher", group = "Prototype V1 Scripts")
public class Launcher extends LinearOpMode {
    @Override
    public void runOpMode() {
        HardwareConfig hardware = new HardwareConfig(this);
        hardware.init();

        Servo ArmServo = HardwareConfig.servoPlaneLauncher;

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.update();
            ArmServo.setPosition(0.0);

            if (gamepad1.x) {
                ArmServo.setPosition(0.5);
            }
        }
    }
}