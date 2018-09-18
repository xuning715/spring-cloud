package com.x.security.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.x.security.model.Account;
import com.x.security.model.AccountRole;
import com.x.security.model.Application;
import com.x.security.model.Property;
import com.x.security.model.Resource;
import com.x.security.model.Role;
import com.x.security.util.PasswordBuilder;
import com.x.framework.dao.BaseDao;

@Repository
public class SecurityDao extends BaseDao {
    
    public SecurityDao() {
    }

    /**
     * 查询账户权限资源
     * @param accountId int 账户id
     * @param applicationId String 应用id
     * @return List
     */
    private final static String selectResourceByAccountApplicationSql = "select distinct t4.* from " +
            "SECURITY_ACCOUNT_ROLE  t2, " +
            "SECURITY_ROLE_RESOURCE t3, " +
            "SECURITY_RESOURCE      t4 " +
            "where t4.APPLICATION_ID = ? " +
            "and t4.RESOURCE_STATE = 1 " +
            "and t3.RESOURCE_ID = t4.RESOURCE_ID " +
            "and t2.ROLE_ID = t3.ROLE_ID " +
            "and t2.ACCOUNT_ID = ? " +
            "order by t4.RESOURCE_SEQ ";
    public List<Resource> selectResourceByAccountApplication(String accountId, String applicationId) throws Exception {
        Object[] params = {applicationId, accountId};
        return this.queryForList(selectResourceByAccountApplicationSql, Resource.class, params);
    }

    /**
     * 查询账户数量
     * @param account Account
     * @return int
     */
    private final static String selectAccountNumSql = "select count(t1.ACCOUNT_ID) as num from SECURITY_ACCOUNT t1 where t1.ACCOUNT_ID is not null ";
    public int selectAccountNum(Account account) {
        StringBuilder sb = new StringBuilder(selectAccountNumSql);
        List params = new ArrayList();
        if (this.isString(account.getAccountId())) {
            sb.append("and t1.ACCOUNT_ID = ? ");
            params.add(account.getAccountId());
        }
        if (this.isString(account.getAccountName())) {
            sb.append("and t1.ACCOUNT_NAME = ? ");
            params.add(account.getAccountName());
        }
        if (this.isString(account.getAccountPassword())) {
            sb.append("and t1.ACCOUNT_PASSWORD = ? ");
            params.add(PasswordBuilder.encodePassword(account.getAccountPassword()));
        }
        if (this.isString(account.getAccountRemark())) {
            sb.append("and t1.ACCOUNT_REMARK like ? ");
            params.add("%" + account.getAccountRemark() + "%");
        }
        if (account.getAccountState() != null) {
            sb.append("and t1.ACCOUNT_STATE = ? ");
            params.add(account.getAccountState());
        }
        return this.queryForInt(sb.toString(), params);
    }

    /**
     * 查询账户
     * @param account Account
     * @param pageIndex int
     * @param pageSize int
     * @return List
     */
    private final static String selectAccountListSql = "select t1.* from SECURITY_ACCOUNT t1 where t1.ACCOUNT_ID is not null ";
    public List<Account> selectAccountList(Account account) throws Exception {
        StringBuilder sb = new StringBuilder(selectAccountListSql);
        List params = new ArrayList();
        if (this.isString(account.getAccountId())) {
            sb.append("and t1.ACCOUNT_ID = ? ");
            params.add(account.getAccountId());
        }
        if (this.isString(account.getAccountName())) {
            sb.append("and t1.ACCOUNT_NAME = ? ");
            params.add(account.getAccountName());
        }
        if (this.isString(account.getAccountPassword())) {
            sb.append("and t1.ACCOUNT_PASSWORD = ? ");
            params.add(PasswordBuilder.encodePassword(account.getAccountPassword()));
        }
        if (this.isString(account.getAccountRemark())) {
            sb.append("and t1.ACCOUNT_REMARK like ? ");
            params.add("%" + account.getAccountRemark() + "%");
        }
        if (account.getAccountState() != null) {
            sb.append("and t1.ACCOUNT_STATE = ? ");
            params.add(account.getAccountState());
        }
        return this.queryForList(sb.toString(), Account.class, params, account);
    }

    /**
     * 查询账户扩展属性
     * @param property Property
     * @return List
     */
    private final static String selectAccountPropertyListSql = "select t1.* from SECURITY_ACCOUNT_PROPERTY t1 where t1.ACCOUNT_ID is not null ";
    public List<Property> selectAccountPropertyList(Property property) throws Exception {
        StringBuilder sb = new StringBuilder(selectAccountPropertyListSql);
        List params = new ArrayList();
        if (this.isString(property.getAccountId())) {
            sb.append("and t1.ACCOUNT_ID = ? ");
            params.add(property.getAccountId());
        }
        if (this.isString(property.getPropertyKey())) {
            sb.append("and t1.PROPERTY_KEY = ? ");
            params.add(property.getPropertyKey());
        }
        if (this.isString(property.getPropertyValue())) {
            sb.append("and t1.PROPERTY_VALUE = ? ");
            params.add(property.getPropertyValue());
        }

        return this.queryForList(sb.toString(), Property.class, params);
    }

