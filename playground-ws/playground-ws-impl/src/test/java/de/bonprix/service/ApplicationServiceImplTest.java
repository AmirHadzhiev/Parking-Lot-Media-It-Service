// package de.bonprix.service;
//
// import java.util.ArrayList;
// import javax.persistence.TypedQuery;
// import javax.ws.rs.BadRequestException;
// import javax.ws.rs.NotFoundException;
//
// import org.junit.Assert;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.testng.annotations.BeforeMethod;
// import org.testng.annotations.Test;
//
// import de.bonprix.BaseConfiguredUnitTest;
// import de.bonprix.dto.Application;
// import de.bonprix.dto.builder.ApplicationBuilder;
// import de.bonprix.dto.builder.ApplicationGroupBuilder;
// import de.bonprix.dto.builder.ApplicationTypeBuilder;
// import de.bonprix.model.ApplicationEntity;
// import de.bonprix.model.builder.ApplicationGroupEntityBuilder;
// import de.bonprix.model.builder.ApplicationTypeEntityBuilder;
// import de.bonprix.repository.ApplicationRepositoryImpl;
// import de.bonprix.repository.jpa.ApplicationRepository;
// import de.bonprix.repository.jpa.ApplicationGroupRepository;
// import de.bonprix.repository.jpa.ApplicationTypeRepository;
// import de.bonprix.service.application.fetch.ApplicationFetchOptions;
// import de.bonprix.service.application.filter.ApplicationFilter;
//
// public class ApplicationServiceImplTest extends BaseConfiguredUnitTest {
//
// @InjectMocks
// private ApplicationServiceImpl applicationService;
//
// @Mock
// private ApplicationRepositoryImpl applicationEntityRepositoryMock;
//
// @Mock
// private TypedQuery<ApplicationEntity> typedQueryApplicationEntityMock;
//
// @Mock
// private ApplicationRepository applicationEntityJpaRepositoryMock;
//
// @Mock
// private ApplicationTypeRepository applicationTypeEntityJpaRepositoryMock;
//
// @Mock
// private ApplicationGroupRepository applicationGroupEntityJpaRepositoryMock;
//
// @BeforeMethod
// private void initApplicationEntityRepository() {
// Mockito .when(this.applicationEntityRepositoryMock.findAll( Mockito.any(ApplicationFilter.class),
// Mockito.any(ApplicationFetchOptions.class)))
// .thenReturn(new ArrayList<>());
// Mockito .when(this.applicationEntityRepositoryMock.findOne( Mockito.any(ApplicationFilter.class),
// Mockito.any(ApplicationFetchOptions.class)))
// .thenReturn(new ApplicationEntity());
// }
//
// @Test()
// private void findAllTest() {
// this.applicationService.findAll(new ApplicationFilter(), new ApplicationFetchOptions());
//
// Mockito .verify(this.applicationEntityRepositoryMock)
// .findAll(Mockito.any(ApplicationFilter.class), Mockito.any(ApplicationFetchOptions.class));
// }
//
// @Test
// private void countTest() {
// this.applicationService.count(new ApplicationFilter());
//
// Mockito .verify(this.applicationEntityRepositoryMock)
// .count(Mockito.any(ApplicationFilter.class));
// }
//
// @Test
// private void findByIdTest() {
// this.applicationService.findById(1L, new ApplicationFetchOptions());
//
// Mockito .verify(this.applicationEntityRepositoryMock)
// .findOne(Mockito.any(ApplicationFilter.class), Mockito.any(ApplicationFetchOptions.class));
// }
//
// @Test
// private void deleteByIdTest() {
// long id = 1L;
//
// Mockito .when(this.applicationEntityJpaRepositoryMock.findOne(Mockito.anyLong()))
// .thenReturn(new ApplicationEntity());
//
// this.applicationService.deleteById(id);
//
// Mockito .verify(this.applicationEntityJpaRepositoryMock)
// .delete(id);
// }
//
// @Test(expectedExceptions = NotFoundException.class)
// private void deleteByIdNotFoundExceptionTest() {
// long id = 1L;
//
// this.applicationService.deleteById(id);
//
// Mockito .verify(this.applicationEntityJpaRepositoryMock)
// .delete(id);
// }
//
// @Test
// private void createTest() {
// ApplicationEntity applicationEntityMock = Mockito.mock(ApplicationEntity.class);
// Mockito .when(applicationEntityMock.getId())
// .thenReturn(1L);
// Mockito .when(this.applicationEntityJpaRepositoryMock.save(Mockito.any(ApplicationEntity.class)))
// .thenReturn(applicationEntityMock);
//
// Application application = new ApplicationBuilder() .withName("Application")
// .withApplicationType(new ApplicationTypeBuilder() .withId(2l)
// .build())
// .withApplicationGroup(new ApplicationGroupBuilder() .withId(3l)
// .build())
// .withScmUrl("scm_url")
// .withOptlock(0L)
// .build();
//
// Mockito .when(this.applicationTypeEntityJpaRepositoryMock.findOne(application .getApplicationType()
// .getId()))
// .thenReturn(new ApplicationTypeEntityBuilder() .withId(application .getApplicationType()
// .getId())
// .build());
//
// Mockito .when(this.applicationGroupEntityJpaRepositoryMock.findOne(application .getApplicationGroup()
// .getId()))
// .thenReturn(new ApplicationGroupEntityBuilder() .withId(application .getApplicationGroup()
// .getId())
// .build());
//
// long createdApplicationId = this.applicationService.create(application);
//
// Mockito .verify(this.applicationEntityJpaRepositoryMock)
// .save(Mockito.any(ApplicationEntity.class));
// Mockito .verify(applicationEntityMock)
// .getId();
//
// Assert.assertEquals(applicationEntityMock .getId()
// .longValue(),
// createdApplicationId);
//
// }
//
// @Test(expectedExceptions = BadRequestException.class)
// private void createWithIdTest() {
// Application application = new ApplicationBuilder() .withId(1L)
// .withName("Application")
// .withApplicationType(new ApplicationTypeBuilder() .withId(2l)
// .build())
// .withApplicationGroup(new ApplicationGroupBuilder() .withId(3l)
// .build())
// .withScmUrl("scm_url")
// .withOptlock(0L)
// .build();
//
// this.applicationService.create(application);
// }
//
// @Test
// private void updateTest() {
// Application application = new ApplicationBuilder() .withId(1L)
// .withName("Application")
// .withApplicationType(new ApplicationTypeBuilder() .withId(2l)
// .build())
// .withApplicationGroup(new ApplicationGroupBuilder() .withId(3l)
// .build())
// .withScmUrl("scm_url")
// .withOptlock(0L)
// .build();
//
// Mockito .when(this.applicationTypeEntityJpaRepositoryMock.findOne(application .getApplicationType()
// .getId()))
// .thenReturn(new ApplicationTypeEntityBuilder() .withId(application .getApplicationType()
// .getId())
// .build());
//
// Mockito .when(this.applicationGroupEntityJpaRepositoryMock.findOne(application .getApplicationGroup()
// .getId()))
// .thenReturn(new ApplicationGroupEntityBuilder() .withId(application .getApplicationGroup()
// .getId())
// .build());
//
// this.applicationService.update(application);
//
// Mockito .verify(this.applicationEntityRepositoryMock)
// .findOne(Mockito.any(ApplicationFilter.class), Mockito.any(ApplicationFetchOptions.class));
// Mockito .verify(this.applicationEntityJpaRepositoryMock)
// .save(Mockito.any(ApplicationEntity.class));
//
// }
//
// @Test(expectedExceptions = BadRequestException.class)
// private void updateWithoutIdTest() {
// Application application = new ApplicationBuilder() .withName("Application")
// .withApplicationType(new ApplicationTypeBuilder() .withId(2l)
// .build())
// .withApplicationGroup(new ApplicationGroupBuilder() .withId(3l)
// .build())
// .withScmUrl("scm_url")
// .withOptlock(0L)
// .build();
//
// this.applicationService.update(application);
// }
//
// }
