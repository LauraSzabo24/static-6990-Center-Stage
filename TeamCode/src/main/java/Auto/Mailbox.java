package Auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;

public class Mailbox
{
    public static double autoEndHead;
    public Mailbox()
    {
    }
    public void setAutoEnd(Pose2d end){
        currentPose = end;
    }
    public double getAutoEnd(){
        return autoEndHead;
    }
    public static Pose2d currentPose = new Pose2d();
}