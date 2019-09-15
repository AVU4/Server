

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;

public class ThreadClient implements Runnable {

    private  CatVector catVector;
    private  Socket socket;
    private Connection connection;

    public ThreadClient(CatVector catVector, Connection connection, Socket socket){
        this.catVector = catVector;
        this.socket = socket;
        this.connection = connection;
    }

    @Override
    public void run()  {
        try {
            Commands commands = new Commands(catVector, connection);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject("true");
            out.flush();
            Thread.sleep(1000);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                System.out.println("Ждём сообщения");
                Object object =  in.readObject();
                Request request = (Request) object;
                System.out.println(request.getCommand());
                String answer = commands.doCommand(request);
                out.writeObject(answer);
                out.flush();

            }

        } catch (InterruptedException | IOException | ClassNotFoundException e) {
        }catch (NullPointerException e){

        }catch (Exception e){
        }
    }

}
