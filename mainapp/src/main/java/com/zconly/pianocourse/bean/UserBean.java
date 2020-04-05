package com.zconly.pianocourse.bean;

/**
 * 用户信息
 * Created by dengbin
 */
public class UserBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    private long birthday;
    private long duration;
    private int examlevel;
    private long id;                                // 主键
    private String mobile;                          // 手机号
    private String nickname;
    private int role_id; // 0未知 1管理员 2学生 3老师
    private int sex;
    private String signature;                       // 签名
    private int status;                         // 是否认证
    private String avatar;
    private String wx_avatar;

    private String mail;                            // 邮箱
    private int type;                               // 用户类型
    private int relstat;                            // 关系状态
    private String remark;
    private long ctime;                             // 创建时间
    private long mtime;                             // 修改时间
    private String token;
    private int inContacts;                         // 是否在通讯录

    // 本地显示使用
    private int viewType;


    public String getWx_avatar() {
        return wx_avatar;
    }

    public void setWx_avatar(String wx_avatar) {
        this.wx_avatar = wx_avatar;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getExamlevel() {
        return examlevel;
    }

    public void setExamlevel(int examlevel) {
        this.examlevel = examlevel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getInContacts() {
        return inContacts;
    }

    public void setInContacts(int inContacts) {
        this.inContacts = inContacts;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserBean))
            return false;
        return getId() == ((UserBean) o).getId();
    }

    public static class UserResult extends BaseBean {

        private UserBean data;

        public UserBean getData() {
            return data;
        }

        public void setData(UserBean data) {
            this.data = data;
        }
    }

}
