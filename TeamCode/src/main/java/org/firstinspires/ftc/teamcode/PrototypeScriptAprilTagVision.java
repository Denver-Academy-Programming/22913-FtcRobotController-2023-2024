package org.firstinspires.ftc.teamcode;


import android.graphics.Canvas;
import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.opencv.core.Mat;

import java.util.ArrayList;

@TeleOp(name = "Prototype Script AprilTag Vision", group = "Prototype V1 Scripts")
public class PrototypeScriptAprilTagVision extends LinearOpMode {

    // Variables
    // AprilTag
    private AprilTagProcessor aprilTag;

    // VisionPortal
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() {
        intiAprilTag();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.update();

            visionPortal.resumeStreaming();

            sleep(20);
        }
    }

    private void intiAprilTag() {

        // Create the AprilTag processor.
        aprilTag = new AprilTagProcessor.Builder()
                .setDrawCubeProjection(true)
                .build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Grab webcam from hardware map and set it's settings.
        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        builder.setCameraResolution(new Size(640, 480)); // Set camera resolution.
        builder.enableLiveView(true); // Enable live view.
        builder.addProcessor(aprilTag); // Set and enable the processor.

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Disable or re-enable the aprilTag processor at any time.
        visionPortal.setProcessorEnabled(aprilTag, true);

    }
}
