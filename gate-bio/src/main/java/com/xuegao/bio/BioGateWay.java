package com.xuegao.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <br/> @PackageName：com.xuegao.bio
 * <br/> @ClassName：BioGateWay
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2021/04/08 16:03
 */
public class BioGateWay {
    private ServerSocket serverSocket;

    public static void main(String[] args) {
        new BioGateWay().service();
    }

    private BioGateWay() {
        try {
            System.out.println("服务器正在启动");
            serverSocket = new ServerSocket(8777);
            System.out.println("服务器启动成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("服务器启动失败");
        }
    }

    private BufferedReader getReader(Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
    }

    private PrintWriter getWriter(Socket socket) throws IOException {
        return new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
    }

    public void service() {
        while (true) {
            Socket socket = null;
            BufferedReader br;
            PrintWriter pw;
            try {
                socket = serverSocket.accept();
                br = getReader(socket);
                pw = getWriter(socket);
                String recMessage = br.readLine();
                if (recMessage == null || "".equals(recMessage)) {
                    continue;
                }
                System.out.println(recMessage);
                if ("quit".equals(recMessage)) {
                    pw.println("bye");
                    break;
                } else {
                    pw.println("I am fine");
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}