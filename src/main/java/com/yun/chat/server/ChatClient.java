package com.yun.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    private static int server_port=6666;
    private static String server_ip="127.0.0.1";

    private Socket socket;
    public  ChatClient(){
        if (socket==null){
            try {
                socket = new Socket(server_ip,server_port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public  void send(String msg){
        send(server_ip,server_port,msg);
    }

    private  void send(String server_ip, int server_port,String msg) {

        PrintWriter out = null;
        try {
            System.out.println("————————————"+msg);
            out = new PrintWriter(socket.getOutputStream(),true);
            out.println(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receive(){
        BufferedReader in = null;
        try {
            String resultmsg;
            in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true){
                if ((resultmsg = in.readLine()) == null) break;
                System.out.println(resultmsg);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (in!=null){
                    in.close();
                    in=null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        ChatClient chatClient = new ChatClient();
        //启动线程处理接收数据
        new Thread(()->{
            while (true) {
                chatClient.receive();
            }
        }).start();

        while (true){
            Scanner sc = new Scanner(System.in);
            chatClient.send(sc.nextLine());
        }

    }
}
