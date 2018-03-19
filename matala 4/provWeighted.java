public class provWeighted
{
	private double power = 2;
	private double sigDiff = 0.4;
	private double norm = 10000;
	private double minDiff = 3;
	private double noSignal = -120;
	private double diffNoSignal = 100;
	
	private String[][] mat;
	private String[][] MACS;
	
	public provWeighted(String[][] _mat, String[][] _MACS)
	{
		mat = _mat;
		MACS = _MACS;
	}
	
	public double[] getResult()
	{
		double[][] diff = new double[3][3];
		for (int j = 0; j < 3; j++)
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
					//System.out.println("diff["+j+"]["+count+"] = " + diff[j][count]);
				}
			count = (count + 1) % 3;
			}
		}
		
		double[][] diffWeight = new double[3][3];
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
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
		
		double[] Midans = new double[3];
		Midans[0] = diffWeight[0][0] * diffWeight[1][0] * diffWeight[2][0];
		Midans[1] = diffWeight[0][1] * diffWeight[1][1] * diffWeight[2][1];
		Midans[2] = diffWeight[0][2] * diffWeight[1][2] * diffWeight[2][2];
		
		String sLoc[][] = getStrogestPoint();
		double wLoc[][] = new double[3][3];
		for (int i = 0; i < 3; i++)
		{
			wLoc[i][0] = Double.parseDouble(sLoc[i][1]);
			wLoc[i][1] = Double.parseDouble(sLoc[i][2]);
			wLoc[i][2] = Double.parseDouble(sLoc[i][3]);
		}
		
		double wSum[] = new double[3];
		double sum = 0;
		for (int i = 0; i < MACS.length; i++)
		{
			sum += Midans[i];
		}
		
		/*
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
		*/

		for (int i = 0; i < 3; i++)
		{
			wLoc[i][0] = wLoc[i][0] * Midans[i];
			wLoc[i][1] = wLoc[i][1] * Midans[i];
			wLoc[i][2] = wLoc[i][2] * Midans[i];
			wSum[0] += wLoc[i][0];
			wSum[1] += wLoc[i][1];
			wSum[2] += wLoc[i][2];
		}
		wSum[0] = wSum[0] / sum;
		wSum[1] = wSum[1] / sum;
		wSum[2] = wSum[2] / sum;
		
		return (new double[] {wSum[0], wSum[1], wSum[2]});
	}
	
	private String[][] getStrogestPoint()
	{
		String[][] ans = new String[3][5];
		for (int i = 0; i < ans.length; i++)
		{
			ans[i] = new String[5];
		}
		ans[0][0] = mat[0][0];
		ans[0][1] = mat[0][1];
		ans[0][2] = mat[0][2];
		ans[0][3] = mat[0][3];
		ans[0][4] = mat[0][4];
		int counter = 1;
		int matCounter = 0;
		while (counter < 3)
		{
			ans[counter][0] = ans[counter-1][0];
			while (ans[counter][0].equals(mat[matCounter][0]))
			{
				matCounter++;
			}
			ans[counter][0] = mat[matCounter][0];
			ans[counter][1] = mat[matCounter][1];
			ans[counter][2] = mat[matCounter][2];
			ans[counter][3] = mat[matCounter][3];
			ans[counter][4] = mat[matCounter][4];
			counter++;
		}
		return ans;
	}
}

/*
	for (int i = 0; i < mat.length; i++)
	{
		for (int j = 0; j < mat[0].length; j++)
		{
			System.out.print(", mat["+i+"]["+j+"] = " + mat[i][j]);
		}
		System.out.println();
	}
*/
