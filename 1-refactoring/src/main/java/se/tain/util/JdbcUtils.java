package se.tain.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtils {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcUtils.class);

    public void closeResources(Connection con, Statement ps){
        closeAutoClosable(ps);
        closeAutoClosable(con);
    }

    public void closeResources(Connection con, Statement ps, ResultSet rs){
        closeAutoClosable(rs);
        closeAutoClosable(ps);
        closeAutoClosable(con);
    }

    protected void closeAutoClosable(AutoCloseable ac){

        if(ac == null){ return; }

        try {
            ac.close();
        } catch (SQLException ex) {
            LOG.debug("Could not close JDBC Resource", ex);
        } catch (Throwable ex) {
            LOG.debug("Unexpected exception on closing JDBC Resource", ex);
        }
    }
}
