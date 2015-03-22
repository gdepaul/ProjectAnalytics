package model.dispatchObject;

public class ChangeDrop extends DispatchObject<DispatchObject>{
	private static final long serialVersionUID = 2715653630216322407L;
	
	public ChangeDrop(String club) {
		this.club=club;	}
	
	@Override
	public String getObjectType(){
		return "ChangeDrop";
	}



}
