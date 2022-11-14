import java.net.*;

import java.net.*;

public class URLDemo {
    public static void main(String[] args) {
        try {
            URL url;
            url = new URL("https://www.javatpoint.com/react-native-tutorial");

            System.out.println("Protocol: " + url.getProtocol());
            System.out.println("Host Name: " + url.getHost());
            System.out.println("Port Number: " + url.getPort());
            System.out.println("Default Port Number: " + url.getDefaultPort());
            System.out.println("Query String: " + url.getQuery());
            System.out.println("Path: " + url.getPath());
            System.out.println("File: " + url.getFile());
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
