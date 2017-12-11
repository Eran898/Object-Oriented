package monha1;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Style;

public class trykml {

	public static void main(String[] args) throws IOException
	{
		final Kml kml = new Kml();
		Document doc = kml.createAndSetDocument().withName("kml").withOpen(true);		
		String csvFile="C:\\temp2\\JavaOut.csv";
		String line = "";
		String cvsSplitBy = ",";
		int numOfRows = 46;
		int counter = 0;
		ArrayList<String[]> mat = new ArrayList<>();
		// create a Folder
				Folder folder = doc.createAndAddFolder();
				folder.withName("Continents with Earth's surface").withOpen(true);
		
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){
		br.readLine(); // this will read the first line
			while ((line = br.readLine()) != null)
			{
				String[] column = line.split(cvsSplitBy);
				if (counter == 0)
				{
					for (int i = 1; i < column.length; i++)
					{
						
						mat.add(new String[numOfRows]);
						
					}
				}
				for (int i = 1; i < column.length; i++)
				{
					//mat.get(i-1)[counter] = column[0] + ": " + column[i];
					mat.get(i-1)[counter] = column[i];
					
				}
				counter++;
				/*
					double lat=Double.parseDouble(column[2]);
					double lon=Double.parseDouble(column[3]);
					double alt=Double.parseDouble(column[4]);
					String id=column[1].replace("model=", "");
					*/
				}
			}
		
		for (String[] s : mat)
		{
			// creating kml
			// create Placemark elements//1 and 2 lat s45=null
			createPlacemarkWithChart(doc, folder, s[0], s[4], s[5], s[6], s[7], s[8], s[9],s[10], s[11], s[12], s[13], s[14],s[15], s[16], s[17], s[18], s[19],s[20], s[21], s[22], s[23], s[24],s[25], s[26], s[27], s[28], s[29],s[30], s[31], s[32], s[33], s[34],s[35], s[36], s[37], s[38], s[39],s[40], s[41], s[42], s[43], s[44], s[45], Double.parseDouble(s[2]), Double.parseDouble(s[1]), Double.parseDouble(s[3]));
		}
		// print and save
		kml.marshal(new File("kml.kml"));
	}	

	private static void createPlacemarkWithChart(Document doc, Folder folder, String string, String string2,
			String string3, String string4, String string5, String string6, String string7, String string8,
			String string9, String string10, String string11, String string12, String string13, String string14,
			String string15, String string16, String string17, String string18, String string19, String string20,
			String string21, String string22, String string23, String string24, String string25, String string26,
			String string27, String string28, String string29, String string30, String string31, String string32,
			String string33, String string34, String string35, String string36, String string37, String string38,
			String string39, String string40, String string41, String string42, String string43, double parseDouble,
			double parseDouble2, double parseDouble3)
	{
			Icon icon = new Icon()
			    .withHref("http://maps.google.com/mapfiles/ms/icons/blue-dot.png");
			Style style = doc.createAndAddStyle();
			style.withId("style_" + string2) // set the stylename to use this style from the placemark
			    .createAndSetIconStyle().withScale(0.8).withIcon(icon); // set size and icon
			style.createAndSetLabelStyle().withColor("5000FF00").withScale(1.0); // set color and size for the name
			Placemark placemark = folder.createAndAddPlacemark();
			// use the style for each continent
			placemark.withName(string)
			    .withStyleUrl("#style_" + string)
			    // 3D chart imgae
			    .withDescription(
			        "Time: <b>"+ string +"</b><br/>DEVICE: <b>"+ "NONE" +"</b><br/>SSID: <b>"+ "NONE" +"</b><br/>MAC: <b>"+ "NONE" +"</b><br/>Number of WIFIs: <b>"+ string2 + "</b><br/>SSID1: <b>"+ string3 + "</b><br/>MAC1: <b>"+ string4 + "</b><br/>Frequency1: <b>"+ string5 + "</b><br/>Signal1: <b>"+ string6 + "</b><br/>SSID2: <b>"+ string7 + "</b><br/>MAC2: <b>"+ string8 + "</b><br/>Frequency2: <b>"+ string9 + "</b><br/>Signal2: <b>"+ string10 + "</b><br/>SSID3: <b>"+ string11 + "</b><br/>MAC3: <b>"+ string12 + "</b><br/>Frequency3: <b>"+ string13 + "</b><br/>Signal3: <b>"+ string14 + "</b><br/>SSID4: <b>"+ string15 + "</b><br/>MAC4: <b>"+ string16 + "</b><br/>Frequency4: <b>"+ string17 + "</b><br/>Signal4: <b>"+ string18 + "</b><br/>SSID5: <b>"+ string19 + "</b><br/>MAC5: <b>"+ string20 + "</b><br/>Frequency5: <b>"+ string21 + "</b><br/>Signal5: <b>"+ string22 + "</b><br/>SSID6: <b>"+ string23 + "</b><br/>MAC6: <b>"+ string24 + "</b><br/>Frequency6: <b>"+ string25 + "</b><br/>Signal6: <b>"+ string26 + "</b><br/>SSID7: <b>"+ string27 + "</b><br/>MAC7: <b>"+ string28 + "</b><br/>Frequency7: <b>"+ string29 + "</b><br/>Signal7: <b>"+ string30 + "</b><br/>SSID8: <b>"+ string31 + "</b><br/>MAC8: <b>"+ string32 + "</b><br/>Frequency8: <b>"+ string33 + "</b><br/>Signal8: <b>"+ string34 + "</b><br/>SSID9: <b>"+ string35 + "</b><br/>MAC9: <b>"+ string36 + "</b><br/>Frequency9: <b>"+ string37 + "</b><br/>Signal9: <b>"+ string38 + "</b><br/>SSID10: <b>"+ string39 + "</b><br/>MAC10: <b>"+ string40 + "</b><br/>Frequency10: <b>"+ string41 + "</b><br/>Signal10: <b>"+ string42)
			    //<![CDATA[Time: <b></b><br/>ID: <b></b><br/>SSID: <b></b><br/>MAC: <b></b><br/>Frequency: <b></b><br/>Signal: <b></b>]]>
			    //"+"
			    // coordinates and distance (zoom level) of the viewer
			    .createAndSetLookAt().withLongitude(parseDouble).withLatitude(parseDouble2).withAltitude(parseDouble3);
			
			placemark.createAndSetPoint().addToCoordinates(parseDouble, parseDouble2); // set coordinates
		}

}
//https://stackoverflow.com/questions/46027460/how-to-pass-export-kml-data-from-java