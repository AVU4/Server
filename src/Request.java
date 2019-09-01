import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Request implements Serializable {

    private String command;
    private String name;
    private Integer age;
    private Integer volume;
    private Position position;
    private Date date;
    private String login;
    private String password;

    public  Request(){}

    public Request(String command){
        this.command = command;
        this.name = null;
        this.age = null;
        this.volume = null;
        this.position = null;
        this.date = null;
        this.login = null;
        this.password = null;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public Integer getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }

    public Integer getVolume() {
        return volume;
    }

    public Position getPosition() {
        return position;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public void setPosition(int x, int y) {
        this.position = new Position(x,y);
    }

}