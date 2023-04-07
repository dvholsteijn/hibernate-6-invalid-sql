package org.hibernate.bugs;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

/**
 * Represents an image that is derived from another image.
 *
 * @author Dennis van Holsteijn
 */
@Entity
// needs to be indexed because parents in the hierarchy are indexed. however, partialImages should
// never end up in a search result.
public class PartialImage extends ImageItem {

	private static final long serialVersionUID = 2491114404741759283L;

	private int croppedHeight;

	private int croppedWidth;

	@Column(name = "leftPosition")
	private int left;

	private String presetKey;

	@Column(name = "topPosition")
	private int top;

//	public PartialImage deepCopy() {
//		try {
//			PartialImage clone = (PartialImage) super.deepCopy();
//			clone.croppedHeight = croppedHeight;
//			clone.croppedWidth = croppedWidth;
//			clone.top = top;
//			clone.left = left;
//			clone.presetKey = presetKey;
//			return clone;
//		} catch (CloneNotSupportedException e) {
//			// should never occur
//			return null;
//		}
//	}

	public int getCroppedHeight() {
		return croppedHeight;
	}

	public void setCroppedHeight(int croppedHeight) {
		this.croppedHeight = croppedHeight;
	}

	public int getCroppedWidth() {
		return croppedWidth;
	}

	public void setCroppedWidth(int croppedWidth) {
		this.croppedWidth = croppedWidth;
	}

	/**
	 * @return the left
	 */
	public int getLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(int left) {
		this.left = left;
	}

	public String getPresetKey() {
		return presetKey;
	}

	public void setPresetKey(String presetKey) {
		this.presetKey = presetKey;
	}

	/**
	 * @return the top
	 */
	public int getTop() {
		return top;
	}

	/**
	 * @param top the top to set
	 */
	public void setTop(int top) {
		this.top = top;
	}

	@Override
	public void setOriginal(ImageItem original) {
		// CAUTION: currently the original image is not set for this partialImage. reason is that
		// this will result in getThumbnailUrl always returning the thumbnail of the
		// original image (which is correct for resized images but not partial images)
	}

}
