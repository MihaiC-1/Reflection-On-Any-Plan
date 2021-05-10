import java.util.Scanner;
import java.util.*;
import java.lang.Math;

public class Main {
    public static int citire_intreg()
    {
        // Functia returneaza un numar intreg citit de la tastatura.
        Scanner scn = new Scanner(System.in);
        return scn.nextInt();
    }

    public static double element_inmultire(double[][] a,double[][] b, int linie, int coloana)
    {
        // Functia inmulteste o linie dintr-o matrice cu o coloana din alta matrice si returneaza rezultatul.
        double sum = 0;
        for (int k = 0; k < 4; k++)
        {
            sum += a[linie][k]*b[k][coloana];
        }
        return sum;
    }

    public static double[][] matrici_inmultire(double[][] a, double[][] b)
    {
        // Functia face inmultirea a doua matrici de acelasi numar de coloane si linii, mai exact 4.

        // Folosit pentru inmultirea matricilor la determinarea matricei de reflexie (stim ca toate sunt de dimensiunea
        // 4 linii si 4 coloana.
        double[][] rez = new double[4][4];

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                rez[i][j] = element_inmultire(a, b, i, j);
            }
        }

        return rez;
    }

    public static double[][] inmultire_negala(double[][] a, double[][] b, int n)
    {
        // Functia face inmultirea matricei de reflexie si a matricei poliedrului care este de dimensiuni diferite,
        // adica este in functie de cate varfuri are se modifica numarul de coloane, dar numarul de linii este
        // tot 4.

        double[][] rez = new double[4][n];

        for (int i = 0; i < 4; i++)
        {                                        // parcurg fiecare linie din matricea de reflexie si o inmultesc
            for (int j = 0; j < n; j++)          // cu fiecare coloana din matricea poliedrului
            {
                rez[i][j] = element_inmultire(a, b, i, j);
            }
        }

        return rez;
    }

    public static void main(String[] args)
    {

        // Citirea cordonatelor varfurilor poliedrului si crearea matricei poliedrului.

        int n;
        System.out.print("Cate varfuri va afea poliedrul : ");
        n = citire_intreg();

        double[][] matrix_forma = new double[4][n];

        for (int k = 0; k < n; k++) {
            System.out.println("Coordonatele varfului "+(k+1)+" :");
            Punct p = new Punct();
            p.citire();
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == 0 && k == j)
                        matrix_forma[i][j] = p.getX();
                    else if (i == 1 && k == j)
                        matrix_forma[i][j] = p.getY();
                    else if (i == 2 && k == j)
                        matrix_forma[i][j] = p.getZ();
                    else if (i == 3)
                        matrix_forma[i][j] = 1;
                }
            }
        }

        // Citirea valorilor pentru crearea ecuatiei planului fata de care se face refexia.

        System.out.println("\n Scrieti valorile coeficientilor din ecuatia planului : ");
        System.out.println("\t Ecuatia planului este de forma : a*x + b*y + c*z + d = 0\n");
        Plan pl = new Plan();
        pl.citire();

        // Citirea unui punct P(x0,y0,z0) care sa verifice si ecuatia planului.

        System.out.println("\n Scrieti coordonatele unui punct din plan : ");
        System.out.println("\nEcuatia planului este : ");
        pl.afisare_ecuatie();
        Punct P = new Punct();
        while (true)
        {
            P.citire();
            if (pl.getA()*P.getX()+pl.getB()*P.getY()+pl.getC()*P.getZ()+pl.getD() != 0)
            {
                System.out.println("\n\tPunctul nu verifica ecuatia planului! Mai incercati.");
            }
            else
            {
                break;
            }
        }

        // Crearea matricilor translatiilor T(x0,y0,z0) si T(-x0,-y0,-z0).

        double[][] matrix_T = new double[4][4];
        double[][] matrix_T_neg = new double[4][4];
        int ok = 0;

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if (i == j && i != 2)
                {
                    matrix_T[i][j] = 1;
                    matrix_T_neg[i][j] = 1;
                }
                else if (j == 3)
                {
                    if (i == 0)
                    {
                        matrix_T[i][j] = P.getX();
                        matrix_T_neg[i][j] = -P.getX();
                    }
                    else if (i == 1)
                    {
                        matrix_T[i][j] = P.getY();
                        matrix_T_neg[i][j] = -P.getY();
                    }
                    else if (i == 2)
                    {
                        matrix_T[i][j] = P.getZ();
                        matrix_T_neg[i][j] = -P.getZ();
                    }
                }
                else
                {
                    matrix_T[i][j] = 0;
                    matrix_T_neg[i][j] = 0;
                }
            }
        }

        // Determinarea verctorului normal la plan N(a,b,c) => n = n(a/sqrt(a^2+b^2+c^2), b/sqrt(a^2+b^2+c^2),
        // c/sqrt(a^2+b^2+c^2)).

        int rad = pl.getA()*pl.getA()+pl.getB()*pl.getB()+pl.getC()*pl.getC();
        double nx = pl.getA()/Math.sqrt(rad);
        double ny = pl.getB()/Math.sqrt(rad);
        double nz = pl.getC()/Math.sqrt(rad);

        // Crearea matricei pentru rotatie ROT_x(theta_x) si ROT_x(-theta_x).

        double[][] matrix_rot_x = new double[4][4];
        double[][] matrix_rot_x_neg = new double[4][4];

        double numitor = Math.sqrt(ny * ny + nz * nz);

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if (i == 0 && j == 0)
                {
                    matrix_rot_x[i][j] = 1;
                    matrix_rot_x_neg[i][j] = 1;
                }
                else if (i == 3 && j == 3)
                {
                    matrix_rot_x[i][j] = 1;
                    matrix_rot_x_neg[i][j] = 1;
                }
                else if (i == j)
                {
                    matrix_rot_x[i][j] = nz / numitor;
                    matrix_rot_x_neg[i][j] = nz / numitor;
                }
                else if (i == 2 && j == 1)
                {
                    matrix_rot_x[i][j] = -ny / numitor;
                    matrix_rot_x_neg[i][j] = ny / numitor;
                }
                else if (i == 1 && j == 2)
                {
                    matrix_rot_x[i][j] = ny / numitor;
                    matrix_rot_x_neg[i][j] = -ny / numitor;
                }
                else
                {
                    matrix_rot_x[i][j] = 0;
                    matrix_rot_x_neg[i][j] = 0;
                }
            }
        }

        // Crearea matricei Rxy.

        double[][] matrix_R_xy = new double[4][4];

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if (i == j)
                {
                    if (i == 2)
                        matrix_R_xy[i][j] = -1;
                    else
                        matrix_R_xy[i][j] = 1;
                }
                else
                    matrix_R_xy[i][j] = 0;
            }
        }

        // Crearea matricelor pentru rotatie ROT_y(theta_y) si ROT_y(-theta_y).

        double[][] matrix_rot_y = new double[4][4];
        double[][] matrix_rot_y_neg = new double[4][4];

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if (i == j)
                {
                    if (i == 0 || i == 2)
                    {
                        matrix_rot_y[i][j] = numitor;
                        matrix_rot_y_neg[i][j] = numitor;
                    }
                    else
                    {
                        matrix_rot_y[i][j] = 1;
                        matrix_rot_y_neg[i][j] = 1;
                    }
                }
                else if (i == 2 && j == 0)
                {
                    matrix_rot_y[i][j] = -nx;
                    matrix_rot_y_neg[i][j] = nx;
                }
                else if (i == 0 && j == 2)
                {
                    matrix_rot_y[i][j] = nx;
                    matrix_rot_y_neg[i][j] = -nx;
                }
                else
                {
                    matrix_rot_y[i][j] = 0;
                    matrix_rot_y_neg[i][j] = 0;
                }
            }
        }

        // Inmultirea matricilor si crearea matricei de reflexie al planului oarecare.

        double[][] R = new double[4][4];

        R = matrici_inmultire(matrix_T, matrix_rot_x);
        R = matrici_inmultire(R, matrix_rot_y);
        R = matrici_inmultire(R, matrix_R_xy);
        R = matrici_inmultire(R, matrix_rot_y_neg);
        R = matrici_inmultire(R, matrix_rot_x_neg);
        R = matrici_inmultire(R, matrix_T_neg);

        // Determinarea matricei imaginii reflexiei fata de planul dat al poliedrului

        double[][] prim = new double[4][n];

        prim = inmultire_negala(R, matrix_forma, n);

        // Afisarea matricei poliedrului initial, matricei de reflexie si a matricei poliedrului reflectat.

        System.out.println("\n Matricea poliedrului : \n");

        for (int i = 0; i < 4; i++)
        {
            for(int j = 0; j < n; j++)
            {
                System.out.print("  "+matrix_forma[i][j]);
            }
            System.out.println();
        }

        System.out.println("Matricea de reflexie este : ");

        for (int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                System.out.print("  "+R[i][j]);
            }
            System.out.println();
        }

        System.out.println("\n Matricea poliedrului reflectat : \n");

        for (int i = 0; i < 4; i++)
        {
            for(int j = 0; j < n; j++)
            {
                System.out.print("  "+prim[i][j]);
            }
            System.out.println();
        }

        // Afisarea coordonatelor punctelor refelctate.

        System.out.println("Coordonatele punctelor reflectate : ");

        for (int j = 0; j < n; j++)
        {
            System.out.print("\t Varful  "+(j+1)+"' : ");
            for(int i = 0; i < 4; i++)
            {
                if (i != 3)
                {
                    if (i == 0)
                        System.out.print(" ( "+prim[i][j]+",");
                    else if (i == 1)
                        System.out.print(" "+prim[i][j]+",");
                    else
                        System.out.print(" "+prim[i][j]+" ) \n");
                }
            }
        }
    }
}
