package se.tain.util;

import org.junit.Test;
import org.mockito.InOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class JdbcUtilsTest {

    @Test
    public void closeResources_2argOrdering() {
        //given
        Connection con = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        JdbcUtils target = spy(new JdbcUtils(){
            protected void closeAutoClosable(AutoCloseable ac){
            }
        });

        //then
        target.closeResources(con, ps);
        InOrder inOrder = inOrder(target, target);

        inOrder.verify(target, times(1)).closeAutoClosable(eq(ps));
        inOrder.verify(target, times(1)).closeAutoClosable(eq(con));
    }

    @Test
    public void closeResources_3ArgOrdering() throws SQLException {
        //given
        Connection con = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        JdbcUtils target = spy(new JdbcUtils(){
            protected void closeAutoClosable(AutoCloseable ac){
            }
        });

        //then
        target.closeResources(con, ps, rs);
        InOrder inOrder = inOrder(target, target);

        inOrder.verify(target, times(1)).closeAutoClosable(eq(rs));
        inOrder.verify(target, times(1)).closeAutoClosable(eq(ps));
        inOrder.verify(target, times(1)).closeAutoClosable(eq(con));
    }

    @Test
    public void closeAutoClosable() throws Exception {
        //given
        AutoCloseable ac = mock(AutoCloseable.class);
        JdbcUtils target = new JdbcUtils();

        //then
        target.closeAutoClosable(ac);

        verify(ac, times(1)).close();
    }

    @Test
    public void closeAutoClosable_NotPropagateException() throws Exception {
        //given
        AutoCloseable ac = mock(AutoCloseable.class);
        JdbcUtils target = new JdbcUtils();

        //when
        doThrow(new SQLException()).when(ac).close();

        //then
        target.closeAutoClosable(ac);

        verify(ac, times(1)).close();
    }

    @Test
    public void closeAutoClosable_Nullable() throws Exception {
        //given
        JdbcUtils target = new JdbcUtils();

        //then
        target.closeAutoClosable(null);
    }

}