package de.codolith.Cinema;

public class Version {
	private Integer major;
	private Integer minor;
	private Integer revision;
	private Integer build;
	
	public Integer getMajor() {
		return major;
	}

	public void setMajor(Integer major) {
		this.major = major;
	}

	public Integer getMinor() {
		return minor;
	}

	public void setMinor(Integer minor) {
		this.minor = minor;
	}

	public Integer getRevision() {
		return revision;
	}

	public void setRevision(Integer revision) {
		this.revision = revision;
	}

	public Integer getBuild() {
		return build;
	}

	public void setBuild(Integer build) {
		this.build = build;
	}

	public Version(Integer major, Integer minor, Integer revision, Integer build){
		this.major= major;
		this.minor = minor;
		this.revision= revision;
		this.build= build;
	}
	
	public Version(){
		this(null,null,null,null);
	}
	
	public Version(Integer major){
		this(major,null,null,null);
	}
	
	public Version(Integer major, Integer minor){
		this(major,minor,null,null);
	}
	
	public Version(Integer major, Integer minor, Integer revision){
		this(major,minor,revision,null);
	}
	
	public Version(String versionString){
		parseFromString(versionString);
	}
	
	private void parseFromString(String str){
		String[] sa = str.split("\\.");
		if (sa.length>0){
			major = Integer.parseInt(sa[0]);
			if(sa.length>1){
				minor = Integer.parseInt(sa[1]);
				if(sa.length>2){
					revision = Integer.parseInt(sa[2]);
					if(sa.length>3){
						build = Integer.parseInt(sa[3]);
					}
				}
			}
		}else{
			throw new IllegalArgumentException();
		}
	}
	
	public static Version parse(String str){
		Version retval = new Version();
		retval.parseFromString(str);
		return retval;
	}
	
	public boolean isNewerThan(Version other){
		int major = this.major != null? this.major :0;
		int minor = this.minor != null? this.minor :0;
		int revision = this.revision != null? this.revision :0;
		int build = this.build != null? this.build :0;
		int omajor = other.major != null? other.major :0;
		int ominor = other.minor != null? other.minor :0;
		int orevision = other.revision != null? other.revision :0;
		int obuild = other.build != null? other.build :0;
		return(	major > omajor
			||
			(major == omajor && minor >  ominor)
			||
			(major == omajor && minor == ominor && revision >  orevision)
			||
			(major == omajor && minor == ominor && revision == orevision && build > obuild)
		);
	}
	
	public boolean isOlderThan(Version other){
		int major = this.major != null? this.major :0;
		int minor = this.minor != null? this.minor :0;
		int revision = this.revision != null? this.revision :0;
		int build = this.build != null? this.build :0;
		int omajor = other.major != null? other.major :0;
		int ominor = other.minor != null? other.minor :0;
		int orevision = other.revision != null? other.revision :0;
		int obuild = other.build != null? other.build :0;
		return(	major < omajor
			||
			(major == omajor && minor <  ominor)
			||
			(major == omajor && minor == ominor && revision <  orevision)
			||
			(major == omajor && minor == ominor && revision == orevision && build < obuild)
		);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((build == null) ? 0 : build.hashCode());
		result = prime * result + ((major == null) ? 0 : major.hashCode());
		result = prime * result + ((minor == null) ? 0 : minor.hashCode());
		result = prime * result + ((revision == null) ? 0 : revision.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Version other = (Version) obj;
		if (build == null) {
			if (other.build != null) {
				return false;
			}
		} else if (!build.equals(other.build)) {
			return false;
		}
		if (major == null) {
			if (other.major != null) {
				return false;
			}
		} else if (!major.equals(other.major)) {
			return false;
		}
		if (minor == null) {
			if (other.minor != null) {
				return false;
			}
		} else if (!minor.equals(other.minor)) {
			return false;
		}
		if (revision == null) {
			if (other.revision != null) {
				return false;
			}
		} else if (!revision.equals(other.revision)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString(){
		String retval = "";
		if(major != null){
			retval = major.toString();
			if(minor != null){
				retval += "."+minor;
				if(revision != null){
					retval += "."+revision;
					if(build != null){
						retval+="."+build;
					}
				}
			}
		}
		return retval;
	}
}
