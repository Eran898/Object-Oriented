import java.util.ArrayList;
import java.util.Arrays;

public class FindLocations
{
	String[][] mat;
	String _strAns[][];
	
	public FindLocations(String[][] _mat)
	{
		mat = _mat;
		_strAns = null;
	}
	
	public String[][] findLocationsOfMAC(String MAC)
	{
		//creating matrix for save all mac appereances
		ArrayList<String[]> ans = new ArrayList<String[]>();
		for (int i = 0; i < mat.length; i++)
		{
			for (int j = 7; j < mat[i].length; j = j + 4)
			{
				if (MAC.equals(mat[i][j]))
				{
					ans.add(new String[] {mat[i][2], mat[i][3], mat[i][4], mat[i][j+2]});
				}
			}
		}
		String strAns[][] = new String[ans.size()][4];
		for (int i = 0; i < strAns.length; i++)
		{
			strAns[i] = ans.get(i);
			//System.out.println(Arrays.toString(strAns[i]));
		}
		_strAns = strAns;
		return strAns;
	}
	
	public String[][] findLocationsOfMAC(String[][] MAC)
	{
		//creating matrix for save all mac appereances
		ArrayList<String[]> ans = new ArrayList<String[]>();
		for (int i = 0; i < MAC.length; i++)
		{
			for (int k = 0; k < mat.length; k++)
			{
				for (int j = 7; j < mat[k].length; j = j + 4)
				{
					//System.out.print("MAC[i][0].toString() = " + MAC[i][0].toString() + " = ");
					//System.out.println(mat[k][j].toString() + " = mat[k][j].toString()");
					if (MAC[i][0].toString().equals(mat[k][j].toString()))
					{
						//System.out.println(mat[k][0] + mat[k][2] + mat[k][3] + mat[k][4] + mat[k][j+2]);
						String[] tmp = new String[] {MAC[i][0], mat[k][2], mat[k][3], mat[k][4], mat[k][j+2]};
						ans.add(tmp);
					}
				}
			}
		}
		//System.out.println("ans len: " + ans.size());
		String strAns[][] = new String[ans.size()][5];
		for (int i = 0; i < strAns.length; i++)
		{
			strAns[i] = ans.get(i);
			//System.out.println(Arrays.toString(strAns[i]));
		}
		_strAns = strAns;
		return strAns;
	}
	
	public int getNumOfLines()
	{
		return _strAns.length;
	}
}
