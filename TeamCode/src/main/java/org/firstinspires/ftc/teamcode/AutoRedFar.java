package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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
@Autonomous (name = "Auto Red Far")
public class AutoRedFar extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        final int pi = -800;
        final int a = 1000;
        final int b = 200;
        final int take = 1000;
        int cameraOutcome = 0;
        waitForStart();
        ML.forward(a);
        ML.turn(pi/2);
        //insert camera code HERE
        if(cameraOutcome == 3)
        {
            ML.turn(pi / 2);
        }
        if(cameraOutcome == 2 || cameraOutcome == 3)
        {
            ML.Intake(take);
        }
        if(cameraOutcome == 1 || cameraOutcome == 2)
        {
            ML.turn(pi/2);
        }
        ML.move(-1*a,0);
        if(cameraOutcome == 1)
        {
            ML.Intake(take);
        }
        //The following segment is to be INCLUDED if we start far from the backdrop, but EXCLUDED if we start near it
        ML.forward(-2*a);
        if(cameraOutcome == 1)
        {
            ML.move(b,1);
        }
        if(cameraOutcome == 3)
        {
            ML.move(-b,1);
        }
        /**code for extending arm/opening claw here*/
        //if this is the far code, do nothing. if it is the near code, either parkcenter and do nothing or parkout and move to the edge of the field.
    }
}
