package Auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import Camera.PropDetectorRED;

@Autonomous
public class RedFar_3_PY_1W_3 extends LinearOpMode {
    OpenCvCamera cam;
    private DcMotorEx motorFrontLeft, motorBackLeft, motorFrontRight, motorBackRight, intakeMotor;
    private Servo clawServo, armLeftServo, armRightServo, intakeLift;
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
        intakeLift = hardwareMap.get(Servo.class, "intakeLiftServo");


        //TRAJECTORIES (left/right in robot perspective)
        Pose2d startPose = new Pose2d(-14, 0, Math.toRadians(-90)); //90
        drive.setPoseEstimate(startPose);


        //purple pixel
       TrajectorySequence right = drive.trajectorySequenceBuilder(startPose)
               .forward(14.5)
               .strafeRight(15)

                //purple pixel
                .addDisplacementMarker(() -> {
                    for(int i=0; i<100; i++) {
                        intakeLift.setPosition(0.75);
                    }
                    for(int i=0; i<100; i++) {
                        intakeMotor.setPower(-0.7);
                    }
                })
                .addTemporalMarker(3, () -> {
                    for(int i=0; i<100; i++) {
                        intakeMotor.setPower(0);
                    }
                    for(int i=0; i<100; i++) {
                        intakeLift.setPosition(0.25);
                    }
                })

                //white pixel
                .strafeLeft(22)
                .lineToLinearHeading(new Pose2d(0,-53, Math.toRadians(0)))
                .addDisplacementMarker( () -> {
                    for(int i=0; i<100; i++) {
                        intakeMotor.setPower(0.5);
                    }
                })
                .forward(7)
                .waitSeconds(2)
                .addDisplacementMarker( () -> {
                    for(int i=0; i<100; i++) {
                        intakeLift.setPosition(0.25);
                    }
                    for(int i=0; i<100; i++) {
                        intakeMotor.setPower(0.7);
                    }
                })
                .back(15)

                .forward(5)
                .addDisplacementMarker(() -> {
                    for(int i=0; i<100; i++) {
                        intakeLift.setPosition(0.25);
                    }
                    for(int i=0; i<100; i++) {
                        clawServo.setPosition(0.45);
                    }
                })
                .back(110)
                .addDisplacementMarker( () -> {
                    for(int i=0; i<100; i++) {
                        intakeMotor.setPower(0);
                    }
                })
                .strafeLeft(35)

                .addDisplacementMarker( () -> {
                    for(int i=0; i<100; i++) {
                        armLeftServo.setPosition(0.2);
                        armRightServo.setPosition(0.8);
                    }
                })
                .back(10)
                .addDisplacementMarker( () -> {
                    for(int i=0; i<100; i++) {
                        clawServo.setPosition(0.8);
                    }
                })
                .forward(5)
                .strafeLeft(6)
                .addDisplacementMarker( () -> {
                    for(int i=0; i<100; i++) {
                        armLeftServo.setPosition(1);
                        armRightServo.setPosition(0);
                    }
                    for(int i=0; i<100; i++) {
                        clawServo.setPosition(0.45);
                    }
                })
                .waitSeconds(1)
                .addDisplacementMarker( () -> {
                    for(int i=0; i<100; i++) {
                        armLeftServo.setPosition(0.2);
                        armRightServo.setPosition(0.8);
                    }
                })
                .waitSeconds(0.5)
                .addDisplacementMarker( () -> {
                    for(int i=0; i<100; i++) {
                        clawServo.setPosition(0.8);
                    }
                })
                .back(5)

                .build();


       //CENTERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR


        TrajectorySequence center = drive.trajectorySequenceBuilder(startPose)
                .strafeLeft(30)
                .lineToLinearHeading(new Pose2d(30,-30,Math.toRadians(180)))
                //purple pixel
                .addDisplacementMarker(() -> {
                    for(int i=0; i<100; i++) {
                        intakeLift.setPosition(0.75);
                    }
                    for(int i=0; i<100; i++) {
                        intakeMotor.setPower(-0.7);
                    }
                })
                .addTemporalMarker(3, () -> {
                    for(int i=0; i<100; i++) {
                        intakeMotor.setPower(0);
                    }
                    for(int i=0; i<100; i++) {
                        intakeLift.setPosition(0.25);
                    }
                })

                //white pixel
                .lineToLinearHeading(new Pose2d(0,-53, Math.toRadians(0)))
                .addDisplacementMarker( () -> {
                    for(int i=0; i<100; i++) {
                        intakeMotor.setPower(0.5);
                    }
                })
                .forward(7)
                .waitSeconds(2)
                .addDisplacementMarker( () -> {
                    for(int i=0; i<100; i++) {
                        intakeLift.setPosition(0.25);
                    }
                    for(int i=0; i<100; i++) {
                        intakeMotor.setPower(0.7);
                    }
                })
                .back(15)

