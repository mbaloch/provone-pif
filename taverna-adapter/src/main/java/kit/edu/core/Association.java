package kit.edu.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Association {

	@XmlElement(name = "hadPlan", namespace = "http://www.w3.org/ns/prov#")
	private HadPlan hadPlan;

	@XmlAnyElement(lax = true)
	private Object anyObject;

	public HadPlan getHadPlan() {
		return hadPlan;
	}

	public void setHadPlan(HadPlan hadPlan) {
		this.hadPlan = hadPlan;
	}

	public Object getAnyObject() {
		return anyObject;
	}

	public void setAnyObject(Object anyObject) {
		this.anyObject = anyObject;
	}

}
