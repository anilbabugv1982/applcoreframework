package pageDefinitions.UI.oracle.applcore.qa.Eff;

import java.util.Arrays;

public class Context {

	private String contextCode;
	private String behaviour;
	private String contextUsage;
	private Segment segments[];
	
	public Context() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Context(String contextCode, String behaviour, String contextUsage) {
		this.contextCode = contextCode;
		this.behaviour = behaviour;
		this.contextUsage = contextUsage;
	}

	public String getContextCode() {
		return contextCode;
	}

	public void setContextCode(String contextCode) {
		this.contextCode = contextCode;
	}

	public String getBehaviour() {
		return behaviour;
	}

	public void setBehaviour(String behaviour) {
		this.behaviour = behaviour;
	}

	public String getContextUsage() {
		return contextUsage;
	}

	public void setContextUsage(String contextUsage) {
		this.contextUsage = contextUsage;
	}

	public Segment[] getSegments() {
		return segments;
	}

	public void setSegments(Segment[] segments) {
		this.segments = segments;
	}

	@Override
	public String toString() {
		return "Context [contextCode=" + contextCode + ", behaviour=" + behaviour + ", contextUsage=" + contextUsage
				+ ", segments=" + Arrays.toString(segments) + "]";
	}

}
