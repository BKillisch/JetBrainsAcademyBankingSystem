import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int sum = 0;
        while (scanner.hasNextInt()) {
            int n = scanner.nextInt();
            sum = sum + n;

            if (n == 0) {
                break;
            }
            if (sum >= 1000) {
                sum = sum - 1000;
                break;
            }
        }

        System.out.println(sum);

    }
}