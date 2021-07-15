package ua.lviv.lgs.dao.impl;

import ua.lviv.lgs.dao.BucketDao;
import ua.lviv.lgs.domain.Bucket;
import ua.lviv.lgs.utils.ConnectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class BucketDaoImpl implements BucketDao {

    private static String READ_ALL = "select * from bucket";
    private static String CREATE = "insert into bucket ('user_id','product_id','purchase_date') values (?,?,?)";
    private static String READ_BY_ID = "select * from bucket where id = ?";
    private static String DELETE_BY_ID = "delete from bucket where id - ?";

    private Connection connection;
    private PreparedStatement preparedStatement;

    public BucketDaoImpl() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        connection = ConnectionUtils.openConection();
    }

    @Override
    public Bucket create(Bucket bucket) {
        try {
            preparedStatement = connection.prepareStatement(CREATE,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,bucket.getUser_id());
            preparedStatement.setInt(2,bucket.getProduct_id());
            preparedStatement.setDate(3,new Date(bucket.getPurchase_date().getTime()));
            preparedStatement.executeUpdate();

            ResultSet result = preparedStatement.getGeneratedKeys();
            bucket.setId(result.getInt(1));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bucket;
    }

    @Override
    public Bucket read(Integer id) {
        Bucket bucket = null;
        try {
            preparedStatement = connection.prepareStatement(READ_BY_ID);
            preparedStatement.setInt(1,id);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            Integer bucket_id = result.getInt("id");
            Integer user_id = result.getInt("user_id");
            Integer product_id = result.getInt("product_id");
            java.util.Date purchase_date = result.getDate("purchase_date");

            bucket = new Bucket(bucket_id,user_id,product_id,purchase_date);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bucket;
    }

    @Override
    public Bucket update(Bucket bucket) {
        throw new IllegalStateException("there is no update for bucket");
    }

    @Override
    public void delete(Integer id){

        try {
            preparedStatement = connection.prepareStatement(DELETE_BY_ID);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Bucket> readAll(){
        List<Bucket> bucketsRecords = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(READ_ALL);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()){
                Integer bucket_id = result.getInt("id");
                Integer user_id = result.getInt("user_id");
                Integer product_id = result.getInt("product_id");
                java.util.Date purchase_date = result.getDate("purchase_date");

                bucketsRecords.add(new Bucket(bucket_id,user_id,product_id,purchase_date));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bucketsRecords;
    }
}
