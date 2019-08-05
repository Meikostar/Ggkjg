package com.ggkjg.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 评价
 */
public class EvaluateDto implements Serializable {
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
    private long user_id;
    private String commented_type;
    private long commented_id;
    private String comment;
    private List<String> images;
    private String created_at;
    private String flag;
    private DataDto<EvaluateUserDto> user;
    private DataDto<EvaluateCommentedDto>  commented;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getCommented_type() {
        return commented_type;
    }

    public void setCommented_type(String commented_type) {
        this.commented_type = commented_type;
    }

    public long getCommented_id() {
        return commented_id;
    }

    public void setCommented_id(long commented_id) {
        this.commented_id = commented_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public DataDto<EvaluateUserDto> getUser() {
        return user;
    }

    public void setUser(DataDto<EvaluateUserDto> user) {
        this.user = user;
    }

    public DataDto<EvaluateCommentedDto> getCommented() {
        return commented;
    }

    public void setCommented(DataDto<EvaluateCommentedDto> commented) {
        this.commented = commented;
    }
}
