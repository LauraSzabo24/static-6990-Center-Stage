package TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import Auto.Mailbox;

@TeleOp
public class tester extends LinearOpMode {
    @Override
    public void runOpMode()
    {
        Mailbox mail =  new Mailbox();

        while(true) {
            if (Mailbox.autoEndHead > 45 && Mailbox.autoEndHead < 135) {
                telemetry.addData("90 degrees", Mailbox.autoEndHead);
            } else if (Mailbox.autoEndHead > 135 && Mailbox.autoEndHead < 225) {
                telemetry.addData("180 degrees", Mailbox.autoEndHead);
            } else if (Mailbox.autoEndHead > 225 && Mailbox.autoEndHead < 315) {
                telemetry.addData("270 degrees", Mailbox.autoEndHead);
            } else if (Mailbox.autoEndHead > 315 || Mailbox.autoEndHead < 45) {
                telemetry.addData("360 degrees", Mailbox.autoEndHead);
            } else {
                telemetry.addData("idk", Mailbox.autoEndHead);
            }
            telemetry.update();
        }
    }
}
