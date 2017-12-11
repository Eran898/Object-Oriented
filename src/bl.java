package monha3;

import java.io.BufferedReader;

import java.io.File;

import java.io.FileNotFoundException;

import java.io.FileReader;

import java.util.ArrayList;

import java.util.Collection;

import java.util.Scanner;

public class bl {

public static String[][] main(String[] args) throws FileNotFoundException {

int i = 0;//line count of csv

String fileName = "C://Users//eran8_000//Desktop//monha//data.csv";

File file = new File(fileName); //need to read about it

Scanner inputStream = new Scanner(file);

String[][] data = new String[0][];//csv data line count=0 initially

while ((inputStream != null)) {

++i;//increment the line count when new line found

String[][] newdata = new String[i][2];//create new array for data

String strar[] = fileName.split(",");//get contents of line as an array

newdata[i - 1] = strar;//add new line to the array

System.arraycopy(data, 0, newdata, 0, i - 1);//copy previously read values to new array

data = newdata;//set new array as csv data

}

for (String[] strings : data) {

for (String string : strings) {

System.out.print("\t" + string);

}

System.out.println();

}

return data;

}

}
