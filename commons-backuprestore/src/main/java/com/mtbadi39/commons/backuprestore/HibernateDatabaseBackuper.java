package com.mtbadi39.commons.backuprestore;

import com.mtbadi39.commons.logging.LogUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.hibernate.converter.HibernatePersistentCollectionConverter;
import com.thoughtworks.xstream.hibernate.converter.HibernatePersistentMapConverter;
import com.thoughtworks.xstream.hibernate.converter.HibernatePersistentSortedMapConverter;
import com.thoughtworks.xstream.hibernate.converter.HibernatePersistentSortedSetConverter;
import com.thoughtworks.xstream.hibernate.converter.HibernateProxyConverter;
import com.thoughtworks.xstream.hibernate.mapper.HibernateMapper;
//import com.thoughtworks.xstream.io.xml.JDomDriver;
//import com.thoughtworks.xstream.io.xml.Xpp3DomDriver;
import com.thoughtworks.xstream.io.xml.KXml2DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.util.LinkedList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.dao.DataAccessException;

import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.util.Assert;

/**
 * An implementation of DabaseBackuper that approaches the database through
 * Hibernate and the backup file through xstream.
 * <p/>
 * An example that loads test data upon initialization:
 * <p/>
 *
 * <pre> &lt;bean id="databaseBackuper"
 * class="be.kahosl.mammoet.serviceimpl.app.database.Hibernate3ToXstreamXmlDatabaseBackuper"&gt;
 * &lt;property name="sessionFactory" ref="sessionFactory"/&gt; &lt;property
 * name="databaseBackupLocation"
 * value="classpath:/org/springframework/petclinic/testDatabaseBackup.xml"/&gt;
 * &lt;property name=xstreamMode"&gt; &lt;bean
 * id="com.thoughtworks.xstream.XStream.ID_REFERENCES"
 * class="org.springframework.beans.factory.config.FieldRetrievingFactoryBean"/&gt;
 * &lt;/property&gt; &lt;property name="restoreDatabaseOnInitialization"
 * value="true"/&gt; &lt;/bean&gt;
 * </pre>
 * <p/>
 * Requires Xstream to be in the classpath.
 *
 * @author Geoffrey De Smet
 * @see DatabaseBackuper
 */
public class HibernateDatabaseBackuper extends AbstractDatabaseBackuper {

    private MockHibernateTemplate hibernateTemplate;
    private Class<?>[] databaseClasses = new Class<?>[]{Object.class};
    private String databaseBackupEncoding = "UTF-8";
    private boolean useSaveOrUpdateToRestore = false;
    private ReplicationMode restoreReplicationMode = ReplicationMode.OVERWRITE;
    private int xstreamMode = XStream.ID_REFERENCES;

    /**
     * Set the Hibernate SessionFactory to be used by this DAO. Will
     * automatically create a HibernateTemplate for the given SessionFactory.
     *
     * @param sessionFactory
     * @see #createHibernateTemplate
     * @see #setHibernateTemplate
     */
    public final void setSessionFactory(SessionFactory sessionFactory) {
        this.hibernateTemplate = createHibernateTemplate(sessionFactory);
    }

    /**
     * Create a HibernateTemplate for the given SessionFactory. Only invoked if
     * populating the DAO with a SessionFactory reference!
     * <p>
     * Can be overridden in subclasses to provide a HibernateTemplate instance
     * with different configuration, or a custom HibernateTemplate subclass.
     *
     * @param sessionFactory the Hibernate SessionFactory to create a
     * HibernateTemplate for
     * @return the new HibernateTemplate instance
     * @see #setSessionFactory
     */
    protected MockHibernateTemplate createHibernateTemplate(SessionFactory sessionFactory) {
        return new MockHibernateTemplate(sessionFactory);
    }

