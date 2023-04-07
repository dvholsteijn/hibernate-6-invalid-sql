package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
	}

	@After
	public void destroy() {
		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	public void testLavic1591() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		// Do stuff...
		ImageItem imageItem = createImageItem("original.jpg");
		imageItem.setThumbnail(createImageItem("thumbnail.jpg"));

		entityManager.persist(imageItem);

		PartialImage partialImage = new PartialImage();
		partialImage.setFileName("partial.jpg");
		partialImage.setOriginal(imageItem);
		partialImage.setThumbnail(createImageItem("partial_thumbnail.jpg"));
		entityManager.persist(partialImage);

		List<ImageItem> originals = entityManager.createQuery("select i from ImageItem i where i.original is null and i.thumbnail is not null").getResultList();

		Assertions.assertEquals(2, originals.size());

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	private static ImageItem createImageItem(String fileName) {
		ImageItem imageItem = new ImageItem();
		imageItem.setFileName(fileName);
		return imageItem;
	}
}
