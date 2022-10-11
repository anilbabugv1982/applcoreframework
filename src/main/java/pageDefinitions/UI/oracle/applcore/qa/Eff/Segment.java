package pageDefinitions.UI.oracle.applcore.qa.Eff;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Segment {

	private String segmentName;
	private String dataType;
	private String valueSet;
	private String displayType;
	
	@JsonProperty(value="isUnique")    
	private boolean isUnique;

	public String getSegmentName() {
		return segmentName;
	}

	public void setSegmentName(String segmentName) {
		this.segmentName = segmentName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getValueSet() {
		return valueSet;
	}

	public void setValueSet(String valueSet) {
		this.valueSet = valueSet;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public boolean isUnique() {
		return isUnique;
	}

	public void setUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}

	@Override
	public String toString() {
		return "Segment [segmentName=" + segmentName + ", dataType=" + dataType + ", valueSet=" + valueSet
				+ ", displayType=" + displayType + ", isUnique=" + isUnique + "]";
	}
	
}
