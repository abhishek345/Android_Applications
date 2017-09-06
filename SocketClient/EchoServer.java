package com.example.socketclient;

import java.net.*;
import java.io.*;

public class EchoServer extends Thread
{
   private ServerSocket serverSocket;
   
   public EchoServer(int port) throws IOException
   {
      serverSocket = new ServerSocket(port);
      //serverSocket.setSoTimeout(10000);
   }

   public void run()
   {
         try
         {
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            
	    Socket server = serverSocket.accept();
            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            
	    DataInputStream in = new DataInputStream(server.getInputStream());
	    DataOutputStream out = new DataOutputStream(server.getOutputStream());

	    String dataFromClient = in.readUTF();
	    while(!dataFromClient.equalsIgnoreCase("exit"))
	    {
		out.writeUTF(dataFromClient);
		dataFromClient = in.readUTF();
	    }
            
	    server.close();
         }catch(IOException e)
         {
            e.printStackTrace();
         }
   }
   public static void main(String [] args)
   {
      int port = Integer.parseInt(args[0]);
      try
      {
         Thread t = new EchoServer(port);
         t.start();
      }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}