package pageDefinitions.UI.oracle.applcore.qa.Eff;

import java.util.Arrays;

public class PerPersonEITEFF {

	private String flexCode;
	private String page;
	private Context contexts[];

	public String getFlexCode() {
		return flexCode;
	}

	public void setFlexCode(String flexCode) {
		this.flexCode = flexCode;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public Context[] getContexts() {
		return contexts;
	}

	public void setContexts(Context[] contexts) {
		this.contexts = contexts;
	}

	@Override
	public String toString() {
		return "PerPersonEITEFF [flexCode=" + flexCode + ", page=" + page + ", contexts=" + Arrays.toString(contexts)
				+ "]";
	}

}
