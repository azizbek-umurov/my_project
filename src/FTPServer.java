import java.net.*;
import java.io.*;

public class FTPServer
{
    public static void main(String args[]) throws Exception
    {
        ServerSocket soc=new ServerSocket(8888);
        System.out.println("FTP Server 8888 Номер порта работает");
        while(true)
        {
            System.out.println("Ожидание соединения...");
            transferfile t = new transferfile(soc.accept());

        }
    }
}

class transferfile extends Thread
{
    Socket ClientSoc;

    DataInputStream din;
    DataOutputStream dout;

    transferfile(Socket soc)
    {
        try
        {
            ClientSoc=soc;
            din=new DataInputStream(ClientSoc.getInputStream());
            dout=new DataOutputStream(ClientSoc.getOutputStream());
            System.out.println("FTP клиент подключён ...");
            start();

        }
        catch(Exception ex)
        {
        }
    }
    void SendFile() throws Exception
    {
        String filename=din.readUTF();
        File f=new File(filename);
        if(!f.exists())
        {
            dout.writeUTF("Файл не найден");
            return;
        }
        else
        {
            dout.writeUTF("Готово");
            FileInputStream fin=new FileInputStream(f);
            int ch;
            do
            {
                ch=fin.read();
                dout.writeUTF(String.valueOf(ch));
            }
            while(ch!=-1);
            fin.close();
            dout.writeUTF("Файл получен");
        }
    }

    void ReceiveFile() throws Exception
    {
        String filename=din.readUTF();
        if(filename.compareTo("Файл не найден")==0)
        {
            return;
        }
        File f=new File(filename);
        String option;

        if(f.exists())
        {
            dout.writeUTF("Файл создан ранее");
            option=din.readUTF();
        }
        else
        {
            dout.writeUTF("SendFile");
            option="Y";
        }

        if(option.compareTo("Y")==0)
        {
            FileOutputStream fout=new FileOutputStream(f);
            int ch;
            String temp;
            do
            {
                temp=din.readUTF();
                ch=Integer.parseInt(temp);
                if(ch!=-1)
                {
                    fout.write(ch);
                }
            }while(ch!=-1);
            fout.close();
            dout.writeUTF("Файл отправлен");
        }
        else
        {
            return;
        }

    }


    public void run()
    {
        while(true)
        {
            try
            {
                System.out.println("Ожидание команды ...");
                String Command=din.readUTF();
                if(Command.compareTo("GET")==0)
                {
                    System.out.println("\tGET Команда получена ...");
                    SendFile();
                }
                else if(Command.compareTo("SEND")==0)
                {
                    System.out.println("\tSEND Команда получена ...");
                    ReceiveFile();
                }
                else if(Command.compareTo("DISCONNECT")==0)
                {
                    System.out.println("\tDisconnect Команда получена ...");
                    System.exit(1);
                }
            }
            catch(Exception ex)
            {
            }
        }
    }
}
