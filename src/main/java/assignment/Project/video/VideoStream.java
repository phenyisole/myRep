package assignment.Project.video;


import java.io.Serializable;

public class VideoStream implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public byte[] by;
    public byte[] getBy() {
        return by;
    }
    public void setBy(byte[] by) {
        this.by = by;
    }
    public VideoStream() {
        // TODO 自动生成的构造函数存根
    }

}