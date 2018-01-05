package holidays.services;

public enum PackageType {
	ADVENTURE, FAMILY, STANDARD, DELUX;
	
	public static PackageType getById(int id) 
	{
	   return PackageType.values()[id];
}
}



