package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.concurrent.TimeUnit;

/**
 * Outline of proposed autonomous redesign
 *
 * Preloads: Purple pixel in intake/outtake area, yellow pixel in claw (or perhaps miniclaw)
 * Additional note: all directions will be coded in BLUE. For RED, take the opposite of all thetas.
 * "a units" shall refer to travelling 1 full tile.
 *
 * //Scoring 20 purple pixel points
 * First, use the camera to determine where the TSE is (left 1, mid 2, right 3)
 * Go forwards a units
 * If 3, turn -pi/2
 * If 2 || 3, Outtake purple pixel
 * If 1 || 2, turn -pi/2
 * If 1, go backwards a units and outtake purple pixel
 * If 2 || 3 Go backwards a units
 *
 * //Move to other side, if necessary
 * //NOTICE: The following segment will ONLY be used when we are in the far position.
 * If in the near position, skip down to the next segment.
 * Go backwards 2a units
 *
 * //Scoring the 25 yellow pixel points
 * Go backwards b units (less than a) to approach backdrop
 * If 1, strafe right
 * If 3, strafe left
 * Release the Yellow Pixel
 *
 * //Parking NOTE: 3 OPTIONS, ParkLeft, ParkCenter, ParkRight
 * If ParkCenter, stop
 * If ParkLeft, strafe right
 * If ParkRight, strafe left
 * If !ParkCenter, go backwards a-b units
 *
 */
@Autonomous (name = "Auto Red Near")
public class AutoRedNear extends LinearOpMode {
    private HuskyLens huskyLens;
    private ML ml = new ML(this);
    @Override
    public void runOpMode() throws InterruptedException {
        huskyLens = hardwareMap.get(HuskyLens.class, "huskylens");
        ml.init();
        ml.iauto();
        final int pi = -2;
        final int a = 1000;
        final int b = 200;
        final int take = 1000;
        int cameraOutcome = 0;
        Deadline rateLimit = new Deadline(1, TimeUnit.SECONDS); //from huskylens example
        rateLimit.expire();
        if (!huskyLens.knock()) {
            telemetry.addData(">>", "Problem communicating with " + huskyLens.getDeviceName());
        } else {
            telemetry.addData(">>", "Press start to continue");
        }//makes sure the huskylens is talking to the control hub
        huskyLens.selectAlgorithm(HuskyLens.Algorithm.COLOR_RECOGNITION);// can change to other algorithms


        // Wait for driver to press start
        telemetry.addData("Camera preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {

            if (!rateLimit.hasExpired()) {
                continue;
            }
            rateLimit.reset();// from huskylens
            HuskyLens.Block[] blocks = huskyLens.blocks();
            telemetry.addData("Block count", blocks.length);
            for (int i = 0; i < blocks.length; i++) {
                telemetry.addData("Block", blocks[i].toString());// this gives you the data
                telemetry.addData("location?", blocks[i].x);// this gives you just x
                //TODO ensure your x values of the husky lens are appropriate to the desired areas
                //----------------------------1----------------------------\\
                if (blocks[i].x < 100) {
                    telemetry.addLine("Through careful calculations, we have concluded Area 1");
                    cameraOutcome = 1;
                }
                if (blocks[i].x > 100 && blocks[i].x < 200) {
                    telemetry.addLine("Through careful calculations, we have concluded Area 2");
                    cameraOutcome = 2;
                }
                if (blocks[i].x > 210) {
                    telemetry.addLine("Through careful calculations, we have concluded Area 3");
                    cameraOutcome = 3;
                }
            }

            waitForStart();
            ML.forward(a);
            ML.turn(pi / 2);
            //insert camera code HERE
            if (cameraOutcome == 3) {
                ML.turn(pi / 2);
            }
            if (cameraOutcome == 2 || cameraOutcome == 3) {
                //ML.Intake(take);
            }
            if (cameraOutcome == 1 || cameraOutcome == 2) {
                ML.turn(pi / 2);
            }
            ML.move(-1 * a, 0);
            if (cameraOutcome == 1) {
                //ML.Intake(take);
            }
            //The following segment is to be INCLUDED if we start far from the backdrop, but EXCLUDED if we start near it
            /*ML.forward(-2*a);*/
            if (cameraOutcome == 1) {
                ML.move(b, 1);
            }
            if (cameraOutcome == 3) {
                ML.move(-b, 1);
            }
            /** code for extending arm/opening claw here */
            //if this is the far code, do nothing. if it is the near code, parkout and move to the edge of the field.
            ML.turn(pi / 2);
            ML.move(a, 0);
        }
    }
}