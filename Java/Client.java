import java.io.*;
import java.net.Socket;

public class Client {

    /**
     * This method name and parameters must remain as-is
     */
    public static int add(int lhs, int rhs) {
        int sum = 0;
        try {
            //CONNECT TO SERVER
            Socket socket = new Socket(server, PORT); //Using server fails. See screenshot for more details

            //CREATE INSTANCE OF REMOTE METHOD
            RemoteMethod add = new RemoteMethod("add", new Object[]{lhs, rhs});

            //USE OBJECTOUTPUTSTREAM TO SERIALIZE AND ADD INSTANCE
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);

            //SEND TO SERVER
            oos.writeObject(add);

            //GET RESPONSE FROM SERVER + DESERIALIZE
            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            RemoteMethod rm = (RemoteMethod) ois.readObject();
            Object[] arr = rm.getArguments();
            sum = (int) arr[0];

            socket.close();
            oos.close();
            ois.close();
            os.close();
            is.close();
        }
        catch (Exception e) {
            if(e.getMessage().equals("Connection refused")) {
                System.out.println("Server not running");
            }else {
                e.printStackTrace();
            }
        }
        return sum;
    }

    /**
     * This method name and parameters must remain as-is
     */
    public static int divide(int num, int denom){
        int div = 0;

        if (denom == 0) {
            throw new ArithmeticException();
        }

        try {
            Socket socket = new Socket(server, PORT);
            RemoteMethod divide = new RemoteMethod("divide", new Object[]{num, denom});

            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);

            oos.writeObject(divide);
            oos.flush();

            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            RemoteMethod rm = (RemoteMethod) ois.readObject();
            Object[] arr = rm.getArguments();

            div = (int) arr[0];

            socket.close();
            oos.close();
            ois.close();
            os.close();
            is.close();
        }
        catch (Exception e) {
            if(e.getMessage().equals("Connection refused")) {
                System.out.println("Server not running");
            }else {
                e.printStackTrace();
            }
        }
        return div;
    }
    /**
     * This method name and parameters must remain as-is
     */
    public static String echo(String message) {
        String toReturn = "";
        try{
            Socket socket = new Socket(server, PORT);
            RemoteMethod echo = new RemoteMethod("echo", new Object[]{message});

            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);

            oos.writeObject(echo);

            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            RemoteMethod rm = (RemoteMethod) ois.readObject();
            Object[] arr = rm.getArguments();
            toReturn = (String) arr[0];

            socket.close();
            oos.close();
            ois.close();
            os.close();
            is.close();
        }
        catch (Exception e) {
            if(e.getMessage().equals("Connection refused")) {
                System.out.println("Server not running");
            }else {
                e.printStackTrace();
            }
        }
        return toReturn;
    }

    // Do not modify any code below this line
    // --------------------------------------
    static String server = "localhost";
    public static final int PORT = 10314;

    public static void main(String... args) {
        // All of the code below this line must be uncommented
        // to be successfully graded.
        System.out.print("Testing... ");

        if (add(2, 4) == 6)
            System.out.print(".");
        else
            System.out.print("X");

        try {
            divide(1, 0);
            System.out.print("X");
        }
        catch (ArithmeticException x) {
            System.out.print(".");
        }

        if (echo("Hello").equals( "You said Hello!"))
            System.out.print(".");
        else
            System.out.print("X");

        System.out.println(" Finished");
    }
}