package com.zconly.pianocourse.bean;

/**
 * 用户信息
 * Created by dengbin
 */
public class UserBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    private long id;                                // 主键
    private String phone;                           // 手机号
    private String mail;                            // 邮箱
    private int type;                               // 用户类型
    private String name;
    private String avatar;
    private int sex;
    private long birthDay;
    private String interests;                       // 兴趣数组
    private int nationality;                        // 国籍
    private String signature;                       // 签名
    private int relstat;                            // 关系状态
    private String remark;
    private String languages;
    private boolean status;                         // 是否认证
    private String area;                            // 区域，逗号分隔
    private long ctime;                             // 创建时间
    private long mtime;                             // 修改时间
    private String token;
    private int inContacts;                         // 是否在通讯录

    private String imtoken;                         // 云信token

    // 本地显示使用
    private int viewType;

    public String getImtoken() {
        return imtoken;
    }

    public void setImtoken(String imtoken) {
        this.imtoken = imtoken;
    }

    public int getInContacts() {
        return inContacts;
    }

    public void setInContacts(int inContacts) {
        this.inContacts = inContacts;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public long getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(long birthDay) {
        this.birthDay = birthDay;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public int getNationality() {
        return nationality;
    }

    public void setNationality(int nationality) {
        this.nationality = nationality;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getRelstat() {
        return relstat;
    }

    public void setRelstat(int relstat) {
        this.relstat = relstat;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public long getMtime() {
        return mtime;
    }

    public void setMtime(long mtime) {
        this.mtime = mtime;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserBean))
            return false;
        return getId() == ((UserBean) o).getId();
    }

    // 聊天ID
    public String getAccount() {
        return String.valueOf(getId());
    }
}
