import java.util.*;

public class Q2_MaxMin_MaxProduct_Composition {

    private static double clamp(double x) {
        if (x < 0.0) return 0.0;
        if (x > 1.0) return 1.0;
        return x;
    }

    private static void printVector(String label, double[] v) {
        System.out.print(label + ": ");
        for (int i = 0; i < v.length; i++) {
            System.out.print(String.format(Locale.US, "%.3f", v[i]));
            if (i < v.length - 1) System.out.print(" ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter size m of fuzzy set A: ");
        int m = sc.nextInt();
        double[] A = new double[m];
        System.out.println("Enter membership values of A (m values in [0,1]):");
        for (int i = 0; i < m; i++) A[i] = clamp(sc.nextDouble());

        System.out.print("Enter size n of output universe (columns of relation R): ");
        int n = sc.nextInt();
        double[][] R = new double[m][n];
        System.out.println("Enter matrix R of size m x n (row-wise), values in [0,1]:");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                R[i][j] = clamp(sc.nextDouble());
            }
        }

        double[] maxMin = new double[n];
        double[] maxProd = new double[n];
        for (int j = 0; j < n; j++) {
            double mm = 0.0;
            double mp = 0.0;
            for (int i = 0; i < m; i++) {
                mm = Math.max(mm, Math.min(A[i], R[i][j]));
                mp = Math.max(mp, A[i] * R[i][j]);
            }
            maxMin[j] = mm;
            maxProd[j] = mp;
        }

        printVector("Max-Min composition A ∘ R", maxMin);
        printVector("Max-Product composition A ∘ R", maxProd);
        sc.close();
    }
}


