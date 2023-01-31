// package de.bonprix.service.integration;
//
// import java.util.Arrays;
// import java.util.List;
//
// import javax.annotation.Resource;
// import javax.persistence.EntityManager;
// import org.junit.Assert;
// import org.springframework.test.context.ContextConfiguration;
// import org.testng.annotations.Test;
//
// import de.bonprix.dto.Application;
// import de.bonprix.dto.builder.ApplicationBuilder;
// import de.bonprix.dto.builder.ApplicationGroupBuilder;
// import de.bonprix.dto.builder.ApplicationTypeBuilder;
// import de.bonprix.repository.ApplicationRepositoryImpl;
// import de.bonprix.service.ApplicationServiceImpl;
// import de.bonprix.service.application.ApplicationService;
// import de.bonprix.service.application.fetch.ApplicationFetchOptions;
// import de.bonprix.service.application.filter.ApplicationFilter;
// import de.bonprix.sqlcount.SQLStatementCountValidator;
// import de.bonprix.test.InMemoryDbAwareUnitTest;
//
// @ContextConfiguration(classes = { ApplicationServiceImpl.class, ApplicationRepositoryImpl.class })
// public class ApplicationServiceImplInMemoryDbTest extends InMemoryDbAwareUnitTest {
//
// @Resource
// private ApplicationService applicationService;
//
// @Resource
// private EntityManager entityManager;
//
// @Test
// private void findAllTest() {
// List<Application> applications = this.applicationService.findAll( new ApplicationFilter(),
// new ApplicationFetchOptions());
//
// SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 0);
//
// Assert.assertEquals(4, applications.size());
//
// Assert.assertTrue(applications .get(0)
// .getApplicationType() == null);
// Assert.assertTrue(applications .get(0)
// .getApplicationGroup() == null);
// }
//
// @Test
// private void findAllWithFilterApplicationIdTest() {
// ApplicationFilter filter = new ApplicationFilter();
// filter.setApplicationIds(Arrays.asList(1L));
//
// List<Application> applications = this.applicationService.findAll(filter, new ApplicationFetchOptions());
//
// SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 0);
//
// Assert.assertEquals(1, applications.size());
// }
//
// @Test
// private void findAllWithFilterApplicationTypeIdTest() {
// ApplicationFilter filter = new ApplicationFilter();
// filter.setApplicationTypeIds(Arrays.asList(1L));
//
// List<Application> applications = this.applicationService.findAll(filter, new ApplicationFetchOptions());
//
// SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 0);
//
// Assert.assertEquals(2, applications.size());
// }
//
// @Test
// private void findAllWithFilterApplicationGroupIdTest() {
// ApplicationFilter filter = new ApplicationFilter();
// filter.setApplicationGroupIds(Arrays.asList(1L));
//
// List<Application> applications = this.applicationService.findAll(filter, new ApplicationFetchOptions());
//
// SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 0);
//
// Assert.assertEquals(2, applications.size());
// }
//
// @Test
// private void findAllFetchApplicationTypeTest() {
// ApplicationFetchOptions fetchOptions = new ApplicationFetchOptions();
// fetchOptions.setFetchApplicationType(true);
//
// List<Application> applications = this.applicationService.findAll(new ApplicationFilter(), fetchOptions);
//
// SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 0);
//
// Assert.assertTrue(applications .get(0)
// .getApplicationType() != null);
// Assert.assertTrue(applications .get(0)
// .getApplicationGroup() == null);
// }
//
// @Test
// private void findAllFetchApplicationGroupTest() {
// ApplicationFetchOptions fetchOptions = new ApplicationFetchOptions();
// fetchOptions.setFetchApplicationGroup(true);
//
// List<Application> applications = this.applicationService.findAll(new ApplicationFilter(), fetchOptions);
//
// SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 0);
//
// Assert.assertTrue(applications .get(0)
// .getApplicationType() == null);
// Assert.assertTrue(applications .get(0)
// .getApplicationGroup() != null);
// }
//
// @Test
// private void countTest() {
// int cnt = this.applicationService.count(new ApplicationFilter());
//
// SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 0);
//
// Assert.assertEquals(4, cnt);
// }
//
// @Test
// private void findByIdTest() {
// this.applicationService.findById(1L, new ApplicationFetchOptions());
//
// SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(1, 0, 0, 0);
// }
//
// @Test
// public void createTest() {
// Application newApplication = new ApplicationBuilder() .withApplicationType(new ApplicationTypeBuilder() .withId(1L)
// .build())
// .withApplicationGroup(new ApplicationGroupBuilder() .withId(1L)
// .build())
// .withName("NEW_APPLICATION")
// .withScmUrl("SOME_NEW_URL")
// .build();
//
// long createdApplicationId = this.applicationService.create(newApplication);
// this.entityManager.flush();
//
// SQLStatementCountValidator.assertSelectInsertUpdateDeleteCount(2, 1, 0, 0);
//
// Application createdApplication = this.applicationService.findById( createdApplicationId,
// new ApplicationFetchOptions().withFetchAll());
//
// Assert.assertEquals(1l, createdApplication .getApplicationType()
// .getId()
// .longValue());
// }
//
// }
