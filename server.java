import java.io.BufferedReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    private Object lock = new Object();
    private String sharedString;
    private BufferedReader reader1;
    private Socket conn1;
    private PrintWriter writer1;
    private BufferedReader reader2;
    private Socket conn2;
    private PrintWriter writer2;



    void waitForLock() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void notifyLock() {
        synchronized (lock) {
            lock.notify();
        }
    }


    public static void main(String[] args) {
        (new server()).go();

    }

    private void go() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(5000);

            System.out.println("Waiting to connect");
            while (true) {
                conn1 = server.accept();
                reader1 = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
                writer1 = new PrintWriter(conn1.getOutputStream());
                System.out.println("SERVER: recieved");
                new Thread(new client1()).start();
                conn2 = server.accept();
                reader2 = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
                writer2 = new PrintWriter(conn2.getOutputStream());
                new Thread(new client2()).start();




            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private class client1 implements Runnable{
        public void offense(){
            try{
                sharedString = reader1.readLine();
                notifyLock();
                waitForLock();
                System.out.println(sharedString);
                writer1.println(sharedString);
                writer1.flush();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void defense(){
            waitForLock();
            try {
                writer2.println(sharedString);
                writer2.flush();

                try {
                    sharedString = reader2.readLine();
                    System.out.println(sharedString);
                    notifyLock();


                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }
        @Override
        public void run(){
            while (true){
                offense();
                defense();
            }
        }
    }




    private class client2 implements Runnable {
        public void defense(){
            waitForLock();
            try {
                writer2.println(sharedString);
                writer2.flush();

                try {
                    sharedString = reader2.readLine();
                    System.out.println(sharedString);
                    notifyLock();


                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }
        public void offense(){
            try{
                sharedString = reader1.readLine();
                notifyLock();
                waitForLock();
                writer1.println(sharedString);
                writer1.flush();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void run() {
            while (true) {
                defense();
                offense();

            }
        }
    }
}



