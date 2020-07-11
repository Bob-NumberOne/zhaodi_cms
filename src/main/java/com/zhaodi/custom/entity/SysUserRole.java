package com.zhaodi.custom.entity;

public class SysUserRole {
    private Integer num;//人员数量
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user_role.id
     *
     * @mbg.generated Thu May 28 10:59:41 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user_role.userId
     *
     * @mbg.generated Thu May 28 10:59:41 CST 2020
     */
    private Long userid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user_role.roleId
     *
     * @mbg.generated Thu May 28 10:59:41 CST 2020
     */
    private Long roleid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user_role.creater
     *
     * @mbg.generated Thu May 28 10:59:41 CST 2020
     */
    private String creater;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user_role.creatTime
     *
     * @mbg.generated Thu May 28 10:59:41 CST 2020
     */
    private String creattime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user_role.creatName
     *
     * @mbg.generated Thu May 28 10:59:41 CST 2020
     */
    private String creatname;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user_role.id
     *
     * @return the value of sys_user_role.id
     *
     * @mbg.generated Thu May 28 10:59:41 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user_role.id
     *
     * @param id the value for sys_user_role.id
     *
     * @mbg.generated Thu May 28 10:59:41 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user_role.userId
     *
     * @return the value of sys_user_role.userId
     *
     * @mbg.generated Thu May 28 10:59:41 CST 2020
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user_role.userId
     *
     * @param userid the value for sys_user_role.userId
     *
     * @mbg.generated Thu May 28 10:59:41 CST 2020
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user_role.roleId
     *
     * @return the value of sys_user_role.roleId
     *
     * @mbg.generated Thu May 28 10:59:41 CST 2020
     */
    public Long getRoleid() {
        return roleid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user_role.roleId
     *
     * @param roleid the value for sys_user_role.roleId
     *
     * @mbg.generated Thu May 28 10:59:41 CST 2020
     */
    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user_role.creater
     *
     * @return the value of sys_user_role.creater
     *
     * @mbg.generated Thu May 28 10:59:41 CST 2020
     */
    public String getCreater() {
        return creater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user_role.creater
     *
     * @param creater the value for sys_user_role.creater
     *
     * @mbg.generated Thu May 28 10:59:41 CST 2020
     */
    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user_role.creatTime
     *
     * @return the value of sys_user_role.creatTime
     *
     * @mbg.generated Thu May 28 10:59:41 CST 2020
     */
    public String getCreattime() {
        return creattime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user_role.creatTime
     *
     * @param creattime the value for sys_user_role.creatTime
     *
     * @mbg.generated Thu May 28 10:59:41 CST 2020
     */
    public void setCreattime(String creattime) {
        this.creattime = creattime == null ? null : creattime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user_role.creatName
     *
     * @return the value of sys_user_role.creatName
     *
     * @mbg.generated Thu May 28 10:59:41 CST 2020
     */
    public String getCreatname() {
        return creatname;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user_role.creatName
     *
     * @param creatname the value for sys_user_role.creatName
     *
     * @mbg.generated Thu May 28 10:59:41 CST 2020
     */
    public void setCreatname(String creatname) {
        this.creatname = creatname == null ? null : creatname.trim();
    }
}