package model.dispatchObject;

public class DispatchFieldSupe extends DispatchObject<DispatchObject>{

	public DispatchFieldSupe(String name) {
		super();
		this.fieldSupeName = name;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = -8709142730697251377L;
	private String fieldSupeName;

	public String getSupeName() { return this.fieldSupeName; }
	
	@Override
	public String getObjectType(){
		return "DispatchFieldSupe";
	}

}
