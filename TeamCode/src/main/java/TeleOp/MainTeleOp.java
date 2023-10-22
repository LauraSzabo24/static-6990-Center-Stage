package TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class MainTeleOp extends OpMode {
    //driver a material
    private boolean confirmA;
    private boolean manualOn;
    private boolean emergencyMode;

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
        if(!emergencyMode)
        {

        }
        //Tetris Driver B Updating
        if(!emergencyMode) {
            printAll();
            updateTetrisThing();
            telemetry.update();
        }

        //Tetris color checker (will normally run as soon as the colors empty, if inconsistent run by command from driver a)
        if(colors[0].equals("") && colors[1].equals("") && confirmB)
        {
            //confirmB = false; //(normally happens after both pixels were placed)
            getColors();
        }

        //done to here
        //Tetris Pixel Placing Thing
        if(confirmA && confirmB && !emergencyMode)
        {
            runPixelPlacing(firstPos, secPos);
            confirmB = false;
            confirmA = false;
        }

        //EMERGENCY MODE CONTROLS
        //driver A turns on emergency mode (change to button press not hold)
        if(gamepad2.a && gamepad2.b && gamepad2.x && gamepad2.y)
        {
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
            telemetry.update();
            emergencyModeControls();
        }
    }
    public void emergencyModeControls()
    {
        //Driver A - controls pretty much same as normal manual
        manualOn = true;
        //Driver B - slides, claw etc
    }
    public void convertX(int xCoor)
    {
        //converts the x coordinate on pixel board to inches|
        // set to pose = (amount for one pixel)(xCoor)+(distance from side)
        // make different one for box row
    }
    public void convertY(int yCoor)
    {
        //converts the y into slides value target| value=(amount for one pixel up)(yCoor)+(base amount)
        //different for box row
    }
    public void runPixelPlacing(int [] target1, int [] target2)
    {
        manualOn = false;
        //turns x value of coordinate into x position
        convertX(target1[0]);
        //turns y value of coordinate into value for slides
        convertY(target1[1]);
        //road runner code that goes to the correct firstPos x in arc shape
        //moves slides while going
        //push pixel out
        //repeat once more for second position
        //lower slides

        //put early end / overriding in here
        manualOn = true;
        confirmA = false;
        confirmB = false;
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

        //retrieval
        if(bReleased && manualOn)
        {
            //write retrieval code here also change how first pos and sec pos are set
            //also use different button
        }
        //confirmation
        if(bReleased && (firstPos[1]!=-1 || secPos[1]!=-1) && manualOn)
        {
            bPressed = false;
            bReleased = false;
            confirmB = true;
            firstPos = new int[]{-1,-1};
            secPos = new int[]{-1,-1};
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
            telemetry.addLine(String.format("QUEUE|   "+firstPos[1]+","+firstPos[0]+" then "+secPos[1]+","+secPos[0], null));
        }
        else if(firstPos[0]!=-1)
        {
            telemetry.addLine(String.format("QUEUE|   "+firstPos[1]+","+firstPos[0]));
        }
        else {
            telemetry.addLine(String.format("QUEUE|   "));

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
        else if(colors[0].equals(""))
        {
            telemetry.addLine(String.format("NO PIXELS LOADED"));
        }
        else if(colors[1].equals(""))
        {
            telemetry.addLine(String.format("UNCONFIRMED CHANGES"));
        }
        else
        {
            telemetry.addLine(String.format("PIXELS READY TO GO"));
        }

        telemetry.update();
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
