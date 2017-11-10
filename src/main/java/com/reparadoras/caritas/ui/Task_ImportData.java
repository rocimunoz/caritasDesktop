package com.reparadoras.caritas.ui;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

 public class Task_ImportData extends SwingWorker<Void, String> {

    JLabel jlabel;
    public Task_ImportData(JLabel jlabel) {
        this.jlabel = jlabel;
    }

    @Override
    public void process(List<String> chunks) {
        jlabel.setText(chunks.get(chunks.size()-1)); 
       
    }

    @Override
    public Void doInBackground() throws Exception {

        publish("Cargando datos...");
        Thread.sleep(1000);
      

        return null;
    }

    @Override
    public void done() {
        try {
            get();
            JOptionPane.showMessageDialog(jlabel.getParent(), "Copia de seguridad realizada", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        
      System.exit(0);
    }
}
