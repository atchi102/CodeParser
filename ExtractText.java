import java.util.ArrayList;
import java.util.TreeSet;
import java.nio.file.*;

import java.io.*;

//takes MATLAB source code and parses out the comments. From the comments, removes punctuation, stop words, and numbers

public class ExtractText {

    public static void main(String[] args) throws IOException
    {

        iterateFiles();
        //removeIntermediateFiles();
    }

    public static void iterateFiles() throws IOException
    {
      //
      // File f = new File("/Users/abigailatchison/Desktop/MLAT/Files");
      //  File [] contents = f.listFiles();
      //
      //  int numFilesInFiles=contents.length;
      //
      //  for (int k=0; k<numFilesInFiles;++k)
      //  {
      //      String name = contents[k].getAbsolutePath();
      //
      //      if (name.endsWith("/.DS_Store"))
      //          continue;
      //
      //      File dir = new File(name);
      //      File [] dirContents = dir.listFiles();
      //
      //      int numFilesInDir = dirContents.length;
      //
      //      for (int i=0; i< numFilesInDir; ++i)
      //      {
      //          String fileName = dirContents[i].getAbsolutePath();
      //
      //
      //          if (fileName.endsWith("/.DS_Store"))
      //              continue;
      //
      //          System.out.println(fileName);
      //         //  int index = fileName.indexOf("/Files");
      //         //  String indexPath = fileName.substring(0, index) + "/Output" + foldersName.substring(index);
      //
      //          if (!fileName.endsWith(".m"))
      //              continue;
      //
      //          else
      //              removePunctuation(fileName);
      //      }
      //
      //  }
      parseDir("/Users/abigailatchison/Desktop/MLAT/Files");
    }

  public static void parseDir(String path) throws IOException
  {
      File currentDir = new File(path);
      File[] contents = currentDir.listFiles();
      int numFilesInDir = contents.length;

      for(int k=0; k<numFilesInDir; ++k)
      {
        String contentName = contents[k].getAbsolutePath();

        if(contentName.endsWith("/.DS_Store"))
          continue;

        Path file = new File(contentName).toPath();

        if(Files.isDirectory(file))
        {
          parseDir(contentName);
          continue;
        }
        else if(contentName.endsWith(".m"))
          removePunctuation(contentName);
      }
  }

    //takes comments from MATLAB source code, removes punctuation and stop words, puts all information into txt file
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
    public static String moveToOutputFile(String nFile)
    {
      int index = nFile.indexOf("/Files");
      String indexPath = nFile.substring(0, index) + "/Output/" + nFile.substring(nFile.lastIndexOf('/'),nFile.indexOf(".m"))+".txt";
      return indexPath;
    }

    public static void removeStopWords(String outputFilePath, String nFile) throws IOException
    {


        //link to stop words: http://www.ranks.nl/stopwords
        TreeSet <String> ts = new TreeSet <String>();
        BufferedReader brSet = new BufferedReader(new FileReader("/Users/abigailatchison/Desktop/MLAT/stopWords.txt"));
        System.out.println("after first buffered reader");

        System.out.println("Name = "+nFile);
        BufferedReader brCheck = new BufferedReader(new FileReader(nFile));

        File newFile = new File(outputFilePath);

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

    //removes the intermediate txt files so that there aren't multiple unneeded files and renames finished file
    public static void removeIntermediateFiles() throws IOException
    {
        File f = new File("/Users/abigailatchison/Desktop/MLAT/Files/IDFProgram");
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
}
