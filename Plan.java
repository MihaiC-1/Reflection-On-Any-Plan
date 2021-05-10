import java.util.Scanner;

public class Plan {
    private int a = 0;
    private int b = 0;
    private int c = 0;
    private int d = 0;

    public void citire()
    {
        System.out.print("Valoarea lui a : ");
        int a = citire_intreg();
        this.a = a;
        System.out.print("Valoarea lui b : ");
        int b = citire_intreg();
        this.b = b;
        System.out.print("Valoarea lui c : ");
        int c = citire_intreg();
        this.c = c;
        System.out.print("Valoarea lui d : ");
        int d = citire_intreg();
        this.d = d;
    }

    private int citire_intreg()
    {
        Scanner scn = new Scanner(System.in);
        return scn.nextInt();
    }

    public void afisare_ecuatie()
    {
        System.out.println(a+" * x + "+b+" * y + "+c+" * z + "+d+" = 0");
    }

    public int getA() {return this.a;}

    public int getB() {return this.b;}

    public int getC() {return this.c;}

    public int getD() {return this.d;}
}
