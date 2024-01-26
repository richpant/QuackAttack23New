package org.firstinspires.ftc.teamcode;

//Ctrl + F
/*
   MethodDNE
   MeasureDNE
   ObjDNE
*/
//Note on SERVOS: I had assumed servos operate via 500-2500


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.Timer;
import java.util.TimerTask;

public class ClawConverter //This will F up in jGrasp, needs to be executed in Android Studio
{
    private ClawKin scuff; //= new ClawKin(/*lambda, h, dSum, Math.PI/4, forearm, clawLen*/);//PUT IN PARAMS
    //ALL ATTRIBUTES
  /*
  CONSTANTS:
  RPM OF ELBOWS
  */
    //private double beltRate = 312; //312 RPM,


    //private double servo0ValShift;//...No need! we can set these manually! still, a deadzone
    //elbows,lifts, wrists all ML.
    private final double servoDeadMin = 0; //WAS: 500 //I have these set at pwm values 500,2500
    private final double servoDeadMax = 1; //WAS: 2500
    private final double pwmToRadPhase = 0; //UNNECCECARY //so the values are read 0-2000 [applied before scalar]
    private final double pwmToRadScal = Math.PI*5/3;//WAS: (Math.PI)/1000.0;

    private double radToBeltScal;//MeasureDNE
    private final double beltMin = 0; //min set at zero, no scalar shift
    private double beltMax; //MeasureDNE

    //Buckets **********************************************
    //regular
    private double bPR = 0; //belt prime bucket regular
    private double aPR = 0; // angle (theta) prime bucket regular

    //Board
    private double bPB = 0; //belt prime bucket Board
    private double aPB = 0; // angle (theta) prime bucket Board

    //manual movement
    private double wPM = 0; //wrist prime manual
    private double forceSetWrist = -1; //used to indicate position needed to reach and prevent velocity changes to servos (-1 means its 'off')
    //switches
    private boolean forcePositivity;
    private boolean brdMode = false; //switch for mode. true = brd, false = reg
    // end of Buckets **************************************

    private Timer stagTimer = new Timer(); //STAGGER TIMER

    //get methods (from driver to clawKin)
    public ClawConverter()
    {
        scuff = new ClawKin();
        //empty constructor to keep it okay being empty
    }
    public ClawConverter(double lambda, double h, double dSum, double phi, double forearm, double clawLen)
    {
        scuff = new ClawKin(lambda, h, dSum, Math.PI/4, forearm, clawLen);
    }
    private double getSrvRad(Servo servo) //Converts pwm signal input to radian angle value output
    {
        return (servo.getPosition() + pwmToRadPhase)*pwmToRadScal;
    }
    private double invSrvRad(double inputRads) //Converts RAD signal input to PWM angle value output
    {
        return (inputRads/pwmToRadScal - pwmToRadPhase);
    }


    private double getBelt(DcMotor beltMotor)
    {
        return beltMotor.getCurrentPosition() * radToBeltScal;
    }


    private void driverToKinPos()//currently made for all of 'elbows, wrists, belt(motors)
    {
        //scuff.storeClawPos(getSrvRad(ML.elbow),getSrvRad(ML.wrist),getBelt(ML.lift));//can be returned as position if needed
    }

    private void getPrimeReg(double stickHoriz, double stickVert)//stickHoriz&stickVert will be based upon direction of joystick
    {
        bPR = scuff.bPrimeReg(stickHoriz, stickVert);//veloc needed for belt motor (in terms of rads)
        aPR = scuff.anglePrimeReg(stickHoriz, stickVert);
    }
    private void getPrimeBrd(double stickHoriz, double stickVert)//stickHoriz&stickVert will be based upon direction of joystick
    {
        bPB = scuff.bPrimeBoard(stickHoriz, stickVert);//veloc needed for belt motor (in terms of rads)
        aPB = scuff.anglePrimeBoard(stickHoriz, stickVert);
    }


    private void wristManual (double triggerInput, double scalar)//triggerInput is amount trigger is pressed, scalar is sensitivity amt [currently to be set = 1]
    {
        wPM = triggerInput*scalar;//returns radians
    }

    //ACTUAL PUBLIC------------------V
    public void switchBoardMode ()
    {
        brdMode = !(brdMode);
        //perhaps add any on-switch features here
    }
    //ACTUAL PUBLIC------------------^


    private void forceWrist (double desiredPos) //desired Pos should probably be set in pwm val (500-2500)
    {
        if(forceSetWrist < 0)
        {
            forceSetWrist = desiredPos;
            //double wristAnchor = ML.wrist.getPosition();
            //if(wristAnchor>forceSetWrist)
            {
                forcePositivity = false;
            }
            //else
            {
                forcePositivity = true;
            }
            //ML.wrist.setPosition(forceSetWrist);//methodDNE
        }
    }

    private void checkForceWrist () //desired Pos should probably be set in pwm val (500-2500)
    {
        //double distFrom = ML.wrist.getPosition() - forceSetWrist;//assuming PWM
        double laxBarrier = 0.0025; //was 5 in pwm, I divided
        //true means anchor < ideal posit, false means anchor > ideal pos
        //if((forcePositivity && (distFrom > -1*laxBarrier))||(!(forcePositivity) && (distFrom < laxBarrier)))
        {
            forceSetWrist = -1;
        }
    }


