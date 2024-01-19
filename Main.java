import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Main {
    public static void main(String[] args) {
        try {
            /*
            * This redirects output to the txt file specified in args[1].
            */
            FileOutputStream fos = new FileOutputStream(args[1]);
            PrintStream ps = new PrintStream(fos);
            System.setOut(ps);
            LibraryCommands.readInput(args[0]);
            ps.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}