package chubyqc.gaeDistributed.client.task;

public abstract class AbstractTask implements ITask {

	private int _id;
	private Object _result;
	
	public AbstractTask(int id) {
		_id = id;
	}
	
	public int getId() {
		return _id;
	}
	
	public Object getResult() {
		return _result;
	}
	
	protected void setResult(Object result) {
		_result = result;
	}
}
