import java.util.Arrays;

public class provWeighted
{
	private double power = 2;
	private double sigDiff = 0.4;
	private double norm = 10000;
	private double minDiff = 3;
	private double noSignal = -120;
	private double diffNoSignal = 100;
	
	public provWeighted() 
	{
		
	}
	
	public double[] getResult(String[][] mat, String[][] MACS)
	{
		double[][] diff = new double[MACS.length][mat.length];
		for (int j = 0; j < MACS.length-1; j++)
		{
			//System.out.println(Arrays.toString(MACS[j]));
			int count = 0;
			for (int i = 0; i < mat.length; i++)
			{
				//System.out.println(i);
				//System.out.println(mat[i][0]);
				if (MACS[j][0].toString().equals(mat[i][0]))
				{
					//System.out.println("J = " + j);
					double orgSignal = Double.parseDouble(MACS[j][1]);
					double curSignal = Double.parseDouble(mat[i][4]);
					//System.out.println("curSignal: " + curSignal);
					if (curSignal <= noSignal)
					{
						diff[j][count] = diffNoSignal;
					}
					else if (curSignal < 0)
					{
						diff[j][count] = Math.max(minDiff, Math.abs(orgSignal - curSignal));
					}
					else
					{
						diff[j][count] = -1;
					}
					System.out.println("diff["+j+"]["+count+"] = " + diff[j][count]);
				}
			count++;
			}
		}
		
		/*
		for (int i = 0; i < diff.length; i++)
		{
			for (int j = 0; j < diff[i].length; j++)
			{
				System.out.print(", diff["+i+"]["+j+"] = " + diff[i][j]);
			}
			System.out.println();
		}
		*/
		
		double[][] diffWeight = new double[MACS.length][mat.length];
		for (int i = 0; i < MACS.length; i++)
		{
			for (int j = 0; j < mat.length; j++)
			{
				if (diff[i][j] == -1)
				{
					diffWeight[i][j] = 1;
				}
				else if (diff[i][j] == 0)
				{
					diffWeight[i][j] = 0;
				}
				else
				{
					diffWeight[i][j] = norm / (Math.pow(diff[i][j], sigDiff) * Math.pow(Double.parseDouble(MACS[i][1]), power));
				}
				//System.out.println("diffWeight["+i+"]["+j+"] = " + diffWeight[i][j]);
			}

		}
		
		/*
		for (int i = 0; i < diffWeight.length; i++)
		{
			for (int j = 0; j < diffWeight[0].length; j++)
			{
				System.out.print(", diffWeight["+i+"]["+j+"] = " + diffWeight[i][j]);
			}
			System.out.println();
		}
		*/
		
		double[] Midans = new double[MACS.length];
		for (int i = 0; i < MACS.length; i++)
		{
			for (int j = 0; j < diffWeight.length; j++)
			{
				if (diffWeight[j][i] > 0)
				{
					if (j == 0)
					{
						Midans[i] = diffWeight[j][i];
					}
					else
					{	
						Midans[i] = Midans[i] * diffWeight[j][i];
					}
				}
			}
		}
		System.out.println(Arrays.toString(Midans));
		
		double wLoc[][] = new double[MACS.length][3];
		double wSum[] = new double[3];
		double sum = 0;
		for (int i = 0; i < MACS.length; i++)
		{
			sum += Midans[i];
		}
		for (int i = 0; i < mat.length; i++)
		{
			for (int j = 0; j < MACS.length; j++)
			{
				//System.out.println("Midans["+j+"]: " + Midans[j]);
				if (MACS[j][0].toString().equals(mat[i][0]))
				{
					//System.out.println("MACS["+j+"]: " + MACS[j][0]);
					//System.out.println("mat["+i+"][1] + mat["+i+"][2] + mat["+i+"][3]: " + mat[i][1] + "," + mat[i][2] + "," + mat[i][3]);
					wLoc[j][0] = Double.parseDouble(mat[i][1]) * Midans[j];
					wLoc[j][1] = Double.parseDouble(mat[i][2]) * Midans[j];
					wLoc[j][2] = Double.parseDouble(mat[i][3]) * Midans[j];
					wSum[0] += wLoc[j][0];
					wSum[1] += wLoc[j][1];
					wSum[2] += wLoc[j][2];
				}
			}
		}
		System.out.println("sum: " + sum);
		//System.out.println("wSum[0]: " + wSum[0] + ", wSum[1]: " + wSum[1] + ", wSum[2]: " + wSum[2]);
		
		//wSum[0] = wSum[0] / MACS.length;
		//wSum[1] = wSum[1] / MACS.length;
		//wSum[2] = wSum[2] / MACS.length;
		
		return (new double[] {(wSum[0] / sum), (wSum[1] / sum), (wSum[2] / sum)});
	}
}
