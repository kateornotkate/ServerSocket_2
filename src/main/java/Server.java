import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int PORT = 8080;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен."); // оповещение о готовности сервера до подключения первого игрока;
            String city = null; // переменная, в которой будут храниться ответы игроков;

            while (true) { // внутри вечного цикла ожидаем подключение;
                try (Socket playerSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(
                             playerSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(
                             new InputStreamReader(playerSocket.getInputStream()))) {
                    if (city == null) {
                        out.println("Игра началась. Введите любой город."); // отправим первому игроку сообщение о начале игры;
                        city = in.readLine(); // считаем первый введённый им город;
                        out.println("Принято!"); // отправим игроку сообщение;
                    } else {
                        out.println("Последний город: " + city);// отправим второму игроку подсказку;
                        String newCity = in.readLine(); // заведем новую переменную, где временно будет храниться ответ второго игрока;
                        if (city.charAt(city.length() - 1) == newCity.charAt(0)) { // если последняя буква старого города равна первой букве нового, то
                            out.println("Принято!"); // отправим второму игроку сообщение;
                            city = newCity; // и сохраним его ответ для следующего раунда;
                        } else { // если буквы городов не совпадают, то
                            out.println("Упс, город должен начинаться на букву - " +
                                    city.toUpperCase().charAt(city.length() - 1)); // то отправим игрокам подсказку с какой буквы должен начинаться новый город;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}