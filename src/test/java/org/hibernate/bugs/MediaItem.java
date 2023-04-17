package org.hibernate.bugs;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MediaItem implements Cloneable, Comparable<MediaItem> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	public Integer getId() {
		return id;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MediaItem mediaItem = (MediaItem) o;

		return id != null ? id.equals(mediaItem.id) : mediaItem.id == null;
	}

	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	private String fileName;

	@Override
	public int compareTo(MediaItem compareMediaItem) {
		int compareResult = this.getFileName().compareToIgnoreCase(compareMediaItem.getFileName());
		return compareResult != 0 ? compareResult : getId().compareTo(compareMediaItem.getId());
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
