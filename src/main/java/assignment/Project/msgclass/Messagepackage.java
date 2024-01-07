package assignment.Project.msgclass;

import java.io.Serializable;

public class Messagepackage implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String user;
	private String password;
	private String question;
	private String answer;
	private byte[] docByte;//数据
	private String docText;//文件名
	private String sendUser;//发送人
	private String BesendedUser;//接收人
	private String time;//发送时间
	private boolean isPri=false;//是否私聊
	private boolean isDoc=false;//是否文件
	private boolean isShake=false;
	private boolean isLogin=false;
	private boolean isForgetPass=false;
	private boolean isRegister=false;
	private boolean isServer=false;//是否系统消息
	private String problem;//系统解决办法
	public boolean isVideo() {
		return isVideo;
	}
	public void setVideo(boolean isVideo) {
		this.isVideo = isVideo;
	}
	public int getVideoPort() {
		return videoPort;
	}
	public void setVideoPort(int videoPort) {
		this.videoPort = videoPort;
	}

	private boolean isVideo ;
	private int videoPort;

	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public boolean isRegister() {
		return isRegister;
	}
	public void setRegister(boolean isRegister) {
		this.isRegister = isRegister;
	}
	public boolean isLogin() {
		return isLogin;
	}
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isServer() {
		return isServer;
	}
	public void setServer(boolean isServer) {
		this.isServer = isServer;
	}
	public String getProblem() {
		return problem;
	}
	public void setProblem(String problem) {
		this.problem = problem;
	}
	public byte[] getDocByte() {
		return docByte;
	}
	public void setDocByte(byte[] docByte) {
		this.docByte = docByte;
	}
	public String getSendUser() {
		return sendUser;
	}
	public boolean isDoc() {
		return isDoc;
	}
	public void setDoc(boolean isDoc) {
		this.isDoc = isDoc;
	}
	public boolean isShake() {
		return isShake;
	}
	public void setShake(boolean isShake) {
		this.isShake = isShake;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}
	public String getBesendedUser() {
		return BesendedUser;
	}
	public void setBesendedUser(String besendedUser) {
		BesendedUser = besendedUser;
	}
	public String getDocText() {
		return docText;
	}
	public void setDocText(String docText) {
		this.docText = docText;
	}
	public boolean isPri() {
		return isPri;
	}
	public void setPri(boolean isPri) {
		this.isPri = isPri;
	}
	public boolean isForgetPass() {
		return isForgetPass;
	}
	public void setForgetPass(boolean isForgetPass) {
		this.isForgetPass = isForgetPass;
	}

	public Messagepackage() {
		// TODO 自动生成的构造函数存根
	}

}
