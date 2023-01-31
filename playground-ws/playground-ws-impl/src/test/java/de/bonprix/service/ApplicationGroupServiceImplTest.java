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
// import de.bonprix.dto.ApplicationGroup;
// import de.bonprix.dto.builder.ApplicationGroupBuilder;
// import de.bonprix.model.ApplicationGroupEntity;
// import de.bonprix.model.builder.ApplicationGroupEntityBuilder;
// import de.bonprix.repository.jpa.ApplicationGroupRepository;
//
// public class ApplicationGroupServiceImplTest extends BaseConfiguredUnitTest {
//
// @InjectMocks
// private ApplicationGroupServiceImpl applicationGroupService;
//
// @Mock
// private ApplicationGroupRepository applicationGroupEntityJpaRepository;
//
// @Test
// private void findAllTest() {
// final int page = 0;
// final int pageSize = 20;
//
// Mockito.when(this.applicationGroupEntityJpaRepository.findAll(Mockito.any(Pageable.class)))
// .thenReturn(new ApplicationGroupEntityPage());
//
// // this.applicationGroupService.findAll(new Paged(page, pageSize));
//
// final ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
// Mockito.verify(this.applicationGroupEntityJpaRepository)
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
// Mockito.when(this.applicationGroupEntityJpaRepository.findOne(Mockito.anyLong()))
// .thenReturn(new ApplicationGroupEntityBuilder().build());
//
// this.applicationGroupService.findById(id);
//
// Mockito.verify(this.applicationGroupEntityJpaRepository)
// .findOne(id);
// }
//
// @Test(
// expectedExceptions = NotFoundException.class)
// private void findByIdNotFoundTest() {
// final long id = 1L;
//
// Mockito.when(this.applicationGroupEntityJpaRepository.findOne(Mockito.anyLong()))
// .thenReturn(null);
//
// this.applicationGroupService.findById(id);
// }
//
// @Test
// private void deleteByIdTest() {
// final long id = 1L;
//
// Mockito.when(this.applicationGroupEntityJpaRepository.findOne(Mockito.anyLong()))
// .thenReturn(new ApplicationGroupEntity());
//
// this.applicationGroupService.deleteById(id);
//
// Mockito.verify(this.applicationGroupEntityJpaRepository)
// .delete(id);
// }
//
// @Test(
// expectedExceptions = NotFoundException.class)
// private void deleteByIdNotFoundExceptionTest() {
// final long id = 1L;
//
// this.applicationGroupService.deleteById(id);
//
// Mockito.verify(this.applicationGroupEntityJpaRepository)
// .delete(id);
// }
//
// @Test
// private void createTest() {
// final ApplicationGroup applicationGroup = new ApplicationGroupBuilder().withNameKey("ApplicationGroup")
// .withOptlock(0L)
// .build();
//
// Mockito.when(this.applicationGroupEntityJpaRepository.save(Mockito.any(ApplicationGroupEntity.class)))
// .thenReturn(new ApplicationGroupEntityBuilder().withId(1L)
// .build());
//
// this.applicationGroupService.create(applicationGroup);
// }
//
// @Test(
// expectedExceptions = BadRequestException.class)
// private void createWithIdTest() {
// final ApplicationGroup applicationGroup = new ApplicationGroupBuilder().withId(1l)
// .withNameKey("ApplicationGroup")
// .withOptlock(0L)
// .build();
//
// Mockito.when(this.applicationGroupEntityJpaRepository.save(Mockito.any(ApplicationGroupEntity.class)))
// .thenReturn(new ApplicationGroupEntityBuilder().build());
//
// this.applicationGroupService.create(applicationGroup);
// }
//
// @Test
// private void updateTest() {
// final ApplicationGroup applicationGroup = new ApplicationGroupBuilder().withId(1l)
// .withNameKey("ApplicationGroup")
// .withOptlock(0L)
// .build();
//
// final ApplicationGroupEntity foundApplicationGroupEntity = new ApplicationGroupEntity();
// Mockito.when(this.applicationGroupEntityJpaRepository.findOne(Mockito.anyLong()))
// .thenReturn(foundApplicationGroupEntity);
//
// Mockito.when(this.applicationGroupEntityJpaRepository.save(Mockito.any(ApplicationGroupEntity.class)))
// .thenReturn(new ApplicationGroupEntityBuilder().build());
//
// this.applicationGroupService.update(applicationGroup);
//
// Mockito.verify(this.applicationGroupEntityJpaRepository)
// .findOne(applicationGroup.getId());
//
// Mockito.verify(this.applicationGroupEntityJpaRepository)
// .save(foundApplicationGroupEntity);
//
// }
//
// @Test(
// expectedExceptions = NotFoundException.class)
// private void updateNotFoundException() {
// final ApplicationGroup applicationGroup = new ApplicationGroupBuilder().withId(1L)
// .withNameKey("ApplicationGroup")
// .withOptlock(0L)
// .build();
//
// this.applicationGroupService.update(applicationGroup);
// }
//
// @Test(
// expectedExceptions = BadRequestException.class)
// private void updateWithoutIdTest() {
// final ApplicationGroup applicationGroup = new ApplicationGroupBuilder().withNameKey("ApplicationGroup")
// .withOptlock(0L)
// .build();
//
// this.applicationGroupService.update(applicationGroup);
// }
//
// public class ApplicationGroupEntityPage implements Page<ApplicationGroupEntity> {
// private final List<ApplicationGroupEntity> keyEntities = new ArrayList<>();
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
// public List<ApplicationGroupEntity> getContent() {
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
// public Iterator<ApplicationGroupEntity> iterator() {
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
// public <S> Page<S> map(Converter<? super ApplicationGroupEntity, ? extends S> converter) {
// return null;
// }
//
// }
//
// }
