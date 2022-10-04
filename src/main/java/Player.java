import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Player {

    private static final int PORT = 8080;
    private static final String HOST = "127.0.0.1";

    public static void main(String[] args) {
        try (Socket playerSocket = new Socket(HOST, PORT);
             PrintWriter out = new PrintWriter(
                     playerSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(playerSocket.getInputStream()))) {

            System.out.println(in.readLine()); // игроку выводится в консоль сообщение сервера о состоянии игры;

            Scanner scanner = new Scanner(System.in);
            out.println(scanner.nextLine().toUpperCase()); // игрок вводит название города в консоль;

            System.out.println(in.readLine()); // игрок получает сообщение или подсказку в ответ;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}