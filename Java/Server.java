import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) throws IOException {
            ServerSocket server = new ServerSocket(10314);
            Socket client = null;
            while((client = server.accept()) != null) {
                try {
                    //READ BYTES + DESERIALIZE
                    InputStream is = client.getInputStream();
                    ObjectInputStream ois = new ObjectInputStream(is);
                    RemoteMethod rm = (RemoteMethod) ois.readObject();

                    //look at method name
                    String method = rm.getMethodName();
                    Object arr[] = rm.getArguments();
                    Object toReturn = null;

                    //call the correct method
                    if(method.equals("add")) {
                        toReturn = add((int) arr[0], (int) arr[1]);
                    }else if(method.equals("divide")) {
                        toReturn = divide((int) arr[0], (int) arr[1]);
                    }else{
                        String message = (String) arr[0];
                        toReturn = echo(message);
                    }
                    //SERIALIZES OUTPUT
                    RemoteMethod serialize = new RemoteMethod(method, new Object[]{toReturn});
                    OutputStream os = client.getOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(os);
                    oos.writeObject(serialize);

                    oos.flush();
                    oos.close();
                    os.close();
                    is.close();
                    ois.close();
                    client.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            server.close();
    }

    // Do not modify any code below tihs line
    // --------------------------------------
    public static String echo(String message) {
        return "You said " + message + "!";
    }
    public static int add(int lhs, int rhs) {
        return lhs + rhs;
    }
    public static int divide(int num, int denom) {
        if (denom == 0)
            throw new ArithmeticException();

        return num / denom;
    }
}