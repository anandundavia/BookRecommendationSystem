import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseGenerator
{

    private static final String DATABASE_NAME = "BOOKRECOMMENDERDB";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "";

    private static final String DATABASE_CONNECTION_PATH = "jdbc:mysql://localhost/" + DATABASE_NAME + "?useSSL=false";

    private static final String BOOK_TABLE = "BOOKATTRIBUTES";
    private static final String BOOK_NAME = "BOOKNAME";
    private static final String BOOK_ATTRIBUTE = "BOOKATTRIBUTE";
    private static final String ATTRIBUTE_FREQ = "ATTRIBUTEFREQUENCY";

    private static final String DROP_BOOK_TABLE = "DROP TABLE IF EXISTS " + BOOK_TABLE;
    private static final String CREATE_BOOK_TABLE = "CREATE TABLE IF NOT EXISTS " + BOOK_TABLE + " (" + BOOK_NAME + " TEXT, " + BOOK_ATTRIBUTE + " TEXT, " + ATTRIBUTE_FREQ + " TEXT);";

    private static final String[] BOOK_NAMES =
    {
        "BOOK1", "BOOK2", "BOOK3", "BOOK4", "BOOK5","BOOK6","BOOK7"
    };
    private static final String[] BOOK_ATTRIBUTES =
    {
        "STACK", "TREE", "BIGO", "ASYMPTOTIC", "ORDER",
    };

    public static void main(String args[])
    {
        try
        {
            Connection cc = DriverManager.getConnection(DATABASE_CONNECTION_PATH, DATABASE_USERNAME, DATABASE_PASSWORD);
            Statement st = cc.createStatement();
            st.executeUpdate(DROP_BOOK_TABLE);
            st.executeUpdate(CREATE_BOOK_TABLE);

            for (String currentBook : BOOK_NAMES)
            {
                for (String currentAttr : BOOK_ATTRIBUTES)
                {
                    double freq = Math.random();
                    freq *= 10000;
                    
                    freq /= 29;
                    freq+=5;
                    System.out.println(currentBook+" "+currentAttr+" "+(int)freq);
                    
                    String query = "INSERT INTO "+BOOK_TABLE+" VALUES ('"+currentBook+"','"+currentAttr+"','"+(int)freq+"')";
                    st.executeUpdate(query);
                }
                System.out.println();
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
