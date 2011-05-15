package hu.bearmaster.phoenix.common.util;

public enum DiscItemType {
	TEXT("txt", "csv"),
	NFO("nfo"),
	VIDEO("avi", "wmv", "mpeg"),
	PDF("pdf"),
	DOC("doc", "docx"),
	AUDIO("mp3", "wav"),
	ZIP("zip"),
	GENERAL;
	
	private String[] extensions;
	
	private DiscItemType(String... extensions) {
		this.extensions = extensions;
	}
	
	public static DiscItemType determineType(String extension) {
		
		for (DiscItemType type : values()) {
			for (String actExt : type.extensions ) {
				if (actExt.equalsIgnoreCase(extension)) {
					return type;
				}
			}
		}
		
		return GENERAL;
	}
}
