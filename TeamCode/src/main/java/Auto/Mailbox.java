package Auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;

public class Mailbox
{
    public static double autoEndHead;
    public Mailbox()
    {
    }
    public void setAutoEnd(double end){
        autoEndHead = end;
    }
    public double getAutoEnd(){
        return autoEndHead;
    }
}