                .forward(5)
                .addDisplacementMarker(() -> {
                    for(int i=0; i<100; i++) {
                        intakeLift.setPosition(0.25);
                    }
                    for(int i=0; i<100; i++) {
                        clawServo.setPosition(0.45);
                    }
                })
                .back(110)
                .addDisplacementMarker( () -> {
                    for(int i=0; i<100; i++) {
                        intakeMotor.setPower(0);
                    }
                })
                .strafeLeft(35)

                .addDisplacementMarker( () -> {
                    for(int i=0; i<100; i++) {
                        armLeftServo.setPosition(0.2);
                        armRightServo.setPosition(0.8);
                    }
                })
                .back(10)
                .addDisplacementMarker( () -> {
                    for(int i=0; i<100; i++) {
                        clawServo.setPosition(0.8);
                    }
                })
                .forward(5)
                .strafeLeft(6)
                .addDisplacementMarker( () -> {
                    for(int i=0; i<100; i++) {
                        armLeftServo.setPosition(1);
                        armRightServo.setPosition(0);
                    }
                    for(int i=0; i<100; i++) {
                        clawServo.setPosition(0.45);
                    }
                })
                .waitSeconds(1)
                .addDisplacementMarker( () -> {
                    for(int i=0; i<100; i++) {
                        armLeftServo.setPosition(0.2);
                        armRightServo.setPosition(0.8);
                    }
                })
                .waitSeconds(0.5)
                .addDisplacementMarker( () -> {
                    for(int i=0; i<100; i++) {
                        clawServo.setPosition(0.8);
                    }
                })
                .back(5)

                .build();


        //LEFTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT

        TrajectorySequence left = drive.trajectorySequenceBuilder(startPose)
                .lineToLinearHeading(new Pose2d(-8,-14.5,Math.toRadians(-90)))
                //purple pixel
                .addDisplacementMarker(() -> {
                    for(int i=0; i<100; i++) {
                        intakeLift.setPosition(0.75);
                    }
                    for(int i=0; i<100; i++) {
                        intakeMotor.setPower(-0.7);
                    }
                })
                .addTemporalMarker(3, () -> {
                    for(int i=0; i<100; i++) {
                        intakeMotor.setPower(0);
                    }
                    for(int i=0; i<100; i++) {
                        intakeLift.setPosition(0.25);
                    }
                })

                //white pixel
                .strafeRight(22)
                .lineToLinearHeading(new Pose2d(0,-53, Math.toRadians(0)))
                .addDisplacementMarker( () -> {
                    for(int i=0; i<100; i++) {
                        intakeMotor.setPower(0.5);
                    }
                })
                .forward(7)
                .waitSeconds(2)
                .addDisplacementMarker( () -> {
                    for(int i=0; i<100; i++) {
                        intakeLift.setPosition(0.25);
                    }
                    for(int i=0; i<100; i++) {
                        intakeMotor.setPower(0.7);
                    }
                })
                .back(15)

                .forward(5)
                .addDisplacementMarker(() -> {
                    for(int i=0; i<100; i++) {
                        intakeLift.setPosition(0.25);
                    }
                    for(int i=0; i<100; i++) {
                        clawServo.setPosition(0.45);
                    }
                })
                .back(110)
                .addDisplacementMarker( () -> {
                    for(int i=0; i<100; i++) {
                        intakeMotor.setPower(0);
                    }
                })
                .strafeLeft(35)

                .addDisplacementMarker( () -> {
                    for(int i=0; i<100; i++) {
                        armLeftServo.setPosition(0.2);
                        armRightServo.setPosition(0.8);
                    }
                })
                .back(10)
                .addDisplacementMarker( () -> {
                    for(int i=0; i<100; i++) {
                        clawServo.setPosition(0.8);
                    }
                })
                .forward(5)
                .strafeLeft(6)
                .addDisplacementMarker( () -> {
                    for(int i=0; i<100; i++) {
                        armLeftServo.setPosition(1);
                        armRightServo.setPosition(0);
                    }
                    for(int i=0; i<100; i++) {
                        clawServo.setPosition(0.45);
                    }
                })
                .waitSeconds(1)
                .addDisplacementMarker( () -> {
                    for(int i=0; i<100; i++) {
                        armLeftServo.setPosition(0.2);
                        armRightServo.setPosition(0.8);
                    }
                })
                .waitSeconds(0.5)
                .addDisplacementMarker( () -> {
                    for(int i=0; i<100; i++) {
                        clawServo.setPosition(0.8);
                    }
                })
                .back(5)

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
        if(place != null) {
            switch (place) {
                case NOT_FOUND:
                    drive.followTrajectorySequence(right);
                    break;
                case CENTER:
                    drive.followTrajectorySequence(center);
                    break;
                case LEFT:
                    drive.followTrajectorySequence(left);

            }
        }
        else{
            drive.followTrajectorySequence(right);
        }

        //testing
        //drive.followTrajectorySequence(left);

        //mailbox
        Mailbox mail =  new Mailbox();
        mail.setAutoEnd(Math.toDegrees(drive.getExternalHeading()));

    }
}
