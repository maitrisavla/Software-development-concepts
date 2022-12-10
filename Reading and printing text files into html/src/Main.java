import java.awt.*;                          // Import java AWT class
import java.io.File;                         // Import the File class
import java.io.FileNotFoundException;       // Import this class to handle exception errors
import java.io.FileWriter;               // Import the FileWriter class
import java.io.IOException;                // Import the IOException class to handle errors
import java.util.ArrayList;            // import the ArrayList class
import java.util.Scanner;                // Import the Scanner class to read text files

public class Main {
    public static void main(String[] args) throws IOException
    {

        Scanner sc = new Scanner(System.in);                  // Taking input file from the user
        System.out.println("Please enter the file path");       // Print the output
        String filepath = sc.nextLine();
        if(filepath.length() == 0)                            // Condition if no filepath is entered
        {
            System.out.println("Please enter a valid file path");
            System.exit(0);
        }


        ArrayList<String> lines = readFile(filepath);      //Using Function "readFile" to read the given input lines
        lines = blankLineCorrection(lines);

        if(lines.size() == 0)                      // Condition if there are no lines in the text file
        {
            System.out.println("The file does not have any lines in it");
            System.exit(0);
        }

        ArrayList<String> outputLines = convertHTML(lines);  // Converting the input lines to the desired outputLines
        outputLines = paragraphBeforeListCorrection(outputLines);

        printTextFile(outputLines);
        outputFile("TextFile",outputLines);
        //displayInBrowser("TextFile.html");
    }


    public static ArrayList<String> paragraphBeforeListCorrection(ArrayList<String> outputlines)     // Handling p tag and ul tag
    {
        ArrayList<String> newOutputLines = new ArrayList<>();
        for(int i = 0 ; i < outputlines.size() ; i ++)
        {
            if(i+1 < outputlines.size() && outputlines.get(i).equals("<p>") && outputlines.get(i+1).equals("<ul>"))
            {
                continue;
            }
            else if(i+1 < outputlines.size() && outputlines.get(i).equals("</ul>") && outputlines.get(i+1).equals("</p>"))
            {
                newOutputLines.add(outputlines.get(i));
                i = i + 1;
            }
            else
            {
                newOutputLines.add(outputlines.get(i));
            }
        }
        return newOutputLines;
    }

    public static ArrayList<String> blankLineCorrection(ArrayList<String> lines)
    {
        while(lines.get(0).length() == 0)         // Executes if there are blank lines before the title
        {
            lines.remove(0);
        }

        ArrayList<String> output = new ArrayList<>();

        for(int i = 0 ; i < lines.size() ; i ++)
        {
            String currentLine = lines.get(i);
            if(currentLine.length() == 0)
            {
                while(i+1 != lines.size() && lines.get(i+1).length() == 0)
                {
                    i++;
                }
            }
            output.add(currentLine);
        }

        return output;

    }

