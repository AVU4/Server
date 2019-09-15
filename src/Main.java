
import java.io.*;
import java.net.*;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
public class Main {
    public static Socket socket;
    public static ServerSocket serverSocket;
    public static ArrayList<Socket> set = new ArrayList<>();
    public static ObjectOutputStream outForSignal;
    public static void main(String[] args) {
        CatVector catVector = new CatVector();
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/Cats";
            String login = "postgres";
            String password = "1234alex";
            Connection con = DriverManager.getConnection(url, login, password);
            Commands commands = new Commands(catVector, con);
            try {
//                ServerSocket serverSocket;
                while (true) {
                    try {
                        System.out.println("Введите порт");
                        String port = new Scanner(System.in).nextLine().trim();
                        Integer port1 = Integer.parseInt(port);
                        serverSocket = new ServerSocket(port1, 100, Inet4Address.getByName("localhost"));
                        break;
                    } catch (NullPointerException | IllegalArgumentException | IOException e) {

                        System.out.println("Неверный порт");
                    } catch (NoSuchElementException e) {
                        System.exit(0);
                    }

                }
//                try {
//                    String Path = System.getenv("Start");
//                    File file = new File(Path);
//                    Scanner scanner = new Scanner(file);
//                    while (scanner.hasNext()) {
//                        String str = scanner.nextLine();
//                        String[] strings = str.split(",");
//                        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
//                        // Name,Age,x,y,volume,date;
//                        catVector.add(new Cat(strings[0], Integer.parseInt(strings[1].trim()), Integer.parseInt(strings[2].trim()), Integer.parseInt(strings[3].trim()), Integer.parseInt(strings[4].trim()), dateFormat.parse(strings[5].trim())));
//                    }
//                    scanner.close();
//                } catch (ParseException e) {
//                    System.out.println("Возможно, вы указали неверно дату, а может вообще не указали.");
//                    System.exit(0);
//                } catch (NullPointerException e) {
//                    System.out.println("Не задана переменная окружения.");
//                    System.exit(0);
//                } catch (FileNotFoundException e) {
//                    System.out.println("Не удаётся открыть файл");
//                    System.exit(0);
//                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
//                    System.out.println("Неверно записаны данные в файле. Образец : Имя, Возраст, x, y, объём желудка, дата. A,1,2,3,4,Tue Jun 07 01:23:42 MSK 2019");
//                    System.exit(0);
//                }
                System.out.println("Ожидаю подключения.");
                while (!serverSocket.isClosed()) {
                    Socket socket = serverSocket.accept();
                    System.out.println("Клиент подключился.");
                    Runnable runnable = new ThreadClient(catVector, con, socket);
                    Thread thread = new Thread(runnable);
                    thread.start();
                    Runnable runnable1 = () -> {
                        try {
                            String string = new Scanner(System.in).nextLine();
                        } catch (NoSuchElementException e) {
                            try {
                                File file = new File(System.getenv("Start"));
                                FileWriter fileWriter = new FileWriter(file);
                                for (Cat cat : catVector.Home) {
                                    fileWriter.write(cat.getName() + ',' + cat.getAge() + ',' + cat.getPosition().getX() + ',' + cat.getPosition().getY() + ',' + cat.getVolume() + ','
                                            + cat.getDate() + '\n');
                                }
                                fileWriter.close();
                                System.out.println("Коллекция сохранилась.");
                                Thread.sleep(1000);
                                for (Socket elem : set) {
                                    elem.close();
                                }
                                System.exit(0);
                            } catch (InterruptedException e1) {
                            } catch (FileNotFoundException e1) {
                                System.out.println("Не удаётся открыть файл.");
                                System.exit(0);
                            } catch (IOException e1) {
                            }
                        }
                    };
                    Thread thread1 = new Thread(runnable1);
                    thread1.start();

                }
            } catch (IOException e) {
            }
        }catch (Exception e){
        }

    }
}
