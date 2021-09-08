package thang.com.wref.util;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

import static thang.com.wref.util.Constants.BASE_URL;

public class SocketIO {
    // connect socket io server
    public static Socket socket;

    public void ConnectSocket(){
        try {
            socket = IO.socket(BASE_URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.connect();
    }
}
