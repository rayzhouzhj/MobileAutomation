package com.github.testclient.context;

import java.io.File;

public class Constants {
	public static String CLASS_PATH = Configurations.getInstance().getAttribute("Class_Path");
	public static String CLASS_FILE_PATH = CLASS_PATH.replace(".", File.separator);
	public static String TEST_EXECUTOR = Configurations.getInstance().getAttribute("Test_Executor");
	public static String EXECUTOR_JAR = Configurations.getInstance().getAttribute("Executor_Jar");
	public static String TESTCASES_JAR = Configurations.getInstance().getAttribute("TestCases_Jar");
}
