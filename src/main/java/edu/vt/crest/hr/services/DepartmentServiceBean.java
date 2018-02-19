package edu.vt.crest.hr.services;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import edu.vt.crest.hr.entity.DepartmentEntity;

/**
 * Implements a DepartmentService
 */
@ApplicationScoped
public class DepartmentServiceBean implements DepartmentService {

  @PersistenceContext
  private EntityManager em;

  /**
   * {@inheritDoc}
   */
  @Override
  public DepartmentEntity createDepartment(final DepartmentEntity department) {
      if (department.getEmployees() != null) {
          department.getEmployees().stream().map((employee) -> {
              em.persist(employee);
              return employee;
          });
      }
      em.persist(department);
      return department;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DepartmentEntity findById(final Long id) {
      final Query query = em.createNamedQuery(DepartmentEntity.NAMED_QUERY_FIND_BY_ID, DepartmentEntity.class);
      query.setParameter("id", id);
      return (DepartmentEntity) query.getSingleResult();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<DepartmentEntity> listAll(final Integer startPosition, final Integer maxResult) {
      final Query query = em.createNamedQuery(DepartmentEntity.NAMED_QUERY_LIST_ALL, DepartmentEntity.class);
      if (maxResult != null) {
          query.setMaxResults(maxResult);
      }
      if (startPosition != null) {
          query.setFirstResult(startPosition);
      }
      return query.getResultList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DepartmentEntity update(final Long id, final DepartmentEntity department) throws OptimisticLockException {
      final DepartmentEntity departmentEntity = em.find(DepartmentEntity.class, id, LockModeType.OPTIMISTIC);
      if (departmentEntity != null) {
          em.merge(department);
          return department;
      }
      throw new NoResultException("Could not find the requested resource to update");
  }
}
