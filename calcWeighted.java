import java.util.Arrays;
import java.util.Comparator;

public class calcWeighted
{
	public calcWeighted()
	{
		
	}
	
	public String[] getResult(String[][] mat, int maxBanches)
	{
		double lat = 0;
		double lon = 0;
		double alt = 0;
		double signal = 0;
		double SignalRibua = 0;
		Arrays.sort(mat, new Comparator<String[]>()
		{
            @Override
            public int compare(final String[] arg0, final String[] arg1)
            {
            	return Double.compare(Double.parseDouble(arg0[0]), Double.parseDouble(arg1[0]));
            }
		});
		
		for (int i = 0; i < maxBanches && i < mat.length; i++)
		{
			System.out.println(Arrays.toString(mat[i]));
			signal = Double.parseDouble(mat[i][3]);
			System.out.println("signal " + i + ": " + signal);
			double tmp = Math.pow(signal, 2);
			System.out.println("tmp " + i + ": " + tmp);
			SignalRibua = SignalRibua + (1/tmp);
			System.out.println("signalRibua " + i + ": " + SignalRibua);
			lat += Double.parseDouble(mat[i][0]) / tmp;
			System.out.println("lat " + i + ": " + lat);
			lon += Double.parseDouble(mat[i][1]) / tmp;
			System.out.println("lon " + i + ": " + lon);
			alt += Double.parseDouble(mat[i][2]) / tmp;
			System.out.println("alt " + i + ": " + alt);
		}
		double latRes = lat/SignalRibua;
		double lonRes = lon/SignalRibua;
		double altRes = alt/SignalRibua;
		System.out.println(latRes);
		System.out.println(lonRes);
		System.out.println(altRes);
		return (new String[] {"" + latRes, "" + lonRes, "" + altRes});
	}
}
