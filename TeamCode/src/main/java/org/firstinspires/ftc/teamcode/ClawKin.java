package org.firstinspires.ftc.teamcode;

//For first edition of joints

public class ClawKin //edition 1.0 //EDIT ALL
{
    private double[][] arrDH = new double[4][4];
    private CuVect posFrameRobot = new CuVect(); //Stores CuVect Val
    private CuVect primeFrameRobot = new CuVect();
    //TRUE CONSTANTS VVV
    private double lambda; //x-dist of belt-0 from centre of Chassis
    private double h; //z-dist of belt-0 from centre of Chassis
    private double dSum; //y-offset of arm from centre of Chassis (d2+d3)
    private double phi = (Math.PI / 4); //angle of arm belt pi/4
    private double forearm; //length of forearm link, 'r2'
    private double clawLen; //length of wrist link, 'r3'

    //Initial Changing val vals
    private double initElbow = 0;
    private double initWrist = 0;
    private double initBelt = 0;


    //CHANGING VALS
    private double elbow = initElbow; //'theta2' mathematic value, NOT TRUE ENCODER VALUE
    private double wrist = initWrist; //'theta3' mathematic value, NOT TRUE ENCODER VALUE
    private double beltLen = initBelt; //'b' methematic Linear value, NOT TRUE ENCODER VALUE

    //TRU CONSTANT FOR MOTION AMOUNT (in args of method Now)
    //private double xPrime = 1;
    //private double zPrime = 1;
    // RESULTING MOTION
    //private double bPrime = 0;
    //private double thPrime = 0; //poorly named, but refers to elbow' and -1*wrist'

    //SHORTCUT QUICK VALS,
    private double yBotSum = -1*dSum;
    private double sinPhi = Math.sin(phi);
    private double cosPhi = Math.cos(phi);
    private double sinPhiBoard = Math.sin(phi - (Math.PI/3.0));
    private double cosPhiBoard = Math.cos(phi - (Math.PI/3.0));



    //Constructor that takes NO INPUT, yes this will require filling out the top TRUE CONSTANTS
    public ClawKin ()
    {
        //constants will be established for init values,
        //assuming claw vector of (0,0,0,1) already multiplied to matrix [hence why not only changes vector]
        double initialX = clawLen*Math.cos(initElbow + initWrist) + forearm*Math.cos(initElbow) + lambda + initBelt*Math.cos(phi);
        double initialZ = clawLen*Math.sin(initElbow + initWrist) + forearm*Math.sin(initElbow) + h + initBelt*Math.sin(phi);
        posFrameRobot.setComp(initialX, yBotSum, initialZ, 1);
    }

    public ClawKin (double a, double b, double c, double d, double e, double f) //bad formal params, I know, see below though
    {
        //constants will be established for init values,
        lambda = a; //x-dist of belt-0 from centre of Chassis
        h = b; //z-dist of belt-0 from centre of Chassis
        dSum = c; //y-offset of arm from centre of Chassis (d2+d3)
        phi = (Math.PI / 4); //angle of arm belt pi/4               EDIT, USE =d
        forearm = e; //length of forearm link, 'r2'
        clawLen = f; //length of wrist link, 'r3
        //assuming claw vector of (0,0,0,1) already multiplied to matrix [hence why not only changes vector]
        double initialX = clawLen*Math.cos(initElbow + initWrist) + forearm*Math.cos(initElbow) + lambda + initBelt*Math.cos(phi);
        double initialZ = clawLen*Math.sin(initElbow + initWrist) + forearm*Math.sin(initElbow) + h + initBelt*Math.sin(phi);
        posFrameRobot.setComp(initialX, yBotSum, initialZ, 1);
    }


    //NEEDED TO STORE POS ROBO FRAME
    public void storeClawPos(double inElbow, double inWrist, double inB)
    {
        double xPos = clawLen*Math.cos(inElbow + inWrist) + forearm*Math.cos(inElbow) + lambda + inB*Math.cos(phi);
        double zPos = clawLen*Math.sin(inElbow + inWrist) + forearm*Math.sin(inElbow) + h + inB*Math.sin(phi);
        posFrameRobot.setComp(xPos, yBotSum, zPos, 1);
    }

    //RETURN POS ROBO FRAME FUNCTIONS
    public double getX ()
    {
        return posFrameRobot.getComp(1);
    }
    public double getY ()
    {
        return posFrameRobot.getComp(2);
    }
    public double getZ ()
    {
        return posFrameRobot.getComp(3);
    }

    public void debugClaw()
    {
        System.out.print("Claw X: " + getX());
        System.out.print("Claw Y: " + getY());
        System.out.print("Claw Z: " + getZ());
    }

    //Jacobian time, (technically inverse) >>> J^-1 * [x' z'] = [b' theta']
    public double bPrimeReg(double xPrime, double zPrime)
    {
        double detJ = 1 / (Math.cos(elbow - phi));
        double xPTerm = Math.cos(elbow) * xPrime * detJ;
        double zPTerm = Math.sin(elbow) * zPrime * detJ;
        double bPrime = xPTerm + zPTerm;
        return bPrime;
    }

    public double anglePrimeReg(double xPrime, double zPrime) //NOW THE RETURN FUNCTION DIRECTLY
    {
        double detJ = 1 / (forearm * Math.cos(elbow - phi));
        double xPTerm = -1 * sinPhi * xPrime * detJ;
        double zPTerm = cosPhi * zPrime * detJ;
        double thPrime = xPTerm + zPTerm;
        return thPrime;

    }

    public double bPrimeBoard(double xPrime, double zPrime)
    {
        double detJ = 1 / (Math.cos(elbow - phi));
        double xPTerm = Math.cos(elbow - (Math.PI/3.0)) * xPrime * detJ;
        double zPTerm = Math.sin(elbow - (Math.PI/3.0)) * zPrime * detJ;
        double bPrime = xPTerm + zPTerm;
        return bPrime;
    }

    public double anglePrimeBoard(double xPrime, double zPrime)
    {
        double detJ = 1 / (forearm * Math.cos(elbow - phi));
        double xPTerm = -1 * sinPhiBoard * xPrime * detJ;
        double zPTerm = cosPhiBoard * zPrime * detJ;
        double thPrime = xPTerm + zPTerm;
        return thPrime;
    }
    //modify prime info CuVect
   /*
   public double getThetaPrime()
   {
     return thPrime;
   }

   public double getBPrime()
   {
     return bPrime;
   } */ //SEE ABOVE RETURN FUNCTIONS
    //set command
    public double wristFlatAngle() //for ground
    {
        double idealWrist = -1 * elbow;
        return idealWrist;
    }

    public double wristBoardAngle() //for Board
    {
        double idealWrist = -1 * elbow + (Math.PI/3);
        return idealWrist;
    }
}