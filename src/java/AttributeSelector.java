
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class AttributeSelector
{

    private static final String DATABASE_NAME = "BOOKRECOMMENDERDB";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "";

    private static final String DATABASE_CONNECTION_PATH = "jdbc:mysql://localhost/" + DATABASE_NAME + "?useSSL=false";

    private static final String KEY = "word";
    private static final String FREQ = "freq";

    public static void main(String args[])
    {
        try
        {
            File allFilesDir = new File("C:\\Users\\Anand Undavia\\Documents\\SEM 6\\MP3\\FREQ\\");
            File inputFiles[] = allFilesDir.listFiles();
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("C:\\Users\\Anand Undavia\\Documents\\SEM 6\\MP3\\ATTRIBUTES.txt")));
            for (int i = 0; i < inputFiles.length; i++)
            {
                File cF = inputFiles[i];
                BufferedReader cfr = new BufferedReader(new FileReader(cF));
                BufferedReader afr = new BufferedReader(new FileReader(new File("C:\\Users\\Anand Undavia\\Documents\\SEM 6\\MP3\\ATTRIBUTES.txt")));
                String ip = cfr.readLine();
                while (ip != null)
                {
                    boolean isThere = false;

                    String ip2 = afr.readLine();
                    while (ip2 != null)
                    {
                        if (ip.equals(ip2))
                        {
                            isThere = true;
                            break;
                        }
                        ip2 = afr.readLine();
                    }
                    if(!isThere)
                    {
                        bw.write(ip+"\n");
                    }
                    ip = cfr.readLine();
                }
            }
            bw.close();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
