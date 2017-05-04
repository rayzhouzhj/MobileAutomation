package com.github.mobiletest.devices;

public class AndroidDevice {

	private String deviceName;
	private String deviceID;
	private String platformVersion;
	
	public AndroidDevice(String deviceName, String deviceID, String platformVersion)
	{
		this.deviceID = deviceID;
		this.deviceName = deviceName;
		this.platformVersion = platformVersion;
	}
	
	public AndroidDevice(String deviceID)
	{
		this.deviceID = deviceID;
	}
	

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deviceID == null) ? 0 : deviceID.hashCode());
		result = prime * result + ((deviceName == null) ? 0 : deviceName.hashCode());
		result = prime * result + ((platformVersion == null) ? 0 : platformVersion.hashCode());
		
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		
		
		AndroidDevice other = (AndroidDevice) obj;
		if (deviceID == null) 
		{
			if (other.deviceID != null) return false;
		} 
		else if (!deviceID.equals(other.deviceID))
		{
			return false;
		}

		return true;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getPlatformVersion() {
		return platformVersion;
	}

	public void setPlatformVersion(String platformVersion) {
		this.platformVersion = platformVersion;
	}
}
