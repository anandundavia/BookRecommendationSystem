
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class blah 
{
    public static void main(String args[]) throws Exception
    {
        File f = new File("C:\\Users\\Anand Undavia\\Documents\\SEM 6\\MP3\\ATTRIBUTES.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        BufferedReader br1 = new BufferedReader(new FileReader(f));
        
        File o = new File("C:\\Users\\Anand Undavia\\Documents\\SEM 6\\MP3\\FINALATTRIBUTES.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(o));
        String att[] = new String[2000];
        String ip = br.readLine();
        int i=0;
        while(ip!=null)
        {
            att[i++] = ip;
            ip = br.readLine();
        }
        for(int j=0;j<2000 && att[j]!= null;j++)
        {
            boolean isR = false;
            for(int k=j+1;k<2000 && att[k]!= null;k++)
            {
                
                if(att[j].toLowerCase().equals(att[k].toLowerCase()))
                {
                    isR = true;
                    break;
                }
            }
            if(!isR)
            {
                bw.write(att[j].toLowerCase()+"\n");
            }
        }
        
        bw.close();
        
    }
}
