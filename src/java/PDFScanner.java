
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Arrays;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PDFScanner
{

    private static final String DATABASE_NAME = "BOOKRECOMMENDERDB";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "";

    private static final String DATABASE_CONNECTION_PATH = "jdbc:mysql://localhost/" + DATABASE_NAME + "?useSSL=false";

    private static final String KEY = "word";
    private static final String FREQ = "freq";

    public static void main(String args[]) throws IOException
    {
        File allFilesDir = new File("C:\\Users\\Anand Undavia\\Documents\\SEM 6\\MP3\\RecommenderSystemPDFs\\");
        File inputFiles[] = allFilesDir.listFiles();
        BufferedWriter bw = null;
        try
        {
            bw = new BufferedWriter(new FileWriter(new File("C:\\Users\\Anand Undavia\\Documents\\SEM 6\\MP3\\Output.txt")));
            Connection cc = DriverManager.getConnection(DATABASE_CONNECTION_PATH, DATABASE_USERNAME, DATABASE_PASSWORD);
            Statement st = cc.createStatement();
            for (File currFile : inputFiles)
            {
                bw.write("\nBook Name : " + currFile.getName() + "\n");
                System.out.println("Book Name : " + currFile.getName() + "\n");
                String TABLENAME = currFile.getName().substring(0, 4);
                String query = "DROP TABLE IF EXISTS "+TABLENAME;
                st.executeUpdate(query);
                query = "CREATE TABLE " + TABLENAME + " (" + KEY + " TEXT," + FREQ + " TEXT);";
                System.out.println(query);
                st.executeUpdate(query);
                
                PDFParser parser = new PDFParser(new FileInputStream(currFile));
                parser.parse();
                PDFTextStripper pdfStripper = new PDFTextStripper();
                COSDocument cosDoc = parser.getDocument();
                PDDocument pdDoc = new PDDocument(cosDoc);
                int n = pdDoc.getNumberOfPages();
                for (int i = 1; i <= 1; i++)
                {
                    pdfStripper.setStartPage(i);
                    pdfStripper.setEndPage(n);
                    String parsedText = pdfStripper.getText(pdDoc);
                    String words[] = parsedText.split(" ");
                    Arrays.sort(words);
                    words = filter(words);
                    bw.write("Word array starts\n");
                    int freq = 1;
                    for (int j = 0; j < words.length - 1; j++)
                    {
                        if (words[j].equals(words[j + 1]))
                        {
                            freq++;
                        } else
                        {
                            int x = (int)(10000 * (double) freq / (double) words.length);
                            if (freq >= 5 && words[j].length() >= 3 && !onlyNums(words[j]) &&  x>= 1)
                            {
                                bw.write("Freq of " + words[j] + " = " + freq + " and avg is " + x + "\n");
                                String q = "INSERT INTO "+TABLENAME+" VALUES ('"+words[j]+"','"+x+"');";
                                st.executeUpdate(q);
                            }
                            freq = 1;
                        }
                    }
                    bw.write("Word array ends\n");
                }
            }
            bw.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static boolean satisfy(String word)
    {
        char c[] = word.toCharArray();
        for (int i = 0; i < c.length; i++)
        {
            if (!((65 <= c[i] && c[i] <= 90) && (97 <= c[i] && c[i] <= 122)))
            {
                return false;
            }
        }
        return true;
    }

    private static String[] filter(String[] words)
    {
        String op[] = new String[words.length];
        for (int i = 0; i < words.length; i++)
        {
            char c[] = words[i].toCharArray();
            String x = "";
            for (int j = 0; j < c.length; j++)
            {
                if ((48 <= c[j] && c[j] <= 57) || (65 <= c[j] && c[j] <= 90) || (97 <= c[j] && c[j] <= 122))
                {
                    x += c[j];
                }
            }
            op[i] = x;
        }
        return op;
    }

    private static boolean onlyNums(String word)
    {
        char c[] = word.toCharArray();
        for (int i = 0; i < c.length; i++)
        {
            if ((65 <= c[i] && c[i] <= 90) || (97 <= c[i] && c[i] <= 122))
            {
                return false;
            }
        }
        return true;
    }
}
