import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public final class Exec {
    public static void main(String[] args) throws IOException {
        //Initialize card dataset and fetch all card data from Scryfall once
        DataHandler dh = new DataHandler();
        System.out.println("Fetching card data...");
        dh.UpdateData();
        System.out.println("Done!");

        String folder = "deck";
        String input = "source.txt";

        if (args.length>0){
            folder = args[0].replaceAll("\\s+","_");
        }
        if (args.length>1){
            input = args[1];
        }

        File file = new File(input);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        System.out.println("Downloading images...");
        while((line = br.readLine()) != null){
            line=line.replaceAll("\\d+\s*","");
            dh.downloadImage(folder, line);
        }
        System.out.println("Done!");
    }
}
