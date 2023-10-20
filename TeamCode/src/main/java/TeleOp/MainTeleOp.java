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

    //cursor movment
    private boolean leftPressed;
    private boolean leftReleased;
    private boolean rightPressed;
    private boolean rightReleased;
    private boolean downPressed;
    private boolean downReleased;
    private boolean upPressed;
    private boolean upReleased;

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
        cursorY = 0;
        cursorFlash = 100;
        firstPos = new int[]{-1,-1};
        secPos = new int[]{-1,-1};
        colors = new String[] {"", ""};
        confirmB = false;
        previousOutput = "";
        makeGrid();
        printAll();

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
    @Override
    public void init()
    {
        driverAInitialize();
        driverBInitialize();
        //driver a init
        //color camera init
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
        //if(!emergencyMode)
        //{

        //}

        //Tetris Driver B Updating
        //if(!emergencyMode) {
            getColors();
            printAll();
            updateTetrisThing();
            telemetry.update();
        //}

        /*
        //Pixel Placing Thing
        if(confirmA && confirmB && !emergencyMode)
        {
            runPixelPlacing();
        }

        //EMERGENCY MODE CONTROLS
        //driver A turns on emergency mode with 2 buttons at the same time
        if(emergencyMode)
        {
            emergencyModeControls();
        }*/
    }
    public void emergencyModeControls()
    {
        //Driver A - controls pretty much same as normal manual
        manualOn = true;
        //Driver B - slides, claw etc
    }
    public void runPixelPlacing()
    {
        manualOn = false;
        //turns x value of coordinate into x position
        //turns y value of coordinate into value for slides
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
        if(cursorFlash>50) {
            output[cursorY][cursorX] = "◼"; //⬛ █◼
        }
        else
        {
            output[cursorY][cursorX] = previousOutput;
        }
        if(cursorFlash<1)
        {
            cursorFlash=100;
            previousOutput = output[cursorY][cursorX];
            output[cursorY][cursorX] = previousOutput;
        }
        cursorUpdate();

/*
        //selection
        if(gamepad1.a && firstPos[1]==-1 && manualOn)
        {
            firstPos = new int[] {cursorX, cursorY};
            output[cursorY][cursorX]=colors[0];
        }
        else if(gamepad1.a && secPos[1]==-1)
        {
            secPos = new int[] {cursorX, cursorY};
            output[cursorY][cursorX]=colors[1];
        }

        //confirmation
        if(gamepad1.b && (firstPos[1]!=-1 || secPos[1]!=-1) && manualOn)
        {
            confirmB = true;
            firstPos = new int[]{-1,-1};
            secPos = new int[]{-1,-1};
        }*/
    }
    public void cursorUpdate()
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

        //cursor movement
        if(leftReleased && cursorX>1)
        {
            leftPressed = false;
            leftReleased = false;
            output[cursorY][cursorX] = previousOutput;
            cursorX--;
        }
        else if(rightReleased && cursorX<12)
        {
            rightPressed = false;
            rightReleased = false;
            output[cursorY][cursorX] = previousOutput;
            cursorX++;
        }
        else if(downReleased && cursorY<10)
        {
            downPressed = false;
            downReleased = false;
            output[cursorY][cursorX] = previousOutput;
            cursorY++;
        }
        else if(upReleased && cursorY>=1)
        {
            upPressed = false;
            upReleased = false;
            output[cursorY][cursorX] = previousOutput;
            cursorY--;
        }
    }
    public void printAll()
    {
        //colors avaliable
        telemetry.addLine(String.format("                     1      2"));
        telemetry.addLine(String.format("COLORS - "+colors[0]+"      "+colors[1]));
        telemetry.addLine(String.format(""));

        //prints to here
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
        telemetry.addLine(String.format("mitten"));
/*
        //selections queue
        telemetry.addLine(String.format("", null));
        telemetry.addLine(String.format("          1      2", null));
        telemetry.addLine(String.format("QUEUE - "+firstPos[0]+","+firstPos[1]+" and "+secPos[0]+secPos[1], null));

        //confirmation queue
        if(confirmB)
        {
            telemetry.addLine(String.format("", null));
            telemetry.addLine(String.format("CONFIRMED PLEASE WAIT", null));
        }
        else
        {
            telemetry.addLine(String.format("", null));
            telemetry.addLine(String.format("UNCONFIRMED", null));
        }

*/
        telemetry.update();
    }
    public void makeGrid() {
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
