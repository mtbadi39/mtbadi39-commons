package com.mtbadi39.commons.backuprestore;

import com.mtbadi39.commons.logging.LogUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;

public class MockHibernateTemplate extends HibernateTemplate {

    private final Logger _logger = LoggerFactory.getLogger(MockHibernateTemplate.class);
    private final List EntitiesList = new ArrayList();
    private final SessionFactory sessionFactory;

    public MockHibernateTemplate(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public <T> List<T> loadAll(Class<T> entityClass) throws DataAccessException {
        return new ArrayList(EntitiesList);
    }

    @Override
    public void deleteAll(Collection<?> entities) throws DataAccessException {
        EntitiesList.clear();
    }

    public void AddToEntitiesList(Object object) {
        EntitiesList.add(object);
    }

    public void ClearEntitiesList() {
        EntitiesList.clear();
    }

    @Override
    public <T> T execute(HibernateCallback<T> action) throws DataAccessException {
        try {
            return action.doInHibernate(this.sessionFactory.getCurrentSession());
        } catch (Exception e) {
            _logger.error(LogUtils.resolveException("MockHibernateTemplate", "execute", e));
            return null;
        }
    }

}
