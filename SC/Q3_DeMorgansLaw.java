import java.util.*;

public class Q3_DeMorgansLaw {

    private static double clamp(double x) {
        if (x < 0.0) return 0.0;
        if (x > 1.0) return 1.0;
        return x;
    }

    private static double[] read(Scanner sc, int n, String name) {
        double[] v = new double[n];
        System.out.println("Enter membership values for set " + name + " (" + n + "):");
        for (int i = 0; i < n; i++) v[i] = clamp(sc.nextDouble());
        return v;
    }

    private static double[] comp(double[] a) {
        double[] r = new double[a.length];
        for (int i = 0; i < a.length; i++) r[i] = 1 - a[i];
        return r;
    }

    private static double[] uni(double[] a, double[] b) {
        double[] r = new double[a.length];
        for (int i = 0; i < a.length; i++) r[i] = Math.max(a[i], b[i]);
        return r;
    }

    private static double[] inter(double[] a, double[] b) {
        double[] r = new double[a.length];
        for (int i = 0; i < a.length; i++) r[i] = Math.min(a[i], b[i]);
        return r;
    }

    private static boolean approxEq(double[] x, double[] y, double eps) {
        for (int i = 0; i < x.length; i++) if (Math.abs(x[i] - y[i]) > eps) return false;
        return true;
    }

    private static void print(String label, double[] a) {
        System.out.print(label + ": ");
        for (int i = 0; i < a.length; i++) {
            System.out.print(String.format(Locale.US, "%.3f", a[i]));
            if (i < a.length - 1) System.out.print(" ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter universe size n: ");
        int n = sc.nextInt();
        double[] A = read(sc, n, "A");
        double[] B = read(sc, n, "B");

        double[] lhs1 = comp(uni(A, B));
        double[] rhs1 = inter(comp(A), comp(B));
        double[] lhs2 = comp(inter(A, B));
        double[] rhs2 = uni(comp(A), comp(B));

        print("~(A ∪ B)", lhs1);
        print("(~A) ∩ (~B)", rhs1);
        System.out.println("Law 1 holds (eps=1e-6): " + approxEq(lhs1, rhs1, 1e-6));

        print("~(A ∩ B)", lhs2);
        print("(~A) ∪ (~B)", rhs2);
        System.out.println("Law 2 holds (eps=1e-6): " + approxEq(lhs2, rhs2, 1e-6));
        sc.close();
    }
}


