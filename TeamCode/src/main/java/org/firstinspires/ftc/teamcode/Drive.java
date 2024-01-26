package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "Drive")
public class Drive extends LinearOpMode {

    ML ml = new ML(this);
    int distance = 0;

    @Override
    public void runOpMode() throws InterruptedException {

        //clawL.setPosition(.5);
        //clawR.setPosition(.5);
        ml.init();
        ml.iTeleOp();

        ML.elbow.setPosition(.52);//.67

        ML.clawL.setPosition(.5);
        ML.clawR.setPosition(.5);
        ML.rotate.setPower(0.1);
        ML.lift.setTargetPosition(0);
        ML.lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addData("Say", "Hello Driver");


        waitForStart();

        while (opModeIsActive()) {


            telemetry.addData("elbow: ", ML.elbow.getPosition());

            telemetry.addData("clawL: ", ML.clawL.getPosition());
            telemetry.addData("clawR: ", ML.clawR.getPosition());
            telemetry.addData("lift: ", ML.lift.getCurrentPosition());
            telemetry.addData("distance: ", distance);
            //telemetry.addData("rotate: ", ML.rotate.getPosition());
            telemetry.update();

            double y = -(1.4142135624 * gamepad1.left_stick_y) /2;
            double x = -(1.4142135624 * gamepad1.left_stick_x) /2;
            double s = -gamepad1.right_stick_x;

            ML.drive(y,x,s);

            ML.lift.setPower(.6);

            if (gamepad1.dpad_up || gamepad2.dpad_up) {
                distance += 10;

                ML.clawL.setPosition(.33);
                ML.clawR.setPosition(.67);
            } else if (gamepad1.dpad_down || gamepad2.dpad_down) {
                distance -= 10;

            } else {
                distance = ML.lift.getCurrentPosition();

            }
            if (distance < 0) {
                distance = 0;
            }
            if (distance > 2500) {
                distance = 2500;
            }
            ML.lift.setTargetPosition(distance);

            if (gamepad1.a) {
                ML.arm.setPower(1);
            } else if(gamepad1.b) {
                ML.arm.setPower(-1);
            } else {
                ML.arm.setPower(0);
            }

            if (gamepad1.x) {
                ML.rotate.setPower(0.1);//horizontal
            }
            else if (gamepad1.y) {
                ML.rotate.setPower(0.44);//vertical
            }

            if (gamepad2.left_bumper) {
                ML.clawL.setPosition(.33);//clawL     in
                ML.clawR.setPosition(.67);
            }
            if(gamepad2.right_bumper) {
                ML.clawL.setPosition(.5);
                ML.clawR.setPosition(.5);//clawR     out     i like me
            }

            if (gamepad2.x) {
                ML.elbow.setPosition(.25);
            }
            if (gamepad2.y) {
                ML.elbow.setPosition(.52);
            }
            if (gamepad2.b) {
                ML.elbow.setPosition(25);
            }

        /*
        if (gamepad1.b) {
            clawL.setPosition(.45);
            clawR.setPosition(.55);
        } else {
            clawL.setPosition(.5);
            clawR.setPosition(.5);
        }

        if (gamepad1.x) {
            elbow.setPower(.1);
            wrist.setPower(-.1);
        } else if (gamepad1.y) {
            elbow.setPower(-.1);
            wrist.setPower(.1);
        } else if (gamepad1.dpad_left) {
            elbow.setPower(.1);
            wrist.setPower(0);
        } else if (gamepad1.dpad_right) {
            elbow.setPower(-.1);
            wrist.setPower(0);
        } else if (gamepad1.left_bumper) {
            elbow.setPower(0);
            wrist.setPower(.1);
        } else if (gamepad1.right_bumper) {
            elbow.setPower(0);
            wrist.setPower(-.1);
        }else {
            elbow.setPower(0);
            wrist.setPower(0);
        }
         */
        }
    }
}