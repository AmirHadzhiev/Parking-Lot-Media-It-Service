// package de.bonprix.service;
//
// import java.util.ArrayList;
// import java.util.Iterator;
// import java.util.List;
//
// import javax.ws.rs.BadRequestException;
// import javax.ws.rs.NotFoundException;
//
// import org.junit.Assert;
// import org.mockito.ArgumentCaptor;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.springframework.core.convert.converter.Converter;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Sort;
// import org.testng.annotations.Test;
//
// import de.bonprix.BaseConfiguredUnitTest;
// import de.bonprix.dto.ApplicationType;
// import de.bonprix.dto.builder.ApplicationTypeBuilder;
// import de.bonprix.model.ApplicationTypeEntity;
// import de.bonprix.model.builder.ApplicationTypeEntityBuilder;
// import de.bonprix.repository.jpa.ApplicationTypeRepository;
//
// public class ApplicationTypeServiceImplTest extends BaseConfiguredUnitTest {
//
// @InjectMocks
// private ApplicationTypeServiceImpl applicationTypeService;
//
// @Mock
// private ApplicationTypeRepository applicationTypeEntityJpaRepository;
//
// @Test
// private void findAllTest() {
// final int page = 0;
// final int pageSize = 20;
//
// Mockito.when(this.applicationTypeEntityJpaRepository.findAll(Mockito.any(Pageable.class)))
// .thenReturn(new ApplicationTypeEntityPage());
//
// // this.applicationTypeService.findAll(new Paged(page, pageSize));
//
// final ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
// Mockito.verify(this.applicationTypeEntityJpaRepository)
// .findAll(pageableCaptor.capture());
//
// final Pageable capturedPageable = pageableCaptor.getValue();
// Assert.assertEquals(page, capturedPageable.getPageNumber());
// Assert.assertEquals(pageSize, capturedPageable.getPageSize());
// }
//
// @Test
// private void findByIdTest() {
// final long id = 1L;
//
// Mockito.when(this.applicationTypeEntityJpaRepository.findOne(Mockito.anyLong()))
// .thenReturn(new ApplicationTypeEntityBuilder().build());
//
// this.applicationTypeService.findById(id);
//
// Mockito.verify(this.applicationTypeEntityJpaRepository)
// .findOne(id);
// }
//
// @Test(
// expectedExceptions = NotFoundException.class)
// private void findByIdNotFoundTest() {
// final long id = 1L;
//
// Mockito.when(this.applicationTypeEntityJpaRepository.findOne(Mockito.anyLong()))
// .thenReturn(null);
//
// this.applicationTypeService.findById(id);
// }
//
// @Test
// private void deleteByIdTest() {
// final long id = 1L;
//
// Mockito.when(this.applicationTypeEntityJpaRepository.findOne(Mockito.anyLong()))
// .thenReturn(new ApplicationTypeEntity());
//
// this.applicationTypeService.deleteById(id);
//
// Mockito.verify(this.applicationTypeEntityJpaRepository)
// .delete(id);
// }
//
// @Test(
// expectedExceptions = NotFoundException.class)
// private void deleteByIdNotFoundExceptionTest() {
// final long id = 1L;
//
// this.applicationTypeService.deleteById(id);
//
// Mockito.verify(this.applicationTypeEntityJpaRepository)
// .delete(id);
// }
//
// @Test
// private void createTest() {
// final ApplicationType applicationType = new ApplicationTypeBuilder().withNameKey("ApplicationType")
// .withOptlock(0L)
// .build();
//
// Mockito.when(this.applicationTypeEntityJpaRepository.save(Mockito.any(ApplicationTypeEntity.class)))
// .thenReturn(new ApplicationTypeEntityBuilder().withId(1L)
// .build());
//
// this.applicationTypeService.create(applicationType);
//
// final ArgumentCaptor<ApplicationTypeEntity> applicationTypeEntitiyCaptor = ArgumentCaptor.forClass(ApplicationTypeEntity.class);
// Mockito.verify(this.applicationTypeEntityJpaRepository)
// .save(applicationTypeEntitiyCaptor.capture());
//
// final ApplicationTypeEntity capturedApplicationTypeEntity = applicationTypeEntitiyCaptor.getValue();
// Assert.assertEquals(applicationType.getId(), capturedApplicationTypeEntity.getId());
// Assert.assertEquals(applicationType.getNameKey(), capturedApplicationTypeEntity.getNameKey());
// Assert.assertEquals(applicationType.getOptlock(), capturedApplicationTypeEntity.getOptlock());
// }
//
// @Test(
// expectedExceptions = BadRequestException.class)
// private void createWithIdTest() {
// final ApplicationType applicationType = new ApplicationTypeBuilder().withId(1l)
// .withNameKey("ApplicationType")
// .withOptlock(0L)
// .build();
//
// Mockito.when(this.applicationTypeEntityJpaRepository.save(Mockito.any(ApplicationTypeEntity.class)))
// .thenReturn(new ApplicationTypeEntityBuilder().build());
//
// this.applicationTypeService.create(applicationType);
// }
//
// @Test
// private void updateTest() {
// final ApplicationType applicationType = new ApplicationTypeBuilder().withId(1l)
// .withNameKey("ApplicationType")
// .withOptlock(0L)
// .build();
//
// final ApplicationTypeEntity foundApplicationTypeEntity = new ApplicationTypeEntity();
// Mockito.when(this.applicationTypeEntityJpaRepository.findOne(Mockito.anyLong()))
// .thenReturn(foundApplicationTypeEntity);
//
// Mockito.when(this.applicationTypeEntityJpaRepository.save(Mockito.any(ApplicationTypeEntity.class)))
// .thenReturn(new ApplicationTypeEntityBuilder().build());
//
// this.applicationTypeService.update(applicationType);
//
// Mockito.verify(this.applicationTypeEntityJpaRepository)
// .findOne(applicationType.getId());
//
// Mockito.verify(this.applicationTypeEntityJpaRepository)
// .save(foundApplicationTypeEntity);
// }
//
// @Test(
// expectedExceptions = NotFoundException.class)
// private void updateNotFoundExceptionTest() {
// final ApplicationType applicationType = new ApplicationTypeBuilder().withId(1L)
// .withNameKey("ApplicationType")
// .withOptlock(0L)
// .build();
//
// this.applicationTypeService.update(applicationType);
// }
//
// @Test(
// expectedExceptions = BadRequestException.class)
// private void updateWithoutIdTest() {
// final ApplicationType applicationType = new ApplicationTypeBuilder().withNameKey("ApplicationType")
// .withOptlock(0L)
// .build();
//
// this.applicationTypeService.update(applicationType);
// }
//
// public class ApplicationTypeEntityPage implements Page<ApplicationTypeEntity> {
// private final List<ApplicationTypeEntity> keyEntities = new ArrayList<>();
//
// @Override
// public int getNumber() {
// return 0;
// }
//
// @Override
// public int getSize() {
// return 0;
// }
//
// @Override
// public int getNumberOfElements() {
// return 0;
// }
//
// @Override
// public List<ApplicationTypeEntity> getContent() {
// return this.keyEntities;
// }
//
// @Override
// public boolean hasContent() {
// return false;
// }
//
// @Override
// public Sort getSort() {
// return null;
// }
//
// @Override
// public boolean isFirst() {
// return false;
// }
//
// @Override
// public boolean isLast() {
// return false;
// }
//
// @Override
// public boolean hasNext() {
// return false;
// }
//
// @Override
// public boolean hasPrevious() {
// return false;
// }
//
// @Override
// public Pageable nextPageable() {
// return null;
// }
//
// @Override
// public Pageable previousPageable() {
// return null;
// }
//
// @Override
// public Iterator<ApplicationTypeEntity> iterator() {
// return this.keyEntities.iterator();
// }
//
// @Override
// public int getTotalPages() {
// return 0;
// }
//
// @Override
// public long getTotalElements() {
// return 0;
// }
//
// @Override
// public <S> Page<S> map(Converter<? super ApplicationTypeEntity, ? extends S> converter) {
// return null;
// }
//
// }
//
// }
