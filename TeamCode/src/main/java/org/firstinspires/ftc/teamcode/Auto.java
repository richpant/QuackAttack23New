package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

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
@Autonomous (name = "Auto")
public class Auto extends LinearOpMode {
    private ML ml = new ML(this);
    @Override
    public void runOpMode() throws InterruptedException {

        ml.init();
        ml.iauto();

        waitForStart();

        ML.forward(2000); //2000
        sleep(1000);
        //ML.turn(1);
        ML.clawL.setPosition(.33);
        ML.clawR.setPosition(.67);
        ML.forward(-100);
        sleep(1000);
        //ML.Intake(-1);
        //sleep(1000);
        //ML.Intake(0);
        //sleep(200);
        //ML.forward(500);
    }

}
