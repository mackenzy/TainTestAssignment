package se.tain.businesslogic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import se.tain.dal.dao.AddressDao;
import se.tain.entity.AddressEntity;
import se.tain.error.DataAccessException;

import java.util.List;

public class AddressBook {

    static final String S070 = "070";

    private final AddressDao addressDao;

    public AddressBook(AddressDao addressDao){
        this.addressDao = addressDao;
    }

    public boolean hasMobile(String name) throws DataAccessException {

        AddressEntity addressEntity = addressDao.getAddressByName(name);

        if (addressEntity == null) {
            return false;
        }

        String phoneNumber = addressEntity.getPhoneNumber();

        return phoneNumber != null && phoneNumber.startsWith(S070);
    }

    public int getBookSize() throws DataAccessException {
        return addressDao.getCount();
    }

    @Nullable
    public String getMobileNumberByName(String name) throws DataAccessException {

        AddressEntity addressEntity = addressDao.getAddressByName(name);

        if(addressEntity == null){
            return null;
        }

        String phoneNumber = addressEntity.getPhoneNumber();

        if(phoneNumber == null){
            return null;
        }

        if(phoneNumber.startsWith(S070)){
            return phoneNumber;
        }

        return null;
    }

    @NotNull
    public List<String> getAllTruncatedNames(int truncate2length) throws DataAccessException {
        return addressDao.getAddressNamesTruncated(truncate2length);
    }

    @NotNull
    public List<AddressEntity> getMobilePhoneOwners() throws DataAccessException {
        return addressDao.getAllAddressesPhoneStartsWith(S070);
    }

}
