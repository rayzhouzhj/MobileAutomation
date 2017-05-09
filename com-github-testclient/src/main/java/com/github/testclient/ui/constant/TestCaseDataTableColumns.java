package com.github.testclient.ui.constant;

public enum TestCaseDataTableColumns {

	EXECUTION_FLAG("", 0),
	SCRIPT_NAME("Test Name", 1),
	SCRIPT_PATH("Class Name", 2),
	DEVICE("Device Name", 3),
	PRIORITY("Priority", 4),
	TIMEOUT("Timeout", 5),
	STARTTIME("Start Time", 6),
	ENDTIME("End Time", 7),
	STATUS("Status", 8),
	COMMENT("Comment", 9),
	TEST_DATA("Data", 10),
	REPORT("...", 11);
	
	public String NAME;
	public int INDEX;
	public static int COLUMN_COUNT = 12;
	
	private TestCaseDataTableColumns(String name, int index)
	{
		NAME = name;
		INDEX = index;
	}
}
