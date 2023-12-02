package org.firstinspires.ftc.teamcode;

//IMPORTANT!!!! COPPER IS BUILDING VECTOR CLASS

//For efficiency, I'll prolly individually construct 1-4 dimensions, perhaps forcing 0 in unused dimensions
public class CuVect
{
    private double x1;
    private double x2;
    private double x3;
    private double x4;

    //0-vector if no input
    public CuVect ()
    {
        x1 = 0.0;
        x2 = 0.0;
        x3 = 0.0;
        x4 = 0.0;
    }
    //2 dimensional vector constructor
    public CuVect (double c1, double c2)
    {
        x1 = c1;
        x2 = c2;
        x3 = 0.0;
        x4 = 0.0;
    }
    //3 dimensional vector constructor
    public CuVect (double c1, double c2, double c3)
    {
        x1 = c1;
        x2 = c2;
        x3 = c3;
        x4 = 0.0;
    }
    //4 dimensional vector constructor
    public CuVect (double c1, double c2, double c3, double c4)
    {
        x1 = c1;
        x2 = c2;
        x3 = c3;
        x4 = c4;
    }
    //vector to vector constructor i.e passing a vector
    //public
    //euclidian 2 norm, i.e. magnitude or ||v||
    public double norm ()
    {
        double normVal = Math.sqrt(x1*x1 + x2*x2 + x3*x3 + x4*x4);
        return normVal;
    }
    // individual component get (f1,f2,f3,f4) Args>> getComp(component index)
    public double getComp (int f)
    {
        double retComp;
        if (f==1)
            retComp = x1;
        else if (f==2)
            retComp = x2;
        else if (f==3)
            retComp = x3;
        else if (f==4)
            retComp = x4;
            // Later, I may learn how to throw errors, but for mow, if f is not 1,2,3, or 4, return NaN
        else retComp = 1/0;
        return retComp;
    }
    // individual component set (f1,f2,f3,f4) Args>> setComp(component index, input value)
    public void setComp (int f, double inpVal)
    {
        if (f==1)
            x1 = inpVal;
        else if (f==2)
            x2 = inpVal;
        else if (f==3)
            x3 = inpVal;
        else if (f==4)
            x4 = inpVal;
            // Later, I may learn how to throw errors, but for mow, if f is not 1,2,3, or 4, print setComp invalid f
        else System.out.println("setComp(int component index, double setting value), the 1st arg can be val 1,2,3, or 4, your 1st arg: " + f);
    }

    //overloading for 4 double inputs to set all components
    public void setComp (double set1, double set2, double set3, double set4)
    {
        x1 = set1;
        x2 = set2;
        x3 = set3;
        x4 = set4;
    }


    //STATIC RETURN METHODS IN GENERAL -->
    // dot prod of two vectors Args>>> dotProd(VECTOR2,VECTOR2) results in double
    public static double dotProd (CuVect v1, CuVect v2)
    {
        double scalarProd = (v1.x1 * v2.x1 + v1.x2 * v2.x2 + v1.x3 * v2.x3 + v1.x4 * v2.x4);
        return scalarProd;
    }
    public static double dotProd (double a, double b, double c, double d, double f, double g, double h, double l) //8 double inputs [vectors (a,b,c,d) dot (f,g,h,l)]
    {
        double scalarProd = (a * f + b * g + c * h + d * l);
        return scalarProd;
    }



    // cross prod vector>> for now, only returns vector and hence must be instantiated ==> CuVect NAME = CuVect.crossProd(Vector1,Vector2)
    public static CuVect crossProd (CuVect a, CuVect b)
    {
        // return CuVect
        //make crossproduct return a CuVect i.e.(a,b,c)X(f,g,h) = (bh-cg,cf-ah,ag-bf) [This can be proven with ijk matrik determinant]
        double i = a.x2*b.x3 - a.x3*b.x2;
        double j = a.x3*b.x1 - a.x1*b.x3;
        double k = a.x1*b.x2 - a.x2*b.x1;
        CuVect obj = new CuVect(i,j,k);
        return obj;
    }
    public static CuVect crossProd (double a, double b, double c, double f, double g, double h) //(a,b,c)X(f,g,h) = (bh-cg,cf-ah,ag-bf)
    {
        // return CuVect [see above version, this method takes six double inputs]
        double i = b*h-c*g;
        double j = c*f-a*h;
        double k = a*g-b*f;
        CuVect obj = new CuVect(i,j,k);
        return obj;
    }



    //NON-STATIC VOID OPS IN GENERAL-->  in general -,+ [currently non-static]
    public void minus (CuVect other)
    {
        x1 -= other.x1;
        x2 -= other.x2;
        x3 -= other.x3;
        x4 -= other.x4;
    }

    public void plus (CuVect other)
    {
        x1 += other.x1;
        x2 += other.x2;
        x3 += other.x3;
        x4 += other.x4;
    }








}



