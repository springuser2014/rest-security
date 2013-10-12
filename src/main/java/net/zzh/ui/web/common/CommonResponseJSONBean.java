/**
 * 
 */
package net.zzh.ui.web.common;

/**
 * 普通响应JSON bean
 * @author zhenhuazhao
 *
 */
public class CommonResponseJSONBean {
	private boolean rc = false;
	private String msg = "";
	private long id = 0;
	
	/**
	 * 结果 true or false
	 * @return the rc
	 */
	public boolean isRc() {
		return rc;
	}
	/**
	 * 结果 true or false
	 * @param rc the rc to set
	 */
	public void setRc(boolean rc) {
		this.rc = rc;
	}
	/**
	 * 消息
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * 消息
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
}
