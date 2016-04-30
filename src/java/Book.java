
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Book
{
    String BOOKNAME;
    
    String ATTRIBUTE[];
    String FREQUENCY[];

    double COSINE_VALUE;
    
    private static final String DATABASE_NAME = "BOOKRECOMMENDERDB";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "";

    private static final String DATABASE_CONNECTION_PATH = "jdbc:mysql://localhost/" + DATABASE_NAME + "?useSSL=false";
    
    private static final String BOOK_TABLE = "BOOKATTRIBUTES";
    private static final String BOOK_NAME = "BOOKNAME";
    private static final String BOOK_ATTRIBUTE = "BOOKATTRIBUTE";
    private static final String ATTRIBUTE_FREQ = "ATTRIBUTEFREQUENCY";
    
    private static final String GET_DATA = "SELECT * FROM "+BOOK_TABLE;
    
    private static final String GET_ALL_BOOKS = "SELECT DISTINCT "+BOOK_NAME+" FROM "+BOOK_TABLE;
    
    Book(String bookname, String[] attributes, int[] frequency)
    {
        if (attributes.length != frequency.length)
        {
            throw new UnsupportedOperationException("Length of attribute array and freqency array must be same");
        } else
        {
            BOOKNAME = bookname;
            ATTRIBUTE = new String[attributes.length];
            System.arraycopy(attributes, 0, ATTRIBUTE, 0, attributes.length);
            FREQUENCY = new String[frequency.length];
            for (int i = 0; i < frequency.length; i++)
            {
                FREQUENCY[i] = frequency[i] + "";
            }
        }
    }

    Book(String bookname, int attributelength)
    {
        BOOKNAME = bookname;
        ATTRIBUTE = new String[attributelength];
        FREQUENCY = new String[attributelength];
    }

    Book(String bookname, String[] attributes, String[] frequency)
    {
        if (attributes.length != frequency.length)
        {
            throw new UnsupportedOperationException("Length of attribute array and freqency array must be same");
        } else
        {
            BOOKNAME = bookname;
            ATTRIBUTE = new String[attributes.length];
            System.arraycopy(attributes, 0, ATTRIBUTE, 0, attributes.length);
            FREQUENCY = new String[frequency.length];
            System.arraycopy(frequency, 0, FREQUENCY, 0, frequency.length);
        }
    }

    public String getBookName()
    {
        return this.BOOKNAME;
    }

    public int getAttributeLength()
    {
        return this.ATTRIBUTE.length;
    }

    public String[] getAttributes()
    {
        return this.ATTRIBUTE;
    }

    public String[] getFrequency()
    {
        return this.FREQUENCY;
    }
    
    public void setAttribute(String[] attrs)
    {
        this.ATTRIBUTE = attrs;
    }
    
    public void setFrequency(String[] freqs)
    {
        this.FREQUENCY = freqs;
    }

    public Book[] getRecommendedBooks()
    {
        Book returnTheseBooks[] = new Book[100];
        double cosines[] = new double[10];
        try
        {
            Connection cc = DriverManager.getConnection(DATABASE_CONNECTION_PATH, DATABASE_USERNAME, DATABASE_PASSWORD);
            Statement st = cc.createStatement();
            
            ResultSet rs = st.executeQuery(GET_ALL_BOOKS);
            int p=0;
          
            while(rs.next())
            {
                String currentBook = rs.getString(BOOK_NAME);
                String query = "SELECT "+BOOK_ATTRIBUTE+","+ATTRIBUTE_FREQ+" FROM "+BOOK_TABLE+" WHERE "+BOOK_NAME+" = '"+currentBook+"'";
                Statement x = cc.createStatement();
                ResultSet set = x.executeQuery(query);
                //System.out.println("Book : "+currentBook);
                String currentBookFreq[] = new String[ATTRIBUTE.length];
                int i=0;
                
                while(set.next())
                {
                    //System.out.println(set.getString(BOOK_ATTRIBUTE)+" "+set.getString(ATTRIBUTE_FREQ));
                    currentBookFreq[i] = set.getString(ATTRIBUTE_FREQ);
                    i++;
                }
                
                double cosine = getCosine(this.getFrequency(),currentBookFreq);
                Book b = new Book(currentBook,ATTRIBUTE.length);
                b.setCosine(cosine);
                System.out.println("Cos : "+cosine);
                returnTheseBooks[p++] = b;
            }
            
            returnTheseBooks = sort(returnTheseBooks);
            
            for(int i=0;i<7;i++)
            {
                System.out.println(returnTheseBooks[i].getBookName());
            }
            
            return returnTheseBooks;
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return returnTheseBooks;
    }
    
    public void setCosine(double cos)
    {
        this.COSINE_VALUE = cos;
    }
    
    private double getCosine(String[] frequency, String[] currentBookFreq)
    {
        double book1Frequency[] = new double[frequency.length];
        double book2Frequency[] = new double[frequency.length];
        double dotProduct=0,book1Norm=0,book2Norm=0;
        
        
        
        for(int i=0;i<frequency.length;i++)
        {
            System.out.println(frequency[i]+" "+currentBookFreq[i]);
            book1Frequency[i] = Double.parseDouble(frequency[i]);
            book2Frequency[i] = Double.parseDouble(currentBookFreq[i]);
        }
        
        for(int i=0;i<frequency.length;i++)
        {
            dotProduct+=book1Frequency[i]*book2Frequency[i];
            book1Norm+=book1Frequency[i]*book1Frequency[i];
            book2Norm+=book2Frequency[i]*book2Frequency[i];
        }
        
        book1Norm = Math.sqrt(book1Norm);
        book2Norm = Math.sqrt(book2Norm);
        
        dotProduct = dotProduct/(book1Norm*book2Norm);
        
        return dotProduct;
        
        
    }

    private Book[] sort(Book[] returnTheseBooks)
    {
        for(int i=0;returnTheseBooks[i]!= null && i<returnTheseBooks.length;i++)
        {
            for(int j=0;returnTheseBooks[j+1]!= null &&j<returnTheseBooks.length-i-1;j++)
            {
                if(returnTheseBooks[j].COSINE_VALUE < returnTheseBooks[j+1].COSINE_VALUE)
                {
                    Book temp = returnTheseBooks[j+1];
                    returnTheseBooks[j+1] = returnTheseBooks[j];
                    returnTheseBooks[j] = temp;
                }
            }
        }
        
        return returnTheseBooks;
    }
    

}
