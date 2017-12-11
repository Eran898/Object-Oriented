/*package monha3;

import java.awt.Dimension;
import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.*;
import java.nio.*;
import java.util.ArrayList;
 import java.io.*;
		 import java.util.*;
public class two {
	public static void main(String[] args){
		try{
			List<String> lines = File.readAllLines(Paths.get("C://Users//eran8_000//Desktop//monha//data.csv"));
			for (String line :lines){
				line = line.replace("\"","");
				String []result =line.split(",");
				
			}
		}
		catch (Exception e)
		{System.out.println(e.getMessage());
		}
}