// package de.bonprix.service.integration;
//
// import javax.annotation.Resource;
// import javax.persistence.EntityManager;
// import javax.ws.rs.NotFoundException;
//
// import org.springframework.test.context.ContextConfiguration;
// import org.testng.Assert;
// import org.testng.annotations.Test;
//
// import de.bonprix.dto.ApplicationType;
// import de.bonprix.dto.builder.ApplicationTypeBuilder;
// import de.bonprix.repository.jpa.ApplicationTypeRepository;
// import de.bonprix.service.ApplicationTypeServiceImpl;
// import de.bonprix.service.applicationtype.ApplicationTypeService;
// import de.bonprix.sqlcount.SQLStatementCountValidator;
// import de.bonprix.test.InMemoryDbAwareUnitTest;
//
// @ContextConfiguration(
// classes = { ApplicationTypeServiceImpl.class, ApplicationTypeRepository.class })
// public class ApplicationTypeServiceImplInMemoryDbTest extends InMemoryDbAwareUnitTest {
//
// @Resource
// private ApplicationTypeService applicationTypeService;
//
// @Resource
// private EntityManager entityManager;
//
// @Test
// private void findAllTest() {
// // this.applicationTypeService.findAll(new Paged(0, 20));
//
// SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(2, 0, 0, 0);
// }
//
// @Test
// private void findByIdTest() {
// this.applicationTypeService.findById(1L);
//
// SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 0);
// }
//
// @Test
// private void createTest() {
// final ApplicationType applicationType = new ApplicationTypeBuilder().withNameKey("ApplicationType")
// .build();
//
// final Long createdApplicationTypeId = this.applicationTypeService.create(applicationType);
// this.entityManager.flush();
//
// SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(0, 1, 0, 0);
//
// final ApplicationType createdApplicationType = this.applicationTypeService.findById(createdApplicationTypeId);
//
// Assert.assertEquals(createdApplicationType.getNameKey(), applicationType.getNameKey());
// }
//
// @Test
// private void updateTest() {
//
// final ApplicationType applicationType = new ApplicationTypeBuilder().withNameKey("ApplicationType")
// .build();
//
// final Long createdApplicationTypeId = this.applicationTypeService.create(applicationType);
// final ApplicationType createdApplicationType = this.applicationTypeService.findById(createdApplicationTypeId);
// createdApplicationType.setNameKey("ApplicationType2");
//
// this.entityManager.flush();
// SQLStatementCountValidator.reset();
//
// this.applicationTypeService.update(createdApplicationType);
// this.entityManager.flush();
//
// SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(0, 0, 1, 0);
//
// final ApplicationType updatedApplicationType = this.applicationTypeService.findById(createdApplicationTypeId);
//
// Assert.assertEquals(updatedApplicationType.getNameKey(), createdApplicationType.getNameKey());
// }
//
// @Test(
// expectedExceptions = NotFoundException.class)
// private void deleteTest() {
// final ApplicationType applicationType = new ApplicationTypeBuilder().withNameKey("ApplicationType")
// .build();
//
// final Long createdApplicationTypeId = this.applicationTypeService.create(applicationType);
//
// this.entityManager.flush();
// SQLStatementCountValidator.reset();
//
// this.applicationTypeService.deleteById(createdApplicationTypeId);
// this.entityManager.flush();
//
// SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(0, 0, 0, 1);
//
// this.applicationTypeService.findById(createdApplicationTypeId);
// }
//
// }
