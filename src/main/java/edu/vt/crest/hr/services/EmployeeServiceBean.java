package edu.vt.crest.hr.services;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import edu.vt.crest.hr.entity.EmployeeEntity;

/**
 * Implements an EmployeeService
 */
@ApplicationScoped
public class EmployeeServiceBean implements EmployeeService {

  @PersistenceContext
  private EntityManager em;

  /**
   * {@inheritDoc}
   */
  @Override
  public EmployeeEntity createEmployee(final EmployeeEntity employee) {
      em.persist(employee);
      return employee;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EmployeeEntity findById(final Long id) {
      final Query query = em.createNamedQuery(EmployeeEntity.NAMED_QUERY_FIND_BY_ID, EmployeeEntity.class);
      query.setParameter("id", id);
      return (EmployeeEntity) query.getSingleResult();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<EmployeeEntity> listAll(final Integer startPosition, final Integer maxResult) {
      final Query query = em.createNamedQuery(EmployeeEntity.NAMED_QUERY_LIST_ALL, EmployeeEntity.class);
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
  public EmployeeEntity update(final Long id, final EmployeeEntity employee) throws OptimisticLockException {
      final EmployeeEntity employeeEntity = em.find(EmployeeEntity.class, id, LockModeType.OPTIMISTIC);
      if (employeeEntity != null) {
          em.merge(employee);
          return employee;
      }
      throw new NoResultException("Could not find the requested resource to update");
  }
}
