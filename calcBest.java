public class calcBest
{
	public calcBest()
	{
	}
	
	public String[] getResult(String[][] mat)
	{
		int indexOfMax = -1;
		double curMax = 0;
		for (int i = 0; i < mat.length; i++)
		{
			double tmp = Double.parseDouble(mat[i][3]);
			if (tmp < curMax)
			{
				curMax = tmp;
				indexOfMax = i;
			}
		}
		if (indexOfMax == -1)
		{
			return (new String[] {"not-found"});
		}
		return mat[indexOfMax];
	}
}
