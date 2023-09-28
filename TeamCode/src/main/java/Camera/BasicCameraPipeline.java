package Camera;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class BasicCameraPipeline extends OpenCvPipeline {
    Mat video = new Mat();
    int result;
    @Override
    public void init(Mat firstFrame)
    {
        video = firstFrame.submat(0,50,0,50);
        result = 0;
    }

    @Override
    public Mat processFrame(Mat input)
    {
        Imgproc.cvtColor(input, video, Imgproc.COLOR_RGB2GRAY);
        result = 1;
        return input;
    }

    public int getLatestResults()
    {
        return result;
    }

}
