package se.tain.dal.dao;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import se.tain.entity.AddressEntity;
import se.tain.error.DataAccessException;

import java.util.List;

public interface AddressDao {

    void addAddress(@NotNull AddressEntity addressEntity) throws DataAccessException;

    @Nullable
    AddressEntity getAddressByName(@NotNull String name) throws DataAccessException;

    @NotNull
    List<AddressEntity> getAllAddresses() throws DataAccessException;

    int getCount() throws DataAccessException;

    @NotNull
    List<String> getAddressNamesTruncated(int maxLength) throws DataAccessException;

    @NotNull
    List<AddressEntity> getAllAddressesPhoneStartsWith(@NotNull String startsWith) throws DataAccessException;

}
