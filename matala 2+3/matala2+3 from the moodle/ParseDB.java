import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ParseDB
{
	String folderPath;
	String filesList[];
	final int COPYS = 10;
	//creating matrix for save information
	ArrayList<ArrayList<String>> mat = new ArrayList<>();
	List<Integer[]> signals = new ArrayList<Integer[]>();

	public ParseDB(String path) throws IOException
	{
		folderPath = path;
		// set folder and create list of files paths
		File folder = new File(path);
		filesList = folder.list();

		//creating titles
		//col0
		ArrayList<String> timeCol = new ArrayList<>();
		//timeCol.add("Time");
		mat.add(timeCol);
		//col1
		ArrayList<String> idCol = new ArrayList<>();
		//idCol.add("ID");
		mat.add(idCol);
		//col2
		ArrayList<String> latCol = new ArrayList<>();
		//latCol.add("LAT");
		mat.add(latCol);
		//col3
		ArrayList<String> lonCol = new ArrayList<>();
		//lonCol.add("LON");
		mat.add(lonCol);
		//col4
		ArrayList<String> altCol = new ArrayList<>();
		//altCol.add("ALT");
		mat.add(altCol);
		//col5
		ArrayList<String> wifiCol = new ArrayList<>();
		//wifiCol.add("WIFI Num");
		mat.add(wifiCol);

		//for every file - fill matrix
		for (int i = 0; i < COPYS; i++)
		{
			ArrayList<String> ssidCol = new ArrayList<>();
			//ssidCol.add("SSID" + i);
			mat.add(ssidCol);
			//col7
			ArrayList<String> macCol = new ArrayList<>();
			//macCol.add("MAC" + i);
			mat.add(macCol);
			//col8
			ArrayList<String> freqCol = new ArrayList<>();
			//freqCol.add("Freq" + i);
			mat.add(freqCol);
			//col9
			ArrayList<String> signalCol = new ArrayList<>();
			//signalCol.add("Signal" + i);
			mat.add(signalCol);	
		}

		createDB();
		sortByDates();
		routMat();
	}

	@SuppressWarnings("resource")
	private void createDB() throws IOException
	{
		for (int i = 0; i < filesList.length; i++)
		{
			int counter = 0;
			signals.clear();
			// creating scanner for reading files
			BufferedReader in = new BufferedReader(new FileReader(folderPath + "//" + filesList[i]));
			Scanner inputStream = new Scanner(in);
			String[] values = null;

			//handle with device information
			String data = inputStream.nextLine();
			values = data.split(",");
			String modelName = values[2] + values[4];

			data = inputStream.nextLine();
			ArrayList<String[]> APs = new ArrayList<>();
			// fill information
			data = inputStream.nextLine();
			values = data.split(",");
			mat.get(0).add(values[3]);
			mat.get(1).add(modelName);
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
	}

	private void sortByDates()
	{
		ArrayList<String> datesArr = new ArrayList<>(mat.get(0));
		datesArr.remove(0);
		Comparator<String> c2 = new Comparator<String>()
		{
			@Override
			public int compare(String arg0, String arg1)
			{
				//System.out.println("year0: " + arg0);
				//System.out.println("year1: " + arg1);

				int year0 = Integer.parseInt(arg0.substring(0,3).replaceAll("/", ""));
				int year1 = Integer.parseInt(arg1.substring(0,3).replaceAll("/", ""));
				if (year1 != year0)
				{
					year0 = Integer.parseInt(arg0.substring(4,6).replaceAll("/", ""));
					year1 = Integer.parseInt(arg1.substring(4,6).replaceAll("/", ""));
					if (year1 != year0)
					{
						year0 = Integer.parseInt(arg0.substring(8,9).replaceAll("/", ""));
						year1 = Integer.parseInt(arg1.substring(8,9).replaceAll("/", ""));
						if (year1 != year0)
						{
							year0 = Integer.parseInt(arg0.substring(11,12).replaceAll("/", ""));
							year1 = Integer.parseInt(arg1.substring(11,12).replaceAll("/", ""));
							if (year1 != year0)
							{
								year0 = Integer.parseInt(arg0.substring(14,15).replaceAll("/", ""));
								year1 = Integer.parseInt(arg1.substring(14,15).replaceAll("/", ""));
								if (year1 != year0 && arg0.length() > 17 && arg1.length() > 17)
								{
									year0 = Integer.parseInt(arg0.substring(17,18).replaceAll("/", ""));
									year1 = Integer.parseInt(arg1.substring(17,18).replaceAll("/", ""));
								}
							}
						}
					}
				}
				return Integer.compare(year0, year1);
			}
		};
		Collections.sort(datesArr, c2);
		
		ArrayList<ArrayList<String>> newMat = new ArrayList<>();
		ArrayList<String> tmp = new ArrayList<>();
		for (int j = 0; j < mat.size(); j++)
		{
			tmp.add(mat.get(j).get(0));
		}
		newMat.add(tmp);
		for (int i = 0; i < datesArr.size(); i++)
		{
			int toAdd = 0;
			for (int j = 1; j < mat.get(0).size(); j++)
			{
				if (datesArr.get(i).equals(mat.get(0).get(j)))
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
		
		mat = newMat;
	}
	
	private void routMat()
	{
		ArrayList<ArrayList<String>> newMat = new ArrayList<ArrayList<String>>();
		for (int i = 1; i < mat.get(0).size(); i++)
		{
			ArrayList<String> tmpArr = new ArrayList<String>();
			for (int j = 0; j < mat.size(); j++)
			{
				tmpArr.add(mat.get(j).get(i));
			}
			newMat.add(tmpArr);
		}

		mat = newMat;
	}
	
	public void createCSV(String path) throws IOException
	{
		// write to csv file
		FileWriter writer = new FileWriter(path + "csvOutputFile");

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
	
	public void printMat()
	{
		// printing NEW matrix
		for (int i = 0; i < mat.size(); i++) 
		{
			System.out.println(mat.get(i).toString());
		}
	}
}
