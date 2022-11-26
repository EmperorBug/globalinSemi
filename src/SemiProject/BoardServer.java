package SemiProject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BoardServer {
    private static final int serverPort = 9999;

    public static void main(String[] args) {
        Socket soc;
        try {
            ServerSocket ssoc = new ServerSocket(serverPort);
            while (true) {
                soc = ssoc.accept();

                Runnable server = new Board(soc);
                Thread t = new Thread(server);
                t.start();
                System.out.println("["+soc.getInetAddress()+":"+soc.getPort()+"]님 서버연결중");
            }

        }catch (IOException e) {
            e.printStackTrace();
            System.out.println("연결오류가 발생했습니다");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
