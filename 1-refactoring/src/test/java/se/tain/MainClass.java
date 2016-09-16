package se.tain;

import se.tain.businesslogic.AddressBook;
import se.tain.dal.dao.AddressDao;
import se.tain.dal.dao.AddressDaoImpl;
import se.tain.dal.ds.SimpleOracleDataSource;
import se.tain.entity.AddressEntity;
import se.tain.error.DataAccessException;
import se.tain.util.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainClass {

    public static void main(String[] args) throws DataAccessException {

        DataSource ds = new SimpleOracleDataSource(){
            public Connection getConnection() throws SQLException {
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.70.129:1521:orcl", "oracle", "oracle");
                con.setAutoCommit(false);
                return con;
            }
        };
        AddressDao dao = new AddressDaoImpl(ds, new JdbcUtils());
        AddressBook ab = new AddressBook(dao);

        dao.addAddress(new AddressEntity(0L, "John Smith", "0705555555"));
        dao.addAddress(new AddressEntity(0L, "Jesica ALba", "0715555555"));

        System.out.println(ab.hasMobile("John Smith"));
        System.out.println(ab.hasMobile("Jesica ALba"));


        System.out.println(ab.getBookSize());


        System.out.println(ab.getMobileNumberByName("John Smith"));
        System.out.println(ab.getMobileNumberByName("Jesica ALba"));

        ab.getAllTruncatedNames(6).forEach(System.out::println);
        ab.getMobilePhoneOwners().forEach(System.out::println);


    }
}