    public static ArrayList<String> convertHTML(ArrayList<String> lines)
    {
        ArrayList<String> outputLines = new ArrayList<>();  // Creating a new arraylist
        outputLines.add("<html>");

        boolean paraStarted = false;
        boolean bodyStarted = false;
        boolean listStarted = false;

        for(String line : lines)     // Iterating the loop for each line
        {
            if(line.contains("title:"))        // Executes if there is title
            {
                outputLines.add("<head>");
                outputLines.add("<title>" + line.substring(7) + "</title>");   // Getting the string after six charcters
                outputLines.add("</head>");


                if(!bodyStarted)
                {
                    outputLines.add("<body>");
                    bodyStarted = true;
                }
            }
            else if(line.length()==0)    // Executes for blank line in between
            {
                if(listStarted)
                {
                    listStarted = !listStarted;
                    outputLines.add("</ul>");
                }

                if(!paraStarted)
                {
                    outputLines.add("<p>");
                    paraStarted = !paraStarted;
                }
                else
                {
                    outputLines.add("</p>");
                    outputLines.add("<p>");
                }
            }
            else if(line.startsWith("-"))              // Executes if there is list in the code
            {
                if(!listStarted)
                {
                    listStarted = !listStarted;
                    outputLines.add("<ul>");
                }

                outputLines.add("<li>" + line.substring(1) + "</li>");     // Getting the string after first character
            }
            else
            {
                if(!bodyStarted)
                {
                    bodyStarted = !bodyStarted;
                    outputLines.add("<body>");
                }

                if(listStarted)
                {
                    listStarted = !listStarted;
                    outputLines.add("</ul>");
                }

                StringBuilder sb = new StringBuilder();
                String[] words = line.split(" ");   // Splitting each line's each word by space

                for(String word : words)               // Iterating the loop if words have any of the defined annotations
                {
                    if(word.contains("*") || word.contains("_") || word.contains("%"))
                    {
                        if(word.equals("*") || word.equals("_") || word.equals("%"))
                        {
                            sb.append(word);
                            continue;
                        }

                        if(word.startsWith("*") && word.endsWith("*"))   //Switching to the loop
                        {
                            sb.append("<b>");
                            sb.append(word.substring(1,word.length()-1));
                            sb.append("</b>");
                        }
                        else if(word.startsWith("*"))
                        {
                            sb.append("<b>");
                            sb.append(word.substring(1));
                        }
                        else if(word.endsWith("*"))
                        {
                            sb.append(word.substring(0,word.length()-1));
                            sb.append("</b>");
                        }

                        if(word.startsWith("_") && word.endsWith("_"))
                        {
                            sb.append("<i>");
                            sb.append(word.substring(1,word.length()-1));
                            sb.append("</i>");
                        }
                        else if(word.startsWith("_"))
                        {
                            sb.append("<i>");
                            sb.append(word.substring(1));
                        }
                        else if(word.endsWith("_"))
                        {
                            sb.append(word.substring(0,word.length()-1));
                            sb.append("</i>");
                        }


                        if(word.startsWith("%") && word.endsWith("%"))
                        {
                            sb.append("<u>");
                            sb.append(word.substring(1,word.length()-1));
                            sb.append("</u>");
                        }
                        else if(word.startsWith("%"))
                        {
                            sb.append("<u>");
                            sb.append(word.substring(1));
                        }
                        else if(word.endsWith("%"))
                        {
                            sb.append(word.substring(0,word.length()-1));
                            sb.append("</u>");
                        }
                    }
                    else if(word.startsWith("!"))
                    {
                        sb.append("<b>");
                        sb.append(word.substring(1));
                        sb.append("</b>");
                    }
                    else
                    {
                        sb.append(word);
                    }
                    sb.append(" ");
                }
                outputLines.add(sb.toString());
            }
        }

        if(listStarted)
        {
            outputLines.add("</ul>");
        }

        if(paraStarted)
        {
            outputLines.add("</p>");
        }
        outputLines.add("</body>");
        outputLines.add("</html>");

        return outputLines;
    }

    public static void printTextFile(ArrayList<String> outputLines)  // Printing the desired outputLines
    {
        for(String str : outputLines)
        {
            System.out.println(str);
        }
    }

    public static void displayInBrowser(String filename) throws IOException       // Displays the IOException filename
    {
        File htmlFile = new File(filename);
        //Desktop.getDesktop().browse(htmlFile.toURI());
    }

    public static void outputFile(String filename, ArrayList<String> outputList) throws IOException    // displays the outputfile with lineseparator
    {
        FileWriter writer = new FileWriter(filename + ".html");
        for(String str: outputList) {
            writer.write(str + System.lineSeparator());
        }
        writer.close();
    }


    public static ArrayList<String> readFile(String filename)  // Reads the newfile arraylist
    {
        ArrayList<String> lines = new ArrayList<>();
        try {                                                  // Trying to read the exception
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine())                      // Iterates whileloop if file has nextline
            {
                String data = myReader.nextLine();
                lines.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e)                      // Exception is thrown when file not found
        {
            System.out.println("An error occurred.");
            System.exit(0);
        }
        return lines;
    }
}