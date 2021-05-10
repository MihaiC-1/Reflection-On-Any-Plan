import java.util.Scanner;

public class Punct {

    private int x = 0;
    private int y = 0;
    private int z = 0;

    public void citire()
    {
        System.out.print("Cordonata x : ");
        int x = citire_intreg();
        setX(x);
        System.out.print("Cordonata y : ");
        int y = citire_intreg();
        setY(y);
        System.out.print("Cordonata z : ");
        int z = citire_intreg();
        setZ(z);
    }

    public void def(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    private int citire_intreg()
    {
        Scanner scn = new Scanner(System.in);
        return scn.nextInt();
    }

    public int getX() {return this.x;}

    int getY() {return this.y;}

    int getZ() {return this.z;}

    void setX(int x)
    {
        this.x = x;
    }

    void setY(int y)
    {
        this.y = y;
    }

    void setZ(int z)
    {
        this.z = z;
    }
}
