import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (0 < scanner.nextInt()) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}
