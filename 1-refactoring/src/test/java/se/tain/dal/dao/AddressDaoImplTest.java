package se.tain.dal.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import se.tain.entity.AddressEntity;
import se.tain.error.DataAccessException;
import se.tain.util.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static se.tain.dal.dao.AddressDaoImpl.*;

@RunWith(MockitoJUnitRunner.class)
public class AddressDaoImplTest {

    @Mock private JdbcUtils jdbcUtils;
    @Mock private DataSource ds;
    @Mock private Connection con;
    @Mock private PreparedStatement ps;
    @Mock private ResultSet rs;

    @Test
    public void addAddress() throws SQLException, DataAccessException {
        //given
        String query = "insert into addressentry values (addressentry_id_seq.nextval, ?, ?)";
        String name = "Name#1";
        String phoneNumber = "PhoneNumber#1";

        AddressDao target = new AddressDaoImpl(ds, jdbcUtils);
        AddressEntity addressEntity = new AddressEntity(0, name, phoneNumber);

        //when
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(eq(query))).thenReturn(ps);

        //then
        target.addAddress(addressEntity);

        verify(ps, times(1)).setString(eq(1), eq(name));
        verify(ps, times(1)).setString(eq(2), eq(phoneNumber));
        verify(ps, times(1)).executeUpdate();
        verify(con, times(1)).commit();
        verify(jdbcUtils, times(1)).closeResources(eq(con), eq(ps));
    }

    @Test(expected = DataAccessException.class)
    public void addAddress_Exception() throws SQLException, DataAccessException {
        //given
        AddressDao target = new AddressDaoImpl(ds, jdbcUtils);
        AddressEntity addressEntity = new AddressEntity(0, "Name#2", "PhoneNumber#2");

        //when
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(any())).thenReturn(ps);
        doThrow(new SQLException()).when(con).commit();

        //then
        try {
            target.addAddress(addressEntity);
        } finally {
            verify(jdbcUtils, times(1)).closeResources(eq(con), eq(ps));
        }

    }

    @Test
    public void getAddressByName() throws SQLException, DataAccessException {
        //given
        String query = "select * from addressentry where name = ?";
        String name = "Name#3";
        String phoneNumber = "phoneNumber#2";
        long id = 12345L;

        AddressDao target = new AddressDaoImpl(ds, jdbcUtils);

        //when
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(eq(query))).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getLong(PERSON_ID)).thenReturn(id);
        when(rs.getString(PERSON_NAME)).thenReturn(name);
        when(rs.getString(PERSON_PHONE_NUMBER)).thenReturn(phoneNumber);

        //then
        AddressEntity entity = target.getAddressByName(name);

        verify(ps, times(1)).setString(eq(1), eq(name));
        verify(ps, times(1)).executeQuery();
        verify(rs, times(1)).next();
        verify(con, never()).commit();
        verify(jdbcUtils, times(1)).closeResources(eq(con), eq(ps), eq(rs));

        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(phoneNumber, entity.getPhoneNumber());
    }

    @Test(expected = DataAccessException.class)
    public void getAddressByName_Exception() throws SQLException, DataAccessException {
        //given
        String query = "select * from addressentry where name = ?";
        String name = "Name#3";

        AddressDao target = new AddressDaoImpl(ds, jdbcUtils);

        //when
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(eq(query))).thenReturn(ps);
        when(ps.executeQuery()).thenThrow(new SQLException());

        //then
        try {
            target.getAddressByName(name);
        } finally {
            verify(jdbcUtils, times(1)).closeResources(eq(con), eq(ps), eq(null));
        }
    }

    @Test
    public void getAllAddresses() throws SQLException, DataAccessException {
        //given
        String query = "select * from addressentry";
        String name = "Name#4", name2 = "Name#5";
        String phoneNumber = "phoneNumber#3", phoneNumber2 = "phoneNumber#4";
        long id = 12346L, id2 = 12347L;

        AddressDao target = new AddressDaoImpl(ds, jdbcUtils);

        //when
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(eq(query))).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getLong(PERSON_ID)).thenReturn(id, id2);
        when(rs.getString(PERSON_NAME)).thenReturn(name, name2);
        when(rs.getString(PERSON_PHONE_NUMBER)).thenReturn(phoneNumber, phoneNumber2);

        //then
        List<AddressEntity> list = target.getAllAddresses();

        verify(ps, times(1)).executeQuery();
        verify(rs, times(3)).next();
        verify(con, never()).commit();
        verify(jdbcUtils, times(1)).closeResources(eq(con), eq(ps), eq(rs));

        assertNotNull(list);
        assertEquals(2, list.size());

        AddressEntity en = list.get(0);
        assertEquals(id, en.getId());
        assertEquals(name, en.getName());
        assertEquals(phoneNumber, en.getPhoneNumber());

        en = list.get(1);
        assertEquals(id2, en.getId());
        assertEquals(name2, en.getName());
        assertEquals(phoneNumber2, en.getPhoneNumber());
    }

    @Test(expected = DataAccessException.class)
    public void getAllAddresses_Exception() throws SQLException, DataAccessException {
        //given
        String query = "select * from addressentry";

        AddressDao target = new AddressDaoImpl(ds, jdbcUtils);

        //when
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(eq(query))).thenReturn(ps);
        when(ps.executeQuery()).thenThrow(new SQLException());

        //then
        try {
            target.getAllAddresses();
        } finally {
            verify(jdbcUtils, times(1)).closeResources(eq(con), eq(ps), eq(null));
        }
    }

    @Test
    public void getCount() throws SQLException, DataAccessException {
        //given
        String query = "select count(*) from addressentry";
        int count = 100500;
        AddressDao target = new AddressDaoImpl(ds, jdbcUtils);

        //when
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(eq(query))).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt(eq(1))).thenReturn(count);

        //then
        int retCount = target.getCount();

        verify(ps, times(1)).executeQuery();
        verify(con, never()).commit();
        verify(jdbcUtils, times(1)).closeResources(eq(con), eq(ps), eq(rs));

        assertEquals(count, retCount);
    }

    @Test(expected = DataAccessException.class)
    public void getCount_Exception() throws SQLException, DataAccessException {
        //given
        AddressDao target = new AddressDaoImpl(ds, jdbcUtils);

        //when
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(any())).thenReturn(ps);
        when(ps.executeQuery()).thenThrow(new SQLException());

        //then
        try {
            target.getCount();
        } finally {
            verify(jdbcUtils, times(1)).closeResources(eq(con), eq(ps), eq(null));
        }
    }

    @Test
    public void getAddressNamesTruncated() throws SQLException, DataAccessException {
        //given
        String query = "select substr(name, 0, ?) as name from addressentry";
        String name1 = "Name#6", name2 = "Name#7";
        int maxLength = 7;

        AddressDao target = new AddressDaoImpl(ds, jdbcUtils);

        //when
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(eq(query))).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getString(PERSON_NAME)).thenReturn(name1, name2);

        //then
        List<String> names = target.getAddressNamesTruncated(maxLength);

        verify(ps, times(1)).setInt(eq(1), eq(maxLength));
        verify(ps, times(1)).executeQuery();
        verify(rs, times(3)).next();
        verify(con, never()).commit();
        verify(jdbcUtils, times(1)).closeResources(eq(con), eq(ps), eq(rs));

        assertNotNull(names);
        assertEquals(2, names.size());

        assertEquals(name1, names.get(0));
        assertEquals(name2, names.get(1));
    }

    @Test(expected = DataAccessException.class)
    public void getAddressNamesTruncated_Exception() throws SQLException, DataAccessException {
        //given
        AddressDao target = new AddressDaoImpl(ds, jdbcUtils);

        //when
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(any())).thenReturn(ps);
        when(ps.executeQuery()).thenThrow(new SQLException());

        //then
        try {
            target.getAddressNamesTruncated(10);
        } finally {
            verify(jdbcUtils, times(1)).closeResources(eq(con), eq(ps), eq(null));
        }
    }

    @Test
    public void getAllAddressesPhoneStartsWith() throws SQLException, DataAccessException {
        //given
        String query = "select * from addressentry where phonenumber like ?";
        String name = "Name#10", name2 = "Name#20";
        String phoneNumber = "phoneNumber#10", phoneNumber2 = "phoneNumber#20";
        long id = 11111L, id2 = 22222L;
        String mask = "080";

        AddressDao target = new AddressDaoImpl(ds, jdbcUtils);

        //when
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(eq(query))).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getLong(PERSON_ID)).thenReturn(id, id2);
        when(rs.getString(PERSON_NAME)).thenReturn(name, name2);
        when(rs.getString(PERSON_PHONE_NUMBER)).thenReturn(phoneNumber, phoneNumber2);

        //then
        List<AddressEntity> list = target.getAllAddressesPhoneStartsWith(mask);

        verify(ps, times(1)).setString(eq(1), eq(mask + "%"));
        verify(ps, times(1)).executeQuery();
        verify(rs, times(3)).next();
        verify(con, never()).commit();
        verify(jdbcUtils, times(1)).closeResources(eq(con), eq(ps), eq(rs));

        assertNotNull(list);
        assertEquals(2, list.size());

        AddressEntity en = list.get(0);
        assertEquals(id, en.getId());
        assertEquals(name, en.getName());
        assertEquals(phoneNumber, en.getPhoneNumber());

        en = list.get(1);
        assertEquals(id2, en.getId());
        assertEquals(name2, en.getName());
        assertEquals(phoneNumber2, en.getPhoneNumber());
    }

    @Test(expected = DataAccessException.class)
    public void getAllAddressesPhoneStartsWith_Exception() throws SQLException, DataAccessException {
        //given
        String mask = "080";

        AddressDao target = new AddressDaoImpl(ds, jdbcUtils);

        //when
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(any())).thenReturn(ps);
        when(ps.executeQuery()).thenThrow(new SQLException());

        //then
        try {
            target.getAllAddressesPhoneStartsWith(mask);
        } finally {
            verify(jdbcUtils, times(1)).closeResources(eq(con), eq(ps), eq(null));
        }
    }
}