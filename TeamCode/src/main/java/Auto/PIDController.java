package Auto;

import androidx.annotation.Nullable;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.drive.DriveSignal;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDController extends LinearOpMode {
    DcMotorEx FrontLeft;
    DcMotorEx FrontRight;
    DcMotorEx BackLeft;
    DcMotorEx BackRight;
    double Kp = 0;
    double Kd = 0;

    ElapsedTime timer = new ElapsedTime();
    private double lastError = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        FrontLeft = hardwareMap.get(DcMotorEx.class, "FrontLeft");
        FrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        FrontRight = hardwareMap.get(DcMotorEx.class, "FrontRight");
        FrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        BackLeft = hardwareMap.get(DcMotorEx.class, "BackLeft");
        BackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        BackRight = hardwareMap.get(DcMotorEx.class, "BackRight");
        BackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

//could be position
        waitForStart();
        while (opModeIsActive()) {
            double power = PIDControl(1000, FrontLeft.getVelocity());
            FrontLeft.setPower(power);

            power = PIDControl(1000, FrontRight.getVelocity());
            FrontRight.setPower(power);

            power = PIDControl(1000, BackRight.getVelocity());
            BackRight.setPower(power);

            power = PIDControl(1000, BackLeft.getVelocity());
            BackLeft.setPower(power);

        }
    }
    public double PIDControl(double reference, double state){
        double error = reference - state;
        double derivative = (error - lastError)/timer.seconds();
        lastError = error;

        timer.reset();

        double output = (error*Kp) + (derivative * Kd);
        return output;
    }
}
