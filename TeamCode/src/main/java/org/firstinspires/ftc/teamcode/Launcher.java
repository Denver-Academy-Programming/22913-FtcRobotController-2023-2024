// This is part of the teamcode package that gets put onto our robot
package org.firstinspires.ftc.teamcode;

// Imports for stuff we use
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Servo;

// Here we are telling the driver hub this is tele-operated (i.e., driver controlled) operation mode
@TeleOp(name="Paper Plane Launcher", group = "Prototype V1 Scripts")
public class Launcher extends LinearOpMode {
    @Override
    public void runOpMode() {
        // Define the variables for our hardware
        Servo servoTest = hardwareMap.get(Servo.class, "servoTest");
        servoTest.scaleRange(0.0, 0.5);
        servoTest.setPosition(0.0);

        // Queue some data to display on our telemetry screen on the Driver Hub
        telemetry.addData("Status", "Initialized");
        // Update the telemetry screen on the Driver Hub our queued data
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Continuously run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // Update the telemetry screen that the game is running
            telemetry.addData("Status", "Running");
            telemetry.update();
            servoTest.setPosition(0.5);
        }
    }
}