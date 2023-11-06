package Camera;

import org.firstinspires.ftc.robotcore.external.Telemetry;
        import org.opencv.core.Core;
        import org.opencv.core.Mat;
        import org.opencv.core.Point;
        import org.opencv.core.Rect;
        import org.opencv.core.Scalar;
        import org.opencv.imgproc.Imgproc;
        import org.openftc.easyopencv.OpenCvPipeline;

public class PropDetectorRED extends OpenCvPipeline {
    Telemetry telemetry;
    Mat mat = new Mat();
    public enum Location {
        LEFT,
        CENTER,
        NOT_FOUND
    }
    private Location location;

    static final Rect LEFT_ROI = new Rect( //make this the correct area
            new Point(60, 35),
            new Point(120, 75));
    static final Rect CENTER_ROI = new Rect( //make this the correct area
            new Point(140, 35),
            new Point(200, 75));
    static double PERCENT_COLOR_THRESHOLD = 0.4;

    public PropDetectorRED(Telemetry t) { telemetry = t; }

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
        Scalar lowHSVRED = new Scalar(10, 72, 76);
        Scalar highHSVRED = new Scalar(0, 96, 49);

        Core.inRange(mat, lowHSVRED, highHSVRED, mat);

        Mat left = mat.submat(LEFT_ROI); //the area on the camera that would be the left prop if it's there
        Mat center = mat.submat(CENTER_ROI); //area on the camera that would be the right prop if it's there

        double leftValue = Core.sumElems(left).val[0] / LEFT_ROI.area() / 255; //percentage red
        double centerValue = Core.sumElems(center).val[0] / CENTER_ROI.area() / 255; //percentage red

        left.release();
        center.release();

        telemetry.addData("Left raw value", (int) Core.sumElems(left).val[0]);
        telemetry.addData("Center raw value", (int) Core.sumElems(center).val[0]);
        telemetry.addData("Left percentage", Math.round(leftValue * 100) + "%");
        telemetry.addData("Center percentage", Math.round(centerValue * 100) + "%");

        //is it there or is it not (true = it is there)
        boolean propLeft = leftValue > PERCENT_COLOR_THRESHOLD;
        boolean propCenter = centerValue > PERCENT_COLOR_THRESHOLD;

        if (propLeft && propCenter) { //if both are there (assume none are there)
            location = Location.NOT_FOUND;
            telemetry.addData("Prop Location", "both center and left");
        }
        else if (propLeft) {
            location = Location.LEFT;
            telemetry.addData("Prop Location", "left");
        }
        else if(propCenter) {
            location = Location.CENTER;
            telemetry.addData("Prop Location", "center");
        }
        else {
            location = Location.NOT_FOUND;
            telemetry.addData("Prop Location", "right");
        }
        telemetry.update();

        //for display purposes
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB);

        Scalar propBoxColor = new Scalar(255, 0, 0); //for box drawing purposes

        //draws rectangle
        if(location == Location.LEFT)
        {
            Imgproc.rectangle(mat, LEFT_ROI, propBoxColor);
        }
        else if(location == Location.CENTER)
        {
            Imgproc.rectangle(mat, CENTER_ROI, propBoxColor);
        }

        return mat;
    }

    public Location getLocation()
    {
        return location;
    }
}