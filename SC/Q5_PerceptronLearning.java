import java.util.*;

public class Q5_PerceptronLearning {

    private static int step(double x) { return x >= 0 ? 1 : 0; }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of samples m: ");
        int m = sc.nextInt();
        System.out.print("Enter number of features n: ");
        int n = sc.nextInt();
        double[][] X = new double[m][n];
        int[] y = new int[m];
        System.out.println("Enter dataset row-wise: n features then target (0/1)");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) X[i][j] = sc.nextDouble();
            y[i] = sc.nextInt();
        }
        System.out.print("Enter learning rate eta (e.g., 0.1): ");
        double eta = sc.nextDouble();
        System.out.print("Enter epochs: ");
        int epochs = sc.nextInt();

        double[] w = new double[n];
        double b = 0.0;
        Arrays.fill(w, 0.0);

        for (int epoch = 0; epoch < epochs; epoch++) {
            int errors = 0;
            for (int i = 0; i < m; i++) {
                double z = b;
                for (int j = 0; j < n; j++) z += w[j] * X[i][j];
                int yHat = step(z);
                int err = y[i] - yHat;
                if (err != 0) errors++;
                // Update rule
                for (int j = 0; j < n; j++) w[j] += eta * err * X[i][j];
                b += eta * err;
            }
            System.out.println("Epoch " + (epoch + 1) + ": errors=" + errors);
            if (errors == 0) break;
        }

        System.out.println("Final weights: " + Arrays.toString(w) + ", bias=" + b);
        System.out.println("Predictions:");
        for (int i = 0; i < m; i++) {
            double z = b;
            for (int j = 0; j < n; j++) z += w[j] * X[i][j];
            int yHat = step(z);
            System.out.println("Sample " + i + ": y=" + y[i] + ", y_hat=" + yHat);
        }
        sc.close();
    }
}


