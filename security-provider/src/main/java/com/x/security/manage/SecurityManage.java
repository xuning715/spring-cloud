package com.x.security.manage;

import java.util.*;

import com.x.security.dao.SecurityDao;
import com.x.security.model.Account;
import com.x.security.model.Resource;
import com.x.security.model.Property;
import com.x.security.model.Application;
import com.x.security.model.Role;

import com.x.security.model.AccountLog;
import com.x.security.model.AccountRole;
import com.x.security.model.RoleResource;
import com.x.security.model.SecurityAccount;
import com.x.security.util.Constant;
import com.x.security.util.PasswordBuilder;
import com.x.framework.manage.BaseManage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityManage extends BaseManage {

    @Autowired
    private SecurityDao securityDao;

    public SecurityManage() {
    }


    /**
     * 查询账户、账户扩展属性、账户下的资源
     *
     * @param account       Account
     * @param applicationId String
     * @return Account
     * @throws Exception
     */
    public SecurityAccount selectAccount(Account account, String applicationId, String accountIp) throws Exception {
        SecurityAccount securityAccount = null;
        String accountId = account.getAccountId();
        String accountName = account.getAccountName();
        String accountPasswordExpress = account.getAccountPassword();
        if (!this.isNotNull(accountId) && !this.isNotNull(accountName) && !this.isNotNull(accountPasswordExpress)) {
            return securityAccount;
        }
        if (this.isNotNull(accountName) && !this.isNotNull(accountPasswordExpress)) {
            return securityAccount;
        }
        account.setAccountPassword(null);
        List<Account> list = securityDao.selectAccountList(account);
        if (list != null && list.size() > 0) {
            account = list.get(0);
            if (this.isNotNull(accountPasswordExpress)) {
                String accountPassword = account.getAccountPassword();
                if (!PasswordBuilder.isPasswordValid(accountPasswordExpress, accountPassword)) {
                    return securityAccount;
                }
            }
            accountId = account.getAccountId();
            accountName = account.getAccountName();
            // 获取账户角色
            AccountRole accountRole = new AccountRole();
            accountRole.setAccountId(accountId);
            List<Role> roleList = securityDao.selectAccountRoleList(accountRole);
            // 获取账户资源
            List<Resource> resourceList = securityDao.selectResourceByAccountApplication(accountId, applicationId);

            securityAccount = new SecurityAccount();
            securityAccount.setAccountId(accountId);
            securityAccount.setAccountName(accountName);
            securityAccount.setAccountRemark(account.getAccountRemark());
            securityAccount.setRoleList(roleList);
            securityAccount.setResourceList(resourceList);
            securityAccount.setApplicationId(applicationId);
            if (resourceList.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (Resource resource : resourceList) {
                    sb.append(resource.getResourceUrl());
                    sb.append(Constant.space);
                }
                if (sb.length() > 0) {
                    securityAccount.setResourceUrls(sb.toString());
                }
            }
            Application application = new Application();
            application.setApplicationId(applicationId);
            List<Application> applicationList = securityDao.selectApplicationList(application);
            if (applicationList.size() > 0) {
                application = applicationList.get(0);
                securityAccount.setApplication(application);
            }
            // 获取账户的扩展属性信息
            Property property = new Property();
            property.setAccountId(accountId);
            List<Property> propertyList = securityDao.selectAccountPropertyList(property);
            // 把账户下所有资源的一维List转化为Map
            Map<String, String> map = new HashMap<String, String>();
            for (Property pro : propertyList) {
                map.put(pro.getPropertyKey(), pro.getPropertyValue());
            }
            securityAccount.setAccountPropertyMap(map);

            //修改登录时间
            Date date = this.getDate(0);
            account = new Account();
            account.setAccountId(accountId);
            account.setAccountLoginTime(date);
            this.update(account);

            // 插入账户登录日志
            AccountLog accountLog = new AccountLog();
            accountLog.setAccountId(accountId);
            accountLog.setAccountName(accountName);
            accountLog.setAccountIp(accountIp);
            accountLog.setAccountLoginTime(date);
            this.insert(accountLog);
        }
        return securityAccount;
    }

    public void deleteAccount(Account account) throws Exception {
        String accountId = account.getAccountId();
        if (this.isString(accountId)) {
            AccountRole accountRole = new AccountRole();
            accountRole.setAccountId(accountId);
            this.delete(accountRole);
            this.delete(account);
        }
    }

    public Account insertAccount(Account account, String roleIds) throws Exception {
        Date date = this.getDate(0);
        account.setAccountCreateTime(date);
        account.setAccountModifyTime(date);
        account = this.insert(account);
        String accountId = account.getAccountId();
        Property property = new Property();
        property.setAccountId(accountId);
        property.setPropertyKey(Constant.ACCOUNT_NAME);
        property.setPropertyValue(account.getAccountName());
        property.setPropertyCreateTime(date);
        property.setPropertyModifyTime(date);
        this.insert(property);
        Map<String, String> propertyMap = account.getAccountPropertyMap();
        if (propertyMap != null) {
            for (Map.Entry<String, String> entry : propertyMap.entrySet()) {
                property.setPropertyKey(entry.getKey());
                property.setPropertyValue(entry.getValue());
                this.insert(property);
            }
        }
        if (this.isString(roleIds)) {
            AccountRole accountRole = new AccountRole();
            accountRole.setAccountId(accountId);
            this.insertAccountRole(accountRole, roleIds, date);
        }
        return account;
    }

    public void updateAccount(Account account) throws Exception {
        if (this.isString(account.getAccountId())) {
            String password = account.getAccountPassword();
            if (password != null) {
                account.setAccountPassword(PasswordBuilder.encodePassword(password));
            }
            account.setAccountModifyTime(this.getDate(0));
            this.update(account);

            Map<String, String> propertyMap = account.getAccountPropertyMap();
            if (propertyMap != null) {
                Property property = new Property();
                Date date = this.getDate(0);
                property.setAccountId(account.getAccountId());
                property.setPropertyModifyTime(date);
                property.setPropertyKey(Constant.ACCOUNT_NAME);
                property.setPropertyValue(account.getAccountName());
                this.update(property);
                for (Map.Entry<String, String> entry : propertyMap.entrySet()) {
                    property.setPropertyKey(entry.getKey());
                    property.setPropertyValue(entry.getValue());
                    this.update(property);
                }
            }
        }
    }

    public void insertAccountRole(AccountRole accountRole, String roleIds, Date accountRoleCreateTime) throws Exception {
        this.delete(accountRole);
        if (this.isString(roleIds)) {
            String[] array = roleIds.split(",");
            for (String roleId : array) {
                accountRole.setRoleId(Integer.parseInt(roleId));
                accountRole.setAccountRoleCreateTime(accountRoleCreateTime);
                this.insert(accountRole);
            }
        }
    }

    public void deleteRole(Role role) throws Exception {
        Integer roleId = role.getRoleId();
        if (roleId != null) {
            RoleResource roleResource = new RoleResource();
            roleResource.setRoleId(roleId);
            this.delete(roleResource);
            this.delete(role);
        }
    }

    public void insertRoleResource(RoleResource roleResource, String resourceIds) throws Exception {
        this.delete(roleResource);
        Date roleResourceCreateTime = this.getDate(0);
        if (this.isString(resourceIds)) {
            String[] array = resourceIds.split(",");
            int i = 1;
            for (String resourceId : array) {
                roleResource.setResourceId(resourceId);
                roleResource.setRoleResourceCreateTime(roleResourceCreateTime);
                roleResource.setResourceSeq(i++);
                this.insert(roleResource);
            }
        }
    }

}
