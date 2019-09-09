package encryptdecrypt;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

interface Shifrovalshik{

     String shif(String s, int l, String com);
}

class ShifrUnicode implements Shifrovalshik{

    @Override
    public String shif(String s, int l, String com){
        StringBuilder strbuild = new StringBuilder();
        for(char x : s.toCharArray()){
            int a;
            if(com.equals("enc"))
                a = (x + l) ;
            else
                a = ((x  - l));
            strbuild.append((char)a);
        }
        return strbuild.toString();
    }
}

class ShifrShift implements Shifrovalshik{

    @Override
    public String shif(String s, int l, String com){
        StringBuilder strbuild = new StringBuilder();

        for(char x : s.toCharArray()){
            if('a'<=x && x<='z') {
                int a = 0;
                if (com.equals("enc"))
                    a = (x - 'a' + l) % 26;
                else
                    a = (x - 'a' + 26 - l) % 26;
                strbuild.append((char) (a + 'a'));
            }
            else
                strbuild.append(x);
        }

        return strbuild.toString();
    }
}

class CryptFactory {

    Shifrovalshik createTable(String type) {
        if (type.equals("unicode")) {
            return new ShifrUnicode();
        } else if (type.equals("shift")) {
            return new ShifrShift();
        } else return null;
    }
}


public class Main {

    public static void coding(String s, String com, int l){

    }


    public static void main(String[] args) {

        File fin, fout;


        String com = "dec", s = "";
        List<String> list = Arrays.asList(args);
        int l= 0, y = list.indexOf("-mode");

        if (y != -1)
            com = args[y+1];
        y = list.indexOf("-data");
        if (y == -1 ) {
            y = list.indexOf("-in");

            if(y != -1) {
                fin = new File(args[y + 1]);
                try (Scanner scanner = new Scanner(fin)) {
                    s = scanner.nextLine();
                    if(list.indexOf("-key")!=-1)
                        l = Integer.parseInt(args[list.indexOf("-key")+1]);
                }
                catch(Exception exc){
                    System.out.println(exc.getMessage());
                    return;
                }
            }
            else{
                Scanner scanner = new Scanner(System.in);
                s = scanner.nextLine();
                if(list.indexOf("-key")!=-1)
                    l = Integer.parseInt(args[list.indexOf("-key")+1]);
                else l = scanner.nextInt();
            }
            /**/
        }
        else {
            s = args[y+1];
            l = Integer.parseInt(args[list.indexOf("-key")+1]);
        }

        y = list.indexOf("-alg");
        String res = new CryptFactory().createTable(args[list.indexOf("-alg")+1]).shif(s, l , com);

        y = list.indexOf("-out");
        if(y==-1){
            System.out.println(res);
        }
        else{
            fout = new File(args[y+1]);
            try(FileWriter fwout = new FileWriter(fout)){
                fwout.write(res);
            }
            catch(Exception exc){
                System.out.println(exc.getMessage());
            }
        }
    }

}