import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public final class Exec {
    public static void main(String[] args) throws IOException {
        String folder = "deck";
        String input = "source.txt";

        //Custom output sub-folder and input file
        if (args.length>0){
            folder = args[0].replaceAll("\\s+","_");
        }
        if (args.length>1){
            input = args[1];
        }

        //Open input file and initialize reader
        File file = new File(input);
        FileReader fr;
        try {
            fr = new FileReader(file);
        } catch (Exception e){
            System.out.println("[!] Couldn't open input file. Default file name is source.txt");
            return;
        }
        BufferedReader br = new BufferedReader(fr);

        //Initialize card dataset and fetch all card data from Scryfall once
        DataHandler dh = new DataHandler();
        System.out.println("Fetching card data...");
        dh.UpdateData();
        System.out.println("Done!");

        //Read all lines of input file and, for each line, try to download the card's image
        System.out.println("Downloading images...");
        String line;
        while((line = br.readLine()) != null){
            line=line.replaceAll("\\d+\s*","");
            try {
                dh.downloadImage(folder, line);
            } catch (Exception e){
                System.out.println("[!] Image download failed, may be caused by wrong input formatting. Line:");
                System.out.println(line);
            }
        }
        System.out.println("Done!");
    }
}
