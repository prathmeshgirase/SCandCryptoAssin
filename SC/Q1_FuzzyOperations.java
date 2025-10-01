import java.util.*;

public class Q1_FuzzyOperations {

    private static double clamp(double x) {
        if (x < 0.0) return 0.0;
        if (x > 1.0) return 1.0;
        return x;
    }

    private static double[] readFuzzySet(Scanner sc, int n, String name) {
        double[] set = new double[n];
        System.out.println("Enter membership values of set " + name + " (" + n + " values in [0,1]):");
        for (int i = 0; i < n; i++) {
            set[i] = clamp(sc.nextDouble());
        }
        return set;
    }

    private static void print(String label, double[] a) {
        System.out.print(label + ": ");
        for (int i = 0; i < a.length; i++) {
            System.out.print(String.format(Locale.US, "%.3f", a[i]));
            if (i < a.length - 1) System.out.print(" ");
        }
        System.out.println();
    }

    private static double[] complement(double[] a) {
        double[] c = new double[a.length];
        for (int i = 0; i < a.length; i++) c[i] = 1.0 - a[i];
        return c;
    }

    private static double[] unionMax(double[] a, double[] b) {
        double[] r = new double[a.length];
        for (int i = 0; i < a.length; i++) r[i] = Math.max(a[i], b[i]);
        return r;
    }

    private static double[] intersectionMin(double[] a, double[] b) {
        double[] r = new double[a.length];
        for (int i = 0; i < a.length; i++) r[i] = Math.min(a[i], b[i]);
        return r;
    }

    // Algebraic sum: a ⊕ b = a + b - a*b
    private static double[] algebraicSum(double[] a, double[] b) {
        double[] r = new double[a.length];
        for (int i = 0; i < a.length; i++) r[i] = clamp(a[i] + b[i] - a[i] * b[i]);
        return r;
    }

    // Algebraic product: a ⊗ b = a*b
    private static double[] algebraicProduct(double[] a, double[] b) {
        double[] r = new double[a.length];
        for (int i = 0; i < a.length; i++) r[i] = a[i] * b[i];
        return r;
    }

    // Cartesian product: matrix R(x,y) = min/max/product as chosen
    private static double[][] cartesianProductMin(double[] a, double[] b) {
        double[][] r = new double[a.length][b.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                r[i][j] = Math.min(a[i], b[j]);
            }
        }
        return r;
    }

    private static void printMatrix(String label, double[][] m) {
        System.out.println(label + ":");
        for (double[] row : m) {
            for (int j = 0; j < row.length; j++) {
                System.out.print(String.format(Locale.US, "%.3f", row[j]));
                if (j < row.length - 1) System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter universe cardinality (number of elements) n: ");
        int n = sc.nextInt();
        double[] A = readFuzzySet(sc, n, "A");
        double[] B = readFuzzySet(sc, n, "B");

        print("A", A);
        print("B", B);

        print("Complement(A)", complement(A));
        print("Complement(B)", complement(B));
        print("Union (max)", unionMax(A, B));
        print("Intersection (min)", intersectionMin(A, B));
        print("Algebraic Sum", algebraicSum(A, B));
        print("Algebraic Product", algebraicProduct(A, B));

        System.out.print("Enter m for second universe size for Cartesian product (B sized by m): ");
        int m = sc.nextInt();
        System.out.println("Enter membership values of set C for Cartesian product (" + m + "):");
        double[] C = new double[m];
        for (int i = 0; i < m; i++) C[i] = clamp(sc.nextDouble());
        printMatrix("Cartesian product (min) A x C", cartesianProductMin(A, C));
        sc.close();
    }
}


