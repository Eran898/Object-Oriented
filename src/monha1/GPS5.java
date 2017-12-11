package monha1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class GPS5 {
	public static void main(String[] args) throws IOException, InterruptedException {
		final int COPYS = 10;
		int counter = 0;
		List<Integer[]> signals = new ArrayList<Integer[]>();
		
		// set folder and create list of files paths
		String Path = "C://temp2";
		File folder = new File(Path);
		String[] fileList = folder.list();
		
		//creating matrix for save information
		ArrayList<ArrayList<String>> mat = new ArrayList<>();
		
		//creating titles
		//col0
		ArrayList<String> timeCol = new ArrayList<>();
		timeCol.add("Time");
		mat.add(timeCol);
		//col1
		ArrayList<String> idCol = new ArrayList<>();
		idCol.add("ID");
		mat.add(idCol);
		//col2
		ArrayList<String> latCol = new ArrayList<>();
		latCol.add("LAT");
		mat.add(latCol);
		//col3
		ArrayList<String> lonCol = new ArrayList<>();
		lonCol.add("LON");
		mat.add(lonCol);
		//col4
		ArrayList<String> altCol = new ArrayList<>();
		altCol.add("ALT");
		mat.add(altCol);
		//col5
		ArrayList<String> wifiCol = new ArrayList<>();
		wifiCol.add("WIFI Num");
		mat.add(wifiCol);
		
		//for every file - fill matrix
		for (int i = 0; i < COPYS; i++)
		{
			ArrayList<String> ssidCol = new ArrayList<>();
			ssidCol.add("SSID" + i);
			mat.add(ssidCol);
			//col7
			ArrayList<String> macCol = new ArrayList<>();
			macCol.add("MAC" + i);
			mat.add(macCol);
			//col8
			ArrayList<String> freqCol = new ArrayList<>();
			freqCol.add("Freq" + i);
			mat.add(freqCol);
			//col9
			ArrayList<String> signalCol = new ArrayList<>();
			signalCol.add("Signal" + i);
			mat.add(signalCol);	
		}
		
		for (int i = 0; i < fileList.length; i++)
		{
			// creating scanner for reading files
			BufferedReader in = new BufferedReader(new FileReader(Path + "//" + fileList[i]));
			Scanner inputStream = new Scanner(in);
			//handle with notes
			String data = inputStream.nextLine();

			//handle with device information
			data = inputStream.nextLine();
			String[] titles = data.split(",");
			ArrayList<String[]> APs = new ArrayList<>();
			String[] values = null;
			
			// fill information
			data = inputStream.nextLine();
			values = data.split(",");
			mat.get(0).add(values[3]);
			mat.get(1).add(values[1]);
			mat.get(2).add(values[6]);
			mat.get(3).add(values[7]);
			mat.get(4).add(values[8]);
			values = data.split(",");
			signals.add(new Integer[] {counter, Integer.parseInt(values[5])});
			APs.add(new String[] {values[1],values[0],values[4],values[5]});
			
			//fill matrix
			while (inputStream.hasNext()){
				counter++;
				values = data.split(",");
				signals.add(new Integer[] {counter, Integer.parseInt(values[5])});
				APs.add(new String[] {values[1],values[0],values[4],values[5]});
				data = inputStream.nextLine();
			}
			mat.get(5).add(Integer.toString(counter));
			
			Comparator<Integer[]> c = new Comparator<Integer[]>() {
				@Override
				public int compare(Integer[] arg0, Integer[] arg1) {
					final int time1 = arg0[1];
	            	final int time2 = arg1[1];
	            	return Integer.compare(time1, time2);
				}
			};
			Collections.sort(signals, c);
			
			/*
			System.out.println("signals:");
			for (int i = 0; i <COPYS; i++) {
				System.out.println(Arrays.toString(signals.get(i)));
			}
			*/
			
			// fill matrix with COPYS strong APs
			for (int j = 0; j < COPYS; j++)
			{
				mat.get((6+(j*4))).add(APs.get(signals.get(j)[0])[0]);
				mat.get((6+(j*4)+1)).add(APs.get(signals.get(j)[0])[1]);
				mat.get((6+(j*4)+2)).add(APs.get(signals.get(j)[0])[2]);
				mat.get((6+(j*4)+3)).add(APs.get(signals.get(j)[0])[3]);
			}
			
			inputStream.close();
			in.close();
		}
		
		// printing matrix
		/*
		for (int i = 0; i < mat.size(); i++) 
		{
			System.out.println(mat.get(i).toString());
		}
		*/
		
		// write to csv file
		FileWriter writer = new FileWriter("C:\\temp2\\marged.csv");

		for (int i = 0; i < mat.size(); i++) 
		{
			for (int j = 0; j < mat.get(i).size(); j++)
			{
				writer.append(mat.get(i).get(j));
				writer.append(",");
			}
			writer.append("\n");
		}
	    //writer.toString();    
	    writer.flush();
	    writer.close();
	}
}
