package monha3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;


public class kml {
	
	public static void csv2kml(String filepath)
	{int line=0;

		try
		{
			FileWriter fw = new FileWriter(filepath,true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			/*****************************************/
			pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			pw.println(System.lineSeparator());
			
			pw.println("<kml xmlns=\"http://www.opengis.net/kml/2.2\">");
			pw.println(System.lineSeparator());
			pw.println("<Document>");
			pw.println(System.lineSeparator());
			// begining of the file*/
			for(int i=0;i<line;i++)
			{   
				pw.println("<Placemark>");
				pw.println(System.lineSeparator());
				pw.println("<name><![CDATA["+temp[i][2]+"]]></name>");
				pw.println(System.lineSeparator());

				pw.println("<description><![CDATA[BSSID: <b>"+temp[i][1]+"</b><br/>Capabilities: <b>[ESS]</b><br/>Frequency: <b>"+temp[i][4]+"</b><br/><br/>Date:");//add ssid 
				pw.println(System.lineSeparator());
			
				pw.println("<b>"+temp[i][3]+"</b>]]></description><styleUrl>#green</styleUrl>");
				pw.println(System.lineSeparator());

				pw.println("<Point>");
				pw.println(System.lineSeparator());

				pw.println("<coordinates>"+temp[i][6]+","+temp[i][5]+","+temp[i][7]+"</coordinates>");
				pw.println("</Point>");
				pw.println(System.lineSeparator());
				pw.println("</Placemark>");
				pw.println(System.lineSeparator());
				//filling the spots
				
				pw.println("</Document>");
				pw.println(System.lineSeparator());
				pw.println("</kml>");
				pw.println(System.lineSeparator());
				 //ending the file
			   
			}
			
			pw.println(timeCol+","+idCol+","+latCol+","+lonCol+","+altCol+","+num+++","+"SSID1"+","+"MAC"+","+"Frequncy"+","+"Signal");
			pw.flush();
			pw.close();
		}
		catch(Exception E)
		{
		}
	public void () throws IOException
	{    convertcsvtotxt("orgnized");
	     String[][]temp=converttocvs(path+"orgnized.txt");
	     
		int rows=countrows(path+"orgnized.txt");
		File file1=new File(path+"googlearth.kml");//creat the kml file
		file1.createNewFile();
		FileWriter writer=new FileWriter(file1);
		////--------------פתיח--------------
		
		///////-----------נקודות----------
		
		/////-----------סגירה--------
		
	  }
	}

}
