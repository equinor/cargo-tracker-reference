package com.equinor.cargotrackerreference;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import com.equinor.cargotrackerreference.FirsttimeSqlDbSetup;

/**
 * This test is a bit expensive to run automatically, thus @Ignore
 * 
 * @author NIAND
 *
 */

public class FirsttimeSqlDbSetupTest {

	@Test
	@Ignore
	public void test() {		
		
		System.setProperty("DB_SERVER_ADMIN_USER", "ct-niand");
		System.setProperty("DB_SERVER_ADMIN_PASSWORD", "<insert here>");
		System.setProperty("DB_APP_USER", "ctapp");
		System.setProperty("DB_APP_PASSWORD", "<insert here>");
				
		FirsttimeSqlDbSetup.verify();
	}
	
	@Test
	public void testConnectionUrl() {		
		
		String inputJdbcString =
				"jdbc:sqlserver://ct-niand.database.windows.net:1433;database=ct;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
		
		String expectedString1 =
				"jdbc:sqlserver://ct-niand.database.windows.net:1433;database=ct;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;user=ctapp@ct-niand;password=password;";
		
		String string1 = FirsttimeSqlDbSetup.getConnectionUrl(
							inputJdbcString,
							"ctapp",
							"password",
							"ct");
		
		
		assertEquals(expectedString1, string1);
		
		String expectedString2 =
				"jdbc:sqlserver://ct-niand.database.windows.net:1433;database=master;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;user=ct-niand@ct-niand;password=password;";
		
		String string2 = FirsttimeSqlDbSetup.getConnectionUrl(
							inputJdbcString,
							"ct-niand",
							"password",
							"master");
		
		
		assertEquals(expectedString2, string2);		
	}

}
