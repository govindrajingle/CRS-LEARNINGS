package in.cdacnoida.dava.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:BOOTresourceBundlePath.properties")
@ConfigurationProperties(prefix = "")
public class BOOTAccessPaths {
	
	private String BIS_GST_NO;
	private String FTP_HOST_NAME;
	private String FTP_AUTH_USER;
	private String FTP_AUTH_PWD;
	private String FTP_PORT;
	private String FTP_DIR;
	private String FTP_HOST;
	private String FTP_USER;
	private String FTP_PWD;
	
	public String getBIS_GST_NO() {
		return BIS_GST_NO;
	}

	public void setBIS_GST_NO(String bIS_GST_NO) {
		BIS_GST_NO = bIS_GST_NO;
	}

	public String getFTP_HOST_NAME() {
		return FTP_HOST_NAME;
	}

	public void setFTP_HOST_NAME(String fTP_HOST_NAME) {
		FTP_HOST_NAME = fTP_HOST_NAME;
	}

	public String getFTP_AUTH_USER() {
		return FTP_AUTH_USER;
	}

	public void setFTP_AUTH_USER(String fTP_AUTH_USER) {
		FTP_AUTH_USER = fTP_AUTH_USER;
	}

	public String getFTP_AUTH_PWD() {
		return FTP_AUTH_PWD;
	}

	public void setFTP_AUTH_PWD(String fTP_AUTH_PWD) {
		FTP_AUTH_PWD = fTP_AUTH_PWD;
	}

	public String getFTP_PORT() {
		return FTP_PORT;
	}

	public void setFTP_PORT(String fTP_PORT) {
		FTP_PORT = fTP_PORT;
	}

	public String getFTP_DIR() {
		return FTP_DIR;
	}

	public void setFTP_DIR(String fTP_DIR) {
		FTP_DIR = fTP_DIR;
	}

	public String getFTP_HOST() {
		return FTP_HOST;
	}

	public void setFTP_HOST(String fTP_HOST) {
		FTP_HOST = fTP_HOST;
	}

	public String getFTP_USER() {
		return FTP_USER;
	}

	public void setFTP_USER(String fTP_USER) {
		FTP_USER = fTP_USER;
	}

	public String getFTP_PWD() {
		return FTP_PWD;
	}

	public void setFTP_PWD(String fTP_PWD) {
		FTP_PWD = fTP_PWD;
	}
}
