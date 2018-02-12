import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;

public class SQLRW
{
	private String url = "jdbc:mysql://localhost:8888/wifis";
	private String user = "root";
	private String password = "root";
	private Connection _con = null;

	public SQLRW()
	{
		url = "jdbc:mysql://localhost:8888/wifis";
		user = "root";
		password = "root";
		_con = null;
	}
	
	public SQLRW(String inUrl, String inUser, String inPassword)
	{
		url = inUrl;
		user = inUser;
		password = inPassword;
		_con = null;
	}

	private void create_table(String[][] e, String table) throws ClassNotFoundException, SQLException
	{
			Class.forName("com.mysql.jdbc.Driver");
			_con = DriverManager.getConnection(url,user, password);
			String myTableName = "CREATE TABLE `"+table+"` ( `Time` TEXT NOT NULL , `ID` TEXT NOT NULL , `Lat` TEXT NOT NULL , `Lon` TEXT NOT NULL , `Alt` TEXT NOT NULL , `SSID` TEXT NOT NULL , `MAC` TEXT NOT NULL , `Ferquency` TEXT NOT NULL , `Signal` TEXT NOT NULL )";
			PreparedStatement preparedStmt = _con.prepareStatement(myTableName);
			preparedStmt.execute();
			String a = Addsamples(e, table);
			PreparedStatement stmt = _con.prepareStatement(a);
			stmt.execute();
			System.out.println("Database created.");
	}
	
	public void add(String[][] e) throws SQLException, ClassNotFoundException
	{
		Class.forName("com.mysql.jdbc.Driver");
		_con = DriverManager.getConnection(url,user, password);
		System.out.println("Connected!");
		boolean exists = tableExist(_con, "samples");
		if(exists)
		{
			String a = Addsamples(e, "samples");
			PreparedStatement preparedStmt = _con.prepareStatement(a);
			preparedStmt.execute();
		}
		else
		{
			create_table(e, "samples");
		}
		System.out.println("Database updated.");
	}

	private boolean tableExist(Connection conn, String tableName) throws SQLException {
		boolean tExists = false;
		ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null);
		while (rs.next())
		{
			String tName = rs.getString("TABLE_NAME");
			if (tName != null && tName.equals(tableName))
			{
				tExists = true;
				break;
			}
		}
		return tExists;
	}

	private String Addsamples(String[][] elements, String table)
	{
		String ans = "INSERT INTO `"+table+"` (`Time`, `ID`, `Lat`, `Lon`, `Alt`, `SSID`, `MAC`, `Ferquency`, `Signal`) VALUES (`"+elements.toString()+"`)";
		for(int i=1; i<elements.length; i++)
		{
			String query = ",("+elements[0].toString()+")";
			ans += query;
		}
		return ans;
	}

	public ArrayList<String> load() throws ClassNotFoundException, SQLException
	{
		ArrayList<String> elements = new ArrayList<>(); 
		Class.forName("com.mysql.jdbc.Driver");
		_con = DriverManager.getConnection(url,user, password);
		if(tableExist(_con, "samples")) {
			String query = "SELECT * FROM samples";
			Statement st = _con.createStatement();   
			ResultSet rs = st.executeQuery(query);
			while (rs.next())

			{
				String p = rs.getString("SSID") +  rs.getString("Time") +  rs.getString("Lon") +  rs.getString("Lat") + rs.getString("Alt") +  rs.getString("MAC") +  rs.getString("Signal") +  rs.getString("Ferquency") +  rs.getString("ID");
				elements.add(p);
			}
			st.close();
		}
		return elements;
	}
}
