package holidays.LoginDetails;

import java.util.*;

import holidays.customer.LoginInfo;

import java.io.*;

public class Login {

	private BufferedReader readFile;
	HashMap<String, String> loginData = new HashMap<String, String>();

	public Login() {
		loaddata();
	}

	private void loaddata() {
		try {
			readFile = new BufferedReader(new FileReader("src/holidays/datacontents/file/logindata.txt"));

			String sLine = "";
			try {
				while ((sLine = readFile.readLine()) != null) {
					String parts[] = sLine.split("#");
					loginData.put(parts[0], parts[1]);
				}
				// in.close();
				//System.out.println(loginData.toString());
			}

			catch (Exception ex) {
				System.out.println(ex.getMessage());
			}

			finally {
				try {
					readFile.close();
				}
				catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}

		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public Boolean validateUser(LoginInfo userInfo) {
		try {
			String value;
			value = (String) loginData.get(userInfo.getUserName());
			if (value != null && value.equals(userInfo.getPassword()))
				return true;
			else
				return false;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}

	}

	public Boolean registerNewUser(LoginInfo userInfo) {
		BufferedWriter writeFile = null;
		try {
			writeFile = new BufferedWriter(new FileWriter("src/holidays/datacontents/file/logindata.txt", true));
			if (loginData.containsKey(userInfo.getUserName())) {
				return false;
			} else {
				loginData.put(userInfo.getUserName(), userInfo.getPassword());
				writeFile.newLine();
				writeFile.write(userInfo.getUserName() + "#" + userInfo.getPassword());
				/*System.out.println(loginData.toString());*/
				return true;
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		} finally {
			try {
				writeFile.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
