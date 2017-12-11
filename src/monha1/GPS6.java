package monha1;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class GPS6 {
	public static void main(String[] args) throws IOException, InterruptedException {
		final int COPYS = 10;
		int counter = 0;
		List<Integer[]> signals = new ArrayList<Integer[]>();
		
		// set folder and create list of files paths
		String Path = "C:\\temp2";
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
			counter = 0;
			signals.clear();
			// creating scanner for reading files
			BufferedReader in = new BufferedReader(new FileReader(Path + "//" + fileList[i]));
			Scanner inputStream = new Scanner(in);
			//handle with notes
			String data = inputStream.nextLine();

			//handle with device information
			data = inputStream.nextLine();
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
			for (int j = 0; j <signals.size(); j++) {
				System.out.println(Arrays.toString(signals.get(j)));
			}
			
			System.out.println("APs:");
			System.out.println("APs len:" + APs.size());
			for (int j = 0; j <APs.size(); j++) {
				System.out.println(Arrays.toString(APs.get(j)));
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
		
		ArrayList<String> tmpArr = new ArrayList<>(mat.get(0));
		tmpArr.remove(0);
		Comparator<String> c2 = new Comparator<String>()
		{
			@Override
			public int compare(String arg0, String arg1)
			{
				int year0 = Integer.parseInt(arg0.substring(0,3));
				int year1 = Integer.parseInt(arg1.substring(0,3));
				if (year1 != year0)
				{
					year0 = Integer.parseInt(arg0.substring(5,6));
					year1 = Integer.parseInt(arg1.substring(5,6));
					if (year1 != year0)
					{
						year0 = Integer.parseInt(arg0.substring(8,9));
						year1 = Integer.parseInt(arg1.substring(8,9));
						if (year1 != year0)
						{
							year0 = Integer.parseInt(arg0.substring(11,12));
							year1 = Integer.parseInt(arg1.substring(11,12));
							if (year1 != year0)
							{
								year0 = Integer.parseInt(arg0.substring(14,15));
								year1 = Integer.parseInt(arg1.substring(14,15));
								if (year1 != year0)
								{
									year0 = Integer.parseInt(arg0.substring(17,18));
									year1 = Integer.parseInt(arg1.substring(17,18));
								}
							}
						}
					}
				}
				return Integer.compare(year0, year1);
			}
		};
		Collections.sort(tmpArr, c2);
		
		ArrayList<ArrayList<String>> newMat = new ArrayList<>();
		ArrayList<String> tmp = new ArrayList<>();
		for (int j = 0; j < mat.size(); j++)
		{
			tmp.add(mat.get(j).get(0));
		}
		newMat.add(tmp);
		for (int i = 0; i < tmpArr.size(); i++)
		{
			int toAdd = 0;
			for (int j = 1; j < mat.get(0).size(); j++)
			{
				if (tmpArr.get(i).equals(mat.get(0).get(j)))
				{
					toAdd = j;
					break;
				}
			}
			tmp = new ArrayList<>();
			for (int j = 0; j < mat.size(); j++)
			{
				tmp.add(mat.get(j).get(toAdd));
			}
			newMat.add(tmp);
		}
		
		// printing matrix
		for (int i = 0; i < newMat.size(); i++) 
		{
			System.out.println(newMat.get(i).toString());
		}
		
		
		// write to csv file
		FileWriter writer = new FileWriter("C:\\temp2\\JavaOut.csv");

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