package assignment.Project.server;


public class PriUser {
    private String sendUser;
    private String beSendUser;

    public PriUser(String sendUser, String beSendUser) {
        this.beSendUser = beSendUser;
        this.sendUser = sendUser;
    }

    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    public String getBeSendUser() {
        return beSendUser;
    }

    public void setBeSendUser(String beSendUser) {
        this.beSendUser = beSendUser;
    }


}

