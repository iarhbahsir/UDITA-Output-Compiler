package uditaCompilerPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TextToCompiledJava 
{
	public static void main(String args[]) throws IOException
	{
		
		System.out.println("Enter the location of the output file to compile.");
		
		Scanner locations = new Scanner(System.in);
		
		String fileLocation = locations.nextLine();
		
		System.out.println("Enter the directory in which compiled files will be located.");
		
		String compileLocation = locations.nextLine();
		
		locations.close();
		
		System.out.println("\nCompiling...\n");
		
		//String fileLocation = "/home/rishabh/jpf-test/new-refactorings-test-inputs";
		
		//take in text file
		File inputFile = new File(fileLocation);
		Scanner sc = new Scanner(inputFile);
		String inputContents = "";
		
		while(sc.hasNextLine())
		{
			inputContents += sc.nextLine();
		}
		
		sc.close();
		
		//divide file into array of strings using <input> as divider
		String[] inputDivided =  inputContents.split("<input>");
		
		//remove everything after </input> from each element of array (including </input>)
		//clear any elements that are not relevant
		for(int i = 0; i < inputDivided.length; i++)
		{
			int endOfContent = inputDivided[i].indexOf("</input>");
			
			if(endOfContent != -1)
			{
				inputDivided[i] = inputDivided[i].substring(0, endOfContent);
			}
			
			else
			{
				inputDivided[i] = "";
			}
		}

		//create new directory (folder on desktop)
		//set base name
		File newDirectory = new File(compileLocation + "/Compiled-UDITA-Files");
		newDirectory.mkdir();
		String baseName = "NFI-Part-";
		int fileCounter = 0;
		
		//iterate through array and create new java file in new directory of base name + counter
		//place strings [counter] into new java file
		//report progress
		
		for(String fileContent: inputDivided)
		{
			if(!(fileContent.equals("")))
			{
				String dirName = newDirectory.getAbsolutePath() + "/" + baseName + fileCounter + "-Dir"; 
				File toCompileDir = new File(dirName);
				toCompileDir.mkdir();
				
				String fileName = dirName + "/" + baseName + fileCounter + ".java";
				File toCompile = new File(fileName);
				toCompile.createNewFile();
				
				FileWriter fw = new FileWriter(fileName);
				fw.write(fileContent, 0, fileContent.length());
				fw.close();
				
				String[] compileCommand = new String[2];
				compileCommand[0] = "javac";
				compileCommand[1] = fileName;
				Runtime.getRuntime().exec(compileCommand);
				
				double progress = ((double) Math.round((fileCounter + 1)/((double) inputDivided.length)*10000))/100;
				System.out.println("Files Compiled: " + (fileCounter + 1) + "/" + inputDivided.length + " (" + progress + " %)");
			
				fileCounter++;
			}
		}
		
		System.out.println("\nCompiled");
	}
}