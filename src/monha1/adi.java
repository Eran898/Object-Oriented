package monha1;

public class adi {

		public static int[][] Mat(char [] x, char [] y){

			int n = x.length + 1;
			int m = y.length + 1;

			int num = 0;
			int [][] ans = new int [n][m];

			for (int i = 1; i < ans.length; i++) {
				for (int j = 1; j < ans[0].length; j++) {
					if (x[i-1] == y[j-1]){
						ans[i][j] = (ans[i-1][j-1] + 1);
					}
					else {
						ans[i][j] = Math.max(ans[i-1][j], ans[i][j-1]);
					}
				}

			}	
			num = ans[n-1][m-1]; //the length of the lcs
			return ans;
		}

		public static String One(char [] a, char [] b){
			String ans = "";
			int [][] temp = Mat(a,b);

			for (int i = 0; i < temp.length; i++) {
				for (int j = 0; j < temp[0].length; j++) {
					System.out.print("," +temp[i][j]);
				}
				System.out.println();
			}

			int n = a.length;
			int m = b.length;
			int k = temp[n][m];

				for (int i = n;k > 0&& i >= 0;) {
					for (int j = m;k > 0&& j >= 0;) {
						if (a[i-1] == b[j-1]){
							ans = a[i-1] + ans;
							i--;
							j--;
							k--;

						}
						else {
							if(temp[i-1][j] >= temp[i][j-1])

								i--;
							else
								j--;
						}
					}
				}
			
			return ans;

		}

		public static void main(String[] args) {

			char [] n = { 'C','A','R','K' } ;
					
			char [] m = { 'E','T','A','K' } ;
			System.out.println(One(n,m));
		}
	}

