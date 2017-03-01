import java.util.ArrayList;
import java.util.TreeSet;

import java.io.*;

//takes MATLAB source code and parses out the comments. From the comments, removes punctuation, stop words, and numbers

public class ExtractText {

    public static void main(String[] args) throws IOException
    {
        //removing files that aren't .m
        //removeUnnecessaryFiles();

        //stripping punctuation and converts camel case for .m files
        File f = new File("/Users/abigailatchison/Desktop/MLAT/IDFProgram");
        File [] contents = f.listFiles();

        int numFiles=contents.length;

        for (int k=0; k<numFiles;++k)
        {
            String name = contents[k].getAbsolutePath();
            if (!name.endsWith(".m"))
                continue;
            else readComments(name);
        }
        removeIntermediateFiles();
    }

    //takes comments from MATLAB source code, removes punctuation and stop words, puts all information into txt file
    public static void readComments(String fileName) throws IOException
    {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){

            File newFile = new File(fileName + ".txt");
            PrintWriter writer = new PrintWriter(newFile);
            while(true)
            {
                String line = reader.readLine();
                if(line == null){
                    break;
                }

                    //removing all punctuation
                    //remove all camel case
                    writer.println(line.replaceAll("\\W", " ").replaceAll("_"," "));
            }

                reader.close();
                writer.close();
                removeCamelCase(newFile.getAbsolutePath());
                removeStopWords(newFile.getAbsolutePath());
        }


    }

    //removes the intermediate txt files so that there aren't multiple unneeded files and renames finished file
    public static void removeIntermediateFiles() throws IOException
    {
        File f = new File("/Users/abigailatchison/Desktop/MLAT/IDFProgram");
        File [] contents = f.listFiles();

        for (int k=0; k<contents.length; ++k)
        {
            String fileName = contents[k].getAbsolutePath();
            if(fileName.endsWith(".m.txt"))
            {
                System.out.println("Deleteing " + fileName);
                contents[k].delete();
            }
        }
    }

    public static void removeCamelCase(String fileName) throws IOException
    {
      try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){

          File newFile = new File(fileName + ".txt");
          PrintWriter writer = new PrintWriter(newFile);
          while(true)
          {
              String line = reader.readLine();
              if(line == null){
                  break;
              }

                  //removing all punctuation
                  //remove all camel case

                  // String[] words = line.split(" ");
                  //
                  // // for(String s : words)
                  // {
                  //   writer.println(splitCamelCase(s));
                  // }
                  writer.println(splitCamelCase(line).replaceAll("[0-9]",""));


          }
          reader.close();
          writer.close();
    }
  }

    public static String splitCamelCase(String s) {
      return s.replaceAll(
      String.format("%s|%s|%s",
         "(?<=[A-Z])(?=[A-Z][a-z]+)",
         "(?<=[^A-Z])(?=[A-Z])",
         "(?<=[A-Za-z])(?=[^A-Za-z])"
                    )," ");
    }

    //removes the files that aren't .m source code
    // public static void removeUnnecessaryFiles() throws IOException
    // {
    //     File f = new File("/Users/abigailatchison/Desktop/MLAT/IDFProgram");
    //     File [] contents = f.listFiles();
    //
    //     for (int k=0; k<contents.length; ++k)
    //     {
    //         String fileName = contents[k].getAbsolutePath();
    //         if (!(fileName.endsWith(".m")))
    //         {
    //             System.out.println("Deleting " + fileName);
    //             contents[k].delete();
    //         }
    //     }
    // }


    public static void removeStopWords(String nFile) throws IOException
    {
        int index = nFile.lastIndexOf('/');
        String indexPath = nFile.substring(0, index) + "/Output/" + nFile.substring(index);


        //link to stop words: http://www.ranks.nl/stopwords
        TreeSet <String> ts = new TreeSet <String>();
        BufferedReader brSet = new BufferedReader(new FileReader("/Users/abigailatchison/Desktop/MLAT/stopWords.txt"));
        System.out.println("after first buffered reader");

        System.out.println("Name = "+nFile);
        BufferedReader brCheck = new BufferedReader(new FileReader(nFile));

        File newFile = new File(indexPath);

        PrintWriter writer = new PrintWriter(newFile);
        String word = "";

        System.out.println("before while loop");
        while((word=brSet.readLine())!=null)
        {
            ts.add(word);
        }

        String line = "";
        while((line=brCheck.readLine())!=null)
        {
            String[] toks = line.split("[\\s]+");

            for(int k=0; k<toks.length; ++k)
            {

                if (!(ts.contains(toks[k]+"\\")))
                {
                    writer.print(toks[k]+" ");
                }
            }
        }


        brSet.close();
        brCheck.close();
        writer.close();


    }
}
