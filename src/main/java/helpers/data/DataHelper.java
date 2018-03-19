package helpers.data;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataHelper {
    private Connection connection = null;

    public DataHelper(String jndiStr) throws NamingException, SQLException, NullPointerException {
        InitialContext initialContext = null;
        DataSource dataSource = null;

        initialContext = new InitialContext();
        dataSource = (DataSource)initialContext.lookup(jndiStr);
        if (dataSource != null){
            connection = dataSource.getConnection();
        }else{
            throw new NullPointerException("DataSource for \"" + jndiStr + " is null.");
        }
    }



}
