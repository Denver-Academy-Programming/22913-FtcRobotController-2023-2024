package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp
public class ReeceMotorTest extends LinearOpMode {

    @Override
    public void runOpMode(){
        DcMotor motorTest = hardwareMap.get(DcMotor.class, "motorTest");
        DistanceSensor sensorColorRange = hardwareMap.get(DistanceSensor.class, "sensorColorRange");
        DigitalChannel digitalTouch = hardwareMap.get(DigitalChannel.class, "digitalTouch");
        Servo servoTest = hardwareMap.get(Servo.class, "servoTest");
    }
}

