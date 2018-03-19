import java.io.IOException;
import java.util.Arrays;

public class Ex02
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
		// E:\\privateLessons\\OOPMaor\\EX02\\Ex2\\wifi_logs
		// String MACS[][] = {{"1c:b9:c4:16:06:bc", "-50"}, {"8c:0c:90:2e:16:88", "-70"}, {"c2:6c:ac:a0:7b:4d", "-110"}};

		filesParser FP = new filesParser(args[0]);
		FindLocations FL = new FindLocations(FP.getString());

		if ("0".equals(args[1]))
		{
			CalcLocation CL = new CalcLocation(FL.findLocationsOfMAC(args[2]));
			System.out.println(Arrays.toString(CL.calcLocation()));
		}
		else if ("1".equals(args[1]))
		{
			CalcLocation CL = new CalcLocation(FL.findLocationsOfMAC(args[2]));
			System.out.println(Arrays.toString(CL.calcLocation(Integer.parseInt(args[3]))));
		}
		else if ("2".equals(args[1]))
		{
			String MACS[][] = new String[(args.length-2)/2][2];
			int counter = 0;
			for (int j = 2; j < args.length; j = j + 2)
			{
				MACS[counter][0] = args[j];
				MACS[counter][1] = args[j+1];
				counter++;
			}
			CalcLocation CL = new CalcLocation(FL.findLocationsOfMAC(MACS));
			System.out.println(Arrays.toString(CL.calcLocation(MACS)));
		}
	}
}