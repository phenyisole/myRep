package assignment.Project.login;

import assignment.Project.msgclass.Messagepackage;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Login {
    final String ADMISSION="ADMISSION";
    final String DENIAL="DENIAL";
    Socket client;
    public Login(String ip,int port,String user,String pass,String question,String answer,boolean isRegister) {
        // TODO 自动生成的构造函数存根
        ObjectOutputStream os = null;
        Messagepackage msg=new Messagepackage();
        msg.setLogin(true);
        if(isRegister)
            msg.setRegister(true);
        msg.setQuestion(question);
        msg.setUser(user);
        msg.setAnswer(answer);
        msg.setPassword(pass);
        try {
            client=new Socket(ip,port);
            os = new ObjectOutputStream(client.getOutputStream());
            os.writeObject(msg);
            os.flush();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "连接服务器失败","提示消息",JOptionPane.WARNING_MESSAGE);

        }
    }
    public boolean check() {
        ObjectInputStream in = null;
        Messagepackage doc=null;

        while(true) {
            try {
                in = new ObjectInputStream(client.getInputStream());
                doc = (Messagepackage) in.readObject();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "连接超时","提示消息",JOptionPane.WARNING_MESSAGE);
                break;
            } catch (ClassNotFoundException e) {

            }
            if(doc.getProblem()!=null&& ADMISSION.equals(doc.getProblem()))	{
                return true;
            }

            if(doc.getProblem()!=null&& DENIAL.equals(doc.getProblem()))	{
                return false;
            }

        }
        return false;

    }
}
