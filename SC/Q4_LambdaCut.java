import java.util.*;

public class Q4_LambdaCut {

    private static double clamp(double x) {
        if (x < 0.0) return 0.0;
        if (x > 1.0) return 1.0;
        return x;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter size n of fuzzy set: ");
        int n = sc.nextInt();
        double[] A = new double[n];
        System.out.println("Enter membership values (n values in [0,1]):");
        for (int i = 0; i < n; i++) A[i] = clamp(sc.nextDouble());
        System.out.print("Enter lambda (in [0,1]): ");
        double lambda = clamp(sc.nextDouble());

        List<Integer> weak = new ArrayList<>(); // alpha-cut: >= lambda
        List<Integer> strong = new ArrayList<>(); // strong alpha-cut: > lambda
        for (int i = 0; i < n; i++) {
            if (A[i] >= lambda) weak.add(i);
            if (A[i] > lambda) strong.add(i);
        }

        System.out.println("Weak lambda-cut indices (>=): " + weak);
        System.out.println("Strong lambda-cut indices (>): " + strong);
        sc.close();
    }
}


