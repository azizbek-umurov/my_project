
import java.io.*;
import java.net.*;
class TCPServer {
    public static void main(String[] args){
        try {
            String clientSentence;
            String capitalizedSentence;
            try (ServerSocket welcomeSocket = new ServerSocket(6789)) {
                System.out.println("Server ishga tushdi!");
                System.out.println("Kliyentdan so‘rovni kutmoqda...");
                while (true) {
                    Socket connectionSocket = welcomeSocket.accept();
                    System.out.println("Kliyent server bilan bog‘landi");
                    BufferedReader inFromClient = new BufferedReader(new
                            InputStreamReader(connectionSocket.getInputStream()));
                    DataOutputStream outToClient = new
                            DataOutputStream(connectionSocket.getOutputStream());
                    clientSentence = inFromClient.readLine();
                    System.out.println("Server kliyentdan so‘rovni qabul qildi");
                    System.out.println("Kliyentdan qabul qilingan matn: " + clientSentence);
                    capitalizedSentence = clientSentence.toUpperCase() + '\n';
                    outToClient.writeBytes(capitalizedSentence);
                    System.out.println("Server qabul qilingan so‘rovni qayta ishlab kliyentga jo‘natdi");
                    System.out.println("Qayta ishlangan matn: " + capitalizedSentence);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
