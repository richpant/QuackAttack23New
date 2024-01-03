package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "Testing Code")
public class Testing_Code extends LinearOpMode {

    ML ml = new ML(this);

    @Override
    public void runOpMode() throws InterruptedException {

        //clawL.setPosition(.5);
        //clawR.setPosition(.5);
        ml.init();
        ml.iTeleOp();

        telemetry.addData("Say", "Hello Driver");

        waitForStart();

        while (opModeIsActive()) {
            double y = -(1.4142135624 * gamepad1.left_stick_y) /2;
            double x = -(1.4142135624 * gamepad1.left_stick_x) /2;
            double s = -gamepad1.right_stick_x;

            ML.drive(y,x,s);

            if (gamepad1.left_bumper) {
                ML.intake.setPower(1);
            } else if (gamepad1.right_bumper) {
                ML.intake.setPower(-1);
            } else {
                ML.intake.setPower(0);
            }
            /*
            if (gamepad1.dpad_up) {
                liftL.setPower(.6);
                liftR.setPower(.6);
            } else if (gamepad1.dpad_down) {
                liftL.setPower(-.6);
                liftR.setPower(-.6);
            } else {
            liftL.setPower(0);
            liftR.setPower(0);
            }
            */
            if (gamepad1.a) {
                ML.arm.setPower(1);
            } else if(gamepad1.b) {
                ML.arm.setPower(-1);
            } else {
                ML.arm.setPower(0);
            }

            if (gamepad1.x) {
                ML.rotate.setPower(.53);
            }
            else if (gamepad1.y) {
                ML.rotate.setPower(.08);
            }

            if (gamepad2.a) {
                ML.clawL.setPosition(.7);
            } else {
                ML.clawL.setPosition(.3);
            }
            if(gamepad2.b) {
                ML.clawR.setPosition(.7);
            } else {
                ML.clawR.setPosition(.3);
            }

            if (gamepad2.x) {
                ML.arm1.setPosition(.7);
            } else {
                ML.arm1.setPosition(.3);
            }
            if (gamepad2.y) {
                ML.arm2.setPosition(.7);
            } else {
                ML.arm2.setPosition(.3);
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
            arm1.setPower(.1);
            arm2.setPower(-.1);
        } else if (gamepad1.y) {
            arm1.setPower(-.1);
            arm2.setPower(.1);
        } else if (gamepad1.dpad_left) {
            arm1.setPower(.1);
            arm2.setPower(0);
        } else if (gamepad1.dpad_right) {
            arm1.setPower(-.1);
            arm2.setPower(0);
        } else if (gamepad1.left_bumper) {
            arm1.setPower(0);
            arm2.setPower(.1);
        } else if (gamepad1.right_bumper) {
            arm1.setPower(0);
            arm2.setPower(-.1);
        }else {
            arm1.setPower(0);
            arm2.setPower(0);
        }
         */
        }
    }
}