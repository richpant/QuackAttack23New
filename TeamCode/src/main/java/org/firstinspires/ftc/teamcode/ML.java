package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class ML
{

    private LinearOpMode myOpMode = null;
    public static DcMotorEx rightFront;
    public static DcMotorEx rightRear;
    public static DcMotorEx leftFront;
    public static DcMotorEx leftRear;
    public static DcMotorEx intake;
    public static DcMotorEx lift;
    public static DcMotorEx arm;
    public static CRServo rotate;
    public static Servo clawL;
    public static Servo clawR;
    public static Servo elbow;
    public static Servo wrist;
    public static double pi = Math.PI;
    public static double π = pi;
    // π = alt + 227


    public ML (LinearOpMode opmode) {
        myOpMode = opmode;
    }
    public void init(){
        leftFront = myOpMode.hardwareMap.get(DcMotorEx.class,"leftFront");
        leftRear = myOpMode.hardwareMap.get(DcMotorEx.class,"leftRear");
        rightFront = myOpMode.hardwareMap.get(DcMotorEx.class,"rightFront");
        rightRear = myOpMode.hardwareMap.get(DcMotorEx.class,"rightRear");
        intake = myOpMode.hardwareMap.get(DcMotorEx.class,"intake");
        lift = myOpMode.hardwareMap.get(DcMotorEx.class,"lift");

        arm = myOpMode.hardwareMap.get(DcMotorEx.class,"arm");
        rotate = myOpMode.hardwareMap.get(CRServo.class,"rotate");
        elbow = myOpMode.hardwareMap.get(Servo.class,"elbow");
        wrist = myOpMode.hardwareMap.get(Servo.class,"wrist");
        clawL = myOpMode.hardwareMap.get(Servo.class,"clawL");
        clawR = myOpMode.hardwareMap.get(Servo.class,"clawR");

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightRear.setDirection(DcMotorSimple.Direction.FORWARD);
        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        arm.setDirection(DcMotorSimple.Direction.FORWARD);
        lift.setDirection(DcMotorSimple.Direction.REVERSE);

    }
    public void iauto() {
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void iTeleOp() {
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public static void drive(double y, double x, double s) {
        leftFront.setPower(y - x - s);
        leftRear.setPower(y + x - s);
        rightFront.setPower(y + x + s);
        rightRear.setPower(y - x + s);
    }
    
    public static void forward(int v)
    {
        move(v,0);
    }
    public static void strafe(int v)
    {
        move(v,1);
    }
    public static void turn(double n)
    {
        move((int)(400*n), 2); //Widdershins
    }
    public static void move(int d, int mode) {
        leftFront.setPower(.4);
        leftRear.setPower(.4);
        rightFront.setPower(.4);
        rightRear.setPower(.4);
        /** mode is to determine if the robot is
         * driving straight, strafing, or turning
         * using 0, 1, or 2 respectively for each mode
         */
        if (mode == 0) {
            leftFront.setTargetPosition(d);
            leftRear.setTargetPosition(d);
            rightFront.setTargetPosition(d);
            rightRear.setTargetPosition(d);
        }
        if (mode == 1) {
            leftFront.setTargetPosition(-d);
            leftRear.setTargetPosition(d);
            rightFront.setTargetPosition(d);
            rightRear.setTargetPosition(-d);
        }
        if (mode == 2) {
            leftFront.setTargetPosition(-d);
            leftRear.setTargetPosition(-d);
            rightFront.setTargetPosition(d);
            rightRear.setTargetPosition(d);
        }
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while ( leftRear.getCurrentPosition() != d) {
        }
        leftFront.setPower(0);
        leftRear.setPower(0);
        rightFront.setPower(0);
        rightRear.setPower(0);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public static void Intake(int p) {
        intake.setPower(p);
    }
}