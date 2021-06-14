import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double m = scanner.nextInt();
        double p = scanner.nextInt();
        double k = scanner.nextInt();
        int years = 0;

        while (k > m) {
            m = m + m * p / 100;
            years++;
        }

        System.out.print(years);
    }
}