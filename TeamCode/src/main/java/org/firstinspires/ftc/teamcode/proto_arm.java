package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.Timer;
import java.util.TimerTask;


@TeleOp(name = "proto arm teleOp")
public class proto_arm extends LinearOpMode {

    private ML ml = new ML(this);
    private ClawConverter armCommand = new ClawConverter();
    private Timer teleArmCheck = new Timer();
    private final CuVect timeIncrem = new CuVect(1.0,1.0);
    private CuVect loopRecord = new CuVect(0.0,0.0); //forces intentional aliasing
    private double anchorSwBoard;


    @Override
    public void runOpMode() throws InterruptedException {

        //clawL.setPosition(.5);
        //clawR.setPosition(.5);
        ml.init();
        ml.iTeleOp();

        telemetry.addData("Say", "Hello Driver");

        waitForStart();
        //(NB: There is a potential non-Timer command available to run)
        //Below is an attempt to initialise a timer task w/Timer within
        armCommand.servoStagger(20, gamepad2.left_stick_x, gamepad2.left_stick_y, (gamepad2.right_trigger - gamepad2.left_trigger), loopRecord, timeIncrem);

        while (opModeIsActive()) {
            double y = -(1.4142135624 * gamepad1.left_stick_y) / 2;//must be for diag
            double x = -(1.4142135624 * gamepad1.left_stick_x) / 2;//diag
            double s = -gamepad1.right_stick_x;


            ML.drive(y, x, s);

            if (gamepad1.left_bumper) {
                ML.intake.setPower(1);
            } else if (gamepad1.right_bumper) {
                ML.intake.setPower(-1);
            } else {
                ML.intake.setPower(0);
            }

            if (gamepad1.a) {
                ML.arm.setPower(1);
            } else if (gamepad1.b) {
                ML.arm.setPower(-1);
            } else {
                ML.arm.setPower(0);
            }

            if (gamepad1.x) {
                ML.rotate.setPower(.53);
            } else if (gamepad1.y) {
                ML.rotate.setPower(.08);
            }

            if (gamepad2.left_bumper)//Note that claw doesn't have a command rn
            {
                armCommand.levelServos();
            }
            if (gamepad2.right_bumper && (loopRecord.getComp(1) - anchorSwBoard > 5))
            {
                armCommand.switchBoardMode();
                anchorSwBoard = loopRecord.getComp(1);
            }

        }
        //Attempt at Timer interrupting while loop

    }


}
