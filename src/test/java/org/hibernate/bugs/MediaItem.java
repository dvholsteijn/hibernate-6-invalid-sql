package org.hibernate.bugs;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.Transient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.IOException;
import java.util.Optional;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MediaItem implements Cloneable, Comparable<MediaItem> {

	public static final String RM_MEDIAITEM_PATH = "{id}/{name}";

	/**
	 * Regular expression for validating filenames allowed for upload:
	 * - may not start with \ . / : * ? " < > | $
	 * - may not have \ / : * ? " < > | $ in the rest of the name (a . is allowed, as long as the filename don't starts with it)
	 * - should be at least 1 character and maximum 255 characters long
	 */
	static final String FILENAME_VALIDATION_EXP = "[^\\\\./:*?\"<>|$][^\\\\/:*?\"<>|$]{0,254}";

	private static final Logger LOGGER = LogManager.getLogger(MediaItem.class);


	private static final long serialVersionUID = -5951992985425021896L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Nullable
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

	/**
	 * Sets the id for this entity. Normally you don't want to use this since the database generates
	 * the id value.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	@Transient
	private byte[] bytes;

//	@Pattern(regexp = FILENAME_VALIDATION_EXP, message = "{lavic.media.name.error.invalidName.text}")
	@Column(nullable = false)
	private String fileName;

//	@ManyToOne
//	private MediaFolder folder;

	@Override
	public int compareTo(MediaItem compareMediaItem) {
		int compareResult = this.getFileName().compareToIgnoreCase(compareMediaItem.getFileName());
		return compareResult != 0 ? compareResult : getId().compareTo(compareMediaItem.getId());
	}
//
//	public byte[] getBytes() {
//		if (bytes != null) {
//			return bytes;
//		}
//
//		try {
//			return ApplicationContextHolder.getBean(FileStorage.class).read(getStorageFileName());
//		} catch (IOException e) {
//			throw new IllegalStateException(e);
//		}
//	}

//	public void setBytes(byte[] bytes) {
//		this.bytes = bytes;
//	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

//	@Transient
//	public String getFileNameWithoutExtension() {
//		return FilenameUtils.removeExtension(getFileName());
//	}

//	public MediaFolder getFolder() {
//		return folder;
//	}

//	public void setFolder(MediaFolder folder) {
//		this.folder = folder;
//	}

//	public String getRequestMapping() {
//		throw new UnsupportedOperationException("This method should be overrided in subclasses");
//	}

//	@Transient
//	public String getStorageFileName() {
//		return getStorageUrl() + getId()
//				+ (FilenameUtils.indexOfExtension(fileName) < 0 ? "" : "." + FilenameUtils.getExtension(fileName));
//	}

//	@Transient
//	public Optional<String> getThumbnailUrl() {
//		return Optional.empty();
//	}

//	@Transient
//	public String getUrl() {
//		return UriComponentsBuilder.fromPath(getRequestMapping()).build(getId(), getUrlFriendlyFileName()).getRawPath();
//	}

//	@Transient
//	public String getUrlFriendlyFileName() {
//		return CmsUrlUtils.toUrlFriendly(getFileName());
//	}

//	@Transient
//	public void rename(String newFileName) {
//		this.setFileName(getNewFileName(newFileName));
//	}

//	protected Object deepCopy() throws CloneNotSupportedException {
//		MediaItem clone = (MediaItem) super.clone();
//		clone.setBytes(getBytes());
//		return clone;
//	}

//	@PostRemove
//	protected void deleteFromStorage() {
//		try {
//			ApplicationContextHolder.getBean(FileStorage.class).delete(getStorageFileName());
//		} catch (IOException e) {
//			LOGGER.warn("File ( {} ) was removed from the database, but could not be removed from disk! {}", fileName, e);
//		}
//	}

//	protected String getControllerUrl() {
//		throw new UnsupportedOperationException("This method should be overrided in subclasses");
//	}

//	protected String getStorageUrl() {
//		throw new UnsupportedOperationException("This method should be overrided in subclasses");
//	}

//	@PostPersist
//	@PostUpdate
//	protected void writeFile() {
//		try {
//			if (bytes != null) {
//				ApplicationContextHolder.getBean(FileStorage.class).write(getStorageFileName(), bytes);
//			}
//		} catch (IOException e) {
//			throw new IllegalStateException(e);
//		}
//	}

	/**
	 * @param newFileName
	 * @return the new file name with the correct file extension.
	 */
//	private String getNewFileName(String newFileName) {
//		String currentExtension = FilenameUtils.getExtension(this.getFileName());
//
//		if (!FilenameUtils.isExtension(newFileName, currentExtension)) {
//			return newFileName + FilenameUtils.EXTENSION_SEPARATOR_STR + currentExtension;
//		}
//
//		return newFileName;
//	}

}
