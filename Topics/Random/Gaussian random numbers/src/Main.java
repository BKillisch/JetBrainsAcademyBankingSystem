import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        long k = scanner.nextLong() - 1;
        int n = scanner.nextInt();
        double m = scanner.nextDouble();
        Random random = new Random();

        outter: do {
            k = k + 1;

            random.setSeed(k);

            for (int i = 0; i < n; i++) {
                if (random.nextGaussian() > m) {
                    continue outter;
                }
            }

            break;
        } while (true);

        System.out.println(k);
    }
}