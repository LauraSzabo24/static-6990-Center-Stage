package TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class MainTeleOp extends OpMode {

    //PID material

    double Kp = 0;
    double Kd = 0;

    ElapsedTime timer = new ElapsedTime();
    private double lastError = 0;

    //driver a material
    private boolean confirmA;
    private boolean manualOn;
    private boolean emergencyMode;

    //emergency mode
    private boolean a2Pressed;
    private boolean a2Released;
    private boolean b2Pressed;
    private boolean b2Released;
    private boolean x2Pressed;
    private boolean x2Released;
    private boolean y2Pressed;
    private boolean y2Released;

    //driver b material
    private static String[][] output;
    private int cursorX;
    private int cursorY;
    private static int cursorFlash;
    private int[] firstPos;
    private int[] secPos;
    private boolean confirmB;
    private String[] colors;
    private String previousOutput;

    //color selection stuff
    private boolean aPressed;
    private boolean aReleased;
    private boolean bPressed;
    private boolean bReleased;

    //cursor movement
    private boolean leftPressed;
    private boolean leftReleased;
    private boolean rightPressed;
    private boolean rightReleased;
    private boolean downPressed;
    private boolean downReleased;
    private boolean upPressed;
    private boolean upReleased;
    private boolean boxRow;

    public void driverAInitialize()
    {
        confirmA = false;
        manualOn = true;
        emergencyMode = false;

        //emergency mode
        a2Pressed = false;
        a2Released = false;
        b2Pressed = false;
        b2Released = false;
        x2Pressed = false;
        x2Released = false;
        y2Pressed = false;
        y2Released = false;

        //motors

    }
    public void driverBInitialize()
    {
        output = new String[12][13]; //15
        cursorX = 1;
        cursorY = 10;
        cursorFlash = 50;
        firstPos = new int[]{-1,-1};
        secPos = new int[]{-1,-1};
        confirmB = false;
        previousOutput = "";
        boxRow = true;
        colors = new String[] {"", ""};
        makeGrid();
        printAll();

        //color
        getColors();
        aPressed = false;
        aReleased = false;
        bPressed = false;
        bReleased = false;

        //cursor
        leftPressed = false;
        leftReleased = false;
        rightPressed = false;
        rightReleased = false;
        upPressed = false;
        upReleased = false;
        downPressed = false;
        downReleased = false;
    }
    public void cameraInit()
    {
        //color camera stuff goes in here
    }
    public void pidInit()
    {

    }
    @Override
    public void init()
    {
        driverAInitialize();
        driverBInitialize();
        cameraInit();
        pidInit();
    }
    @Override
    public void start()
    {
        makeGrid();
        //everything goes in here that isn't looping, won't be much
    }
    @Override
    public void loop()
    {
        //PID


        //Normal Driver A Controls
        updateDriverAButtons();
        if(!emergencyMode && manualOn)
        {
            //confirmation
            if(b2Released && !(a2Released || x2Released || y2Released))
            {
                b2Released = false;
                b2Pressed = false;
                confirmA = true;
            }

            //everything else
            updateDriverAControls();

        }
        //Tetris Driver B Updating
        if(!emergencyMode) {
            printAll();
            updateTetrisThing();
        }

        //Tetris color checker
        if(colors[0].equals("") && colors[1].equals("") && confirmB && confirmA)
        {
            //getColors();
        }

        //Tetris Pixel Placing Thing
        if(confirmA && confirmB && !emergencyMode)
        {
            int[] place1 = firstPos;
            int[] place2 = secPos;
            runPixelPlacing(place1, place2);
            //firstPos = new int[]{-1,-1};
            //secPos = new int[]{-1,-1};
            //confirmB = false;
            //confirmA = false;
        }

        //EMERGENCY MODE CONTROLS
        if(a2Released && b2Released && x2Released && y2Released)
        {
            b2Released = false;
            b2Pressed = false;
            a2Released = false;
            a2Pressed = false;
            x2Released = false;
            x2Pressed = false;
            y2Released = false;
            y2Pressed = false;
            if(emergencyMode)            {
                emergencyMode = false;
            }
            else {
                emergencyMode = true;
            }
        }
        if(emergencyMode)
        {
            telemetry.addLine(String.format("EMERGENCYYYYYYYY MODEEEEEEEEEE"));
            emergencyModeControls();
        }

        telemetry.update();
    }

    //PIDDDDDDDDDDDDDD
    public double PIDControl(double reference, double state){
        double error = reference - state;
        double derivative = (error - lastError)/timer.seconds();
        lastError = error;

        timer.reset();

        double output = (error*Kp) + (derivative * Kd);
        return output;
    }

    //DRIVER A NORMAL CONTROLS FROM HEREEEE
    public void updateDriverAControls()
    {


    }








    //EMERGENCY MODE THINGS HEREEE
    public void emergencyModeControls()
    {
        //Driver A - controls pretty much same as normal manual
        manualOn = true;
        //Driver B - slides, claw etc
    }
    public void updateDriverAButtons()
    {
        //a
        if(gamepad2.a)
        {
            a2Pressed = true;
        }
        else if(a2Pressed){
            a2Released = true;
        }
        //b
        if(gamepad2.b)
        {
            b2Pressed = true;
        }
        else if(b2Pressed){
            b2Released = true;
        }
        //x
        if(gamepad2.x)
        {
            x2Pressed = true;
        }
        else if(x2Pressed){
            x2Released = true;
        }
        //y
        if(gamepad2.y)
        {
            y2Pressed = true;
        }
        else if(y2Pressed){
            y2Released = true;
        }
    }






    //ALL TETRIS TRASH DOWN FROM HERE
    public int xCoorSimplify(int xCoor) //doesn't work
    {
        //remove spaces from x coor
        int newX = -1;
        if(boxRow)
        {
            return (xCoor-1)%2;
        }
        return (xCoor-2)%2;
    }
    public double convertX(int xCoor) //doesn't work
    {
        telemetry.addLine(String.format("Old x coor" + xCoor));
        xCoor = xCoorSimplify(xCoor);
        telemetry.addLine(String.format("New x coor" + xCoor));
        if(boxRow)
        {
            return 4.75+(3*xCoor*0.5);
            //return distanceToFirstTopPixelFromScrew+(pixelWidth*xCoor*0.5);
        }
        else {
            return 3.25+(3*xCoor*0.5);
            //return distanceToFirstPixel+(pixelWidth*xCoor*0.5);
        }
    }
    public double convertY(int yCoor) //seems to work
    {
        double inches = 5.75;
        for(int x=10; x>=yCoor; x--)
        {
            if(x%2==1)
            {
                inches+=2;
            }
            else {
                inches+=3;
            }
        }
        return inches;
    }
    public void runPixelPlacing(int [] target1, int [] target2)
    {
        manualOn = false;
        double[] position1 = new double[2];
        double[] position2 = new double[2];

        telemetry.addLine(String.format("" + target1[0]));
        if(target1[0]!=-1) {
            telemetry.addLine(String.format("x normal " + target1[1]));
            telemetry.addLine(String.format("y normal " + target1[0]));
            position1[0] = convertX(target1[1]);
            position1[1] = convertY(target1[0]);

            telemetry.addLine(String.format("x in inches " + position1[0]));
            telemetry.addLine(String.format("y in inches " + position1[1]));
            //road runner code that goes to the correct firstPos x in arc shape
            //moves slides while going
            //push pixel out

            if(target2[0]!=-1) {
                telemetry.addLine(String.format("x normal " + target2[1]));
                telemetry.addLine(String.format("y normal " + target2[0]));
                position2[0] = convertX(target2[1]);
                position2[1] = convertY(target2[0]);
                telemetry.addLine(String.format("x in inches " + position2[0]));
                telemetry.addLine(String.format("y in inches " + position2[1]));
            }
        }

        //put early end / overriding in here
        manualOn = true;
    }

    public void getColors()
    {
        colors = new String[] {"G", "P"}; //⬜ 🟪 🟩 🟨
    }
    public void updateTetrisThing()
    {
        //cursor flashing
        if(output[cursorY][cursorX]!="◼")
        {
            previousOutput = output[cursorY][cursorX];
        }
        cursorFlash--;
        if(cursorFlash>25) {
            output[cursorY][cursorX] = "◼"; //⬛ █◼
        }
        else
        {
            output[cursorY][cursorX] = previousOutput;
        }
        if(cursorFlash<1)
        {
            cursorFlash=50;
            previousOutput = output[cursorY][cursorX];
            output[cursorY][cursorX] = previousOutput;
        }
        cursorUpdate();


        //selection
        if(aReleased && manualOn && !(colors[0].equals("")))
        {
            aPressed = false;
            aReleased = false;
            output[cursorY][cursorX] = colors[0];
            if(colors[1]=="")
            {
                secPos = new int[]{cursorY, cursorX};
            }
            else{
                firstPos = new int[]{cursorY, cursorX};
            }
            colors[0]=colors[1];
            colors[1]="";
        }

        //retrieval???? necessary???

        //confirmation
        if(bReleased && (firstPos[1]!=-1 || secPos[1]!=-1) && manualOn)
        {
            bPressed = false;
            bReleased = false;
            confirmB = true;
        }
    }
    public void cursorUpdate() //WORKS
    {
        //left
        if(gamepad1.dpad_left)
        {
            leftPressed = true;
        }
        else if(leftPressed){
            leftReleased = true;
        }
        //right
        if(gamepad1.dpad_right && cursorX<12)
        {
            rightPressed = true;
        }
        else if(rightPressed){
            rightReleased = true;
        }
        //down
        if(gamepad1.dpad_down && cursorY<10)
        {
            downPressed = true;
        }
        else if(downPressed){
            downReleased = true;
        }
        //up
        if(gamepad1.dpad_up && cursorY>=1)
        {
            upPressed = true;
        }
        else if(upPressed){
            upReleased = true;
        }

        //a and b
        if(gamepad1.a)
        {
            aPressed = true;
        }
        else if(aPressed){
            aReleased = true;
        }
        //up
        if(gamepad1.b)
        {
            bPressed = true;
        }
        else if(bPressed){
            bReleased = true;
        }

        //cursor movement
        isBoxRow();
        if(leftReleased && cursorX>1)
        {
            leftPressed = false;
            leftReleased = false;
            output[cursorY][cursorX] = previousOutput;
            if(cursorX-1>1){
                cursorX-=2;
            }
        }
        else if(rightReleased && cursorX<12)
        {
            rightPressed = false;
            rightReleased = false;
            output[cursorY][cursorX] = previousOutput;
            if(cursorX+1<12){
                cursorX+=2;
            }
        }
        else if(downReleased && cursorY<10)
        {
            downPressed = false;
            downReleased = false;
            output[cursorY][cursorX] = previousOutput;
            cursorY++;
            if(boxRow){
                cursorX++;
            }
            else{
                cursorX--;
            }
        }
        else if(upReleased && cursorY>=1)
        {
            upPressed = false;
            upReleased = false;
            output[cursorY][cursorX] = previousOutput;
            cursorY--;
            if(boxRow){
                cursorX++;
            }
            else{
                cursorX--;
            }
        }
    }
    public void isBoxRow() //WORKS
    {
        if(cursorY%2==0)
        {
            boxRow = true;
        }
        else{
            boxRow = false;
        }
    }
    public void printAll()
    {
        //colors avaliable
        telemetry.addLine(String.format("                    1      2"));
        telemetry.addLine(String.format("COLORS - "+colors[0]+"      "+colors[1]));

        //2d array output
        String rowOut = "";
        for(int r=0; r<output.length; r++)
        {
            rowOut = "";
            for(int c=0; c<output[1].length; c++) {
                rowOut += output[r][c];
            }
            telemetry.addData("", rowOut);
        }

        //selections queue
        if(firstPos[0]!=-1 && secPos[0]!=-1)
        {
            telemetry.addLine(String.format("QUEUE |   "+firstPos[1]+","+firstPos[0]+" then "+secPos[1]+","+secPos[0], null));
        }
        else if(firstPos[0]!=-1)
        {
            telemetry.addLine(String.format("QUEUE |   "+firstPos[1]+","+firstPos[0]));
        }
        else {
            telemetry.addLine(String.format("QUEUE |   "));

        }

        //confirmation queue
        if(confirmB && confirmA)
        {
            telemetry.addLine(String.format("ROBOT RUNNING"));
        }
        else if(confirmB)
        {
            telemetry.addLine(String.format("CONFIRMED PLEASE WAIT"));
        }
        else if(colors[1].equals("") && firstPos[0]!=-1)
        {
            telemetry.addLine(String.format("UNCONFIRMED CHANGES"));
        }
        else if(colors[0].equals("") && colors[1].equals(""))
        {
            telemetry.addLine(String.format("NO PIXELS LOADED"));
        }
        else
        {
            telemetry.addLine(String.format("PIXELS READY TO GO"));
        }

    }
    public void makeGrid() { //WORKS
        for (int r = 0; r < output.length; r++) {
            for (int c = 0; c < output[1].length; c++) {
                if (c != 0 && c != 14) {
                    if (r % 2 == 0 && c % 2 == 0) {
                        output[r][c] = "   ";
                    } else if (r % 2 == 1 && c % 2 == 1) {
                        output[r][c] = "   ";
                    } else {
                        output[r][c] = "◻"; //O
                    }
                } else {
                    output[r][c] = "   ";
                }
                if ((r == 2 || r == 5 || r == 8) && output[r][c] == "   ") {
                    output[r][c] = "_.";
                }
                if (r == 11) {
                    if (c == 3 || c == 7 || c == 11) {
                        output[r][c] = "X";
                    } else {
                        output[r][c] = "_";
                    }
                }
            }
        }
    }
}
