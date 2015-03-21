package model.dispatchObject;

public class AddFieldSupe extends DispatchObject<DispatchObject>{

	public AddFieldSupe(String name) {
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
		return "AddFieldSupe";
	}

}
