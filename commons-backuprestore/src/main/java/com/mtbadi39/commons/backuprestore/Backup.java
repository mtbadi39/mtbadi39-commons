package com.mtbadi39.commons.backuprestore;

import com.mtbadi39.commons.logging.LogUtils;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * This test case can be improved a lot, by using decent hibernate template and
 * session mocks. Or by making it an integration test wich actually uses a
 * database.
 *
 * @author Geoffrey De Smet
 */
public class Backup {

    public Backup() {
        hibernateDatabaseBackuper = new HibernateDatabaseBackuper();
        hibernateDatabaseBackuper.setXstreamMode(XStream.ID_REFERENCES);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        hibernateTemplate = new MockHibernateTemplate(sessionFactory);
        hibernateDatabaseBackuper.setHibernateTemplate(hibernateTemplate);
    }

    public void setDatabaseClasses(List<Class<?>> databaseClasses) {
        this.databaseClasses = databaseClasses;
    }

    public void setBackupLocation(String filename) throws Exception {
        try {
            File file = new File(filename);
            File dir = file.getParentFile();
            if (dir != null) {
                if (!dir.exists()) {
                    dir.mkdirs();
                }
            }
            Resource databaseBackupLocation = new FileSystemResource(file);
            hibernateDatabaseBackuper.setDatabaseBackupLocation(databaseBackupLocation);
        } catch (Exception e) {
            logger.error(LogUtils.resolveException("Backup", "setBackupLocation", e));
            throw new Exception(e);
        }
    }
    
    public void setXstreamMode(int xstreamMode) {
        hibernateDatabaseBackuper.setXstreamMode(xstreamMode);
    }
    
    public void dumpDatabase() throws Exception {

        List<?> list;
        Session session = sessionFactory.openSession();
        hibernateTemplate.ClearEntitiesList();
        try {
            for (Class<?> dbClass : databaseClasses) {
                // dump touts les objets dbClass;
                list = session.createCriteria(dbClass).list();
                for (Object o : list) {
                    hibernateTemplate.AddToEntitiesList(o);
                }
            }
            hibernateDatabaseBackuper.dumpDatabase();

        } catch (Exception e) {
            logger.error(LogUtils.resolveException("Backup", "dumpDatabase", e));
            throw new Exception(e);
        } finally {
            session.close();
            hibernateTemplate.ClearEntitiesList();
        }
    }
    private final Logger logger = LoggerFactory.getLogger(Backup.class);
    private SessionFactory sessionFactory;
    private final HibernateDatabaseBackuper hibernateDatabaseBackuper;
    private MockHibernateTemplate hibernateTemplate;
    private List<Class<?>> databaseClasses = new ArrayList<Class<?>>();
}
