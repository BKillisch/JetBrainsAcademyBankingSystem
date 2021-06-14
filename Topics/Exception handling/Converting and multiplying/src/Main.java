import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String x = "";
        int num = 1;

        while (num != 0) {
            try {
                x = scanner.next();
                num = Integer.parseInt(x);

                if (num != 0) {
                    System.out.println(num * 10);
                }
            } catch (Exception e) {
                System.out.println("Invalid user input: " + x);
            }
        }

    }
}