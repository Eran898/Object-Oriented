import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class filesParser
{
	private String answare[][];

	public filesParser(String Path) throws FileNotFoundException
	{
		// set folder and create list of files paths
		File folder = new File(Path);
		String[] fileList = folder.list();

		//creating matrix for save information
		ArrayList<String[]> mat = new ArrayList<String[]>();

		System.out.println(Arrays.toString(fileList));
		
		for (int i = 0; i < fileList.length; i++)
		{
			// creating scanner for reading files
			BufferedReader in = new BufferedReader(new FileReader(Path + "//" + fileList[i]));
			@SuppressWarnings("resource")
			Scanner inputStream = new Scanner(in);
			//handle with notes
			String data = inputStream.nextLine();

			//handle with device information
			data = inputStream.nextLine();
			////handle with titles
			data = inputStream.nextLine();

			//fill information till end
			while (inputStream.hasNext())
			{
				String[] values = null;
				String[] spliteData = data.split(",");
				int numOfCols = spliteData.length;
				values = new String[numOfCols];
				for (int j = 0; j < numOfCols; j++)
				{
					values[j] = new String(spliteData[j]);				
				}
				mat.add(values);
				data = inputStream.nextLine();
			}
		}
		answare = mat.toArray(new String[][] {});
	}

	public String[][] getString()
	{
		/*
		// printing information
		System.out.println("prints all lines from csv files");
		for (int i = 0; i < answare.length; i++)
		{
			System.out.println(Arrays.toString(answare[i]));
		}
		*/
		System.out.println("number og lines in all files: " + answare.length);
		return(answare);
	}
}
