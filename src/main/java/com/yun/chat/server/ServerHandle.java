package com.yun.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ServerHandle implements  Runnable{

    private List<PrintWriter> printWriters ; //获取所有客户端的输出
    private Socket socket;

    public  ServerHandle(List<PrintWriter> writer, Socket socket){
        this.printWriters=writer;
        this.socket=socket;
    }

    @Override
    public void run() {
        System.out.println("进入线程");
        BufferedReader in = null;
        String msg;
        try {
             in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true){
                if ((msg = in.readLine()) == null) break;

                System.out.println("服务器收到的信息"+msg);
                //循环输出，把接收到的信息，发生给所有的客户端，需要完善本客户端不发，
                for (PrintWriter writer : printWriters){
                    System.out.println("正在转发");
                    writer.println(msg);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (in!=null){
                    in.close();
                    in=null;
                }
            } catch (IOException e){
                e.printStackTrace();
            }

            try {
                if (socket!=null){
                    socket.close();
                    socket=null;
                }
            } catch (IOException e){
                e.printStackTrace();
            }

//            for(PrintWriter writer: printWriters){
//                writer.close();
//            }

        }
    }


}
