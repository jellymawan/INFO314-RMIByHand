import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class RemoteMethod implements Serializable {
    private String methodName;
    private Object[] arrs;

    public RemoteMethod(String methodName, Object arrs[]) {
        this.methodName = methodName;
        this.arrs = arrs;
    }

    public String getMethodName() throws RemoteException {
        return methodName;
    }

    public Object[] getArguments() throws RemoteException {
        return arrs;
    }

}
