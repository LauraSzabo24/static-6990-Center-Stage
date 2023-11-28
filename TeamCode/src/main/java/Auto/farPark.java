package Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class farPark extends LinearOpMode {

    private DcMotorEx motorFrontLeft, motorBackLeft, motorFrontRight, motorBackRight, intakeMotor;
    private Servo intakeLiftServo;

    public void runOpMode()
    {
        //drive motors
        motorFrontLeft = (DcMotorEx) hardwareMap.dcMotor.get("FL");
        motorBackLeft = (DcMotorEx) hardwareMap.dcMotor.get("BL");
        motorFrontRight = (DcMotorEx) hardwareMap.dcMotor.get("FR");
        motorBackRight = (DcMotorEx) hardwareMap.dcMotor.get("BR");

        motorFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        intakeMotor = (DcMotorEx) hardwareMap.dcMotor.get("intakeMotor");
        intakeLiftServo = hardwareMap.get(Servo.class, "intakeLiftServo");


        waitForStart();

        //turns on
        motorFrontLeft.setPower(0.5);
        motorBackLeft.setPower(-0.5);
        motorFrontRight.setPower(-0.5);
        motorBackRight.setPower(0.5);
        sleep(400);
        motorFrontLeft.setPower(-0.5);
        motorBackLeft.setPower(-0.5);
        motorFrontRight.setPower(-0.5);
        motorBackRight.setPower(-0.5);
        sleep(8000); //2000
        motorFrontLeft.setPower(0);
        motorBackLeft.setPower(0);
        motorFrontRight.setPower(0);
        motorBackRight.setPower(0);
    }

}
