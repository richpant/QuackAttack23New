package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "Drive")
public class Drive extends LinearOpMode {

    ML ml = new ML(this);
    int distance = 0;
    int angle = 0;

    @Override
    public void runOpMode() throws InterruptedException {

        //clawL.setPosition(.5);
        //clawR.setPosition(.5);
        ml.init();
        ml.iTeleOp();

        ML.wrist.setPosition(0.33);//down = .33; up = .44
        ML.clawL.setPosition(.5);
        ML.clawR.setPosition(.5);
        ML.lift.setTargetPosition(0);
        ML.lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ML.elbow.setTargetPosition(0);
        ML.elbow.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addData("Say", "Hello Driver");


        waitForStart();

        while (opModeIsActive()) {

            telemetry.addData("clawL: ", ML.clawL.getPosition());
            telemetry.addData("clawR: ", ML.clawR.getPosition());
            telemetry.addData("wrist: ", ML.wrist.getPosition());

            telemetry.addData("distance: ", distance);
            telemetry.addData("angle: ", angle);
            telemetry.update();

            double y = -(1.4142135624 * gamepad1.left_stick_y) /2;
            double x = -(1.4142135624 * gamepad1.left_stick_x) /2;
            double s = -gamepad1.right_stick_x;

            ML.drive(y,x,s);
            //------------------lift------------------
            if (gamepad1.dpad_up || gamepad2.dpad_up) {
                distance += 10;
            } else if (gamepad1.dpad_down || gamepad2.dpad_down) {
                distance -= 10;
            }
            if (distance < 0) {
                distance = 0;
            }
            if (distance > 2500) {
                distance = 2500;
            }
            ML.lift.setTargetPosition(distance);
            if (ML.lift.getCurrentPosition() < 10 && ML.lift.getTargetPosition() < 10) {
                ML.lift.setPower(0);
            } else {
                ML.lift.setPower(1);
            }
            //------------------elbow------------------
            if (gamepad1.dpad_left || gamepad2.dpad_left) {
                angle += 10;
                ML.clawL.setPosition(.33);
                ML.clawR.setPosition(.67);
            } else if (gamepad1.dpad_right || gamepad2.dpad_right) {
                angle -= 10;
            }
            if (angle < 0) {
                angle = 0;
            }
            if (angle > 2500) {
                angle = 2500;
            }
            ML.elbow.setTargetPosition(angle);
            if (ML.elbow.getCurrentPosition() < 10 && ML.elbow.getTargetPosition() < 10) {
                ML.elbow.setPower(0);
            } else {
                ML.elbow.setPower(.4);
            }
            //------------------claw------------------
            if (gamepad2.left_bumper || gamepad1.left_bumper) {
                ML.clawL.setPosition(.33);//clawL     in
                ML.clawR.setPosition(.67);
            }
            if(gamepad2.right_bumper || gamepad1.right_bumper) {
                ML.clawL.setPosition(.5);
                ML.clawR.setPosition(.5);//clawR     out
            }
            //-----------------presets------------------
            if (gamepad1.x) { // plane position
                angle = 300;
                ML.clawL.setPosition(.33);
                ML.clawR.setPosition(.67);
                distance = 0;
                ML.plane.setPosition(0);
            }
            if (gamepad1.y) { // launch
                ML.plane.setPosition(0.5);
            }
            if (gamepad2.a) { // board
                angle = 890;
                ML.clawL.setPosition(.33);
                ML.clawR.setPosition(.67);
                ML.wrist.setPosition(.43);
            }
            if (gamepad2.x) { // extend
                distance = 1000;
            }
            if (gamepad2.y) {
                distance = 0;
            }
            if (gamepad2.b) { // reset & retract
                angle = 0;
                ML.clawL.setPosition(.33);
                ML.clawR.setPosition(.67);
                distance = 0;
                ML.wrist.setPosition(.44);
            }
            if (gamepad1.left_trigger > 0.6 || gamepad2.left_trigger > 0.6) {
                ML.wrist.setPosition(.44);
            }
            if (gamepad1.right_trigger > 0.6 || gamepad2.right_trigger > 0.6) {
                ML.wrist.setPosition(.33);
            }
        }
    }
}