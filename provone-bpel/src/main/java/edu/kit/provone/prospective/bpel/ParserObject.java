package edu.kit.provone.prospective.bpel;


import org.apache.ode.bpel.o.OBase;

import java.util.LinkedHashSet;

public class ParserObject {

        private Object structure;//=new OActivity();
	private LinkedHashSet<OBase> head=new LinkedHashSet<>();
	private LinkedHashSet<OBase> tail=new LinkedHashSet<>();
	public Object getStructure() {
            if(structure != null)
		return structure;
            else
                return new Object();
	}
	public void setStructure(Object structure) {
		this.structure = structure;
	}
	public LinkedHashSet<OBase> getOne() {
		return head;
	}
	public void setOne(LinkedHashSet<OBase> head) {
		this.head = head;
	}
	public LinkedHashSet<OBase> getTwo() {
		return tail;
	}
	public void setTwo(LinkedHashSet<OBase> tail) {
		this.tail = tail;
	}


}