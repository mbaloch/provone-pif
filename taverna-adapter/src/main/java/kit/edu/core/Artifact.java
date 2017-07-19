package kit.edu.core;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Artifact {
	
	@XmlElement(name="hadMember",namespace="http://www.w3.org/ns/prov#",required=false)
	private List<HadMember> hadMember;
	
	@XmlAnyElement(lax = true)
	public List<Object> any;

	public List<HadMember> getHadMember() {
		return hadMember;
	}

	public void setHadMember(List<HadMember> hadMember) {
		this.hadMember = hadMember;
	}

	public List<Object> getAny() {
		return any;
	}

	public void setAny(List<Object> any) {
		this.any = any;
	}

}