    /**
     * 查询账户角色
     * @param accountId int
     * @return List
     */
    private final static String selectAccountRoleListSql = "select t2.* from SECURITY_ACCOUNT_ROLE t1, SECURITY_ROLE t2 where t1.ACCOUNT_ID = ? and t1.ROLE_ID = t2.ROLE_ID";
    public List<Role> selectAccountRoleList(AccountRole accountRole) throws Exception {
        Object[] params = {accountRole.getAccountId()};
        return this.queryForList(selectAccountRoleListSql, Role.class, params);
    }

    /**
     * 查询系统应用
     * @param application
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws SQLException
     */
    private final static String selectApplicationListSql = "select * from SECURITY_APPLICATION t1 where t1.APPLICATION_NAME IS NOT NULL ";
    public List<Application> selectApplicationList(Application application) throws Exception {
        StringBuilder sb = new StringBuilder(selectApplicationListSql);
        List params = new ArrayList();
        if (this.isString(application.getApplicationId())) {
            sb.append("AND t1.APPLICATION_ID = ? ");
            params.add(application.getApplicationId());
        }
        if (this.isString(application.getApplicationName())) {
            sb.append("AND t1.APPLICATION_NAME = ? ");
            params.add(application.getApplicationName());
        }
        sb.append(" order by t1.application_id");
        return this.queryForList(sb.toString(), Application.class, params, application);
    }

    /**
     * 查询系统应用
     * @param application
     * @return
     * @throws SQLException
     */
    private final static String selectApplicationNumSql = "SELECT COUNT(1) AS NUM FROM SECURITY_APPLICATION T1 WHERE T1.APPLICATION_NAME IS NOT NULL ";
    public int selectApplicationNum(Application application) throws SQLException {
        StringBuilder sb = new StringBuilder(selectApplicationNumSql);
        List params = new ArrayList();
        if (this.isString(application.getApplicationName())) {
            sb.append("AND t1.APPLICATION_NAME = ? ");
            params.add(application.getApplicationName());
        }
        return this.queryForInt(sb.toString(), params);
    }

    /**
     * 通过上级资源id和系统id查询资源
     * @param applicationId String
     * @param resourceParentId int
     * @return List
     */
    private final static String selectResourceListSql = "select * from SECURITY_RESOURCE where RESOURCE_ID > 1 ";
    public List<Resource> selectResourceList(Resource resource) throws Exception {
        StringBuilder sb = new StringBuilder(selectResourceListSql);
        List params = new ArrayList();
        if (this.isString(resource.getApplicationId())) {
            sb.append("and APPLICATION_ID = ? ");
            params.add(resource.getApplicationId());
        }
        if (this.isString(resource.getResourceParentId())) {
            sb.append("and RESOURCE_PARENT_ID = ? ");
            params.add(resource.getResourceParentId());
        }
        if (this.isString(resource.getResourceId())) {
            sb.append("and RESOURCE_ID = ? ");
            params.add(resource.getResourceId());
        }
        sb.append("order by RESOURCE_SEQ");
        return this.queryForList(sb.toString(), Resource.class, params);
    }
  
    /**
     * 查询角色
     * @param role
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws SQLException
     */
    private final static String selectRoleListSql = "select * from SECURITY_ROLE t1 where t1.ROLE_NAME IS NOT NULL ";
    public List<Role> selectRoleList(Role role) throws Exception {
        StringBuilder sb = new StringBuilder(selectRoleListSql);
        List params = new ArrayList();
        if (this.isString(role.getRoleName())) {
            sb.append("AND t1.ROLE_NAME = ? ");
            params.add(role.getRoleName());
        }
        
        return this.queryForList(sb.toString(), Role.class, params, role);
    }
    
    /**
     * 查询角色
     * @param role
     * @return
     * @throws SQLException
     */
    private final static String selectRoleNumSql = "SELECT COUNT(1) AS NUM FROM SECURITY_ROLE T1 WHERE T1.ROLE_NAME IS NOT NULL ";
    public int selectRoleNum(Role role) throws SQLException {
        StringBuilder sb = new StringBuilder(selectRoleNumSql);
        List params = new ArrayList();
        if (this.isString(role.getRoleName())) {
            sb.append("AND T1.ROLE_NAME = ? ");
            params.add(role.getRoleName());
        }
        return this.queryForInt(sb.toString(), params);
    }

    /**
     * 查询角色资源
     * @param roleId int
     * @return List
     */
    private final static String selectRoleResourceListSql = "select * from SECURITY_ROLE_RESOURCE where ROLE_ID = ?";
    public List<String> selectRoleResourceList(int roleId) {
        Object[] params = {roleId};
        RowMapper<String> mapper = new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
//                RoleResource roleResource = new RoleResource();
//                roleResource.setRoleId(rs.getInt("ROLE_ID"));
//                roleResource.setResourceId(rs.getInt("RESOURCE_ID"));
//                roleResource.setResourceSeq(rs.getInt("RESOURCE_SEQ"));
                return rs.getString("RESOURCE_ID");
            }
        };
        return this.queryForList(selectRoleResourceListSql, mapper, params);
    }
    
}
