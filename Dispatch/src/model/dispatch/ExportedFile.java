package model.dispatch;

import controller.CompleteClient;
import exceptions.*;

public class ExportedFile extends Dispatch<CompleteClient> {

	private String fileContents;
	
	public ExportedFile(String source, String fileContents) {
		super(source);
		this.fileContents = fileContents;
	}

	@Override
	public void execute(CompleteClient executeOn) {
		executeOn.saveExport(fileContents);
	}
}
