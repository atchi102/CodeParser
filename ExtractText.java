import java.util.ArrayList;
import java.util.TreeSet;
import java.nio.file.*;

import java.io.*;

//Parses a file of directories containing MATLAB source code
//Removes stop words, punctuation, numbers, and converts camal case to regular case
//Places finished product in a Output file

public class ExtractText {

    public static void main(String[] args) throws IOException
    {

        parseDir("/home/ott109/M3");
    }

  public static void parseDir(String path) throws IOException
  {
      //list files in current directory
      File currentDir = new File(path);
      File[] contents = currentDir.listFiles();
      int numFilesInDir = contents.length;

      //iterate through files
      for(int k=0; k<numFilesInDir; ++k)
      {
        String contentName = contents[k].getAbsolutePath();

        if(contentName.endsWith("/.DS_Store"))
          continue;

        Path file = new File(contentName).toPath();

        //if the file is a directory call the parseDir method on it and skip to next file
        if(Files.isDirectory(file))
        {
          parseDir(contentName);
          continue;
        }
        else if(contentName.endsWith(".m")) //if the file is a .m file start program on it
          removePunctuation(contentName);


      }

      //remove intermediate files in the current directory before finishing
      removeIntermediateFiles(path);

  }


    //Removes punctuation from file and place into a file ending with commentsOut.txt
    public static void removePunctuation(String fileName) throws IOException
    {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){

            File newFile = new File(fileName + "commentsOut.txt");
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

        }


    }

    //Convert all camal case to regular case and place into a text file ending with camelCaseOut.txt
        public static void removeCamelCase(String fileName) throws IOException
        {
          try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){

              File newFile = new File(fileName + "camelCaseOut.txt");
              PrintWriter writer = new PrintWriter(newFile);
              while(true)
              {
                  String line = reader.readLine();
                  if(line == null){
                      break;
                  }
                      writer.println(splitCamelCase(line).replaceAll("[0-9]",""));


              }
              reader.close();
              writer.close();
              removeStopWords(moveToOutputFile(newFile.getAbsolutePath()),newFile.getAbsolutePath());
        }
      }

      //Split camal case
      // "testTest" -> "test Test"
      // "TESTTest" -> "TEST Test"
      // "TTest"    -> "T Test"
      // "99Test"   -> "99 Test"
          public static String splitCamelCase(String s) {
            return s.replaceAll(
            String.format("%s|%s|%s",
               "(?<=[A-Z])(?=[A-Z][a-z]+)",
               "(?<=[^A-Z])(?=[A-Z])",
               "(?<=[A-Za-z])(?=[^A-Za-z])"
                          )," ");
          }

    //Create path to the output file
    public static String moveToOutputFile(String nFile)
    {
      int index = nFile.indexOf("/M3");
      String indexPath = nFile.substring(0, index) + "/Output/" + nFile.substring(nFile.lastIndexOf('/'),nFile.indexOf(".m"))+".txt";
      return indexPath;
    }

    //Remove all stop words in file and place in output file
    public static void removeStopWords(String outputFilePath, String nFile) throws IOException
    {


        //link to stop words: http://www.ranks.nl/stopwords
        TreeSet <String> ts = new TreeSet <String>();
        BufferedReader brSet = new BufferedReader(new FileReader("/home/ott109/stopWords.txt"));

        BufferedReader brCheck = new BufferedReader(new FileReader(nFile));

        File newFile = new File(outputFilePath);

        PrintWriter writer = new PrintWriter(newFile);
        String word = "";

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

    //Remove the intermediate txt files that were created in the cleaning process
    public static void removeIntermediateFiles(String path) throws IOException
    {
        File f = new File(path);
        File [] contents = f.listFiles();

        for (int k=0; k<contents.length; ++k)
        {
            String fileName = contents[k].getAbsolutePath();
            if(fileName.contains("mcommentsOut"))
            {
                contents[k].delete();
            }
        }
    }
}
