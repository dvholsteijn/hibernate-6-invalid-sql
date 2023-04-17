package org.hibernate.bugs;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.Cascade;

@Entity
public class ImageItem extends MediaItem {

	private int height;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private ImageItem original;

	@ManyToOne(cascade = CascadeType.ALL)
	private ImageItem thumbnail;

	private int width;

	public ImageItem() {
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public ImageItem getOriginal() {
		return original;
	}

	public void setOriginal(ImageItem original) {
		this.original = original;
	}

	public ImageItem getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(ImageItem thumbnail) {
		this.thumbnail = thumbnail;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	protected ImageItem getOriginalOrSelf() {
		return getOriginal() == null ? this : getOriginal();
	}

}
