package Auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import Camera.PropDetectorRED;
import Camera.PropDetectorBLUE;
import Camera.AprilTagDetectionPipeline;

@Autonomous
public class farRedPurpleYellow extends LinearOpMode {
    OpenCvCamera cam;
    private DcMotorEx motorFrontLeft, motorBackLeft, motorFrontRight, motorBackRight, intakeMotor;
    private Servo clawServo, armLeftServo, armRightServo;
    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext
                .getResources().getIdentifier("cameraMonitorViewId",
                        "id", hardwareMap.appContext.getPackageName());

        WebcamName camera = hardwareMap.get(WebcamName.class, "camera");
        OpenCvCamera cam = OpenCvCameraFactory.getInstance().createWebcam(camera, cameraMonitorViewId);

        PropDetectorRED redDetector = new PropDetectorRED(telemetry);
        cam.setPipeline(redDetector);
        cam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                telemetry.addLine("CAMERA WORKS");
                telemetry.update();
                cam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }
            @Override
            public void onError(int errorCode)
            {
                telemetry.addData("THE CAMERA DID NOT OPEN PROPERLY SEND HELP", errorCode);
                telemetry.update();
            }
        });

        sleep(20);

        //MOTORS AND SERVOS
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        intakeMotor = (DcMotorEx) hardwareMap.dcMotor.get("intakeMotor");
        clawServo = hardwareMap.get(Servo.class, "claw");
        armRightServo = hardwareMap.get(Servo.class, "armRightServo");
        armLeftServo = hardwareMap.get(Servo.class, "armLeftServo");


        //TRAJECTORIES (left/right in robot perspective)
        Pose2d startPose = new Pose2d(-14, 0, Math.toRadians(-90));
        drive.setPoseEstimate(startPose);


        //purple pixel
        TrajectorySequence rightPurple = drive.trajectorySequenceBuilder(startPose)
                .lineToLinearHeading(new Pose2d(0,-36,Math.toRadians(180)))
                .lineToLinearHeading(new Pose2d(0,-30,Math.toRadians(0)))
                .forward(72/3)
                .build();
        TrajectorySequence centerPurple = drive.trajectorySequenceBuilder(startPose)
                .lineToLinearHeading(new Pose2d(0,-36,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-16,-36, Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-16,-30))
                .forward(96/3)
                .build();
        TrajectorySequence leftPurple = drive.trajectorySequenceBuilder(startPose)
                .lineToLinearHeading(new Pose2d(0,-13.5,Math.toRadians(-90)))
                //.lineToLinearHeading(new Pose2d(-22,-37,Math.toRadians(0)))
                //purple pixel
                .addTemporalMarker(0.8, () -> {
                    intakeMotor.setPower(-0.7);
                })
                .addTemporalMarker(1.8, () -> {
                    intakeMotor.setPower(0);
                })
                .strafeLeft(8)
                .build();

        TrajectorySequence main = drive.trajectorySequenceBuilder(leftPurple.end())

                .lineToLinearHeading(new Pose2d(0,-46, Math.toRadians(0)))
                //.splineToLinearHeading(new Pose2d(25,-52,Math.toRadians(0)), Math.toRadians(0))
                //.back(70)

                //yellow pixel
                /*.lineToLinearHeading(new Pose2d(-84,-32,Math.toRadians(0)))
                .addTemporalMarker(8, () -> {
                    armLeftServo.setPosition(0);
                    armRightServo.setPosition(1);
                })
                .back(5)
                .addDisplacementMarker(() -> {
                    clawServo.setPosition(0.8);
                })
                .forward(3)
                .addDisplacementMarker( () -> {
                    clawServo.setPosition(0.5);
                    armLeftServo.setPosition(1);
                    armRightServo.setPosition(0);
                })

                //white pixel
                .strafeLeft(5)
                .back(5)
                .addDisplacementMarker(() -> {
                    armLeftServo.setPosition(0);
                    armRightServo.setPosition(1);
                    clawServo.setPosition(0.8);
                })
                .forward(3)

                //park
                .addDisplacementMarker( () -> {
                    armLeftServo.setPosition(1);
                    armRightServo.setPosition(0);
                })
                .strafeRight(24)
                .back(8)

                .lineToLinearHeading(new Pose2d(0,0,Math.toRadians(-90)))*/
                .build();



        waitForStart();
        PropDetectorRED.Location place = redDetector.getLocation();
        armLeftServo.setPosition(1);
        armRightServo.setPosition(0);
        clawServo.setPosition(0.5);

        telemetry.setMsTransmissionInterval(50);
        if(isStopRequested()) return;

        //DRIVING
        drive.setPoseEstimate(startPose);
        /*if(place != null) {
            switch (place) {
                case NOT_FOUND:
                    drive.followTrajectorySequence(rightPurple);
                    break;
                case CENTER:
                    drive.followTrajectorySequence(centerPurple);
                    break;
                case LEFT:
                    drive.followTrajectorySequence(leftPurple);

            }
        }
        else{
            drive.followTrajectorySequence(rightPurple);
        }*/

        drive.followTrajectorySequence(leftPurple);
        drive.followTrajectorySequence(main);
    }
}
