package org.hibernate.bugs;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

/**
 * Represents a cropped image that is derived from an original image.
 *
 */
@Entity
public class PartialImage extends ImageItem {

	private int croppedHeight;

	private int croppedWidth;

	@Column(name = "leftPosition")
	private int left;

	private String presetKey;

	@Column(name = "topPosition")
	private int top;

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

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public String getPresetKey() {
		return presetKey;
	}

	public void setPresetKey(String presetKey) {
		this.presetKey = presetKey;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

}
