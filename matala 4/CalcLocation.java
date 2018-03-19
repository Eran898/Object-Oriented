
public class CalcLocation {

	String[][] mat;
	
	public CalcLocation(String[][] _mat)
	{
		mat = _mat;
	}
	
	public double[] calcLocation(String[][] MACS)
	{
		provWeighted PW = new provWeighted(mat, MACS);
		return PW.getResult();
	}
	
	
	public String[] calcLocation(Integer maxBanches)
	{
		calcWeighted CW = new calcWeighted();
		return CW.getResult(mat, maxBanches);
	}
	
	public String[] calcLocation()
	{
		calcBest CB = new calcBest();
		return CB.getResult(mat);
	}
}
