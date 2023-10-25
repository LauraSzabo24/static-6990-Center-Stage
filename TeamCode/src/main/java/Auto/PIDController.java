package Auto;

import androidx.annotation.Nullable;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.drive.DriveSignal;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.sequencesegment.SequenceSegment;


@Config
@TeleOp(name = "PID Test")
public class PIDController extends LinearOpMode {

    DcMotorEx motor;

    ElapsedTime timer = new ElapsedTime();

    private double lastError = 0;
    private double integralSum = 0;

    public static double Kp = 0.01;
    public static double Kd = 0.0;

    public static double targetPosition = 5000;

    private final FtcDashboard dashboard = FtcDashboard.getInstance();
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        TelemetryPacket packet = new TelemetryPacket();

        dashboard.setTelemetryTransmissionInterval(25);
        drive = hardwareMap.get(SampleMecanumDrive.class, "motor");

        drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();

        int targetPosition = 0;


        while(opModeIsActive()){
            if(gamepad1.a) {
                targetPosition = 5000;
            }
            if(gamepad1.b){
                targetPosition = 0;
            }
                double power = returnPower(targetPosition, motor.getCurrentPosition());
                packet.put("power", power);
                packet.put("position", motor.getCurrentPosition());
                packet.put("error", lastError);
                packet.put("targetPosition", targetPosition);
                telemetry.addData("power", power);
                telemetry.addData("position",motor.getCurrentPosition());
                telemetry.addData("error", lastError);
                motor.setPower(power);
                telemetry.update();

                dashboard.sendTelemetryPacket(packet);
        }
    }

    public double returnPower(double reference, double state){
        double error = reference - state;
        double derivative = (error-lastError)/timer.seconds();
        lastError = error;

        timer.reset();

        double output = (error* Kp) +  (derivative * Kd);
        return output;
    }
}
