package com.yun.chat.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BIOServer {

    private static int SERVER_PORT=6666;
    private static ServerSocket serverSocket;


    public static void start(){
        start(SERVER_PORT);
    }

    private static void start(int serverPort) {
        if (serverSocket!=null) return ;
        try {
            System.out.println("服务的正在启动，端口："+serverPort);
            serverSocket = new ServerSocket(serverPort);
            List<PrintWriter> printWriterList = new ArrayList<PrintWriter>();
            while (true){
                Socket accept = serverSocket.accept();
                PrintWriter out = new PrintWriter(accept.getOutputStream(),true);
                printWriterList.add(out);
                new Thread(new ServerHandle(printWriterList,accept)).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (serverSocket!=null){
                    System.out.println("关闭serverSocket");
                    serverSocket.close();
                    serverSocket=null;
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        BIOServer.start();
    }
}
