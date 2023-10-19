package TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class MainTeleOp extends OpMode {
    private static String[][] output;
    private int cursorX;
    private int cursorY;
    private static int cursorFlash;
    private int[] firstPos;
    private int[] secPos;
    private boolean confirmed;
    private String[] colors;

    public void driverBInitialize()
    {
        output = new String[12][13]; //15
        cursorX = 1;
        cursorY = 10;
        cursorFlash = 100;
        firstPos = new int[]{-1,-1};
        secPos = new int[]{-1,-1};
        colors = new String[] {"", ""};
        confirmed = false;
        makeGrid();
        printAll();
    }
    @Override
    public void init()
    {
        driverBInitialize();
        //driver a init
        //color camera init
    }

    @Override
    public void start()
    {
        //everything goes in here that isn't looping, won't be much
    }
    @Override
    public void loop()
    {
        //PID


        //Normal Driver Controls


        //Tetris Driver Trash Updating
        printAll();
        getColors();
        updateTetrisThing();
        telemetry.update();
    }

    public void getColors()
    {
        colors = new String[] {"G", "P"}; //⬜ 🟪 🟩 🟨
    }
    public void updateTetrisThing()
    {
        //cursor movement
        if(gamepad1.dpad_left && cursorX>0)
        {
            cursorX--;
        }
        else if(gamepad1.dpad_right && cursorX<13)
        {
            cursorX++;
        }
        else if(gamepad1.dpad_down && cursorY<11)
        {
            cursorY++;
        }
        else if(gamepad1.dpad_up && cursorY>=0)
        {
            cursorY--;
        }

        //cursor flashing
        cursorFlash--;
        if(cursorFlash>50) {
            output[cursorY][cursorX] = "█"; //⬛ █◼
        }
        if(cursorFlash<1)
        {
            cursorFlash=100;
        }

        //selection
        if(gamepad1.a && firstPos[1]==-1)
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
        if(gamepad1.b && (firstPos[1]!=-1 || secPos[1]!=-1))
        {
            confirmed = true;
            firstPos = new int[]{-1,-1};
            secPos = new int[]{-1,-1};
        }
    }
    public void printAll()
    {
        //colors avaliable
        telemetry.addLine(String.format("          1      2", null));
        telemetry.addLine(String.format("COLORS - "+colors[0]+"      "+colors[1], null));
        telemetry.addLine(String.format("", null));

        //2d array output
        String rowOut = "";
        for(int r=0; r<output.length; r++)
        {
            rowOut = "";
            for(int c=0; c<output[1].length; c++) {
                rowOut += output[r][c];
            }
            telemetry.addLine(String.format("", rowOut));
            //telemetry.addData("", null);
        }

        //selections queue
        telemetry.addLine(String.format("", null));
        telemetry.addLine(String.format("          1      2", null));
        telemetry.addLine(String.format("QUEUE - "+firstPos[0]+","+firstPos[1]+" and "+secPos[0]+secPos[1], null));

        //confirmation queue
        if(confirmed)
        {
            telemetry.addLine(String.format("", null));
            telemetry.addLine(String.format("CONFIRMED PLEASE WAIT", null));
        }
        else
        {
            telemetry.addLine(String.format("", null));
            telemetry.addLine(String.format("UNCONFIRMED", null));
        }


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
                        output[r][c] = "_.";
                    }
                }
            }
        }
    }
}
