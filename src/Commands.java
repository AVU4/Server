
import org.postgresql.util.PSQLException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;

public class Commands {
    private CatVector catVector;
    Coding coding = new Coding();
    Connection connection;
    public Commands(CatVector catVector, Connection connection){
        this.catVector = catVector;
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public String doSave()  {
        try {
            FileWriter fileWriter = new FileWriter(System.getenv("Start"));
            for (Cat elem : catVector.Home) {
                String string = elem.getName() + "," + elem.getAge() + "," + elem.getVolume() + "," + elem.getPosition().getX() + ',' + elem.getPosition().getY() + "," + elem.getDate();
                fileWriter.write(string);
                fileWriter.write('\n');
            }
            fileWriter.close();
            return "Коллекция сохранена.";
        }catch (IOException e){
            return ("Проблема с файлом.");
        }

    }

    public String doCommand(Request obj) throws  Exception{
        // Нужен импорт
        switch (obj.getCommand()) {
            case "save":
                try {
                    File file = new File(System.getenv("Start"));
                    FileWriter fileWriter = new FileWriter(file);
                    for (Cat cat : catVector.Home) {
                        fileWriter.write(cat.getName() + ',' + cat.getAge() + ',' + cat.getPosition().getX() + ',' + cat.getPosition().getY() + ',' + cat.getVolume() + ','
                                + cat.getDate() + '\n');
                    }
                    fileWriter.close();
                    return "Коллекция сохранилась.";

                }catch (FileNotFoundException e) {
                    return ("Не удаётся открыть файл");
                }catch (IOException e){
                    return ("Файл не открыть.");
                }
            case "add":
                int x = catVector.Home.size();
                    PreparedStatement stmt = connection.prepareStatement("INSERT INTO HomeOfCats (login, name, age, volume, x, y, date) VALUES ( ?, ?, ?, ?, ?, ?, ?)");
                    stmt.setString(1,obj.getLogin());
                    stmt.setString(2,obj.getName());
                    stmt.setInt(3,obj.getAge());
                    stmt.setInt(4, obj.getVolume());
                    stmt.setInt(5,obj.getPosition().getX());
                    stmt.setInt(6,obj.getPosition().getY());
                    stmt.setString(7,obj.getDate().toString());
                    int i = stmt.executeUpdate();
                    stmt.close();
                    if (i != 0) {
                        return "Добавление прошло успешно.";
                    }else {
                        return "Что-то пошло не так";
                    }

            case "remove":
                try {
                    // Удаление без введения даты
                    catVector.remove(new Cat(obj.getName(), obj.getAge(), obj.getVolume(), obj.getPosition().getX(), obj.getPosition().getY()));
                    PreparedStatement statement = connection.prepareStatement("DELETE FROM homeofcats WHERE login=? and name=? and age=? and volume=? and x=? and y=?");
                    statement.setString(1, obj.getLogin());
                    statement.setString(2, obj.getName());
                    statement.setString(3, String.valueOf(obj.getAge()));
                    statement.setString(4, String.valueOf(obj.getVolume()));
                    statement.setString(5, String.valueOf(obj.getPosition().getX()));
                    statement.setString(6, String.valueOf(obj.getPosition().getY()));
//                statement.setString(7,obj.getDate().toString());
                    int a = statement.executeUpdate();
                    statement.close();
                    if (a != 0) {
                        return "Удаление прошло успешно";
                    } else {
                        return "Что-то пошло не так";
                    }
                }catch (PSQLException e){
                    return "Такого кота нет, либо у него другой владелец.";
                }

            case "remove_greater":
                try {
                    x = catVector.Home.size();
                    catVector.remove_greater(new Cat(obj.getName(), obj.getAge(), obj.getVolume(), obj.getPosition().getX(), obj.getPosition().getY()));
                    PreparedStatement statement1 = connection.prepareStatement("DELETE FROM homeofcats WHERE login=? and age>=?");
                    statement1.setString(1, obj.getLogin());
                    statement1.setInt(2, obj.getAge());
                    int y = statement1.executeUpdate();
                    statement1.close();
                    if (y != 0) {
                        return "Удаление прошло успешно.";
                    } else {
                        return "Что-то пошло не так.";
                    }
                }catch (PSQLException e){
                    return "Таких котов нет, либо они принадлежат другому владельцу.";
                }
            case "help":
                return "Команда add позволяет пользователю добавить элемент в коллекцию.\nФормат ввода : add{ name : String, age : int, x : int, y : int Stomach{ volume : int}}\nКоманда remove позволяет пользователю удалить элемент из коллекции.\nФормат ввода : remove{ name : String, age : int, x : int, y : int, Stomach{ volume : int}}\nКоманда remove_greater позволяет пользователю удалить элементы, которые старше данного, из коллекции.\nФормат ввода : remove_greater{ name : String, age : int, x : int, y : int, Stomach{ volume : int}}\nКоманда info предоставляет пользователю информацию о коллекции.\nФормат ввода : info\nКоманда show предоставляет пользователю информацию о содержимом коллекции в строковом представлении.\nФормат ввода : show\nКоманда save сохраняет содержимое коллекции в файл.\nФормат ввода : save\nКоманда import передаёт программе адрес файла\nФормат ввода : import{String}\nКоманда out позволяет Вам выйти из системы.";
            case "show":
                String string = "";
                PreparedStatement statement = connection.prepareStatement("SELECT * from homeofcats");
                ResultSet reSet = statement.executeQuery();
                while (reSet.next()){
                    string += reSet.getString(2) + " " + reSet.getString(3) + " " + reSet.getString(4) + " " + reSet.getString(5) + " " + reSet.getString(6) + " " + reSet.getString(7) + "\n";
                }
                return string;
            case "info":
                int num = 0;
                statement = connection.prepareStatement("Select login from homeofcats");
                ResultSet ReSet = statement.executeQuery();
                while (ReSet.next()){
                    num ++;
                }
                return "Количество элементов " + num;
            case "in":
                int flag = 0;
                PreparedStatement stamt = connection.prepareStatement("Select login, password from users where login = ?");
                stamt.setString(1, obj.getLogin());
                ResultSet resultSet = stamt.executeQuery();
                if (resultSet.next()){ // Следовательно такая почта зарегестрирована в бд
                    try {
                        if (resultSet.getString(2).equals(coding.getCode("54Fz" + obj.getPassword()))) { // Хэши совпали, следовательно может войти
                            return "Авторизация прошла успешна.";
                        } else {
                            return "Неверный пароль";
                        }
                    }catch (NumberFormatException e){
                        return "Вы забыли ввести пароль.";
                    }
                }else{ //Почты нет, значит регистрация
                    String password = coding.getPassword();
                    SendEmail.SMTP_SERVER = "smtp.mail.ru";
                    SendEmail.SMTP_Port = "465";
                    SendEmail.EMAIL_FROM = "lexa200004@mail.ru";
                    SendEmail.SMTP_AUTH_USER = "lexa200004";
                    SendEmail.SMTP_AUTH_PWD = "alex6a";
                    SendEmail sendEmail = new SendEmail(obj.getLogin(),"Регистрация");
                    boolean flagWr = sendEmail.sendMessage("Ваш пароль : " + password);

                    if (flagWr) {
                        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users VALUES(?, ?)");
                        preparedStatement.setString(1, obj.getLogin());
                        preparedStatement.setString(2, coding.getCode("54Fz" + password));
                        preparedStatement.executeUpdate();
                        return ("Вы успешно зарегестрированы, на Вашу почту отправлен пароль.");
                    }else {
                        return ("Не удалось зарегестрироваться или Вы неправильно ввели почту.");
                    }
                }
//                if (flag == 1) {
//                    try {
//                        statement = connection.prepareStatement("Select * from users");
//                        resultSet = statement.executeQuery();
//                        while (resultSet.next()) {
//                            if (resultSet.getString(1).equals(obj.getLogin()) && (resultSet.getString(2).equals(coding.getCode("54Fz" + obj.getPassword())))) {
//                                flag++;
//                                return "Авторизация прошла успешна.";
//                            }
//                        }
//                        if (flag == 1) {
//                            return "Неверный пароль.";
//                        }
//                    }catch (NumberFormatException e){
//                        return ("Вы забыли ввести пароль.");
//                    }
//                }else{
//                    String password = coding.getPassword();
//                    SendEmail.SMTP_SERVER = "smtp.mail.ru";
//                    SendEmail.SMTP_Port = "465";
//                    SendEmail.EMAIL_FROM = "lexa200004@mail.ru";
//                    SendEmail.SMTP_AUTH_USER = "lexa200004";
//                    SendEmail.SMTP_AUTH_PWD = "alex6a";
//
//
//                    SendEmail sendEmail = new SendEmail(obj.getLogin(),"Регистрация");
//                    boolean flagWr = sendEmail.sendMessage("Ваш пароль : " + password);
//
//
//                    if (flagWr) {
//                        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users VALUES(?, ?)");
//                        preparedStatement.setString(1, obj.getLogin());
//                        preparedStatement.setString(2, coding.getCode("54Fz" + password));
//                        preparedStatement.executeUpdate();
//                        return ("Вы успешно зарегестрированы, на Вашу почту отправлен пароль.");
//                    }else {
//                        return ("Не удалось зарегестрироваться или Вы неправильно ввели почту.");
//                    }
//                }


            default:
                return "";
        }
    }
}
