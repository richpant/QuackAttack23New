package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.concurrent.TimeUnit;
@Autonomous (name = "AndrewAutoSimple")

public class AutoScanSimple extends LinearOpMode {
    private HuskyLens huskyLens;
    private ML ml = new ML(this);

    @Override
    public void runOpMode() throws InterruptedException {
        huskyLens = hardwareMap.get(HuskyLens.class, "huskylens");
        ml.init();
        ml.iauto();
        final int turns = -1;
        final int a = 700;
        final int b = 200;
        final int take = 1000;
        int cameraOutcome = 0;
        Deadline rateLimit = new Deadline(1, TimeUnit.SECONDS); //from huskylens example
        rateLimit.expire();
        if (!huskyLens.knock()) {
            telemetry.addData(">>", "Problem communicating with " + huskyLens.getDeviceName());
        }
        else {
            telemetry.addData(">>", "Press start to continue");
        }//makes sure the huskylens is talking to the control hub
        huskyLens.selectAlgorithm(HuskyLens.Algorithm.COLOR_RECOGNITION);// can change to other algorithms


        // Wait for driver to press start
        telemetry.addData("Camera preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        waitForStart();

        //while (opModeIsActive()) {

        ML.lift.setTargetPosition(200);

            /*if (!rateLimit.hasExpired()) {
                continue;
            }*/
        rateLimit.reset();// from huskylens
        HuskyLens.Block[] blocks = huskyLens.blocks();
        telemetry.addData("Block count", blocks.length);
        for (int i = 0; i < blocks.length; i++) {
            telemetry.addData("Block", blocks[i].toString());// this gives you the data
            telemetry.addData("location?", blocks[i].x);// this gives you just x
            //TODO ensure your x values of the husky lens are appropriate to the desired areas
            //----------------------------1----------------------------\\
            if (blocks[i].x < 50) {
                telemetry.addLine("Through careful calculations, we have concluded Area 1");
                cameraOutcome = 1;
            }
            else if (blocks[i].x > 50 && blocks[i].x < 200) {
                telemetry.addLine("Through careful calculations, we have concluded Area 2");
                cameraOutcome = 2;
            }
            else if (blocks[i].x > 210) {
                telemetry.addLine("Through careful calculations, we have concluded Area 3");
                cameraOutcome = 3;
            }
            else {
                telemetry.addLine("GUESSING Area 1.");
                cameraOutcome = 1;
            }
        }

        waitForStart();
        ML.forward(a);
        //ML.turn(turns);
        telemetry.update();
        //insert camera code HERE
        telemetry.addData("String",5);
        telemetry.update();
        //insert camera code HERE
        if (cameraOutcome == 3) {
            ML.turn(turns);
            ML.forward(200);
        }
        if(cameraOutcome == 2)
        {
            ML.forward(300);
        }
        if (cameraOutcome == 2 || cameraOutcome == 3) {
            //ML.Intake(take);
            ML.clawL.setPosition(.5);
            //ML.forward(-100);
        }
        if (cameraOutcome == 1 /*|| cameraOutcome == 2*/) {
            ML.turn(-turns);
            ML.forward(200);
        }
        ML.move(a, 0);
        if (cameraOutcome == 1) {
            //ML.Intake(take);
            ML.clawL.setPosition(.5);
            //ml.forward(-100);
        }
        ML.forward(-100);
        //The following segment is to be INCLUDED if we start far from the backdrop, but EXCLUDED if we start near it
        /*ML.forward(a);
        if (cameraOutcome == 1) {
            ML.move(b, 1);
        }
        if (cameraOutcome == 3) {
            ML.move(-b, 1);
        }*/
        /* code for extending arm/opening claw here */
        //if this is the far code, do nothing. if it is the near code, either parkcenter and do nothing or parkout and move to the edge of the field.
        //}
    }
}
