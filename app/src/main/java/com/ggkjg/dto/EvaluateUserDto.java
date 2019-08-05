package com.ggkjg.dto;

import java.io.Serializable;

/**
 * 评价
 */
public class EvaluateUserDto implements Serializable {
    //    {
//        "id": 29,
//            "user_id": 9,
//            "commented_type": "SMG\\Product\\Models\\Product",
//            "commented_id": 2,
//            "comment": "\u597d\u597d",
//            "rate": {
//        "default": "5"
//    },
//        "images": ["comment\/201812\/20\/cB4TvEVdoQqqPXj315LAV0cU6XbpSpLuNyRnejCO.jpeg",
//		"comment\/201812\/20\/SquvJ78olMxViKWsj10wZGSyF4gvflteRuSmQsO5.jpeg",
//		"comment\/201812\/20\/kZoE4a8BKewILbyF0ch1yLISy2h8LZYPvzZiASUn.jpeg"],
//            "created_at": "2019-01-02 12:06:53",
//            "flag": "90",
//            "user": {
//        "data": {
//            "id": 9,
//                    "name": "\u7f8e\u5986",
//                    "avatar": "avatar\/201901\/02\/D2iIGaDifnH8P52UzNCj8A2FHYir0zW8v3rfvoGh.jpeg",
//                    "phone": "138****8000",
//                    "sex": 0,
//                    "level": 1,
//                    "sn": "2018121314080762225"
//        }
//    }
//    }
    private long id;
    private String name;
    private String avatar;
    private String phone;
    private int sex;
    private int level;
    private String sn;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
