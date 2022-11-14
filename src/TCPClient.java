import java.io.*;
import java.net.*;
class TCPClient {
    public static void main(String[] args) throws Exception
    {
        String sentence;
        String modifiedSentence;
        BufferedReader inFromUser = new BufferedReader(new
                InputStreamReader(System.in));
        System.out.println("Kliyent ishga tushdi!!!");
        Socket clientSocket = new Socket("localhost", 6789);
        System.out.println("Kliyent server bilan bog‘landi");
        DataOutputStream outToServer = new
                DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new
                InputStreamReader(clientSocket.getInputStream()));
        System.out.println("Serverga jo‘natsh uchun ixtiyoriy matnni kiriting:");
        sentence = inFromUser.readLine();
        outToServer.writeBytes(sentence + '\n');
        System.out.println("Kiritilgan matn serverga jo‘natildi");
        modifiedSentence = inFromServer.readLine();
        System.out.println("Qayta ishlangan matn serverdan keldi: " +
                modifiedSentence);

        clientSocket.close();
        System.out.println("Kliyent soketi yopildi!");
    }
}
