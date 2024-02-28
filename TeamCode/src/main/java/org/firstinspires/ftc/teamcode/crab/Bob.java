package org.firstinspires.ftc.teamcode.crab;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="BOB", group="Linear OpMode")
//@Disabled
public class Bob extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor RFMotor = null;
    private DcMotor LFMotor = null;
    private DcMotor RBMotor = null;
    private DcMotor LBMotor = null;
    private Servo box = null;
    private DcMotor lift = null;
    private DcMotor intake = null;

    @Override
    public void runOpMode() {
        Init();
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            Loop();
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }

    void Init() {
        RFMotor = hardwareMap.get(DcMotor.class, "RFMotor");
        LFMotor  = hardwareMap.get(DcMotor.class, "LFMotor");
        RBMotor = hardwareMap.get(DcMotor.class, "RBMotor");
        LBMotor  = hardwareMap.get(DcMotor.class, "LBMotor");
        lift = hardwareMap.get(DcMotor.class, "lift");
        box = hardwareMap.get(Servo.class, "box");
        intake = hardwareMap.get(DcMotor.class,"intake");

        RFMotor.setDirection(DcMotor.Direction.REVERSE);
        LFMotor.setDirection(DcMotor.Direction.REVERSE);
        RBMotor.setDirection(DcMotor.Direction.FORWARD);
        LBMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    void Loop() {
        double max;
        double axial   = gamepad1.left_stick_x;
        double lateral =  -gamepad1.right_stick_x;
        double yaw     =  -gamepad1.right_stick_y;
        double RFPower = axial - lateral - yaw;
        double LFPower  = axial + lateral + yaw;
        double RBPower  = axial + lateral - yaw;
        double LBPower   = axial - lateral + yaw;

        max = Math.max(Math.abs(LFPower), Math.abs(RFPower));
        max = Math.max(max, Math.abs(LBPower));
        max = Math.max(max, Math.abs(RBPower));

        if (max > 1.0) {
            RFPower /= max;
            LFPower  /= max;
            RBPower  /= max;
            LBPower   /= max;
        }

        buttonPowerMotor(gamepad2.a, lift, 0.8f, 0f);
        buttonPowerMotor(gamepad2.b, lift, -0.8f, 0);
        buttonPowerServo(gamepad2.x, box, 0.3f, 0.5f);
        buttonPowerServo(gamepad2.y, box, 0.7f, 0.5f);
        buttonPowerMotor(gamepad2.dpad_up, intake, 1f, 0f);

        RFMotor.setPower(RFPower);
        LFMotor.setPower(LFPower);
        RBMotor.setPower(RBPower);
        LBMotor.setPower(LBPower);
    }

    void buttonPowerMotor(boolean bool, DcMotor motor, float valueOn, float valueOff) {
        if(bool) {
            motor.setPower(valueOn);
        }
        else {
            motor.setPower(valueOff);
        }
    }

    void buttonPowerServo(boolean bool, Servo motor, float valueOn, float valueOff) {
        if(bool) {
            motor.setPosition(valueOn);
        }
        else {
            motor.setPosition(valueOff);
        }
    }
}


/*import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.List;


@TeleOp
public class Bob extends OpMode {

    DcMotor RFMotor;
    DcMotor LFMotor;
    DcMotor RBMotor;
    DcMotor LBMotor;
    Servo box;
    DcMotor lift;
    DcMotor intake;

    @Override
    public void init() {
        RFMotor = hardwareMap.get(DcMotor.class, "RFMotor");
        LFMotor = hardwareMap.get(DcMotor.class, "LFMotor");
        RBMotor = hardwareMap.get(DcMotor.class, "RBMotor");
        LBMotor = hardwareMap.get(DcMotor.class, "LBMotor");
        lift = hardwareMap.get(DcMotor.class, "lift");
        box = hardwareMap.get(Servo.class, "box");
        intake = hardwareMap.get(DcMotor.class, "intake");

        RFMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        LFMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        RBMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        LBMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void loop() {
        moveDriveTrain();
    }

    public void moveDriveTrain() {
        double vertical;
        double horizontal;
        double pivot;

        vertical = -gamepad1.left_stick_y;
        horizontal = gamepad1.left_stick_x;
        pivot = gamepad1.right_stick_x;

        RFMotor.setPower(pivot + (-vertical + horizontal));
        LFMotor.setPower(pivot + (-vertical - horizontal));
        RBMotor.setPower(pivot + (-vertical - horizontal));
        LBMotor.setPower(pivot + (-vertical + horizontal));

        buttonPowerMotor(gamepad2.a, lift, 0.8f, 0f);
        buttonPowerMotor(gamepad2.b, lift, -0.8f, 0);
        buttonPowerServo(gamepad2.x, box, 0.3f, 0f);
        buttonPowerMotor(gamepad2.dpad_up, intake, 1f, 0f);
    }

    public void buttonPowerMotor(boolean bool, DcMotor motor, float valueOn, float valueOff) {
        if(bool) {
            motor.setPower(valueOn);
        }
        else {
            motor.setPower(valueOff);
        }
    }

    public void buttonPowerServo(boolean bool, Servo motor, float valueOn, float valueOff) {
        if(bool) {
            motor.setPosition(valueOn);
        }
        else {
            motor.setPosition(valueOff);
        }
    }
}*/

    /*public void RecordRobot() {

        boolean recording = false;
        double time = 0;

        List<Float> leftStickYR = new ArrayList<Float>();
        float leftStickY = gamepad1.left_stick_y;
        List<Float> leftStickXR = new ArrayList<Float>();
        float leftStickX = gamepad1.left_stick_x;

        if(gamepad1.start) {
            recording = true;
        }

        if(gamepad1.back) {
            recording = false;
        }

        while(recording) {
            telemetry.addData("Time", time);
            time++;
            leftStickYR.add(gamepad1.left_stick_y);
            leftStickXR.add(gamepad1.left_stick_x);
        }
    }*/

    /*public void SaveFile(ArrayList<float> list)
    {
        PrintWriter out = new PrintWriter(new FileWriter("OutFile.txt"));

        for(int i = 0; i < list.size(); i++)
        {

        }
    }*/
