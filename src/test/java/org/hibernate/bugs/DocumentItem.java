package org.hibernate.bugs;

import jakarta.persistence.Entity;

@Entity
public class DocumentItem extends MediaItem {

	public static final String CONTROLLER_URL = "/site/documents/";

	public static final String RM = DocumentItem.CONTROLLER_URL + MediaItem.RM_MEDIAITEM_PATH;

	public static final String STORAGE_URL = "/cmsmedia/documents/";

	private static final long serialVersionUID = -1436100688452512086L;

	public DocumentItem() {
	}

	public DocumentItem(Integer id, String fileName) {
		setId(id);
		setFileName(fileName);
	}

//	@Override
//	public String getRequestMapping() {
//		return RM;
//	}

//	@Override
//	protected String getControllerUrl() {
//		return DocumentItem.CONTROLLER_URL;
//	}

//	@Override
//	protected String getStorageUrl() {
//		return DocumentItem.STORAGE_URL;
//	}

}
