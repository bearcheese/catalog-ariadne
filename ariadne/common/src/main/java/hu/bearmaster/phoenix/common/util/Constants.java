package hu.bearmaster.phoenix.common.util;

public final class Constants {
	
	public static final String DISC_DAO_BEAN = "discDao";
	public static final String DISC_ITEM_DAO_BEAN = "discItemDao";
	
	public static final String FILE_MANAGEMENT_BEAN = "fileManageServ";
	public static final String DB_MANAGEMENT_BEAN = "dbManageServ";
	
	public static enum Unit {
		BYTE	(1, "b"),
		KB		(1024 * BYTE.divider, "Kb"),
		MB		(1024 * KB.divider, "Mb"),
		GB		(1024 * MB.divider, "Gb"),
		TB		(1024 * GB.divider, "Tb"),
		PB		(1024 * GB.divider, "Pb");
		
		private final long divider;
		private final String suffix;
		
		Unit(final long divider, final String suffix) {
			this.divider = divider;
			this.suffix = suffix;
		}
		
		public long divider() {
			return divider;
		}
		
		public String suffix() {
			return suffix;
		}
	}
	
}
