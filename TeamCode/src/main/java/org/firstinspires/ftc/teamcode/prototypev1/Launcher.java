// This is part of the teamcode package that gets put onto our robot
package org.firstinspires.ftc.teamcode.prototypev1;

// Imports for stuff we use
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

// Here we are telling the driver hub this is tele-operated (i.e., driver controlled) operation mode
@TeleOp(name="Paper Plane Launcher", group = "Prototype V1 Scripts")
public class Launcher extends LinearOpMode {
    @Override
    public void runOpMode() {

        Servo ArmServo = hardwareMap.get(Servo.class, "ArmServo");
        ArmServo.scaleRange(0, 1.0);
        ArmServo.setPosition(0.0);

        telemetry.addData("Status", "Initialized");

        telemetry.update();

        waitForStart();


        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.update();
            ArmServo.setPosition(0.0);  }


        if (gamepad1.x) {
            ArmServo.setPosition(0.5);
        }

    }
}