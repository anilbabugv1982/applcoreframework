package UtilClasses.UI;

public class ConnectionLeakPage {
	private String amName;
	private String hostName;
	private String server;
	private int checkinCount;
	private int checkoutCount;
	private int failedCount;

	public ConnectionLeakPage(String amName, String hostName, String server, int checkinCount, int checkoutCount,
			int failedCount) {
		super();
		this.amName = amName;
		this.hostName = hostName;
		this.server = server;
		this.checkinCount = checkinCount;
		this.checkoutCount = checkoutCount;
		this.failedCount = failedCount;
	}

	public String getAmName() {
		return amName;
	}

	public void setAmName(String amName) {
		this.amName = amName;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getCheckinCount() {
		return checkinCount;
	}

	public void setCheckinCount(int checkinCount) {
		this.checkinCount = checkinCount;
	}

	public int getCheckoutCount() {
		return checkoutCount;
	}

	public void setCheckoutCount(int checkoutCount) {
		this.checkoutCount = checkoutCount;
	}

	public int getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(int failedCount) {
		this.failedCount = failedCount;
	}

	@Override
	public String toString() {
		return "ConnectionLeakPage [amName=" + amName + ", hostName=" + hostName + ", server=" + server
				+ ", checkinCount=" + checkinCount + ", checkoutCount=" + checkoutCount + ", failedCount=" + failedCount
				+ "]";
	}

}
