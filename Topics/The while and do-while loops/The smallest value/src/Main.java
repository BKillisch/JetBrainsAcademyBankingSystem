import java.util.Scanner;

class Main {
    static long factorial(long n) {
        return n <= 1 ? 1 : n * factorial(n - 1);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long m = scanner.nextLong();
        long n = 0;

        while (factorial(n) <= m) {
            n++;
        }
        System.out.println(n);
    }

}