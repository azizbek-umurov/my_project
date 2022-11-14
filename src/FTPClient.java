import java.net.*;
import java.io.*;

class FTPClient{
    public static void main(String args[]) throws Exception{
        Socket soc=new Socket("127.0.0.1",9875);
        transferfileClient t=new transferfileClient(soc);
        t.displayMenu();
    }
}
class transferfileClient{
    Socket ClientSoc;
    DataInputStream din;
    DataOutputStream dout;
    BufferedReader br;
    transferfileClient(Socket soc){
        try {
            ClientSoc=soc;
            din=new DataInputStream(ClientSoc.getInputStream());
            dout=new DataOutputStream(ClientSoc.getOutputStream());
            br=new BufferedReader(new InputStreamReader(System.in));
        }
        catch(Exception ex)
        {
        }
    }
    void SendFile() throws Exception
    {

        String filename;
        System.out.print("Введите название файла :");
        filename=br.readLine();

        File f=new File(filename);
        if(!f.exists())
        {
            System.out.println("Файл не существует...");
            dout.writeUTF("Файл не найден");
            return;
        }

        dout.writeUTF(filename);

        String msgFromServer=din.readUTF();
        if(msgFromServer.compareTo("Файл ранее создан")==0)
        {
            String Option;
            System.out.println("Файл ранее создан. Перенаписать? (Y/N) ?");
            Option=br.readLine();
            if(Option=="Y")
            {
                dout.writeUTF("Y");
            }
            else
            {
                dout.writeUTF("N");
                return;
            }
        }

        System.out.println("Отправка файла ...");
        FileInputStream fin=new FileInputStream(f);
        int ch;
        do
        {
            ch=fin.read();
            dout.writeUTF(String.valueOf(ch));
        }
        while(ch!=-1);
        fin.close();
        System.out.println(din.readUTF());

    }

    void ReceiveFile() throws Exception
    {
        String fileName;

        System.out.print("Введите название файла :");
        fileName=br.readLine();
        dout.writeUTF(fileName);
        String msgFromServer=din.readUTF();

        if(msgFromServer.compareTo("Файл не найден")==0)
        {
            System.out.println("Файл не найден в сервере ...");
            return;
        }
        else if(msgFromServer.compareTo("Готово")==0)
        {
            System.out.println("Принятие файла ...");
            File f=new File(fileName);
            if(f.exists())
            {
                String Option;
                System.out.println("Файл ранее создан. Перенаписать? (Y/N) ?");
                Option=br.readLine();
                if(Option=="N")
                {
                    dout.flush();
                    return;
                }
            }
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
            System.out.println(din.readUTF());

        }


    }

    public void displayMenu() throws Exception
    {
        while(true)
        {
            System.out.println("[ MENU ]");
            System.out.println("1. Отправка файла");
            System.out.println("2. Получение файла");
            System.out.println("3. Выход");
            System.out.print("\nВведите выбор :");
            int choice;
            choice=Integer.parseInt(br.readLine());
            if(choice==1)
            {
                dout.writeUTF("SEND");
                SendFile();
            }
            else if(choice==2)
            {
                dout.writeUTF("GET");
                ReceiveFile();
            }
            else
            {
                dout.writeUTF("DISCONNECT");
                System.exit(1);
            }
        }
    }
}

