package org.hibernate.bugs;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import org.hibernate.annotations.Cascade;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
public class ImageItem extends MediaItem {

	public static final String CONTROLLER_URL = "/site/images/";

	public static final String RM = CONTROLLER_URL + MediaItem.RM_MEDIAITEM_PATH;

	public static final String STORAGE_URL = "/cmsmedia/images/";

	public static final int THUMBNAIL_HEIGHT = 80;

	public static final int THUMBNAIL_WIDTH = 120;

	private static final long serialVersionUID = 915626549666564042L;

	@Transient
	private boolean createThumbnail = true;

	private int height;

	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
	private ImageItem original;

	@OneToMany(mappedBy = "original", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	private Set<ImageItem> resizedImages;

	// TODO: LAVIC-441 Refactor: relatie vanuit ImageItem naar thumbnail zou geen ImageItem entity moeten zijn
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@ManyToOne(cascade = CascadeType.ALL)
	private ImageItem thumbnail;

	private int width;

	public ImageItem() {
	}

	public ImageItem(Integer id, String fileName) {
		setId(id);
		setFileName(fileName);
	}

	/**
	 * Looks for resized image, if the sizes are equal with original size, the original object will
	 * be returned. A resized image will be created if none exists.
	 *
	 * @param width  the width of the image, or null if the new width should be calculated.
	 * @param height the height of the image, or null if the new height should be calculated.
	 * @return a resized image
	 */
//	public ImageItem createResizedImage(Integer width, Integer height) {
//		if (width == null) {
//			return createResizedImageBasedOnHeight(height);
//		}
//
//		if (height == null) {
//			return createResizedImageBasedOnWidth(width);
//		}
//
//		return createResizedImage(width, height, false);
//	}

	/**
	 * Looks for resized image, if the sizes are equal with original size, the original object will
	 * be returned. A resized image will be created if none exists.
	 *
	 * Paul de Raaij (7/1/2013) Synchronized this method to prevent StaleObjectExceptions when two
	 * concurrent request try to create a resized image. Synchronization is done in the new method
	 * createAndPersistResizedImage
	 *
	 * @param width     the width of the image.
	 * @param height    the height of the image.
	 * @param keepRatio keep the aspect ratio.
	 * @return a resized image
	 */
//	public ImageItem createResizedImage(int width, int height, boolean keepRatio) {
//		if (isSameSize(width, height)) {
//			return this;
//		}
//
//		if (original != null && original.isSameSize(width, height)) {
//			return original;
//		}
//
//		Set<ImageItem> images = getOriginalOrSelf().getResizedImages();
//		for (ImageItem image : images) {
//			if (image.isSameSize(width, height)) {
//				return image;
//			}
//		}
//
//		ImageItem resizedImage = resizeImage(width, height, keepRatio);
//		if (!resizedImage.equals(this)) {
//			createAndPersistResizedImage(resizedImage);
//		}
//
//		return resizedImage;
//	}

	/**
	 * Looks for resized image, if the sizes are equal with original size, the original object will
	 * be returned. A resized image will be created if none exists for the given height.
	 * The new width is calculated.
	 *
	 * @param height the height of the image.
	 * @return a resized image
	 */
//	public ImageItem createResizedImageBasedOnHeight(int height) {
//		return createResizedImage(getCalculatedWidth(height), height, false);
//	}

	/**
	 * Looks for resized image, if the sizes are equal with original size, the original object will
	 * be returned. A resized image will be created if none exists for the given width.
	 * The new height is calculated.
	 *
	 * @param width the width of the image.
	 * @return a resized image
	 */
//	public ImageItem createResizedImageBasedOnWidth(int width) {
//		return createResizedImage(width, getCalculatedHeight(width), false);
//	}

//	@Override
//	public ImageItem deepCopy() throws CloneNotSupportedException {
//		ImageItem clone = (ImageItem) super.deepCopy();
//
//		clone.resizedImages = new HashSet<>();
//
//		clone.original = original;
//		clone.createThumbnail = createThumbnail;
//
//		clone.height = height;
//		clone.width = width;
//		return clone;
//	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Note: Thumbnail ImageItems do not have an original in the current implementation.
	 * This should not provide a problem because thumbnail ImageItems 'should' never be connected to
	 * a ItemContent. However this also asks for refactoring out the thumbnail as entity.
	 *
	 * @return the original ImageItem this ImageItem was based on.
	 */
	public ImageItem getOriginal() {
		return original;
	}

	public void setOriginal(ImageItem original) {
		this.original = original;
	}

//	@Override
//	public String getRequestMapping() {
//		return RM;
//	}

	public Set<ImageItem> getResizedImages() {
		if (resizedImages == null) {
			resizedImages = new HashSet<>();
		}

		return resizedImages;
	}

	public ImageItem getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(ImageItem thumbnail) {
		this.thumbnail = thumbnail;
	}

//	@Override
//	@Transient
//	public Optional<String> getThumbnailUrl() {
//		// resized images not taken into account, because a resized images will never be asked to
//		// show its thumbnail.
//		return Optional.ofNullable(thumbnail)
//				.map(ImageItem::getUrl);
//	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

//	@Transient
//	public boolean isThumbnailImage() {
//		return thumbnail == null && original == null;
//	}

//	@Override
//	public void rename(String newFileName) {
//		super.rename(newFileName);
//
//		if (thumbnail != null) {
//			thumbnail.rename(newFileName);
//		}
//
//		for (ImageItem resizedImage : getResizedImages()) {
//			resizedImage.rename(newFileName);
//		}
//	}

//	@Override
//	public void setBytes(byte[] bytes) {
//		try {
//			super.setBytes(bytes);
//
//			AdjustableImage adjustableImage = new AdjustableImage(bytes);
//			setWidth(adjustableImage.getWidth());
//			setHeight(adjustableImage.getHeight());
//
//			if (createThumbnail) {
//				thumbnail = resizeImage(ImageItem.THUMBNAIL_WIDTH, ImageItem.THUMBNAIL_HEIGHT, true);
//			}
//		} catch (IOException e) {
//			throw new IllegalStateException(e);
//		}
//	}

//	@Override
//	protected String getControllerUrl() {
//		return ImageItem.CONTROLLER_URL;
//	}

	protected ImageItem getOriginalOrSelf() {
		return getOriginal() == null ? this : getOriginal();
	}

//	@Override
//	protected String getStorageUrl() {
//		return ImageItem.STORAGE_URL;
//	}

//	private synchronized void createAndPersistResizedImage(ImageItem resizedImage) {
//		ImageItem originalOrSelf = getOriginalOrSelf();
//		resizedImage.setOriginal(originalOrSelf);
//		originalOrSelf.getResizedImages().add(resizedImage);
//
//		ImageItemRepository repository = ApplicationContextHolder.getBean(ImageItemRepository.class);
//
//		repository.save(resizedImage);
//	}

	private int getCalculatedHeight(double width) {
		ImageItem originalOrSelf = getOriginalOrSelf();

		double scaleRatio = (double) originalOrSelf.getWidth() / width;
		return (int) Math.floor(originalOrSelf.getHeight() / scaleRatio);
	}

	private int getCalculatedWidth(double height) {
		ImageItem originalOrSelf = getOriginalOrSelf();

		double scaleRatio = (double) originalOrSelf.getHeight() / height;
		return (int) Math.floor(originalOrSelf.getWidth() / scaleRatio);
	}

	private boolean isSameSize(int width, int height) {
		return this.width == width && this.height == height;
	}

//	private ImageItem resizeImage(int width, int height, boolean keepRatio) {
//		try {
//			AdjustableImage adjustableImage = new AdjustableImage(getOriginalOrSelf().getBytes());
//			boolean imageReduced = adjustableImage.reduceImage(width, height, keepRatio);
//
//			if (imageReduced) {
//				// Only create a new imageItem if the image was actually reduced to avoid getting
//				// copies of the image in the db and on this that have identical dimensions
//				ImageItem resizedImage = new ImageItem();
//				resizedImage.createThumbnail = false;
//				resizedImage.setBytes(adjustableImage.getAsByteArray());
//				resizedImage.setWidth(adjustableImage.getWidth());
//				resizedImage.setHeight(adjustableImage.getHeight());
//				resizedImage.setFileName(FilenameUtils.getBaseName(getFileName()) + "." + adjustableImage.getFileType());
//
//				return resizedImage;
//			}
//
//			// The image was not/ could not be reduced. So return the original image instead
//			return this;
//		} catch (IOException e) {
//			throw new IllegalStateException(e);
//		}
//	}
}
