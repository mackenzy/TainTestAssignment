package se.tain.dal.dao;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import se.tain.entity.AddressEntity;
import se.tain.error.DataAccessException;
import se.tain.util.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AddressDaoImpl implements AddressDao {

    static final String PERSON_ID = "id";
    static final String PERSON_NAME = "name";
    static final String PERSON_PHONE_NUMBER = "phoneNumber";

    private final DataSource dataSource;
    private final JdbcUtils jdbcUtils;

    public AddressDaoImpl(DataSource dataSource,
                          JdbcUtils jdbcUtils) {
        this.dataSource = dataSource;
        this.jdbcUtils = jdbcUtils;
    }

    @Override
    public void addAddress(@NotNull AddressEntity addressEntity) throws DataAccessException {

        Connection con = null; PreparedStatement ps = null;

        try {
            con = dataSource.getConnection();

            ps = con.prepareStatement("insert into addressentry values (addressentry_id_seq.nextval, ?, ?)");
            ps.setString(1, addressEntity.getName());
            ps.setString(2, addressEntity.getPhoneNumber());
            ps.executeUpdate();

            con.commit();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            jdbcUtils.closeResources(con, ps);
        }
    }

    @Override
    @Nullable
    public AddressEntity getAddressByName(@NotNull String name) throws DataAccessException {

        Connection con = null; PreparedStatement ps = null; ResultSet rs = null;

        try {
            con = dataSource.getConnection();

            ps = con.prepareStatement("select * from addressentry where name = ?");
            ps.setString(1, name);
            rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }

            return new AddressEntity(
                rs.getLong(PERSON_ID),
                rs.getString(PERSON_NAME),
                rs.getString(PERSON_PHONE_NUMBER)
            );

        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            jdbcUtils.closeResources(con, ps, rs);
        }
    }

    @Override
    @NotNull
    public List<AddressEntity> getAllAddresses() throws DataAccessException {

        Connection con = null; PreparedStatement ps = null; ResultSet rs = null;

        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement("select * from addressentry");
            rs = ps.executeQuery();

            List<AddressEntity> entries = new ArrayList<>();

            while (rs.next()) {
                entries.add(
                    new AddressEntity(
                        rs.getLong(PERSON_ID),
                        rs.getString(PERSON_NAME),
                        rs.getString(PERSON_PHONE_NUMBER)
                    )
                );
            }

            return entries;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            jdbcUtils.closeResources(con, ps, rs);
        }
    }

    @Override
    public int getCount() throws DataAccessException {

        Connection con = null; PreparedStatement ps = null; ResultSet rs = null;

        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement("select count(*) from addressentry");
            rs = ps.executeQuery();

            if (!rs.next()) {
                throw new DataAccessException("Smth went wrong. ResultSet is empty");
            }

            return rs.getInt(1);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            jdbcUtils.closeResources(con, ps, rs);
        }

    }

    @Override
    @NotNull
    public List<String> getAddressNamesTruncated(int maxLength) throws DataAccessException {

        Connection con = null; PreparedStatement ps = null; ResultSet rs = null;

        try {
            con = dataSource.getConnection();

            ps = con.prepareStatement("select substr(name, 0, ?) as name from addressentry");
            ps.setInt(1, maxLength);
            rs = ps.executeQuery();

            List<String> names = new ArrayList<>();

            while (rs.next()) {
                names.add(rs.getString(PERSON_NAME));
            }

            return names;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            jdbcUtils.closeResources(con, ps, rs);
        }
    }

    @Override
    @NotNull
    public List<AddressEntity> getAllAddressesPhoneStartsWith(@NotNull String startsWith) throws DataAccessException {

        Connection con = null; PreparedStatement ps = null; ResultSet rs = null;

        try {
            con = dataSource.getConnection();

            ps = con.prepareStatement("select * from addressentry where phonenumber like ?");
            ps.setString(1, startsWith.concat("%"));

            rs = ps.executeQuery();

            List<AddressEntity> entries = new ArrayList<>();

            while (rs.next()) {
                entries.add(
                    new AddressEntity(
                        rs.getLong(PERSON_ID),
                        rs.getString(PERSON_NAME),
                        rs.getString(PERSON_PHONE_NUMBER)
                    )
                );
            }

            return entries;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            jdbcUtils.closeResources(con, ps, rs);
        }
    }
}
