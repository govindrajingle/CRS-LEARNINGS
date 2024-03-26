package in.cdacnoida.dava.util;

import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BOOTFTPAction {

	private BOOTAccessPaths bOOTAccessPaths;

	@Autowired
	public BOOTFTPAction(BOOTAccessPaths bOOTAccessPaths) {
		this.bOOTAccessPaths = bOOTAccessPaths;
	}

	public boolean ftpConnection(final FTPClient ftpClient) {
		boolean result = false;
		final String ftpServer = bOOTAccessPaths.getFTP_HOST();
		final String ftpUser = bOOTAccessPaths.getFTP_USER();
		final String ftpPassword = bOOTAccessPaths.getFTP_PWD();
		final String ftpDir = bOOTAccessPaths.getFTP_DIR();
		
		return result;
	}
	
	
}