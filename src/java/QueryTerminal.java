import java.io.BufferedReader;
import java.io.InputStreamReader;

public class QueryTerminal
{

    private static final String[] BOOK_ATTRIBUTES =
    {
        "STACK", "TREE", "BIGO", "ASYMPTOTIC", "ORDER",
    };

    public static void main(String args[]) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter the 5 frequencies");
        String ip = br.readLine();
        String f[] = ip.split(" ");
        
        Book bookx = new Book("BOOKX", 5);
        bookx.setAttribute(BOOK_ATTRIBUTES);
        bookx.setFrequency(f);
        
        Book recommendedBooks[] = bookx.getRecommendedBooks();
        br.close();
    }
}
