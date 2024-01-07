package assignment.Project.login;

import assignment.Project.msgclass.Messagepackage;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RetrievePass {
    Socket client;
    LoginUI ui;
    public RetrievePass(LoginUI ui) {
        ObjectOutputStream os = null;
        Messagepackage msg=new Messagepackage();
        msg.setForgetPass(true);
        msg.setUser(ui.forgetA.getText());
        try {
            client=new Socket(ui.serverInput.getText(),Integer.parseInt(ui.portInput.getText()));
            os = new ObjectOutputStream(client.getOutputStream());
            os.writeObject(msg);
            os.flush();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "连接服务器失败","提示消息",JOptionPane.WARNING_MESSAGE);
        }
        this.ui=ui;
    }
    public boolean check() {
        ObjectInputStream in = null;
        Messagepackage doc=null;

        while(true) {
            try {
                in = new ObjectInputStream(client.getInputStream());
                doc = (Messagepackage) in.readObject();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(ui, "连接超时","提示消息",JOptionPane.WARNING_MESSAGE);
                break;
            } catch (ClassNotFoundException e) {
            }
            if(doc.getProblem() != null &&doc.getProblem().equals("NULL")) {
                JOptionPane.showMessageDialog(ui, "null","提示消息",JOptionPane.WARNING_MESSAGE);
                return false;
            }
            else {
                ui.getForgetPass=doc.getPassword();
                ui.forgetQues.setText("密保问题:"+doc.getQuestion());
                ui.answers=doc.getAnswer();
                return true;
            }
        }
        return false;

    }
}
