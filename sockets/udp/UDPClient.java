package sockets.udp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {

    public static void main(String args[]) throws Exception {
        Criptografia cripto = new Criptografia();
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");
        byte[] sendData = new byte[16];
        byte[] receiveData = new byte[16];
        while (true) {

            System.out.println("Cliente preparado para enviar: ");
            //Lê entrada do usuário
            String sentence = inFromUser.readLine();

            sendData = cripto.encrypt(sentence);
            System.out.print("Envio de texto Encriptado ao Servidor ");
            System.out.println("");
            for (int i = 0; i < sendData.length; i++) {
                System.out.print(new Integer(sendData[i]) + " ");
            }
            System.out.println("");
            //Cria pacote udp
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
            //envia ao servidor
            clientSocket.send(sendPacket);
            //Recebe resposta do servidor
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            byte[] pad = receiveData;
            System.out.print("Resposta Encriptada do Servidor ");
            System.out.println("");
            System.out.print("cipher:  ");
            for (int i = 0; i < pad.length; i++) {
                System.out.print(new Integer(pad[i]) + " ");
            }
            System.out.println("");
            String decrip = cripto.decrypt(pad);
            System.out.println("");
            System.out.println("Recebido do Servidor UDP:" + decrip);
            //Fecha conexão:
            clientSocket.close();
        }
    }
}