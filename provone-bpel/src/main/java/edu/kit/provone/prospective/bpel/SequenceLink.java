/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kit.provone.prospective.bpel;
import org.jgrapht.graph.DefaultEdge;

/**
 *
 * @author mukhtar
 */
public class SequenceLink extends DefaultEdge{

    public SequenceLink() {
       // super("");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String title;
    @Override
    public String toString() {
        return this.title;
    }
    
    
}
