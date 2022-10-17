package com.network.management.common.mysql;

import com.google.common.collect.Lists;
import com.network.management.common.mysql.bo.LocomotiveBo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

@Slf4j
public class JDBCClient {

    private static final String EPC_UE_IP_ADDR = "epcUeIpAddr";
    private static final String EPC_ENB_IP_ADDR = "epcEnbIpAddr";
    private static final String EPC_UC_STATE = "epcUeState";
    private static final String EPC_SUB_SCRIPTION_IMSI = "epcSubScriptionImsi";

    private static final Integer SOCKET_TIME_OUT =10000;
    private static final Integer READ_TIME_OUT = 10;


    /**
     * 获取数据库连接
     * @param url 数据库url
     * @param userName 用户名
     * @param passWord 密码
     * @return 数据库连接 {@link Connection}
     */
    private static Connection getConnection(String url, String userName, String passWord) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  //注册数据库驱动
            return DriverManager.getConnection(url, userName, passWord); //获取数据连接
        } catch (Exception e) {
            log.error("获取数据库连接失败url->{},userName->{}", url, userName, e);
        }
        return null;
    }

    /**
     * 执行机车查询语句
     * @param url 数据库url
     * @param userName 用户名称
     * @param passWord 密码
     * @param sql sql
     * @return 机车对象列表 {@link List<LocomotiveBo>}
     */
    public static List<LocomotiveBo> execute(String url, String userName, String passWord, String sql){
        List<LocomotiveBo> locomotiveBos = Lists.newArrayList();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            //获取数据连接
            connection = getConnection(url, userName, passWord);
            if(Objects.isNull(connection)){
                log.error("获取数据库连接失败url->{},userName->{}", url, userName);
                return locomotiveBos;
            }
            //设置网络超时时间
            connection.setNetworkTimeout(Executors.newSingleThreadExecutor(), SOCKET_TIME_OUT);
            //获取发送sql指令执行sql对象
            statement = connection.prepareStatement(sql);
            //设置查询超时时间
            statement.setQueryTimeout(READ_TIME_OUT);
            //返回查询结果集用于保存数据库查询内容
            rs = statement.executeQuery();
            //遍历结果集拿到数据
            while (rs.next()) {
                LocomotiveBo locomotiveBo = new LocomotiveBo(rs.getString(EPC_SUB_SCRIPTION_IMSI), rs.getString(EPC_UE_IP_ADDR), rs.getString(EPC_ENB_IP_ADDR), rs.getInt(EPC_UC_STATE));
                if(isValid(locomotiveBo)){
                    locomotiveBos.add(locomotiveBo);
                }
            }
        } catch (Exception e) {
            log.error("sql执行失败url->{},userName->{}", url, userName, e);
        }finally {
            //执行完数据库操作后记得关闭数据库连接资源
            closeQuietly(connection, statement, rs);
        }
        return locomotiveBos;
    }

    private static boolean isValid(LocomotiveBo locomotiveBo) {
        return StringUtils.isNotEmpty(locomotiveBo.getUeIp())
                && StringUtils.isNotEmpty(locomotiveBo.getENodeBIP())
                && Objects.nonNull(locomotiveBo.getStatus())
                && locomotiveBo.getStatus() != 0;
    }

    /**
     * 填充执行语句
     * @param statement {@link PreparedStatement}
     * @param ueIps 机车ip列表
     */
    private static void fillStatement(PreparedStatement statement, List<String> ueIps) throws Exception {
        if(Objects.isNull(statement) || CollectionUtils.isEmpty(ueIps)){
            log.warn("PreparedStatement is null or up ip empty.");
            return;
        }
        for(int i=0; i< ueIps.size(); i++){
            statement.setString(i, ueIps.get(i));
        }
    }

    /**
     * 关闭sql对象
     * @param connection 连接对象 {@link Connection}
     * @param statement sql执行对象 {@link Statement}
     * @param rs sql执行结果对象 {@link ResultSet}
     */
    private static void closeQuietly(Connection connection, Statement statement, ResultSet rs) {
        try{
            if(Objects.nonNull(rs)){
                rs.close();
            }
            if(Objects.nonNull(statement)){
                statement.close();
            }
            if(Objects.nonNull(connection)){
                connection.close();
            }
        }catch (Exception e){
            log.error("数据连接关闭失败", e);
        }
    }
}