package com;

import java.io.Serializable;

public class CAOCache implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7289671122717340553L;
	private String instanceName;
	private String objName;
	private Long id;
	
	public CAOCache(Long id,String instanceName,String objName){
		this.id= id;
		this.instanceName = instanceName;
		this.objName =objName;
	}
	
	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	public String getObjName() {
		return objName;
	}
	public void setObjName(String objName) {
		this.objName = objName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "CAOCache [instanceName=" + instanceName + ", objName="
				+ objName + ", id=" + id + "]";
	}
	

}
