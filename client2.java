import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Random;

public class client2 {

    String[][] grid1 = {
            {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
            {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
            {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
            {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
            {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
            {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
            {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
            {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
            {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
            {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
            {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
            {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-"}
    };

    String[][] grid = {
            {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
            {"s", "-", "-", "-", "-", "-", "-", "-", "s", "-"},
            {"-", "-", "-", "s", "s", "s", "s", "-", "s", "-"},
            {"-", "-", "-", "-", "-", "-", "-", "-", "s", "-"},
            {"-", "-", "-", "-", "-", "s", "-", "-", "s", "-"},
            {"-", "-", "-", "-", "-", "s", "-", "-", "s", "-"},
            {"-", "-", "-", "-", "-", "s", "-", "-", "-", "-"},
            {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
            {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
            {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
            {"-", "-", "-", "s", "s", "s", "s", "-", "-", "-"},
            {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-"}
    };

    Socket socket;
    InputStreamReader stream;
    BufferedReader reader;
    PrintWriter writer;
    Scanner input;
    BufferedReader Ureader;
    public static void main(String[] args) {

        (new client2()).go();
    }
    void go(){
        try {
            socket = new Socket("127.0.0.1",5000);
            stream = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(stream);
            Ureader = new BufferedReader(new InputStreamReader(System.in));
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true){
            defense();
            offense();


        }
    }


    void defense() {
        try {

            String sharedString = reader.readLine();
            String[] words = sharedString.split(" ");

            int r = Integer.parseInt(words[0]);
            int c = Integer.parseInt(words[1]);

            if(grid[r][c] == "s") {
                System.out.println("Hit");
                writer.println("Hit");
                writer.flush();
            } else{
                System.out.println("Miss");
                writer.println("Miss");
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void offense() {
        try {

            String msg;
            msg = Ureader.readLine();
            writer.println(msg);
            writer.flush();
            String resp;
            resp =reader.readLine();
            System.out.println(resp);

            //if resp is hit then parse message into cordinates then
            String[] words = msg.split(" ");
            int r = Integer.parseInt(words[0]);
            int c = Integer.parseInt(words[1]);
            if (resp == "Hit"){
                grid[r][c] = "H";
                grid1[r][c]="H";
            }else {
                grid[r][c] = "M";
                grid1[r][c]="M";
            }
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    System.out.print(grid1[i][j]);
                    System.out.print(" ");
                }
                System.out.print("\n");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

