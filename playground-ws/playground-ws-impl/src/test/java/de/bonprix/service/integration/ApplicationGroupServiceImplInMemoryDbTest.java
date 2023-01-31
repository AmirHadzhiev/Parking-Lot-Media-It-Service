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
// import de.bonprix.dto.ApplicationGroup;
// import de.bonprix.dto.builder.ApplicationGroupBuilder;
// import de.bonprix.repository.jpa.ApplicationGroupRepository;
// import de.bonprix.service.ApplicationGroupServiceImpl;
// import de.bonprix.service.applicationgroup.ApplicationGroupService;
// import de.bonprix.sqlcount.SQLStatementCountValidator;
// import de.bonprix.test.InMemoryDbAwareUnitTest;
//
// @ContextConfiguration(
// classes = { ApplicationGroupServiceImpl.class, ApplicationGroupRepository.class })
// public class ApplicationGroupServiceImplInMemoryDbTest extends InMemoryDbAwareUnitTest {
//
// @Resource
// private ApplicationGroupService applicationGroupService;
//
// @Resource
// private EntityManager entityManager;
//
// @Test
// private void findAllTest() {
// // this.applicationGroupService.findAll(new Paged(0, 20));
//
// SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(2, 0, 0, 0);
// }
//
// @Test
// private void findByIdTest() {
// this.applicationGroupService.findById(1L);
//
// SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 0);
// }
//
// @Test
// private void createTest() {
// final ApplicationGroup applicationGroup = new ApplicationGroupBuilder().withNameKey("ApplicationGroup")
// .build();
//
// final Long createdApplicationGroupId = this.applicationGroupService.create(applicationGroup);
// this.entityManager.flush();
//
// SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(0, 1, 0, 0);
//
// final ApplicationGroup createdApplicationGroup = this.applicationGroupService.findById(createdApplicationGroupId);
//
// Assert.assertEquals(createdApplicationGroup.getNameKey(), applicationGroup.getNameKey());
// }
//
// @Test
// private void updateTest() {
//
// final ApplicationGroup applicationGroup = new ApplicationGroupBuilder().withNameKey("ApplicationGroup")
// .build();
//
// final Long createdApplicationGroupId = this.applicationGroupService.create(applicationGroup);
// final ApplicationGroup createdApplicationGroup = this.applicationGroupService.findById(createdApplicationGroupId);
// createdApplicationGroup.setNameKey("ApplicationGroup2");
//
// this.entityManager.flush();
// SQLStatementCountValidator.reset();
//
// this.applicationGroupService.update(createdApplicationGroup);
// this.entityManager.flush();
//
// SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(0, 0, 1, 0);
//
// final ApplicationGroup updatedApplicationGroup = this.applicationGroupService.findById(createdApplicationGroupId);
//
// Assert.assertEquals(updatedApplicationGroup.getNameKey(), createdApplicationGroup.getNameKey());
// }
//
// @Test(
// expectedExceptions = NotFoundException.class)
// private void deleteTest() {
// final ApplicationGroup applicationGroup = new ApplicationGroupBuilder().withNameKey("ApplicationGroup")
// .build();
//
// final Long createdApplicationGroupId = this.applicationGroupService.create(applicationGroup);
// this.entityManager.flush();
//
// SQLStatementCountValidator.reset();
//
// this.applicationGroupService.deleteById(createdApplicationGroupId);
// this.entityManager.flush();
//
// SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(0, 0, 0, 1);
//
// this.applicationGroupService.findById(createdApplicationGroupId);
// }
//
// }
