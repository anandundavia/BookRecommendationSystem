
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DB
{

    private static final String DATABASE_NAME = "BOOKRECOMMENDERDB";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "";

    private static final String DATABASE_CONNECTION_PATH = "jdbc:mysql://localhost/" + DATABASE_NAME + "?useSSL=false";

    private static final String KEY = "word";
    private static final String FREQ = "freq";

    public static void main(String args[])
    {
        File allFilesDir = new File("C:\\Users\\Anand Undavia\\Documents\\SEM 6\\MP3\\RecommenderSystemPDFs\\");
        File inputFiles[] = allFilesDir.listFiles();
        BufferedWriter bw = null;
        BufferedWriter bw1 = null;
        try
        {
            bw = new BufferedWriter(new FileWriter(new File("C:\\Users\\Anand Undavia\\Documents\\SEM 6\\MP3\\Output1.txt")));
            //bw1 = new BufferedWriter(new FileWriter(new File("C:\\Users\\Anand Undavia\\Documents\\SEM 6\\MP3\\Output2.txt")));
            Connection cc = DriverManager.getConnection(DATABASE_CONNECTION_PATH, DATABASE_USERNAME, DATABASE_PASSWORD);
            Statement st = cc.createStatement();
            for (File currFile : inputFiles)
            {
                String TABLENAME = currFile.getName().substring(0, 4);
                ResultSet rs = st.executeQuery("SELECT * FROM " + TABLENAME);
                double avg = 0;
                double count = 0;
                while (rs.next())
                {
                    avg += Double.parseDouble(rs.getString(FREQ));
                    count++;
                }
                bw.write("Average for table " + TABLENAME + " is " + (avg / count) + "\n");

                double mark = (avg/count);
                
                rs = st.executeQuery("SELECT * FROM " + TABLENAME);
                bw1 = new BufferedWriter(new FileWriter(new File("C:\\Users\\Anand Undavia\\Documents\\SEM 6\\MP3\\FREQ\\"+TABLENAME+".txt")));
                while (rs.next())
                {
                    if ((6*mark/10)<=Double.parseDouble(rs.getString(FREQ)) && Double.parseDouble(rs.getString(FREQ))<=(8*mark/10))
                    {
                        bw1.write(rs.getString(KEY) +"\n");
                    }

                }
                bw1.close();
                //bw1.write(TABLENAME + " ends\n\n");

            }
            bw.close();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
