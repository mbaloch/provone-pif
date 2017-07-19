package kit.edu.core;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Entity {

	@XmlAttribute(name = "about", namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
	private String about;

	@XmlElement(name = "qualifiedGeneration", namespace = "http://www.w3.org/ns/prov#")
	private QualifiedGeneration qualifiedGeneration;

	@XmlElement(name = "content", namespace = "http://ns.taverna.org.uk/2012/tavernaprov/")
	private Content content;

	@XmlElement(name = "describedByParameter", namespace = "http://purl.org/wf4ever/wfprov#")
	private List<DescribedByParameter> describedByParameter;

	@XmlElement(name = "hadDictionaryMember", namespace = "http://www.w3.org/ns/prov#", required = false)
	private List<HadDictionaryMember> hadDictionaryMember;

	@XmlElement(name = "hadMember", namespace = "http://www.w3.org/ns/prov#", required = false)
	private List<HadMember> hadMember;

	@XmlElement(name = "wasGeneratedBy", namespace = "http://www.w3.org/ns/prov#", required = false)
	private List<WasGeneratedBy> wasGeneratedBy;

	@XmlElement(name = "wasOutputFrom", namespace = "http://purl.org/wf4ever/wfprov#", required = false)
	private List<WasOutputFrom> wasOutputFrom;

	@XmlAnyElement(lax = true)
	private List<Object> any;

	public List<Object> getAny() {
		return any;
	}

	public void setAny(List<Object> any) {
		this.any = any;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public QualifiedGeneration getQualifiedGeneration() {
		return qualifiedGeneration;
	}

	public void setQualifiedGeneration(QualifiedGeneration qualifiedGeneration) {
		this.qualifiedGeneration = qualifiedGeneration;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public List<DescribedByParameter> getDescribedByParameter() {
		return describedByParameter;
	}

	public void setDescribedByParameter(List<DescribedByParameter> describedByParameter) {
		this.describedByParameter = describedByParameter;
	}

	public List<HadDictionaryMember> getHadDictionaryMember() {
		return hadDictionaryMember;
	}

	public void setHadDictionaryMember(List<HadDictionaryMember> hadDictionaryMember) {
		this.hadDictionaryMember = hadDictionaryMember;
	}

	public List<HadMember> getHadMember() {
		return hadMember;
	}

	public void setHadMember(List<HadMember> hadMember) {
		this.hadMember = hadMember;
	}

	public List<WasGeneratedBy> getWasGeneratedBy() {
		return wasGeneratedBy;
	}

	public void setWasGeneratedBy(List<WasGeneratedBy> wasGeneratedBy) {
		this.wasGeneratedBy = wasGeneratedBy;
	}

	public List<WasOutputFrom> getWasOutputFrom() {
		return wasOutputFrom;
	}

	public void setWasOutputFrom(List<WasOutputFrom> wasOutputFrom) {
		this.wasOutputFrom = wasOutputFrom;
	}

}
