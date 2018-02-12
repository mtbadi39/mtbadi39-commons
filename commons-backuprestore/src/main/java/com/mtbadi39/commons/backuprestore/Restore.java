package com.mtbadi39.commons.backuprestore;

import com.mtbadi39.commons.logging.LogUtils;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import org.hibernate.ReplicationMode;
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
public class Restore {

    public Restore() {
        hibernateDatabaseBackuper = new HibernateDatabaseBackuper();
        hibernateDatabaseBackuper.setXstreamMode(XStream.ID_REFERENCES);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        MockHibernateTemplate hibernateTemplate = new MockHibernateTemplate(sessionFactory);
        hibernateDatabaseBackuper.setHibernateTemplate(hibernateTemplate);
    }

    public void setBackupLocation(String filename) throws Exception {
        try {
            File file = new File(filename);
            Resource databaseBackupLocation = new FileSystemResource(file);
            hibernateDatabaseBackuper.setDatabaseBackupLocation(databaseBackupLocation);
        } catch (Exception ex) {
            logger.error(LogUtils.resolveException("Restore", "setBackupLocation", ex));
            throw new Exception(ex);
        }
    }

    public void setUseSaveOrUpdateToRestore(boolean useSaveOrUpdateToRestore) {
        hibernateDatabaseBackuper.setUseSaveOrUpdateToRestore(useSaveOrUpdateToRestore);
    }

    public void setCleanDatabaseBeforeRestore(boolean cleanDatabaseBeforeRestore) {
        hibernateDatabaseBackuper.setCleanDatabaseBeforeRestore(true);
    }

    public void setRestoreReplicationMode(ReplicationMode replicationMode) {
        hibernateDatabaseBackuper.setRestoreReplicationMode(replicationMode);
    }

    public void setXstreamMode(int xstreamMode) {
        hibernateDatabaseBackuper.setXstreamMode(xstreamMode);
    }

    public void restoreDatabase() throws Exception {
        try {

            hibernateDatabaseBackuper.setUseSaveOrUpdateToRestore(false);
            hibernateDatabaseBackuper.restoreDatabase();
        } catch (Exception ex) {
            logger.error(LogUtils.resolveException("Restore", "restoreDatabase", ex));
            throw new Exception(ex);
            //ex.printStackTrace();
        }

    }
    private final Logger logger = LoggerFactory.getLogger(Restore.class);
    private final HibernateDatabaseBackuper hibernateDatabaseBackuper;
}
