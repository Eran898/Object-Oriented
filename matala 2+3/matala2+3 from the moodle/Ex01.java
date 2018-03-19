import java.io.IOException;

public class Ex01
{
	public static void main(String[] args) throws IOException
	{
		// E:\\privateLessons\\OOPMaor\\EX01\\foldereader\\
		// E:\\wifi_logs\\JavaOut.csv
		ParseDB parseDB = new ParseDB(args[0]);
		parseDB.printMat();
		parseDB.createCSV(args[1]);
	}
}