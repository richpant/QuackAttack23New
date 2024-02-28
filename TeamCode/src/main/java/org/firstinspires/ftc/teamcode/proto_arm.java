package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.Timer;
import java.util.TimerTask;


@TeleOp(name = "proto arm teleOp")
public class proto_arm extends LinearOpMode {

    private ML ml = new ML(this);
    private ClawConverter armCommand = new ClawConverter(0,0,0,Math.PI/4,0.157,0.1143);
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
        ML.elbow.setTargetPosition(0);//.67
        //ML.wrist.setPosition(.55);//.49
        ML.clawL.setPosition(.5);
        ML.clawR.setPosition(.5);

        telemetry.addData("Say", "Hello Driver");

        waitForStart();
        //(NB: There is a potential non-Timer command available to run)
        //Below is an attempt to initialise a timer task w/Timer within
        //armCommand.servoStagger(20, gamepad2.left_stick_x, gamepad2.left_stick_y, (gamepad2.right_trigger - gamepad2.left_trigger), loopRecord, timeIncrem);

        //No Timer version
        teleArmCheck.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run()
            {
                armCommand.timeStagger(20, gamepad2.left_stick_x, gamepad2.left_stick_y, (gamepad2.right_trigger - gamepad2.left_trigger));
                loopRecord.plus(timeIncrem);//yes,this will Alias

            }
        }, 0L, 20); //occurs every 20 milisec


        while (opModeIsActive()) {

            telemetry.addData("elbow: ", ML.elbow.getCurrentPosition());
            //telemetry.addData("wrist: ", ML.wrist.getPosition());
            telemetry.addData("clawL: ", ML.clawL.getPosition());
            telemetry.addData("clawR: ", ML.clawR.getPosition());
            //telemetry.addData("rotate: ", ML.rotate.getPosition());
            telemetry.update();



            double y = -(1.4142135624 * gamepad1.left_stick_y) / 2;//must be for diag
            double x = -(1.4142135624 * gamepad1.left_stick_x) / 2;//diag
            double s = -gamepad1.right_stick_x;


            ML.drive(y, x, s);

            if (gamepad1.left_bumper) {
                //ML.intake.setPower(1);
            } else if (gamepad1.right_bumper) {
                //ML.intake.setPower(-1);
            } else {
                //ML.intake.setPower(0);
            }



            // ###### levels wrist (assumes no protruding claw)
            if (gamepad2.left_bumper)
            {
                armCommand.levelServos();
            }

            // ####### allows for switching board mode
            if (gamepad2.right_bumper && (loopRecord.getComp(1) - anchorSwBoard > 5))
            {
                armCommand.switchBoardMode();
                anchorSwBoard = loopRecord.getComp(1);
            }

            //######### claw #############
            if (gamepad2.a) {
                ML.clawL.setPosition(.33);//clawL     in
                ML.clawR.setPosition(.67);
            }
            if(gamepad2.b) {
                ML.clawL.setPosition(.5);
                ML.clawR.setPosition(.5);//clawR     out
            }


        }
        //Attempt at Timer interrupting while loop

    }


}
