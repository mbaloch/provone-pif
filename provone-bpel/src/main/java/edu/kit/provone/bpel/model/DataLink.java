package edu.kit.provone.bpel.model;

/**
 * Created by mukhtar on 15.02.17.
 */
public class DataLink {
    private String id;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public InputPort getDlToInPort() {
        return dlToInPort;
    }

    public void setDlToInPort(InputPort dlToInPort) {
        this.dlToInPort = dlToInPort;
    }

    public OutputPort getDlToOutPort() {
        return dlToOutPort;
    }

    public void setDlToOutPort(OutputPort dlToOutPort) {
        this.dlToOutPort = dlToOutPort;
    }

    private InputPort dlToInPort;
    private OutputPort dlToOutPort;
}
