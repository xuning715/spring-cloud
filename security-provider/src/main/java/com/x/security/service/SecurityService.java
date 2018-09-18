package com.x.security.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import com.x.framework.annotation.NotKey;
import com.x.security.dao.SecurityDao;
import com.x.security.manage.SecurityManage;
import com.x.security.model.Account;
import com.x.security.model.AccountRole;
import com.x.security.model.Resource;
import com.x.security.model.Application;
import com.x.security.model.Property;
import com.x.security.model.Role;
import com.x.security.model.RoleResource;
import com.x.security.model.SecurityAccount;
import com.x.security.rpc.SecurityRpcService;
import com.x.security.util.Constant;
import com.x.security.util.PasswordBuilder;
import com.x.framework.exception.BusinessException;
import com.x.framework.exception.ExceptionCode;
import com.x.framework.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.dubbo.config.annotation.Service;

@Service(interfaceName = "com.x.security.rpc.SecurityRpcService", timeout = 10000)
@Component
public class SecurityService extends BaseService implements SecurityRpcService {

    @Autowired
    private SecurityManage securityManage;

    @Autowired
    private SecurityDao securityDao;

    public SecurityService() {

    }

    @Override
    public String selectResourceUrlByApplication(Application application) {
        try {
            StringBuilder sb = new StringBuilder(Constant.blank);
            if (this.isString(application.getApplicationId())) {
                Resource resource = new Resource();
                resource.setApplicationId(application.getApplicationId());
                List<Resource> resourceList = securityDao.selectResourceList(resource);
                for (Resource r : resourceList) {
                    sb.append(r.getResourceUrl());
                    sb.append(Constant.space);
                }
            }
            return sb.toString();
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0000, e);
        }
    }

    @Override
    public SecurityAccount selectAccount(Account account, String applicationId, @NotKey String accountIp) {
        try {
            return securityManage.selectAccount(account, applicationId, accountIp);
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    @Override
    public boolean validateAccountPassword(String accountPasswordExpress, String accountPasswordEncryption) {
        try {
            if (PasswordBuilder.isPasswordValid(accountPasswordExpress, accountPasswordEncryption)) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }
    /**
     * 查询某一应用下所有资源并返回map
     *
     * @param applicationId
     *            String
     * @return Map public List<String> selectAllResourceUrlByApplication(String
     *         applicationId) { try { List<String> resourceUrlList = new
     *         ArrayList<String>(); List<Resource> list =
     *         securityManage.selectResource(applicationId); for (Resource
     *         resource : list) { if (Base.isString(resource.getResourceUrl()))
     *         { resourceUrlList.add(resource.getResourceUrl()); } } return
     *         resourceUrlList; } catch (Exception e) { throw new
     *         BusinessException(ExceptionCode.EXCEPTION_CODE0001, e); } }
     */

    /**
     * @param account Account
     * @return Account
     */
    @Override
    public Account insertAccount(Account account, String roleIds) {
        try {
            String password = account.getAccountPassword();
            if (password != null) {
                account.setAccountPassword(PasswordBuilder.encodePassword(password));
            }
            securityManage.insertAccount(account, roleIds);
            return account;
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    /**
     * @param account Account
     */
    @Override
    public void updateAccount(Account account) {
        try {
            securityManage.updateAccount(account);
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    @Override
    public int updateAccountByAccount(Account accountSet, Account accountWhere) {
        try {
            String password = accountSet.getAccountPassword();
            if (password != null) {
                accountSet.setAccountPassword(PasswordBuilder.encodePassword(password));
            }
            accountSet.setAccountModifyTime(this.getDate(0));
            return this.update(accountSet, accountWhere);
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    @Override
    public void deleteAccount(Account account) {
        try {
            securityManage.deleteAccount(account);
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    /**
     * @param account Account
     * @return int
     */
    @Override
    public int selectAccountNum(Account account) {
        try {
            return securityDao.selectAccountNum(account);
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    @Override
    public List<Account> selectAccountList(Account account) {
        try {
            return securityDao.selectAccountList(account);
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    @Override
    public void insertAccountProperty(Property property) {
        try {
            Date date = this.getDate(0);
            property.setPropertyCreateTime(date);
            property.setPropertyModifyTime(date);
            this.insert(property);
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    @Override
    public void updateAccountProperty(Property property) {
        try {
            if (this.isString(property.getAccountId()) && this.isString(property.getPropertyKey())) {
                property.setPropertyModifyTime(this.getDate(0));
                this.update(property);
            }
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    @Override
    public void deleteAccountProperty(Property property) {
        try {
            if (this.isString(property.getAccountId()) && this.isString(property.getPropertyKey())) {
                this.delete(property);
            }
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    @Override
    public List<Property> selectAccountPropertyList(Property property) {
        try {
            return securityDao.selectAccountPropertyList(property);
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    @Override
    public void insertAccountRole(AccountRole accountRole, String roleIds) {
        try {
            securityManage.insertAccountRole(accountRole, roleIds, this.getDate(0));
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    @Override
    public List<Role> selectAccountRoleList(AccountRole accountRole) {
        try {
            return securityDao.selectAccountRoleList(accountRole);
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    /**
     * 新建应用
     *
     * @param application Application
     * @return Application
     */
    @Override
    public Application insertApplication(Application application) {
        try {
            Date date = this.getDate(0);
            application.setApplicationCreateTime(date);
            application.setApplicationModifyTime(date);
            return this.insert(application);
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    /**
     * 更新应用
     *
     * @param application Application
     */
    @Override
    public void updateApplication(Application application) {
        try {
            if (this.isString(application.getApplicationId())) {
                application.setApplicationModifyTime(this.getDate(0));
                this.update(application);
            }
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    @Override
    public void deleteApplication(Application application) {
        try {
            if (this.isString(application.getApplicationId())) {
                this.delete(application);
            }
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    /**
     * 查询系统应用
     *
     * @return List
     */
    @Override
    public List<Application> selectApplicationList(Application application) {
        try {
            return securityDao.selectApplicationList(application);
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    /**
     * 查询系统应用
     *
     * @param application
     * @return
     */
    @Override
    public int selectApplicationNum(Application application) {
        try {
            return securityDao.selectApplicationNum(application);
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    /**
     * 新建资源
     *
     * @param resource Resource
     * @return Resource
     */
    @Override
    public Resource insertResource(Resource resource) {
        try {
            Date date = this.getDate(0);
            resource.setResourceCreateTime(date);
            resource.setResourceModifyTime(date);
            return this.insert(resource);
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    /**
     * 更新资源
     *
     * @param resource Resource
     */
    @Override
    public int updateResource(Resource resource) {
        try {
            if (this.isString(resource.getResourceId())) {
                resource.setResourceModifyTime(this.getDate(0));
                return this.update(resource);
            } else {
                return 0;
            }
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    @Override
    public void deleteResource(Resource resource) {
        try {
            if (this.isString(resource.getResourceId())) {
                this.delete(resource);
            }
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    /**
     * 返回所有的资源放在ApplicationList里
     *
     * @return List
     */
    @Override
    public List<Application> selectApplicationResourceList(Application application) {
        try {
            List<Application> applications = securityDao.selectApplicationList(application);
            List<Resource> list = securityDao.selectResourceList(new Resource());
            List<Resource> resources = new ArrayList<Resource>();
            for (Resource resource : list) {
                if (resource.getResourceParentId().equals(Constant.MENU_INIT_ID)) {
                    this.fitResource(resource, list);
                    resources.add(resource);
                }
            }
            for (int i = 0; i < applications.size(); i++) {
                for (int j = 0; j < resources.size(); j++) {
                    if (applications.get(i).getApplicationId().equals(resources.get(j).getApplicationId())) {
                        applications.get(i).getResourceList().add(resources.get(j));
                    }
                }
            }
            return applications;
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    /**
     * 返回所有的资源放在ResourceList里
     *
     * @param list List
     * @return List
     */
    private Resource fitResource(Resource resourceParent, List<Resource> list) {
        try {
            for (Resource resource : list) {
                if (resourceParent.getResourceId().equals(resource.getResourceParentId())) {
                    resourceParent.getResourceList().add(resource);
                    this.fitResource(resource, list);
                }
            }
            return resourceParent;
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    /**
     * 新建角色
     *
     * @param role Role
     * @return Role
     */
    @Override
    public Role insertRole(Role role) {
        try {
            Date date = this.getDate(0);
            role.setRoleCreateTime(date);
            role.setRoleModifyTime(date);
            return this.insert(role);
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    /**
     * 更新角色
     *
     * @param role Role
     */
    @Override
    public void updateRole(Role role) {
        try {
            if (role.getRoleId() != null) {
                role.setRoleModifyTime(this.getDate(0));
                this.update(role);
            }
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    @Override
    public void deleteRole(Role role) {
        try {
            if (role.getRoleId() != null) {
                securityManage.deleteRole(role);
            }
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    /**
     * 查询角色
     *
     * @return List
     */
    @Override
    public List<Role> selectRoleList(Role role) {
        try {
            return securityDao.selectRoleList(role);
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    /**
     * 查询角色
     *
     * @param role
     * @return
     */
    @Override
    public int selectRoleNum(Role role) {
        try {
            return securityDao.selectRoleNum(role);
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    @Override
    public void insertRoleResource(RoleResource roleResource, String resourceIds) {
        try {
            securityManage.insertRoleResource(roleResource, resourceIds);
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    @Override
    public List<Resource> selectResourceList(Resource resource) {
        try {
            return securityDao.selectResourceList(resource);
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

    @Override
    public List<String> selectRoleResourceList(RoleResource roleResource) {
        try {
            return securityDao.selectRoleResourceList(roleResource.getRoleId());
        } catch (Exception e) {
            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
        }
    }

}
