
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class TEMP
{

    public static void main(String args[]) throws Exception
    {
        File f = new File("C:\\Users\\Anand Undavia\\Documents\\SEM 6\\MP3\\FINALATTRIBUTES2.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));

        File o = new File("C:\\Users\\Anand Undavia\\Documents\\SEM 6\\MP3\\FINALATTRIBUTES3.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(o));
        String ip = br.readLine();
        int i=0;
        while (ip != null)
        {
            if(!ip.contains("ing"))
            {
                bw.write(ip+"\n");
            }
            ip = br.readLine();
        }
     
        bw.close();
        
    }
}
