package se.tain.businesslogic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import se.tain.dal.dao.AddressDao;
import se.tain.entity.AddressEntity;
import se.tain.error.DataAccessException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static se.tain.businesslogic.AddressBook.S070;

@RunWith(MockitoJUnitRunner.class)
public class AddressBookTest {

    @Mock private AddressDao addressDao;

    @Test
    public void hasMobile() throws DataAccessException {
        //given
        String name = "name$1";

        AddressBook target = new AddressBook(addressDao);

        //when
        when(addressDao.getAddressByName(eq(name))).thenReturn(new AddressEntity(1234567, name, S070 + "1111122"));

        //then
        assertTrue(target.hasMobile(name));

        verify(addressDao, times(1)).getAddressByName(eq(name));
    }

    @Test
    public void hasMobile_negate() throws DataAccessException {
        //given
        String name = "name$1";

        AddressBook target = new AddressBook(addressDao);

        //when
        when(addressDao.getAddressByName(eq(name))).thenReturn(new AddressEntity(1234567, name, "1111122"));

        //then
        assertFalse(target.hasMobile(name));

        verify(addressDao, times(1)).getAddressByName(eq(name));
    }

    @Test
    public void hasMobile_null() throws DataAccessException {
        //given
        String name = "name$1";

        AddressBook target = new AddressBook(addressDao);

        //when
        when(addressDao.getAddressByName(eq(name))).thenReturn(null);

        //then
        assertFalse(target.hasMobile(name));

        verify(addressDao, times(1)).getAddressByName(eq(name));
    }

    @Test
    public void getBookSize() throws DataAccessException {
        //given
        int size = 100500;

        AddressBook target = new AddressBook(addressDao);

        //when
        when(addressDao.getCount()).thenReturn(size);

        //then
        assertEquals(size, target.getBookSize());

        verify(addressDao, times(1)).getCount();
    }

    @Test
    public void getMobileNumberByName() throws DataAccessException {
        //given
        String name = "name$2";
        String phoneNumber = S070 + "1112233";

        AddressBook target = new AddressBook(addressDao);

        //when
        when(addressDao.getAddressByName(eq(name))).thenReturn(new AddressEntity(1L, name, phoneNumber));

        //then
        assertEquals(phoneNumber, target.getMobileNumberByName(name));

        verify(addressDao, times(1)).getAddressByName(eq(name));
    }

    @Test
    public void getMobileNumberByName_negate() throws DataAccessException {
        //given
        String name = "name$3";
        String phoneNumber = "1112233";

        AddressBook target = new AddressBook(addressDao);

        //when
        when(addressDao.getAddressByName(eq(name))).thenReturn(new AddressEntity(1L, name, phoneNumber));

        //then
        assertEquals(null, target.getMobileNumberByName(name));

        verify(addressDao, times(1)).getAddressByName(eq(name));
    }

    @Test
    public void getMobileNumberByName_null() throws DataAccessException {
        //given
        String name = "name$4";

        AddressBook target = new AddressBook(addressDao);

        //when
        when(addressDao.getAddressByName(eq(name))).thenReturn(null);

        //then
        assertEquals(null, target.getMobileNumberByName(name));

        verify(addressDao, times(1)).getAddressByName(eq(name));
    }

    @Test
    public void getAllTruncatedNames() throws DataAccessException {
        //given
        int truncate2length = 1234567;
        AddressBook target = new AddressBook(addressDao);
        List<String> list = new ArrayList<>(0);

        //when
        when(addressDao.getAddressNamesTruncated(eq(truncate2length))).thenReturn(list);

        //then
        assertTrue(list == target.getAllTruncatedNames(truncate2length));

        verify(addressDao, times(1)).getAddressNamesTruncated(eq(truncate2length));
    }

    @Test
    public void getMobilePhoneOwners() throws DataAccessException {
        //given
        String startsWith = S070;
        AddressBook target = new AddressBook(addressDao);
        List<AddressEntity> list = new ArrayList<>(0);

        //when
        when(addressDao.getAllAddressesPhoneStartsWith(eq(startsWith))).thenReturn(list);

        //then
        assertTrue(list == target.getMobilePhoneOwners());

        verify(addressDao, times(1)).getAllAddressesPhoneStartsWith(eq(startsWith));
    }

}