    /**
     * Set the HibernateTemplate for this DAO explicitly, as an alternative to
     * specifying a SessionFactory.
     *
     * @param hibernateTemplate
     * @see #setSessionFactory
     */
    public final void setHibernateTemplate(MockHibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    /**
     * Set the list of Class objects which will be backuped. Note that
     * {@link #restoreDatabase()} fails if any element directly under the root
     * element (of the database backup file) is not an instance of any of these
     * database classes. Defaults to a list with a single element: the class of
     * Object.
     *
     * @param databaseClasses a list of classes to clean, dump and restore
     */
    public void setDatabaseClasses(Class<?>[] databaseClasses) {
        this.databaseClasses = databaseClasses;
    }

    /**
     * Set the encoding of the database backup resource. Defaults to UTF-8.
     *
     * @param databaseBackupEncoding the encoding of the database backup
     * resource
     */
    public void setDatabaseBackupEncoding(String databaseBackupEncoding) {
        this.databaseBackupEncoding = databaseBackupEncoding;
    }

    /**
     * Set wheter to use replicate or saveOrUpdate to restore the database.
     * Defaults to false, which means that replicate is used.
     *
     * @param useSaveOrUpdateToRestore true when saveOrUpdate should be used to
     * restore
     */
    public void setUseSaveOrUpdateToRestore(boolean useSaveOrUpdateToRestore) {
        this.useSaveOrUpdateToRestore = useSaveOrUpdateToRestore;
    }

    /**
     * Set the hibernate replication mode used when restoring the database.
     * Defaults to OVERWRITE, which means that any existing records will be
     * overwritten.
     *
     * @param restoreReplicationMode the hibernate replication mode
     */
    public void setRestoreReplicationMode(ReplicationMode restoreReplicationMode) {
        this.restoreReplicationMode = restoreReplicationMode;
    }

    /**
     * Set the xstream mode of the database backup resource. Defaults to
     * XStream.XPATH_REFERENCES. This is typically injected by use of
     * FieldRetrievingFactoryBean bean.
     *
     * @param xstreamMode can be XStream.XPATH_REFERENCES,
     * XStream.ID_REFERENCES, XStream.NO_REFERENCES
     */
    public void setXstreamMode(int xstreamMode) {
        this.xstreamMode = xstreamMode;
    }

    @Override
    public void setCleanDatabaseBeforeRestore(boolean cleanDatabaseBeforeRestore) {
        this.cleanDatabaseBeforeRestore = cleanDatabaseBeforeRestore;
    }

    @Override
    public void setRestoreDatabaseOnInitialization(boolean restoreDatabaseOnInitialization) {
        this.restoreDatabaseOnInitialization = restoreDatabaseOnInitialization;
    }

    @Override
    protected void checkConfig() {
        super.checkConfig();
        Assert.notNull(hibernateTemplate, "sessionFactory or hibernateTemplate is required");
    }

    @Override
    public void cleanDatabase() throws DataAccessException {
        for (Class<?> databaseClass : databaseClasses) {
            List<?> recordList = hibernateTemplate.loadAll(databaseClass);
            hibernateTemplate.deleteAll(recordList);
        }
        logger.debug("Database cleaned");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void dumpDatabase() throws Exception {
        List<Object> recordList = new LinkedList<Object>();
        for (Class<?> databaseClass : databaseClasses) {
            recordList.addAll(hibernateTemplate.loadAll(databaseClass));
        }
        //XStream xStream = new XStream(new KXml2DomDriver());

        XStream xStream = new XStream(new KXml2DomDriver()) {

            @Override
            protected MapperWrapper wrapMapper(final MapperWrapper next) {
                return new HibernateMapper(next);
            }

        };

        xStream.setMode(xstreamMode);

        //xStream.registerConverter(new ReflectionConverter(xStream.getMapper(), xStream.getReflectionProvider()));
        xStream.registerConverter(new HibernateProxyConverter());
        xStream.registerConverter(new HibernatePersistentCollectionConverter(xStream.getMapper()));
        xStream.registerConverter(new HibernatePersistentSortedSetConverter(xStream.getMapper()));
        xStream.registerConverter(new HibernatePersistentMapConverter(xStream.getMapper()));
        xStream.registerConverter(new HibernatePersistentSortedMapConverter(xStream.getMapper()));

        try {
            OutputStream out = new FileOutputStream(databaseBackupLocation.getFile());
            try {
                xStream.toXML(recordList, new OutputStreamWriter(out, databaseBackupEncoding));
            } finally {
                out.close();
            }
        } catch (Exception e) {
            logger.error(LogUtils.resolveException("HibernateDatabaseBackuper", "dumpDatabase", e));
            throw new IllegalArgumentException("Failed dumping the database to "
                    + databaseBackupLocation, e);
        }
        logger.debug("Database dumped");
    }

    @Override
    public void restoreDatabase() throws Exception {
        if (cleanDatabaseBeforeRestore) {
            cleanDatabase();
        }
        final List<Object> recordList;
        //XStream xStream = new XStream(new KXml2DomDriver());

        XStream xStream = new XStream(new KXml2DomDriver()) {

            @Override
            protected MapperWrapper wrapMapper(final MapperWrapper next) {
                return new HibernateMapper(next);
            }

        };

        xStream.setMode(xstreamMode);

        //xStream.registerConverter(new ReflectionConverter(xStream.getMapper(), xStream.getReflectionProvider()));
        xStream.registerConverter(new HibernateProxyConverter());
        xStream.registerConverter(new HibernatePersistentCollectionConverter(xStream.getMapper()));
        xStream.registerConverter(new HibernatePersistentSortedSetConverter(xStream.getMapper()));
        xStream.registerConverter(new HibernatePersistentMapConverter(xStream.getMapper()));
        xStream.registerConverter(new HibernatePersistentSortedMapConverter(xStream.getMapper()));

        try {
            InputStream in = databaseBackupLocation.getInputStream();

            try {
                Object o = xStream.fromXML(new InputStreamReader(in, databaseBackupEncoding));
                if (o instanceof List) {
                    recordList = (List) o;
                } else {
                    logger.error("Root element of the database backup resource should be a list");
                    throw new IllegalArgumentException("Root element of the database backup resource should be a list");
                }
            } finally {
                in.close();
            }
        } catch (Exception e) {
            logger.error(LogUtils.resolveException("HibernateDatabaseBackuper", "restoreDatabase", e));
            throw new IllegalArgumentException("Failed restoring the database from "
                    + databaseBackupLocation, e);
        }

        checkRecordList(recordList);

        if (useSaveOrUpdateToRestore) {
            try {
                Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
                Transaction tx = session.beginTransaction();
                for (Object o : recordList) {
                    System.out.println(" => [HibernateDatabaseBackuper] restoreDatabase : " + o);
                    hibernateTemplate.saveOrUpdate(o);
                }
                tx.commit();
            } catch (Exception ex) {
                logger.error(LogUtils.resolveException("HibernateDatabaseBackuper", "restoreDatabase", ex));
                //throw new Exception(ex);
                ex.printStackTrace();
            }
        } else {
            // hibernateTemplate.replicateAll() is not supported in HibernateTemplate
            hibernateTemplate.execute(new HibernateCallback() {
                @Override
                public Object doInHibernate(Session session) throws HibernateException {
                    // hibernateTemplate.checkWriteOperationAllowed(session); has non-public access on the template
                    // Rollback to java 1.4 compatible for (Object record : recordList) {
                    //Object r = null;
                    try {
                        Transaction tx = session.beginTransaction();
                        //int i = 0;
                        for (Object record : recordList) {
                            //Object record = it.next();
                            //r = record;
                            session.replicate(record, restoreReplicationMode);
                            //i++;
                            //if (i % 10 == 0) { //20, same as the JDBC batch size
                            //flush a batch of inserts and release memory:
                            //session.flush();
                            //session.clear();
                            //}
                        }
                        tx.commit();
                    } catch (Exception ex) {
                        //System.err.println(" => [HibernateDatabaseBackuper] restoreDatabase : " + r);
                        logger.error(LogUtils.resolveException("HibernateDatabaseBackuper", "restoreDatabase", ex));
                        //throw new HibernateException(ex);
                        ex.printStackTrace();
                    }
                    return null;
                }
            });
        }
        logger.debug("Database restored");
    }

    /**
     * check if all records are indeed an instance of one of the databaseClasses
     *
     * @param recordList list of objects to check
     */
    private void checkRecordList(List<Object> recordList) {
        // Rollback to java 1.4 compatible
        for (Object record : recordList) {
            //for (Iterator it = recordList.iterator(); it.hasNext();) {
            try {
                //Object record = it.next();
                boolean instanceOfDatabaseClasses = false;
                for (Class<?> databaseClass : databaseClasses) {
                    if (databaseClass.isInstance(record)) {
                        instanceOfDatabaseClasses = true;
                        break;
                    }
                }

                if (!instanceOfDatabaseClasses) {
                    logger.error("The object found to restore is not an instance of one of the database classes:" + record);
                    throw new IllegalArgumentException(
                            "The object found to restore is not an instance of one of the database classes:" + record);
                }
            } catch (Exception ex) {
                logger.error(LogUtils.resolveException("HibernateDatabaseBackuper", "checkRecordList", ex));
            }
        }
    }
}
