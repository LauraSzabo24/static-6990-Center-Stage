package TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import Camera.PropDetectorRED;

public class ColorDetector extends OpenCvPipeline {
    Telemetry telemetry;
    Mat mat = new Mat();
    public enum Color {
        WHITE,
        PURPLE,
        YELLOW,
        GREEN,
        NADA
    }
    private Color pixelColor;

    //for reference only (0,0) in top left
    static final Rect SCREENSIZEBOX = new Rect( //make this the correct area
            new Point(0, 0),
            new Point(320, 240));

    //actual boxes
    static final Rect INTAKEBOX = new Rect( //make this the correct area
            new Point(14, 0),
            new Point(140, 90));
    static double PERCENT_COLOR_THRESHOLD = 0.1;


    public ColorDetector(Telemetry t) { telemetry = t; }

    @Override
    public Mat processFrame(Mat input) {

        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);

        //COLORS
        Scalar lowHSVYELLOW= new Scalar(10, 0, 0);
        Scalar highHSVYELLOW = new Scalar(25, 255, 255);

        Scalar lowHSVGREEN= new Scalar(45, 0, 0);
        Scalar highHSVGREEN = new Scalar(76, 255, 255);

        Scalar lowHSVPURPLE= new Scalar(120, 50, 50);
        Scalar highHSVPURPLE = new Scalar(170, 255, 255);

        Scalar lowHSVWHITE= new Scalar(0, 0, 80);
        Scalar highHSVWHITE = new Scalar(180, 30, 255);

        Mat left = mat.submat(INTAKEBOX);
        pixelColor = Color.NADA;

        //yellow
        Core.inRange(mat, lowHSVYELLOW, highHSVYELLOW, mat);
        double colorPercentage = Core.sumElems(left).val[0] / INTAKEBOX.area() / 255;
        if(colorPercentage > PERCENT_COLOR_THRESHOLD)
        {
            pixelColor = Color.YELLOW;
            telemetry.addData("Color", "yellow");
        }
        //purple
        Core.inRange(mat, lowHSVPURPLE, highHSVPURPLE, mat);
        colorPercentage = Core.sumElems(left).val[0] / INTAKEBOX.area() / 255;
        if(colorPercentage > PERCENT_COLOR_THRESHOLD)
        {
            pixelColor = Color.PURPLE;
            telemetry.addData("Color", "purple");
        }
        //green
        Core.inRange(mat, lowHSVGREEN, highHSVGREEN, mat);
        colorPercentage = Core.sumElems(left).val[0] / INTAKEBOX.area() / 255;
        if(colorPercentage > PERCENT_COLOR_THRESHOLD)
        {
            pixelColor = Color.GREEN;
            telemetry.addData("Color", "green");
        }
        //white
        Core.inRange(mat, lowHSVWHITE, highHSVWHITE, mat);
        colorPercentage = Core.sumElems(left).val[0] / INTAKEBOX.area() / 255;
        if(colorPercentage > PERCENT_COLOR_THRESHOLD)
        {
            pixelColor = Color.WHITE;
            telemetry.addData("Color", "white");
        }

        left.release();

        //for display purposes
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB);

        Scalar propBoxColor = new Scalar(255, 0, 0); //for box drawing purposes

        //draws rectangle
        Imgproc.rectangle(mat, INTAKEBOX, propBoxColor);

        return mat;
    }
    public Color getColor()
    {
        return pixelColor;
    }
}