    //ACTUAL PUBLIC------------------V
    public void levelServos() //presumes scuff object already knows about position of elbow
    {
        double bucket;
        if(forceSetWrist < 0)
        {
            if(brdMode)
            {
                bucket = invSrvRad(scuff.wristBoardAngle());
            }
            else
            {
                bucket = invSrvRad(scuff.wristFlatAngle());
            }
            //ML.wrist.setPosition(bucket);//methodDNE
        }
    }
    //ACTUAL PUBLIC------------------^

    private void ultiPrimeMove(double thetaP, double bP, double wMan)//theta prime, belt prime, wrist manual
    {
        checkForceWrist();
        if(forceSetWrist < 0)
        {
            //wrist
            //double idealWristPwm = ML.wrist.getPosition() + invSrvRad(wMan - thetaP);//Currently set for negativeWirsts (same servo initialisation direction) //MethodDNE
            //ML.wrist.setPosition(idealWristPwm);
        }
    /*else
    {
       wrist.setPosition(forceSetWrist);//Is it an issue if I repeatedly call set position to the same spot? //MethodDNE
    }*/
        //elbow
        double idealElbowPwm = ML.elbow.getPosition() + invSrvRad(thetaP);
        ML.elbow.setPosition(idealElbowPwm);
        //belt
        ML.lift.setVelocity(bP/radToBeltScal, AngleUnit.RADIANS);
    }
  /*


  SERVO SPECS
  WILL BE SET BACKWARDS (pos theta = pos)
  RANGE: 500-2500 microsec pwm == (5/3)pi radians == 300 degrees
  'MAX RATE' (per microsec): (1 pwm == pi/1200 radians == 0.15 degrees) per microsecond UNLIKELY
  'MAX RATE' (per milisec): (1000 pwm == pi/1.2 radians == 150 degrees) per milisecond  UNLIKELY
  MAX RATE by voltage 4.8-7.4 (no-load): 40-60 RPM or or (4(pi)/3)-2pi  240-360 degrees/sec


  */

    public void servoStagger(double stagLen, double stiHoriz, double stiVert, double trigInput)//stagLen = length of time for each deriv in milisec, t = time
    {
        servoStagger(stagLen, stiHoriz, stiVert, trigInput, null, null);
    }

    public void servoStagger(double stagLen, double stiHoriz, double stiVert, double trigInput, CuVect record, CuVect accumul)//stagLen = length of time for each deriv in milisec, t = time
    {
        stagTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run()
            {
                timeStagger(stagLen, stiHoriz, stiVert, trigInput);
                if (accumul != null)
                {
                    record.plus(accumul);//yes,this will Alias
                }
            }
        }, 0L, (long) stagLen); //occurs every 20 milisec
    }
    //ACTUAL PUBLIC------------------V
    //SAME THING NO TIMER
    public void timeStagger(double stagLen, double stiHoriz, double stiVert, double trigInput)//stagLen = length of time for each deriv in milisec, t = time
    {
        //obv distance/time = speed, time = stagLen, distance = (getPos+=amnt), speed = desired prime
        //check boundaries-> determine movement based upon functions innately within motors and servos
        double stagSec = stagLen/1000;
        driverToKinPos();
        wristManual(trigInput, 1.0);
        //get all primes
        if(brdMode)//sep results for board mode and non board mode
        {
            getPrimeBrd(stiHoriz, stiVert);
            ultiPrimeMove(aPB*stagSec,bPB,wPM*stagSec);//*stagSec
        }
        else
        {
            getPrimeReg(stiHoriz, stiVert);
            ultiPrimeMove(aPR*stagSec,bPR,wPM*stagSec);//*stagSec for servos only

        }

    }
    //ACTUAL PUBLIC------------------^

    //PRECONDITION: only passed 'primes' that are below max speed of servo (whatever it is)
    //servo.setPosition(/*thprime*/);//FIND FRAMERATE? FIND APPROX FRAMERATE [I am assuming unreliable frame rate],need to utilise TIMER //MethodDNE

    //will force stagger to slow,
    //given that servos are the issue here, if max veloc is hit, perhaps scale motor spd by (desiredServoVeloc/maxServoVeloc)
}
//Attempted Timer*************************************************
    /*
  stagTimer.scheduleAtFixedRate(new TimerTask() {
  @Override
  public void run() {
    // Your database code here
  }
}, 0, 2*60*1000);
  */







//Sorry, Cu is teaching himself to code encoders and such
/*

Encoder OBJNAME = new Encoder(0,1); //defaults to 4x and non inverted
DcMotorEx >>>> setVelocity [or setPower (as Ideal SPEED over TOTAL POSSIBLE

//RUN_USING_ENCODER >>> Motor attempts to run at target veloc
//SHORTCUT:setVelocityPIDFCoefficients(double p, double i, double d, double f) [1.0, 0.0, 0.25, 0.0]
//setVelocity(double 'Cu'Prime, RADIANS)
//CR Servos are moved via pwm for SPEED, [actually, they may be treated as DcMotors?]
//If servos are not declared, as CR, but ARE CR, 0= -1 speed, 0.5 = 0 speed, 1 = 1 speed



Servo Question, to CR or not to CR
CR capabilities: Speed change, NO position reporting (must rely upon integrals and accurate speed measurement)
Non-CR capabilities: position reporting, NO speed changing (must rely on incrementing below max value) //so far to be attempted



FIND MAX SPEED, FIND CORRESPONDING VALUES AND HOW TO CALCULATE

*/



/*
TO DO--------
  > Establish 'Boundaries'
  > Establish 'force set positions
    > Awesome, now need a way of 'turning off' forceSetPosition
*/
