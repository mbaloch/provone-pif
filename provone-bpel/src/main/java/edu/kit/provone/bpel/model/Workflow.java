package edu.kit.provone.bpel.model;


/**
 * Created by mukhtar on 15.02.17.
 */
public class Workflow extends ProvOneProcess {
    String type;
    ProvOneProcess provOneProcess;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ProvOneProcess getProvOneProcess() {
        return provOneProcess;
    }

    public void setProvOneProcess(ProvOneProcess provOneProcess) {
        this.provOneProcess = provOneProcess;
    }



    public String getTitle() {
        return super.getTitle();
    }

    public void setTitle(String title) {
        super.setTitle(title);
    }
}
